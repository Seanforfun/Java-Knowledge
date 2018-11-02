package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class NettyTimerServerHandler extends ChannelInboundHandlerAdapter {
    private static final String TOKEN = "TIME QUERY";
    private static final DateFormat df  = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    /**
     * 当通道可读时的回调函数。
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buffer = (ByteBuf) msg;
        byte[] bytes = new byte[buffer.readableBytes()];
        buffer.readBytes(bytes);
        String request = new String(bytes);
        System.out.println("[Server]: server received request " + request);
        if(TOKEN.equals(request)){
            ByteBuf response = Unpooled.copiedBuffer(df.format(System.currentTimeMillis()).getBytes());
            ctx.writeAndFlush(response);
        }
    }

    /**
     * 通道读取完毕后的回调函数。
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /**
     * 通道发生异常时的回调函数。
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
