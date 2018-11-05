package protobuf;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

public class ProtobufClient {
    public void connect(String url, int port) throws InterruptedException {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            /**
                             * 用作半包处理
                             */
                            ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                            /**
                             * 添加解码器并在解码器中生命目标类。protobuf则会通过目标类解码。
                             */
                            ch.pipeline().addLast(new ProtobufDecoder(UserInfoProto.UserInfo.getDefaultInstance()));
                            /**
                             * 添加用于对象注入的句柄
                             */
                            ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                            /**
                             * 添加编码器
                             */
                            ch.pipeline().addLast(new ProtobufEncoder());
                            ch.pipeline().addLast(new ProtobufClientHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect(url, port).sync();
            future.channel().closeFuture().sync();
        }finally {
            workerGroup.shutdownGracefully();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        new ProtobufClient().connect("127.0.0.1", 8080);
    }
}
