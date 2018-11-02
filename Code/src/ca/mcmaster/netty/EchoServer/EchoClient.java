package netty.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import netty.ServerSideConst;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EchoClient {
    public void connect(String url, int port) throws InterruptedException, IOException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
//                            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,
//                                    Unpooled.copiedBuffer(ServerSideConst.SPLITER.getBytes())));
                            ch.pipeline().addLast(new FixedLengthFrameDecoder(10));
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            /**
             * 此步骤是异步的，该函数会直接返回，并不会阻塞。
             */
            ChannelFuture future = bootstrap.connect(url, port).sync();
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while (true){
                /**
                 * 程序会在这个步骤进行阻塞。直到获得输入行中的换行符。
                 */
                String read = in.readLine();
                future.channel().writeAndFlush(Unpooled.copiedBuffer((read + ServerSideConst.SPLITER).getBytes()));
            }
//            future.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        new EchoClient().connect("127.0.0.1", 8080);
    }
}
