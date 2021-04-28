package advisor.MusicApi;

import advisor.Authentication;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class NewReleases extends MusicApi {

    ArrayList<Album> newReleasesList;

    int start;
    int end;
    int totalPages;
    int currentPage;

    public NewReleases() {

        this.newReleasesList = new ArrayList<>();
    }

    void getNewReleases() {

        String requestUri = API_LINK + "/v1/browse/new-releases";

        HttpRequest request = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + Authentication.ACCESS_TOKEN)
                .uri(URI.create(requestUri))
                .GET()
                .build();
        HttpClient client = HttpClient.newBuilder().build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonArray array = JsonParser.parseString(response.body()).getAsJsonObject()
                    .get("albums").getAsJsonObject()
                    .getAsJsonArray("items");

            for (JsonElement item : array) {


                String albumName = item.getAsJsonObject().get("name").getAsString();
                String albumLink = item.getAsJsonObject()
                        .get("external_urls").getAsJsonObject()
                        .get("spotify").getAsString();

                ArrayList<String> artistList = new ArrayList<>();

                JsonArray artists = item.getAsJsonObject().get("artists").getAsJsonArray();

                for (JsonElement artist : artists) {
                    artistList.add(artist.getAsJsonObject().get("name").getAsString());
                }
                newReleasesList.add(new Album(albumName, artistList, albumLink));
            }

            this.start = 0;
            this.end = Math.min(start + entriesPerPage, newReleasesList.size());
            this.totalPages = newReleasesList.size() % entriesPerPage == 0 ?
                    newReleasesList.size() / entriesPerPage :
                    newReleasesList.size() / entriesPerPage + 1;
            this.currentPage = 1;

        } catch (IOException | InterruptedException e) {
            System.out.println("Problem in handling new releases response.");
            e.printStackTrace();
        }
    }

    @Override
    public void obtain() {
        getNewReleases();
        printList();
    }

    @Override
    void printList() {

        for (int i = start; i < end; i++) {

            Album current = newReleasesList.get(i);

            System.out.println(current.name);
            System.out.println(current.artists);
            System.out.println(current.link);
            System.out.println();
        }
        System.out.printf("---PAGE %d OF %d---\n", currentPage, totalPages);
    }

    @Override
    public void next() {

        if (end == newReleasesList.size()) {
            System.out.println("No more pages.");
            return;
        }

        start = end;
        end = Math.min(start + entriesPerPage, newReleasesList.size());
        currentPage++;
        printList();
    }

    @Override
    public void prev() {

        if (start == 0) {
            System.out.println("No more pages.");
            return;
        }

        end = start;
        start = end - entriesPerPage;
        currentPage--;
        printList();
    }
}
