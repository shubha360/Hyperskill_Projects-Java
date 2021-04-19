import org.hyperskill.hstest.mocks.web.WebServerMock;

public class MockTokenServer extends Thread {

    WebServerMock accessServer;

    public MockTokenServer (WebServerMock accessServer) {
        this.accessServer = accessServer;
    }

    @Override
    public void run() {
        accessServer.start();
        accessServer.run();
    }

    public void stopMock() {
        interrupt();
    }
}
