package protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ProtobufServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        UserInfoProto.UserInfo info = (UserInfoProto.UserInfo) msg;
        System.out.println(info.toString());
        UserInfoProto.UserInfo irene = resp(10, "Irene");
        ctx.writeAndFlush(irene);
    }

    private UserInfoProto.UserInfo resp(long id, String name){
        UserInfoProto.UserInfo.Builder builder = UserInfoProto.UserInfo.newBuilder();
        builder.setId(id);
        builder.setName(name);
        return builder.build();
    };

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
