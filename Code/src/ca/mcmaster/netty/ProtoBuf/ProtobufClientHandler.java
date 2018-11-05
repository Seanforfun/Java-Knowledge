package protobuf;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;

public class ProtobufClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        UserInfoProto.UserInfo info = (UserInfoProto.UserInfo) msg;
        System.out.println(info);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        UserInfoProto.UserInfo jenny = createInstance(11L, "Jenny");
        ctx.writeAndFlush(jenny);
    }
    private UserInfoProto.UserInfo createInstance(long id, String name){
        UserInfoProto.UserInfo.Builder builder = UserInfoProto.UserInfo.newBuilder();
        builder.setId(id);
        builder.setName(name);
        return builder.build();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
