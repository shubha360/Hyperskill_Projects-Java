import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.mocks.web.WebServerMock;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;
import org.junit.AfterClass;

public class MusicAdvisorTest extends StageTest<String> {

    private static final String fictiveAuthCode = "123123";
    private static final String fictiveAccessToken = "456456";
    private static final String fictiveRefreshToken = "567567";

    private static final int accessServerPort = 45678;
    private static final String accessServerUrl = "http://127.0.0.1:" + accessServerPort;

    private static final String[] arguments = new String[]{
            "-access",
            accessServerUrl
    };

    private static final String tokenResponse = "{" +
            "\"access_token\":\"" + fictiveAccessToken + "\"," +
            "\"token_type\":\"Bearer\"," +
            "\"expires_in\":3600," +
            "\"refresh_token\":" + "\"" + fictiveRefreshToken + "\"," +
            "\"scope\":\"\"" +
            "}";

    private static final WebServerMock accessServer = new WebServerMock(accessServerPort)
            .setPage("/api/token", tokenResponse);

    private static final MockTokenServer tokenServer = new MockTokenServer(accessServer);

    @DynamicTestingMethod
    CheckResult testAuth() {

        TestedProgram userProgram = new TestedProgram();
        userProgram.start(arguments);
        userProgram.setReturnOutputAfterExecution(false);

        Server server = new Server(userProgram, fictiveAuthCode);
        server.start();
        tokenServer.start();

        userProgram.goBackground();
        userProgram.execute("auth");

        try {
            server.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (Server.checkResult != null) {
            return Server.checkResult;
        }

        userProgram.stopBackground();

        String outputAfterAuth = userProgram.getOutput();
        if (!outputAfterAuth.contains(fictiveAccessToken)) {
            return CheckResult.wrong("Not found correct access token in the result. " +
                    "Make sure, that you use the server from the command line arguments to access the token.");
        }

        userProgram.execute("featured");

        String outputAfterFeatured = userProgram.getOutput();
        if (!outputAfterFeatured.contains("---FEATURED---")) {
            return CheckResult.wrong("When \"featured\" was inputted there should be \"---FEATURED---\" line");
        }

        userProgram.execute("exit");
        userProgram.stop();

        return CheckResult.correct();
    }

    @DynamicTestingMethod
    CheckResult testNewWithoutAuth() {

        TestedProgram userProgram = new TestedProgram();
        userProgram.start(arguments);
        userProgram.setReturnOutputAfterExecution(false);

        userProgram.execute("new");
        String outputAfterNew = userProgram.getOutput();

        if (!outputAfterNew.strip().startsWith("Please, provide access for application.")) {
            return CheckResult.wrong("When no access provided you should output " +
                    "\"Please, provide access for application.\"");
        }

        userProgram.execute("exit");
        userProgram.stop();

        return CheckResult.correct();
    }

    @DynamicTestingMethod
    CheckResult testFeaturedWithoutAuth() {

        TestedProgram userProgram = new TestedProgram();
        userProgram.start(arguments);
        userProgram.setReturnOutputAfterExecution(false);

        userProgram.execute("featured");
        String outputAfterNew = userProgram.getOutput();

        if (!outputAfterNew.strip().startsWith("Please, provide access for application.")) {
            return CheckResult.wrong("When no access provided you should output " +
                    "\"Please, provide access for application.\"");
        }

        userProgram.execute("exit");
        userProgram.stop();

        return CheckResult.correct();
    }

    @AfterClass
    public static void afterTest() {
        tokenServer.stopMock();
    }

}