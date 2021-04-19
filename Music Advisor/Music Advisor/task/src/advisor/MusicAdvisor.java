package advisor;

import java.util.Scanner;

public class MusicAdvisor {

    Scanner scanner = new Scanner(System.in);
    boolean authorized = false;

    public void start() {

        while (true) {

            String input = scanner.nextLine();

            switch (input) {

                case "auth":
                    authorizeUser();
                    break;

                case "featured":
                    printFeatured(authorized);
                    break;

                case "new":
                    printNew(authorized);
                    break;

                case "categories":
                    printCategories(authorized);
                    break;

                case "playlists Mood":

                    break;

                case "exit":
                    System.out.println("---GOODBYE!---");
                    return;
            }
        }
    }

    public void authorizeUser() {

        Authentication authentication = new Authentication();
        authentication.getAccessCode();
        authentication.getAccessToken();
        authorized = true;
    }



    private void printFeatured(boolean auth) {

        if (auth) {

            System.out.println("---FEATURED---\n" +
                    "Mellow Morning\n" +
                    "Wake Up and Smell the Coffee\n" +
                    "Monday Motivation\n" +
                    "Songs to Sing in the Shower");
        } else {
            System.out.println("Please, provide access for application.");
        }
    }

    private void printNew(boolean auth) {

        if (auth) {

            System.out.println("---NEW RELEASES---\n" +
                    "Mountains [Sia, Diplo, Labrinth]\n" +
                    "Runaway [Lil Peep]\n" +
                    "The Greatest Show [Panic! At The Disco]\n" +
                    "All Out Life [Slipknot]");
        } else {
            System.out.println("Please, provide access for application.");
        }
    }

    private void printCategories(boolean auth) {

        if (auth) {

            System.out.println("---CATEGORIES---\n" +
                    "Top Lists\n" +
                    "Pop\n" +
                    "Mood\n" +
                    "Latin");
        } else {
            System.out.println("Please, provide access for application.");
        }
    }

    private void printPlayList(boolean auth) {

        if (auth) {

            System.out.println("---MOOD PLAYLISTS---\n" +
                    "Walk Like A Badass  \n" +
                    "Rage Beats  \n" +
                    "Arab Mood Booster  \n" +
                    "Sunday Stroll");
        } else {
            System.out.println("Please, provide access for application.");
        }
    }
}
