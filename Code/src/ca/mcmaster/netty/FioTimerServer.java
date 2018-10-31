package fnio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
