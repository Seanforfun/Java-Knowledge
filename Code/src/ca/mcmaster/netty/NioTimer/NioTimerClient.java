package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioTimerClient implements Runnable {
    private SocketChannel channel = null;
    private Selector selector = null;
    private static String TOKEN = "TIMER QUERY";
    private static String BAD_REQUEST = "BAD REQUEST";
    private static String STOP = "STOP";
    private boolean stop = false;
    public NioTimerClient(String url, String port) throws IOException {
        url = url == null ? "127.0.0.1" : url;
        int portNum = port != null ? Integer.parseInt(port) : 8080;
        channel = SocketChannel.open();
        channel.configureBlocking(false);
        selector = Selector.open();
        doConnect(url, portNum);
    }
    public void run() {
        while(!stop){
            try {
                selector.select(1000L);
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                SelectionKey key = null;
                try {
                    while (iterator.hasNext()){
                        key = iterator.next();
                        iterator.remove();
                        handleInput(key);
                    }
                }catch (IOException e){
                    if(key != null) key.cancel();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void handleInput(SelectionKey key) throws IOException {
        if(key.isValid()){
            SocketChannel channel = (SocketChannel) key.channel();
            if(key.isConnectable()){
                if(channel.finishConnect()){
                    channel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    sendRequest(TOKEN);
                }
            }
            /**
             * readable说明当前的缓存区中数据已经准备完全了
             */
            if(key.isReadable()){
                String response = readFromChannel();
                if(response != null && response.equals(BAD_REQUEST))
                    sendRequest(TOKEN);
                else {
                    System.out.println("[Client]: Current time is" + response);
                    sendRequest(STOP);
                    this.stop = true;
                }
            }
        }
    }
    private void doConnect(String url, int port) throws IOException {
        /**
         * 调用connect方法，如果返回了true，就说明已经连接成功了，只需要发送就行了。
         * 只要connectable就说明当前的通道是writeable的了。
         */
        if(this.channel.connect(new InetSocketAddress(url, port))){
            channel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
            sendRequest(TOKEN);
        }else{
            channel.register(selector, SelectionKey.OP_CONNECT, ByteBuffer.allocate(1024));
        }
    }
    private void sendRequest(String request) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(request.length());
        buffer.put(request.getBytes());
        buffer.flip();
        while (buffer.hasRemaining())
            channel.write(buffer);
    }
    private String readFromChannel() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        StringBuilder sb = new StringBuilder();
        while(channel.read(buffer) != 0){
            buffer.flip();
            while (buffer.hasRemaining())
                sb.append((char)buffer.get());
            buffer.clear();
        }
        return sb.toString();
    }
    public static void main(String[] args) throws IOException {
        new Thread(new NioTimerClient("127.0.0.1", "8080"), "Thread-client").start();
    }
}
