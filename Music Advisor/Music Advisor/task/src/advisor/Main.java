package advisor;

public class Main {
    public static void main(String[] args) {

        if (args.length > 1 && args[0].equals("-access")) {
            Authentication.SERVER_PATH = args[1];
        }

        MusicAdvisor musicAdvisor = new MusicAdvisor();
        musicAdvisor.authorizeUser();
    }
}
