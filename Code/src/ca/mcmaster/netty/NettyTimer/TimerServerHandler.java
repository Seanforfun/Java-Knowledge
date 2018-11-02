package fnio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
