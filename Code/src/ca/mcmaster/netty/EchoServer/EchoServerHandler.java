package netty.echo;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.ServerSideConst;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String request = (String)msg;
        System.out.println("[Server]: receive request " + request);
        ctx.writeAndFlush(Unpooled.copiedBuffer((request + ServerSideConst.SPLITER).getBytes()));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
