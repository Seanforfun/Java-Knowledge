package fnio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
