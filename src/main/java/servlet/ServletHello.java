package servlet;

import org.web.container.FileServlet;
import org.web.container.HttpServlet;
import org.web.container.HttpServletRequest;
import org.web.container.HttpServletResponse;

public class ServletHello extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getPath();
        String fileName;

        System.out.println(path);
        if ("/hello".equals(path)) {
            fileName = "HelloPage/hello.html"; // Serve HTML
        } else if ("/style.css".equals(path)) {
            fileName = "HelloPage/style.css"; // Serve CSS
        } else if ("/script.js".equals(path)) {
            fileName = "HelloPage/script.js";
        } else {
//            response.getOutPutstream().write("404 Not Found".getBytes());
            return;
        }
//        System.out.println(fileName);
        FileServlet fileServlet = new FileServlet(fileName, response.getOutPutstream());
        fileServlet.WriteToStream();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
    }
}
