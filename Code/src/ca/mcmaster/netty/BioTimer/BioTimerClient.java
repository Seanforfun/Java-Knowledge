package bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
