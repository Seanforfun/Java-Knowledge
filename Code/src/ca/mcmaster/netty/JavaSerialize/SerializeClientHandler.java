package serialize.java;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;

public class SerializeClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        UserInfo info = new UserInfo(1L, "Sean");
        for(int i= 0; i < 10; i++)
            ctx.writeAndFlush(info);
    }
}
