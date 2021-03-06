# Netty Conclusion
Netty是由JBOSS提供的一个java开源框架。Netty提供异步的、事件驱动的网络应用程序框架和工具，用以快速开发高性能、高可靠性的网络服务器和客户端程序。

Netty整合了nio的基础设施，并且提供了对于多种协议，相比于Tomcat只对HTTP进行支持，Netty易于我们扩展自己的第三方协议并且稳定性较强。

这篇文章是基于《Netty权威指南》的总结，中间的代码参考了nettybook2项目，这两个都在引用中给了连接。

### 三种I/O模型构造时间服务器/客户端
#### 阻塞式I/O
创建一个Server,对于每一个请求，都会创建一个新的线程处理逻辑业务。
```Java
public class BioTimerServer {
    private Integer port = null;
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    public BioTimerServer(int port) throws IOException {
        this.port = port;
        /**
         * 创建一个服务器Socket，该服务器程序会坚定某个端口。
         */
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("[Configure]: Server starts at " + df.format(System.currentTimeMillis()));
            Socket socket = null;
            /**
             * 该服务器会一直监听该端口，一直阻塞到从该端口获得一个socket套接字。
             */
            while (true){
                socket = serverSocket.accept();
                /**
                 * 开启一个线程用于实现业务逻辑。
                 */
                new Thread(new BioTimerServerHandler(socket), "TIMER SERVER").start();
            }
        }finally {
            if(serverSocket != null)
                serverSocket.close();
        }
    }
    public static void main(String[] args) throws IOException {
        new BioTimerServer(8080);
    }
}
```

服务器端的逻辑处理业务，该业务是一个实现Runnable的类。
```Java
public class BioTimerServerHandler implements Runnable {
    private static final String TOKEN = "QUERY TIME";
    private static final String BAD_RESPONSE = "BAD ORDER";
    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private Socket socket = null;
    public BioTimerServerHandler(Socket socket){
        this.socket = socket;
    }
    public void run() {
        /**
         * 从Socket中获得输入输出流，向输入输出流中写入写出数据。
         */
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            String body = null;
            while (true){
                body = in.readLine();
                if(body == null) break;
                System.out.println("[Configure]: Server received body " + body);
                String response = body.equals(TOKEN) ? df.format(System.currentTimeMillis()): BAD_RESPONSE;
                out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(in != null)
                    in.close();
                if(out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
```

客户端程序，创建一个socket请求，向服务器发出请求。
```Java
public class BioTimerClient {
    private static final String TOKEN = "QUERY TIME";
    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    public static void main(String[] args) throws IOException {
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            socket = new Socket("127.0.0.1", 8080);
            System.out.println(socket);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println(TOKEN);
            System.out.println("[Client]: Client sent order to server at " + df.format(System.currentTimeMillis()));
            String respone = in.readLine();
            System.out.println("[Client]: Current time is " + respone);
        }finally {
            if(socket != null)
                socket.close();
            if(in != null)
                in.close();
            if(out != null)
                out.close();
        }
    }
}
```

#### 伪异步I/O
伪异步IO是用于线程池替代每次创建新的线程，所以我们可以通过实现一个线程池，并通过每次使用线程池创建新的task。
* 服务器端
    * 服务器类
    ```Java
    public class FioTimerServer {
        private Integer port = null;
        private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        public  FioTimerServer(int port) throws IOException {
            this.port = port;
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(port);
                System.out.println("[Server]: Server starts at " + df.format(System.currentTimeMillis()));
                Socket socket = null;
                /**
                 * Create a thread pool for execution.
                 */
                TimerServerThreadPool executors = new TimerServerThreadPool(20, 40);
                while (true){
                    socket = serverSocket.accept();
                    executors.execute(new TimerServerHandler(socket));
                }
            }finally {
                if(serverSocket != null)
                    serverSocket.close();
            }
        }
        public static void main(String[] args) throws IOException {
            new FioTimerServer(8080);
        }
    }
    ```

    * 服务器处理请求的业务
    ```Java
    public class TimerServerHandler implements Runnable {
        private Socket socket = null;
        private static String TOKEN = "TIMER QUERY";
        private static String BAD_REQUEST = "BAD REQUEST";
        private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        public TimerServerHandler(Socket socket){
            this.socket = socket;
        }
        public void run() {
            BufferedReader in = null;
            PrintWriter out = null;
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                String request = null;
                String response = null;
                while((request = in.readLine()) != null){
                    System.out.println(request);
                    if(request.equals(TOKEN)) {
                        response = df.format(System.currentTimeMillis());
                        break;
                    }
                }
                if(response == null) response = BAD_REQUEST;
                out.println(response);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(out != null) out.close();
            }
        }
    }
    ```

    * 线程池的创建
    ```Java
    public class TimerServerThreadPool {
        private ThreadPoolExecutor executors = null;
        ArrayBlockingQueue<Runnable> blockingQueue = null;
        public TimerServerThreadPool(int coreNum, int maxNum){
            this.blockingQueue = new ArrayBlockingQueue<Runnable>(1000, false);
            this.executors = new ThreadPoolExecutor(coreNum, maxNum, 1000L ,
                    TimeUnit.SECONDS, blockingQueue, Executors.defaultThreadFactory());
        }
        public void execute(Runnable target){
            this.executors.execute(target);
        }
    }
    ```

