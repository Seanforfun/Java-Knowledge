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

    /**
     * 当通道可读时，发送100次请求。
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        byte[] bytes = TOKEN.getBytes();
        for (int i = 0; i < 100; i++){
            buffer = Unpooled.copiedBuffer(bytes);
            ctx.writeAndFlush(buffer);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf)msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        String response = new String(bytes, "UTF-8");
        System.out.println("[Client]: current time is " + response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
