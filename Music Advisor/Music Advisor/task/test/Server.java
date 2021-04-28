import org.hyperskill.hstest.dynamic.output.OutputHandler;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;

public class Server extends Thread {

    TestedProgram userProgram;
    private final String fictiveAuthCode;
    public static CheckResult checkResult = null;
    String redirectUri = "";


    public Server(TestedProgram userProgram, String fictiveAuthCode) {
        this.userProgram = userProgram;
        this.fictiveAuthCode = fictiveAuthCode;
    }

    @Override
    public void run() {
        long searchTime = System.currentTimeMillis();

        while (!Thread.interrupted()) {

            if (System.currentTimeMillis() - searchTime > 1000 * 9) {
                checkResult = CheckResult.wrong("Not found a link with redirect_uri after 9 seconds.");
                return;
            }

            String out = OutputHandler.getDynamicOutput();
            if (out.contains("redirect_uri=")) {
                redirectUri = out.split("redirect_uri=")[1];
                if (redirectUri.contains("&")) {
                    redirectUri = redirectUri.split("&")[0];
                }
                if (redirectUri.contains("\n")) {
                    redirectUri = redirectUri.split("\\R")[0];
                }
                break;
            }

            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest emptyRequest = HttpRequest.newBuilder()
                .uri(URI.create(redirectUri))
                .timeout(Duration.ofMillis(500))
                .GET()
                .build();
            HttpRequest errorRequest = HttpRequest.newBuilder()
                .uri(URI.create(redirectUri + "?error=access_denied"))
                .timeout(Duration.ofMillis(500))
                .GET()
                .build();
            HttpRequest codeRequest = HttpRequest.newBuilder()
                .uri(URI.create(redirectUri + "?code=" + fictiveAuthCode))
                .timeout(Duration.ofMillis(500))
                .GET()
                .build();

            System.out.println("Tester: making requests to redirect uri: " + redirectUri);
            HttpResponse<String> badResponse = client.send(emptyRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println("Tester: done request 1: " + badResponse.body());
            HttpResponse<String> badResponse2 = client.send(errorRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println("Tester: done request 2: " + badResponse2.body());
            HttpResponse<String> goodResponse = client.send(codeRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println("Tester: done request 3: " + goodResponse.body());

            if (!badResponse.body().contains("Authorization code not found. Try again.")
                || !badResponse2.body().contains("Authorization code not found. Try again.")) {
                checkResult = CheckResult.wrong("You should send to the browser: `Authorization code not found. Try again.` if there is no code.");
                return;
            }

            if (!goodResponse.body().contains("Got the code. Return back to your program.")) {
                checkResult = CheckResult.wrong("\"You should send `Got the code. Return back to your program.` \" +\n" +
                    "                        \"if the query contains the code.");
            }
        } catch (HttpTimeoutException e) {
            checkResult = CheckResult.wrong("Not received any response from the server, found in redirect_uri: "
                + redirectUri);
        } catch (InterruptedException e) {
            checkResult = CheckResult.wrong("Request to " + redirectUri + " was interrupted. " +
                "Make sure, that you give the right feedback in your browser.");
        } catch (Exception e) {
            System.out.println("Tester: Error: " + e.getMessage());
            checkResult = CheckResult.wrong("Something wrong with the server response.\n" +
                "Make sure, that you get the right feedback in your browser.");
        }
    }
}
