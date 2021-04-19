package advisor;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Authentication {

    public static String SERVER_PATH = "https://accounts.spotify.com";
    public static String REDIRECT_URI = "http://localhost:8080";
    public static String CLIENT_ID = "d5e4a5e431d9471e97fec15d26509715";
    public static String CLIENT_SECRET = "fbc4ec032b6843c88f5bbe0e5244e1d4";
    public static String ACCESS_TOKEN = "";
    public static String ACCESS_CODE = "";

    public void getAccessCode() {

        String uri = SERVER_PATH + "/authorize"
                + "?client_id=" + CLIENT_ID
                + "&redirect_uri=" + REDIRECT_URI
                + "&response_type=code";

        System.out.println("use this link to request the access code:");
        System.out.println(uri);

        try {
            HttpServer server = HttpServer.create();
            server.bind(new InetSocketAddress(8080), 0);
            server.start();
            server.createContext("/",
                    new HttpHandler() {
                        public void handle(HttpExchange exchange) throws IOException {
                            String query = exchange.getRequestURI().getQuery();
                            String request;

                            if (query != null && query.contains("code")) {
                                ACCESS_CODE = query.substring(5);
                                System.out.println("code received");
                                System.out.println(ACCESS_CODE);
                                request = "Got the code. Return back to your program.";
                            } else {
                                request = "Not found authorization code. Try again.";
                            }

                            exchange.sendResponseHeaders(200, request.length());
                            exchange.getResponseBody().write(request.getBytes());
                            exchange.getResponseBody().close();
                        }
                    });

            System.out.println("waiting for code...");
            while (ACCESS_CODE.length() == 0) {
                Thread.sleep(100);
            }
            server.stop(5);

        } catch (IOException | InterruptedException e) {
            System.out.println("Server error");
        }
    }

    public void getAccessToken() {

        System.out.println("making http request for access_token...");
        System.out.println("response:");

        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(SERVER_PATH + "/api/token"))
                .POST(HttpRequest.BodyPublishers.ofString(
                        "grant_type=authorization_code"
                                + "&code=" + ACCESS_CODE
                                + "&client_id=" + CLIENT_ID
                                + "&client_secret=" + CLIENT_SECRET
                                + "&redirect_uri=" + REDIRECT_URI))
                .build();

        try {

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            assert response != null;
            System.out.println(response.body());
            System.out.println("---SUCCESS---");

        } catch (InterruptedException | IOException e) {
            System.out.println("Error response");
        }
    }
}
