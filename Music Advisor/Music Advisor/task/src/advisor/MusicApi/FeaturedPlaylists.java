package advisor.MusicApi;

import advisor.Authentication;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class FeaturedPlaylists extends MusicApi {

    ArrayList<Playlist> fPlaylists;

    int start;
    int end;
    int totalPages;
    int currentPage;

    public FeaturedPlaylists() {

        this.fPlaylists = new ArrayList<>();
    }

    void getFeaturedPlaylists() {

        String requestUri = API_LINK + "/v1/browse/featured-playlists";

        HttpRequest request = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + Authentication.ACCESS_TOKEN)
                .uri(URI.create(requestUri))
                .GET()
                .build();

        HttpClient client = HttpClient.newBuilder().build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonObject object = JsonParser.parseString(response.body())
                    .getAsJsonObject()
                    .getAsJsonObject("playlists");

            JsonArray array = object.getAsJsonArray("items");

            for (JsonElement item : array) {

                String url = item.getAsJsonObject()
                        .get("external_urls").getAsJsonObject()
                        .get("spotify").getAsString();

                String name = item.getAsJsonObject().get("name").getAsString();

                fPlaylists.add(new Playlist(name, url));
            }

            this.start = 0;
            this.end = Math.min(start + entriesPerPage, fPlaylists.size());
            this.totalPages = fPlaylists.size() % entriesPerPage == 0 ?
                    fPlaylists.size() / 5 :
                    fPlaylists.size() / 5 + 1;
            this.currentPage = 1;

        } catch (IOException | InterruptedException e) {
            System.out.println("Problem in handling featured playlist response.");
            e.printStackTrace();
        }
    }

    @Override
    public void obtain() {
        getFeaturedPlaylists();
        printList();
    }

    @Override
    void printList() {

        for (int i = start; i < end; i++) {

            Playlist current = fPlaylists.get(i);

            System.out.println(current.name);
            System.out.println(current.link);
            System.out.println();
        }
        System.out.printf("---PAGE %d OF %d---\n", currentPage, totalPages);
    }

    @Override
    public void next() {

        if (end == fPlaylists.size()) {
            System.out.println("No more pages.");
            return;
        }

        start = end;
        end = Math.min(start + entriesPerPage, fPlaylists.size());
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