* 客户端
```Java
public class TimerClient {
    private static String TOKEN = "TIMER QUERY";
    private static String BAD_REQUEST = "BAD REQUEST";
    public static void main(String[] args) throws IOException {
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            socket = new Socket("127.0.0.1", 8080);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println(TOKEN);
            System.out.println("[Client]: Client send order to server.");
            String response = in.readLine();
            if(response == null || response.equals(BAD_REQUEST))
                System.out.println("[Client]: " + BAD_REQUEST);
            else
                System.out.println("[Client]: Responding time " + response);
        }finally {
            if(socket != null)  socket.close();
            if(in != null) in.close();
            if(out != null) out.close();
        }
    }
}
```

* 总结
实际上这个伪异步IO真的是很吃对于线程池的设置（executor的数量，最大数量，blocking queue的大小都很限制）。

### 非阻塞I/O
使用nio，对channel状况进行监听。
* 服务器端，接受信息，如果收到“TIME QUERY”会发送当前时间给服务器，如果接收到STOP会关闭服务器。
```Java
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
```

* 客户端
```Java
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
        if(selector != null){
            try {
                selector.close();
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
```

#### 异步I/O
借助了NIO2实现的完全异步I/O。
* 服务器端
```Java
public class AioTimerServer {
    public static void main(String[] args) throws IOException {
        new Thread(new AioTimerServerHandler(8080)).start();
    }
}

public class AioTimerServerHandler implements Runnable {
    private Integer port = null;
    private AsynchronousServerSocketChannel aChannel = null;
    public AsynchronousServerSocketChannel getChannel() {
        return aChannel;
    }
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private CountDownLatch latch = null;
    public CountDownLatch getLatch() {
        return latch;
    }
    public AioTimerServerHandler(Integer port) throws IOException {
        this.port = port;
        aChannel = AsynchronousServerSocketChannel.open();
        aChannel.bind(new InetSocketAddress(this.port), 1024);
        System.out.println("[Server]: Server starts at " + df.format(System.currentTimeMillis()));
    }
    public void run() {
        latch = new CountDownLatch(1);
        try {
            doAccept();
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void doAccept() throws InterruptedException {
        this.aChannel.accept(this, new AcceptCompletionHandler());
    }
}

public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AioTimerServerHandler> {
    @Override
    public void completed(AsynchronousSocketChannel result, AioTimerServerHandler attachment) {
        attachment.getChannel().accept(attachment, this);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        result.read(buffer, buffer, new ReadCompletionHandler(result, attachment));
    }

    @Override
    public void failed(Throwable exc, AioTimerServerHandler attachment) {
        attachment.getLatch().countDown();
    }
}

public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {
    private AsynchronousSocketChannel channel = null;
    private static final  String TOKEN = "QUERY TIME";
    private AioTimerServerHandler timerServerHandler = null;
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    public ReadCompletionHandler(AsynchronousSocketChannel result, AioTimerServerHandler attachment) {
        this.channel = result;
        this.timerServerHandler = attachment;
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        byte[] bytes = new byte[attachment.remaining()];
        attachment.get(bytes);
        String request = new String(bytes);
        if(TOKEN.equals(request)){
            System.out.println("[Server]: received message " + request);
            doWrite(df.format(System.currentTimeMillis()));
            System.out.println("[Server]: send a message to client.");
        }
    }

    private void doWrite(String response) {
        ByteBuffer attachment = ByteBuffer.allocate(1024);
        attachment.put(response.getBytes());
        attachment.flip();
        while (attachment.hasRemaining())
            this.channel.write(attachment, attachment, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    if(attachment.hasRemaining())
                        channel.write(attachment, attachment, this);
                    timerServerHandler.getLatch().countDown();
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    try {
                        channel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            this.channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

* 客户端
```Java
public class AioTimerClient {
    public static void main(String[] args) {
        new Thread(new AioClientHandler("127.0.0.1", 8080)).start();
    }
}
public class AioClientHandler implements Runnable, CompletionHandler<Void, AioClientHandler> {
    private String url = null;
    private Integer port = null;
    private AsynchronousSocketChannel channel = null;
    private static final  String TOKEN = "QUERY TIME";
    public CountDownLatch getLatch() {
        return latch;
    }

