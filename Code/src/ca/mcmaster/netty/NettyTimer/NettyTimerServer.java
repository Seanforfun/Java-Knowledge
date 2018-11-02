package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyTimerServer {
    private Integer port = null;

    public void bind(int port) throws InterruptedException {
        this.port = port;
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap server = new ServerBootstrap();
            /**
             * 定义一个线程组，配置服务端的NIO线程组
             */
            server.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    /**
                     * 选择服务器的种类
                     */
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    /**
                     * workGroup的初始化的方法，并在初始化的时候注册监听的事件。
                     */
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyTimerServerHandler());
                        }
                    });
            /**
             * 绑定监听一个端口
             */
            ChannelFuture future = server.bind(this.port).sync();
            /**
             * 监听端口的关闭
             */
            future.channel().closeFuture().sync();
        }finally {
            /**
             * 优雅退出，释放线程池资源
             */
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new NettyTimerServer().bind(8080);
    }
}
