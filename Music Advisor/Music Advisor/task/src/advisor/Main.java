package advisor;

public class Main {
    public static void main(String[] args) {

        if (args.length > 1 && args[0].equals("-access")) {
            Authentication.SERVER_PATH = args[1];
        }

        MusicAdvisor musicAdvisor = new MusicAdvisor();
        musicAdvisor.start();

//        String authRequestUri = Authentication.SERVER_PATH +
//                "/authorize?client_id=" + Authentication.CLIENT_ID +
//                "&response_type=code" +
//                "&redirect_uri=" + Authentication.REDIRECT_URI;
//
//        System.out.println(authRequestUri);
    }
}