    private CountDownLatch latch = null;
    public AioClientHandler(String url, Integer port) {
        this.port = port;
        this.url = url;
        try {
            channel = AsynchronousSocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        latch = new CountDownLatch(1);
        channel.connect(new InetSocketAddress(this.url, this.port), this, this);
        try {
            latch.await();
            channel.close();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void completed(Void result, AioClientHandler attachment) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(TOKEN.getBytes());
        buffer.flip();
        channel.write(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                if(attachment.hasRemaining())
                    channel.write(attachment, attachment, this);
                else{
                    ByteBuffer buf = ByteBuffer.allocate(1024);
                    channel.read(buf, buf, new CompletionHandler<Integer, ByteBuffer>() {
                        @Override
                        public void completed(Integer result, ByteBuffer attachment) {
                            attachment.flip();
                            byte[] bytes = new byte[attachment.remaining()];
                            attachment.get(bytes);
                            String response = new String(bytes);
                            System.out.println("[Client]: current time is " + response);
                            latch.countDown();
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer attachment) {
                            exc.printStackTrace();
                            try {
                                channel.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                exc.printStackTrace();
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void failed(Throwable exc, AioClientHandler attachment) {
        exc.printStackTrace();
        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

#### 四种模型的效率
![Imgur](https://i.imgur.com/JyIomrD.png)

### Netty的入门应用
#### 通过Netty重写时间服务器和客户端
* Netty服务器端
```Java
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
```

* 服务器的处理流程
```Java
public class NettyTimerServerHandler extends ChannelInboundHandlerAdapter {
    private static final String TOKEN = "TIME QUERY";
    private static final DateFormat df  = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    /**
     * 当通道可读时的回调函数。
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buffer = (ByteBuf) msg;
        byte[] bytes = new byte[buffer.readableBytes()];
        buffer.readBytes(bytes);
        String request = new String(bytes);
        System.out.println("[Server]: server received request " + request);
        if(TOKEN.equals(request)){
            ByteBuf response = Unpooled.copiedBuffer(df.format(System.currentTimeMillis()).getBytes());
            ctx.writeAndFlush(response);
        }
    }

    /**
     * 通道读取完毕后的回调函数。
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /**
     * 通道发生异常时的回调函数。
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
```

* 客户端
```Java
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
```

* 客户端的回调函数
```Java
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    private static final String TOKEN = "TIME QUERY";
    private ByteBuf buf = null;
    public NettyClientHandler() {
        byte[] bytes = TOKEN.getBytes();
        buf = Unpooled.copiedBuffer(bytes);
    }

    /**
     * 当连接建立的时候，将数据写入通道中。
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(buf);
    }

    /**
     * 当通道可读时从通道中读出数据。
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buffer = (ByteBuf) msg;
        byte[] bytes = new byte[buffer.readableBytes()];
        buffer.readBytes(bytes);
        String response = new String(bytes);
        System.out.println("[Client]: current time is " + response);
    }

    /**
     * 异常时的回调函数。
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
```

### TCP的粘包（Package splicing）、拆包(Unpack)
TCP是流协议，双方建立了一条流通道，所有数据都是连在一起的，包和包之间在TCP流中是没有分界线的。这说明多个包可能被当成一个包接收，或是一个大包被分成多个小包接收。
![Imgur](https://i.imgur.com/0qBmb3h.png)
1. D1和D2按照先后顺序到达。
2. D1和D2作为一个大包到达。
3. D1和D2的一部分先到达，D2的剩下来的小包后到达。
4. 和3类似，D1一部分先到达，剩下的到达。
5. 还有多种情况出现。

#### TCP发生拆包的原因
1. 应用程序write写入的长度大于socket缓存区的长度，发送初端将大包分解。
2. 进行MSS的TCP分片。
3. 超过MTU的IP分片。

#### 未处理拆包的情况
* 服务器
```Java
public class TimerServer {
    public void bind(int port) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup clientGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup, clientGroup)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new TimerServerHandler());
                        }
                    });
            ChannelFuture future = server.bind(port).sync();
            future.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            clientGroup.shutdownGracefully();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        new TimerServer().bind(8080);
    }
}
```

* 服务器端响应的处理事件
```Java
public class TimerServerHandler extends ChannelInboundHandlerAdapter {
    private int count = 0;
    public static final String TOKEN = "QUERY TIME";
    public static final String BAD_REQUEST = "BAD REQUEST";
    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    /**
     * 通道可读时的回调函数，打印出接收到的请求的数量
     * 并响应。
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buffer = (ByteBuf)msg;
        byte[] bytes = new byte[buffer.readableBytes()];
        buffer.readBytes(bytes);
        String request = new String(bytes, "UTF-8");
        request = request.substring(0, request.length() - System.lineSeparator().length());
        System.out.println("[Server]: Server received request " + request + " " + ++count + "times.");
        String response = request.equals(TOKEN) ? df.format(System.currentTimeMillis()): BAD_REQUEST;
        ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
```

* 客户端
```Java
public class TimerClient {
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
                            ch.pipeline().addLast(new TimerClientHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect(url, port).sync();
            future.channel().closeFuture().sync();
        }finally {
            workerGroup.shutdownGracefully();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        new TimerClient().connect("127.0.0.1", 8080);
    }
}
```

* 客户端的响应
```Java
public class TimerClientHandler extends ChannelInboundHandlerAdapter {
    public static final String TOKEN = "QUERY TIME" + System.lineSeparator();
    public static final String BAD_REQUEST = "BAD REQUEST";
    public ByteBuf buffer = null;

    /**
     * 当通道可读时，发送100次请求。
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        byte[] bytes = TOKEN.getBytes();
        for (int i = 0; i < 100; i++){
            buffer = Unpooled.copiedBuffer(bytes);
            ctx.writeAndFlush(buffer);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf)msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        String response = new String(bytes, "UTF-8");
        System.out.println("[Client]: current time is " + response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
```

* 结果
实际上所有的请求都被接收到了，但是在服务器端很多请求都被粘包了，并没有将包正确的分割。

#### 处理TCP粘包
* 服务器
```Java
public class TimerServer {
    public void bind(int port) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup clientGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup, clientGroup)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            /**
                             * 添加了两个新的事件处理器
                             * 1. 通过line分割请求。
                             * 2. 将ByteBuf解析成String的解析器。
                             */
                            ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new TimerServerHandler());
                        }
                    });
            ChannelFuture future = server.bind(port).sync();
            future.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            clientGroup.shutdownGracefully();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        new TimerServer().bind(8080);
    }
}
```

* 客户端
```Java
public class TimerClient {
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
                            ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new TimerClientHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect(url, port).sync();
            future.channel().closeFuture().sync();
        }finally {
            workerGroup.shutdownGracefully();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        new TimerClient().connect("127.0.0.1", 8080);
    }
}
```

* 总结
1. 由于代码和上一部分重复性太高，就省略贴出了。
2. 通过linesplitor来分割请求。

### 分隔符和定长解码器
#### 分隔符 DelimiterBasedFrameDecoder
使用分隔符解码器的可以很好的解决TCP粘包的问题，会将收到的信息存入缓存区中，如果没有碰到分割符，就会一直存储下去直到到达最大长度。

#### 定长解码器
接收端会使用一个缓存区存储数据，一旦大一某个值就会刷出缓存区。

* 回显服务器服务器端
```Java
public class EchoServer {
    public void bind(int port) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap server  = new ServerBootstrap();
            server.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            /**
                             * 定义一个分隔符解码器，定义最大长度，分隔符
                             */
//                            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,true, false,
//                                    Unpooled.copiedBuffer(ServerSideConst.SPLITER.getBytes())));
                            /**
                             * 定义一个定长解码器，如果缓存区中的字符的长度大于设置值就会直接
                             * 刷出缓存区。
                             */
                            ch.pipeline().addLast(new FixedLengthFrameDecoder(10));
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new EchoServerHandler());
                        }
                    });
            ChannelFuture future = server.bind(port).sync();
            future.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        new EchoServer().bind(8080);
    }
}
```

* 回显服务器处理器
```Java
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
```

* 回显服务客户端
```Java
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
                            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,
                                    Unpooled.copiedBuffer(ServerSideConst.SPLITER.getBytes())));
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
```

### POJO的传递
在上面的例子中，我们传递的都是简单的字符码，但是在真正的开发中，我们会使用网络传递java对象。所以我们要使用java的序列化和反序列化技术。书中总共列举了四种技术：
1. Java本身的序列化技术
2. Google的ProtoBuf
3. Facebook的Thrift
4. JBoss的Marshalling

#### 使用Java的序列化技术
1. 类要实现Serializable接口并定义出UID。
```Java
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 2497464897604961378L;
    private Long id = null;
    private String name = null;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserInfo(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "[UserInfo: id = "+ getId() +"; name = "+ getName()+"]";
    }
}
```

2. 服务器端，增加了两个新的handler作为编解码器
```Java
public class SerializeServer {
    public void bind(int port) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup bworkGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup, bworkGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            /**
                             * 增加一个解码器用于解析类的序列化。
                             * 增加一个编码器用于将对象序列化。
                             */
                            ch.pipeline().addLast(new ObjectDecoder(1024 * 1024, ClassResolvers.weakCachingResolver(this.getClass().getClassLoader())));
                            ch.pipeline().addLast(new ObjectEncoder());
                            ch.pipeline().addLast(new SerializeServerHandler());
                        }
                    });
            ChannelFuture future = server.bind(port).sync();
            future.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            bworkGroup.shutdownGracefully();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        new SerializeServer().bind(8080);
    }
}
```

3. 实现句柄函数用于处理我们server的业务
```Java
public class SerializeServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        UserInfo info = (UserInfo)msg;
        System.out.println(info);
    }
}
```

4. 客户端和服务器端的重合度很高，取决于我们是否想要发送和接受序列化的对象。
```Java
public class SerializeClient {
    public void connect(String url, int port) throws InterruptedException {
        EventLoopGroup client = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(client)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ObjectDecoder(1024 * 1024, ClassResolvers.weakCachingResolver(this.getClass().getClassLoader())));
                            ch.pipeline().addLast(new ObjectEncoder());
                            ch.pipeline().addLast(new SerializeClientHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect(url, port).sync();
            future.channel().closeFuture().sync();
        }finally {
            client.shutdownGracefully();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        new SerializeClient().connect("127.0.0.1", 8080);
    }
}
```

5. 客户端的处理函数
```Java
public class SerializeClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        UserInfo info = new UserInfo(1L, "Sean");
        for(int i= 0; i < 10; i++)
            ctx.writeAndFlush(info);  //直接将对象写入context即可。
    }
}
```

### 使用Google Protobuf作为编解码器。
已经写了一篇文章总结总结了[google protobuf](https://github.com/Seanforfun/Java-Knowledge/blob/master/Conclusions/ProtoBuf.md)
1. 服务器端
```Java
public class ProtobufServer {
    public void bind(int port) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
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
                            ch.pipeline().addLast(new ProtobufServerHandler());
                        }
                    });
            ChannelFuture future = server.bind(port).sync();
            future.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        new ProtobufServer().bind(8080);
    }
}
```

2. 服务器端的句柄函数
```Java
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
```

3. 客户端
```Java
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
```

4. 客户端的句柄函数
```Java
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
```

### 引用
1. [Netty 4.x User Guide 中文翻译《Netty 4.x 用户指南》](https://waylau.com/netty-4-user-guide/)
2. [Netty](https://baike.baidu.com/item/Netty/10061624?fr=aladdin)
3. [nettybook2](https://github.com/wuyinxian124/nettybook2)
4. [Netty 权威指南（请购买正版书，该连接只提供短期阅读）](https://github.com/Seanforfun/Books/tree/master/Netty)
