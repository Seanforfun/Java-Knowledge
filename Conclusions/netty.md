# Netty Conclusion
Netty是由JBOSS提供的一个java开源框架。Netty提供异步的、事件驱动的网络应用程序框架和工具，用以快速开发高性能、高可靠性的网络服务器和客户端程序。

Netty整合了nio的基础设施，并且提供了对于多种协议，相比于Tomcat只对HTTP进行支持，Netty易于我们扩展自己的第三方协议并且稳定性较强。

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

### 引用
1. [Netty 4.x User Guide 中文翻译《Netty 4.x 用户指南》](https://waylau.com/netty-4-user-guide/)
2. [Netty](https://baike.baidu.com/item/Netty/10061624?fr=aladdin)