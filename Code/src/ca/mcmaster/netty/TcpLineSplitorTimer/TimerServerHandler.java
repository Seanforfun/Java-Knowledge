package netty.sliceError;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TimerServerHandler extends ChannelInboundHandlerAdapter {
    private int count = 0;
    public static final String TOKEN = "QUERY TIME";
    public static final String BAD_REQUEST = "BAD REQUEST";
    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    /**
     * 通道可读时的回调函数，打印出接收到的请求的数量
     * 并响应。
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String request =(String)msg;
//        request = request.substring(0, request.length() - System.lineSeparator().length());
        System.out.println("[Server]: Server received request " + request + " " + ++count + "times.");
        String response = (request.equals(TOKEN) ? df.format(System.currentTimeMillis()): BAD_REQUEST) + System.lineSeparator();
        ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
