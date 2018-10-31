# Netty Conclusion

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
