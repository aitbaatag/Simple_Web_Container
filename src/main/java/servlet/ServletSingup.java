package servlet;

import org.web.container.FileServlet;
import org.web.container.HttpServlet;
import org.web.container.HttpServletRequest;
import org.web.container.HttpServletResponse;

public class ServletSingup extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getPath();
        String fileName;

        System.out.println(path);
        if ("/singup".equals(path)) {
            fileName = "SingUp/singup.html"; // Serve HTML
        } else if ("/stylesSingUp.css".equals(path)) {
            fileName = "SingUp/stylesSingUp.css"; // Serve CSS
        } else if ("/scriptSingUp.js".equals(path)) {
            fileName = "SingUp/scriptSingUp.js";
        } else {
//            response.getOutPutstream().write("404 Not Found".getBytes());
            return;
        }
        FileServlet fileServlet = new FileServlet(fileName, response.getOutPutstream());
        fileServlet.WriteToStream();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("doPost");
    }
}
