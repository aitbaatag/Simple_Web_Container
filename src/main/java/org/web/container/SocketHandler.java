package org.web.container;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

public class SocketHandler extends Thread {
    private Socket socket;
    private Map<String, HttpServlet> handlers;

    public SocketHandler(Socket socket, Map<String, HttpServlet> handlers) {
        this.socket = socket;
        this.handlers = handlers;
    }

    public void run() {
        try {
            InputStreamReader input = new InputStreamReader(socket.getInputStream());

            BufferedReader reader = new BufferedReader(input);
            String s = reader.readLine();
            while (!s.isEmpty()) {
                System.out.println(s);
                s = reader.readLine();
            }
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: text/html");
            out.println();
            out.println("<h1>Hello from my simple web server!</h1>");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
