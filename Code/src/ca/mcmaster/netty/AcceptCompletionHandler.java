package aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AioTimerServerHandler> {
    @Override
    public void completed(AsynchronousSocketChannel result, AioTimerServerHandler attachment) {
        attachment.getChannel().accept(attachment, this);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        result.read(buffer, buffer, new ReadCompletionHandler(result, attachment));
    }

    @Override
    public void failed(Throwable exc, AioTimerServerHandler attachment) {
        attachment.getLatch().countDown();
    }
}
