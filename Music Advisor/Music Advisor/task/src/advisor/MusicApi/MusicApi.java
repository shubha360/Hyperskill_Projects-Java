package advisor.MusicApi;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class MusicApi {

    public static String API_LINK = "https://api.spotify.com";
    public static int entriesPerPage = 5;
    static boolean categoriesObtained = false;

    static HashMap<String, String> categoryNameAndId = new HashMap<>();

    abstract public void next();
    abstract public void prev();
    abstract public void obtain();
    abstract void printList();
}

class Album {

    String name;
    ArrayList<String> artists;
    String link;

    public Album(String name, ArrayList<String> artists, String link) {
        this.name = name;
        this.artists = artists;
        this.link = link;
    }
}

class Playlist {

    String name;
    String link;

    public Playlist(String name, String link) {
        this.name = name;
        this.link = link;
    }
}