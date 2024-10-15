package servlet;

import org.web.container.HttpServlet;

public class ServletHello extends HttpServlet {
    @Override
    public void doGet() {
        System.out.println("Hello World doGet().. ");
    }
}
