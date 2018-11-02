package netty.sliceError;

import com.sun.org.apache.bcel.internal.generic.NEW;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimerClientHandler extends ChannelInboundHandlerAdapter {
    public static final String TOKEN = "QUERY TIME" + System.lineSeparator();
    public static final String BAD_REQUEST = "BAD REQUEST";
    public ByteBuf buffer = null;
    private int count = 0;
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /**
     * 当通道可读时，发送100次请求。
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        byte[] bytes = TOKEN.getBytes();
        buffer = Unpooled.copiedBuffer(bytes);
        Thread.sleep(1000L);
        ctx.writeAndFlush(buffer);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String response = (String) msg;
        System.out.println("[Client]: current time is " + response);
        if(++count < 100) {
            byte[] bytes = TOKEN.getBytes();
            buffer = Unpooled.copiedBuffer(bytes);
            Thread.sleep(1000L);
            ctx.writeAndFlush(buffer);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
