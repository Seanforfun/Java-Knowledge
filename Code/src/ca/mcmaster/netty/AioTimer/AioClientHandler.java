package aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

public class AioClientHandler implements Runnable, CompletionHandler<Void, AioClientHandler> {
    private String url = null;
    private Integer port = null;
    private AsynchronousSocketChannel channel = null;
    private static final  String TOKEN = "QUERY TIME";
    public CountDownLatch getLatch() {
        return latch;
    }

    private CountDownLatch latch = null;
    public AioClientHandler(String url, Integer port) {
        this.port = port;
        this.url = url;
        try {
            channel = AsynchronousSocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        latch = new CountDownLatch(1);
        channel.connect(new InetSocketAddress(this.url, this.port), this, this);
        try {
            latch.await();
            channel.close();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void completed(Void result, AioClientHandler attachment) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(TOKEN.getBytes());
        buffer.flip();
        channel.write(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                if(attachment.hasRemaining())
                    channel.write(attachment, attachment, this);
                else{
                    ByteBuffer buf = ByteBuffer.allocate(1024);
                    channel.read(buf, buf, new CompletionHandler<Integer, ByteBuffer>() {
                        @Override
                        public void completed(Integer result, ByteBuffer attachment) {
                            attachment.flip();
                            byte[] bytes = new byte[attachment.remaining()];
                            attachment.get(bytes);
                            String response = new String(bytes);
                            System.out.println("[Client]: current time is " + response);
                            latch.countDown();
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer attachment) {
                            exc.printStackTrace();
                            try {
                                channel.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                exc.printStackTrace();
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void failed(Throwable exc, AioClientHandler attachment) {
        exc.printStackTrace();
        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
