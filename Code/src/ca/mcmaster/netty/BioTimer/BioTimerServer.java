package bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
