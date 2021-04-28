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

public class CategorizedPlaylists extends MusicApi {

    ArrayList<Playlist> playlists;
    String categoryName;

    int start;
    int end;
    int totalPages;
    int currentPage;

    public CategorizedPlaylists(String categoryName) {

        this.playlists = new ArrayList<>();
        this.categoryName = categoryName;

    }

    boolean getSelectedPlaylists() {

        if (!categoriesObtained) {
            Category category = new Category();
            category.getAllCategories();
        }

        if (!categoryNameAndId.containsKey(categoryName)) {
            System.out.println("Unknown category name.");
            return false;
        }

        String categoryID = categoryNameAndId.get(categoryName);
        String requestUri = API_LINK + "/v1/browse/categories/" + categoryID + "/playlists";

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + Authentication.ACCESS_TOKEN)
                .uri(URI.create(requestUri))
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newBuilder().build();

        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.body().contains("error") && response.body().contains("404")) {

                JsonObject object = JsonParser.parseString(response.body()).getAsJsonObject();
                String message = object.get("error").getAsJsonObject()
                        .get("message").getAsString();
                System.out.println(message);
                return false;
            }

            JsonObject object = JsonParser.parseString(response.body()).getAsJsonObject()
                    .get("playlists").getAsJsonObject();

            JsonArray array = object.get("items").getAsJsonArray();

            for (JsonElement item : array) {

                String name = item.getAsJsonObject().get("name").getAsString();
                String link = item.getAsJsonObject().get("external_urls").getAsJsonObject()
                        .get("spotify").getAsString();

                playlists.add(new Playlist(name, link));
            }

            this.start = 0;
            this.end = Math.min(start + entriesPerPage, playlists.size());
            this.totalPages = playlists.size() % entriesPerPage == 0 ?
                    playlists.size() / 5 :
                    playlists.size() / 5 + 1;
            this.currentPage = 1;

        } catch (IOException | InterruptedException e) {
            System.out.println("Problem in handling categorised playlist response.");
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void obtain() {

        boolean isObtained = getSelectedPlaylists();

        if (isObtained) {
            printList();
        }
    }

    @Override
    public void next() {

        if (end == playlists.size()) {
            System.out.println("No more pages.");
            return;
        }

        start = end;
        end = Math.min(start + entriesPerPage, playlists.size());
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

    @Override
    void printList() {

        for (int i = start; i < end; i++) {

            Playlist current = playlists.get(i);

            System.out.println(current.name);
            System.out.println(current.link);
            System.out.println();
        }
        System.out.printf("---PAGE %d OF %d---\n", currentPage, totalPages);
    }
}
