package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Set;

public class NioTimerServer implements  Runnable{
    private Integer port = null;
    private ServerSocketChannel server = null;
    private Selector selector = null;
    private boolean stop = false;
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private static String TOKEN = "TIMER QUERY";
    private static String BAD_REQUEST = "BAD REQUEST";
    private static String STOP = "STOP";
    public NioTimerServer(int port) throws IOException {
        this.port = port;
        /**
         * 打开一个ServerSocketChannel，该channel每接收到一个请求都会生成一个socketChannel。
         */
        server = ServerSocketChannel.open();
        server.socket().bind(new InetSocketAddress(port), 1024);
        /**
         * 将服务器设置为非阻塞式的。
         * 并将服务器注册到selector上。同时selector会监听ACCEPT事件的。
         */
        server.configureBlocking(false);
        selector = Selector.open();
        server.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("[Server]: Server is binding to port " + this.port + " at " + df.format(System.currentTimeMillis()));
    }
    public void run() {
        /**
         * 轮询当前的selector线程
         */
        while (!stop){
            try {
                this.selector.select(1000L);    //为了不要一致轮询浪费CPU，设置一个中间值。
                Set<SelectionKey> keys = this.selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = keys.iterator();
                SelectionKey key = null;
                while (keyIterator.hasNext()){
                    key = keyIterator.next();
                    keyIterator.remove();   //一定要记得将当前事件从集合中删除，不然会一直被处理。
                    handlerInput(key);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void handlerInput(SelectionKey key) throws IOException {
        if(key.isValid()){
            /**
             * 监听某个端口，此处不会阻塞。
             * 此时已经确定当前的socket是连通的了，所以不可能阻塞。
             */
            if(key.isAcceptable()){
                ServerSocketChannel channel = (ServerSocketChannel)key.channel();
                SocketChannel socket = channel.accept();
                socket.configureBlocking(false);
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                /**
                 * 此时selector同时会响应accept事件，因为已经accept，所以已经确定了可以读取
                 * 数据，此时我们再添加一个读取事件。以供selector检查。
                 */
                socket.register(this.selector, SelectionKey.OP_READ, buffer);
            }
            if(key.isReadable()){
                /**
                 * 此时当前管道已经可以读取，我们可以处理逻辑业务。
                 */
                SocketChannel socketChannel = (SocketChannel)key.channel();
                ByteBuffer buffer = (ByteBuffer) key.attachment();
                String request = null;
                StringBuilder sb = new StringBuilder();
                while (socketChannel.read(buffer) != 0){
                    buffer.flip();
                    while (buffer.hasRemaining()){
                        sb.append((char) buffer.get());
                    }
                    buffer.clear();
                }
                request = sb.toString();
                System.out.println(request);
                if(request != null && request.equals(TOKEN)) {
                    buffer.put(df.format(System.currentTimeMillis()).getBytes());
                }else if(request != null && request.equals(STOP))
                    this.stop = true;
                else{
                    buffer.put(BAD_REQUEST.getBytes());
                }
                buffer.flip();
                while (buffer.hasRemaining())
                    socketChannel.write(buffer);
                buffer.clear();
            }
        }
    }
    public static void main(String[] args) throws IOException {
        new Thread(new NioTimerServer(8080), "Server-thread").start();
    }
}
