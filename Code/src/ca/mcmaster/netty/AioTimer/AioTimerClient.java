package aio;

public class AioTimerClient {
    public static void main(String[] args) {
        new Thread(new AioClientHandler("127.0.0.1", 8080)).start();
    }
}
