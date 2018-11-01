package aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;

public class AioTimerServerHandler implements Runnable {
    private Integer port = null;
    private AsynchronousServerSocketChannel aChannel = null;
    public AsynchronousServerSocketChannel getChannel() {
        return aChannel;
    }
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private CountDownLatch latch = null;
    public CountDownLatch getLatch() {
        return latch;
    }
    public AioTimerServerHandler(Integer port) throws IOException {
        this.port = port;
        aChannel = AsynchronousServerSocketChannel.open();
        aChannel.bind(new InetSocketAddress(this.port), 1024);
        System.out.println("[Server]: Server starts at " + df.format(System.currentTimeMillis()));
    }
    public void run() {
        latch = new CountDownLatch(1);
        try {
            doAccept();
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void doAccept() throws InterruptedException {
        this.aChannel.accept(this, new AcceptCompletionHandler());
    }
}
