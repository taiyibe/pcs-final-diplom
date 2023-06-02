import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {
        BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));

        try (ServerSocket serverSocket = new ServerSocket(8989);) {
            while (true) {
                try (Socket clienSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clienSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clienSocket.getInputStream()));
                ) {
                    String req = in.readLine();
                    var res = engine.search(req);
                    ObjectMapper mapper = new ObjectMapper();
                    StringWriter writer = new StringWriter();
                    mapper.writeValue(writer, res);

                    out.println(writer);
                }
            }
        } catch (IOException e) {
            System.out.println("Can't start server");
            e.printStackTrace();
        }

    }
}