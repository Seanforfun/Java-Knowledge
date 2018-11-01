package aio;

import java.io.IOException;

public class AioTimerServer {
    public static void main(String[] args) throws IOException {
        new Thread(new AioTimerServerHandler(8080)).start();
    }
}
