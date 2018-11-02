package bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
