package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyTimerClient {
    public void connect(String url, int port){
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            /**
                             * 添加一个响应事件并注册到端口的对应链上
                             */
                            ch.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            /**
             * 发起异步连接操作，打开了一个通道并实现同步。
             */
            ChannelFuture future = bootstrap.connect(url, port).sync();
            /**
             * 链路端断开后进行关闭。
             */
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
    public static void main(String[] args) {
        new NettyTimerClient().connect("127.0.0.1", 8080);
    }
}
