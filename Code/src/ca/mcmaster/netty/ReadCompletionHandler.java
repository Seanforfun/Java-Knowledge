package aio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {
    private AsynchronousSocketChannel channel = null;
    private static final  String TOKEN = "QUERY TIME";
    private AioTimerServerHandler timerServerHandler = null;
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    public ReadCompletionHandler(AsynchronousSocketChannel result, AioTimerServerHandler attachment) {
        this.channel = result;
        this.timerServerHandler = attachment;
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        byte[] bytes = new byte[attachment.remaining()];
        attachment.get(bytes);
        String request = new String(bytes);
        if(TOKEN.equals(request)){
            System.out.println("[Server]: received message " + request);
            doWrite(df.format(System.currentTimeMillis()));
            System.out.println("[Server]: send a message to client.");
        }
    }

    private void doWrite(String response) {
        ByteBuffer attachment = ByteBuffer.allocate(1024);
        attachment.put(response.getBytes());
        attachment.flip();
        while (attachment.hasRemaining())
            this.channel.write(attachment, attachment, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    if(attachment.hasRemaining())
                        channel.write(attachment, attachment, this);
                    timerServerHandler.getLatch().countDown();
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    try {
                        channel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            this.channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
