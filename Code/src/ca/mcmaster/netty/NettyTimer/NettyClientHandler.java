package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    private static final String TOKEN = "TIME QUERY";
    private ByteBuf buf = null;
    public NettyClientHandler() {
        byte[] bytes = TOKEN.getBytes();
        buf = Unpooled.copiedBuffer(bytes);
    }

    /**
     * 当连接建立的时候，将数据写入通道中。
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(buf);
    }

    /**
     * 当通道可读时从通道中读出数据。
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buffer = (ByteBuf) msg;
        byte[] bytes = new byte[buffer.readableBytes()];
        buffer.readBytes(bytes);
        String response = new String(bytes);
        System.out.println("[Client]: current time is " + response);
    }

    /**
     * 异常时的回调函数。
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
