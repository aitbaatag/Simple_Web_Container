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
            HttpServletRequest httpServletRequest = new HttpServletRequest(reader);
            if (httpServletRequest == null)
            {
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                out.println("HTTP/1.1 500 Internal Server Error");
                out.println("Content-Type: text/plain");
                out.println();
                out.println("<html><body>Cannot process your request </body></html>");
                out.flush();
            }
            else
            {
                HttpServlet servlet = handlers.get(httpServletRequest.getPath());
                if (servlet == null)
                {
                    // Handle case where no servlet matches the request path
                    PrintWriter out = new PrintWriter(socket.getOutputStream());
                    out.println("HTTP/1.1 404 Not Found");
                    out.println("Content-Type: text/html");
                    out.println();
                    out.println("<html><body><h1>404 Not Found</h1></body></html>");
                    out.flush();
                }
                else
                {
                    HttpServletResponse httpServletResponse = new HttpServletResponse(socket.getOutputStream());
                    servlet.service(httpServletRequest, httpServletResponse);
                }
            }
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
