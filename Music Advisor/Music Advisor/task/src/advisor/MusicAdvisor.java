package advisor;

import advisor.MusicApi.*;

import java.util.Scanner;

public class MusicAdvisor {

    Scanner scanner = new Scanner(System.in);
    boolean authorized = false;

    MusicApi api = null;

    public void start() {

        while (true) {

            String input = scanner.nextLine();

            switch (input) {

                case "auth":
                    authorizeUser();
                    break;

                case "featured":
                    printFeaturedPlaylists();
                    break;

                case "new":
                    printNewReleases();
                    break;

                case "categories":
                    printCategories();
                    break;

                case "next" :
                    api.next();
                    break;

                case "prev":
                    api.prev();
                    break;

                case "exit":
                    System.out.println("---GOODBYE!---");
                    return;

                default:

                    if (input.matches("playlists .+")) {

                        String cName = input.substring(10);
                        printPlayListWithCName( cName);
                    }
            }
        }
    }

    public void authorizeUser() {

        Authentication authentication = new Authentication();
        authentication.getAccessCode();
        authentication.getAccessToken();
        authorized = true;
    }

    private void printFeaturedPlaylists() {

        if (authorized) {
            api = new FeaturedPlaylists();
            api.obtain();
        } else {
            System.out.println("Please, provide access for application.");
        }
    }

    private void printNewReleases() {

        if (authorized) {
            api = new NewReleases();
            api.obtain();
        } else {
            System.out.println("Please, provide access for application.");
        }
    }

    private void printCategories() {

        if (authorized) {
            api = new Category();
            api.obtain();
        } else {
            System.out.println("Please, provide access for application.");
        }
    }

    private void printPlayListWithCName(String cName) {

        if (authorized) {
            api = new CategorizedPlaylists(cName);
            api.obtain();
        } else {
            System.out.println("Please, provide access for application.");
        }
    }
}