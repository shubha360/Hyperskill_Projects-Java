package advisor;

import advisor.MusicApi.MusicApi;

public class Main {
    public static void main(String[] args) {

        if (args.length > 0) {

            for (int i = 0; i < args.length; i++) {

                switch (args[i]) {

                    case "-access":
                        i++;
                        Authentication.SERVER_PATH = args[i];
                        break;

                    case "-resource":
                        i++;
                        MusicApi.API_LINK = args[i];
                        break;

                    case "-page":
                        i++;
                        MusicApi.entriesPerPage = Integer.parseInt(args[i]);
                        break;
                }
            }
        }

        MusicAdvisor musicAdvisor = new MusicAdvisor();
        musicAdvisor.start();
    }
}