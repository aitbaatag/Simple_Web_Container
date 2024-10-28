package org.web.container;

public abstract class HttpServlet {

    public void init() {
        System.out.println("HttpServlet init() Default Impl... ");
    }

    public void service(HttpServletRequest request, HttpServletResponse response) {
        String method = request.getMethod();
        if ("GET".equals(method))
            this.doGet(request, response);
        else if ("POST".equals(method))
            this.doPost(request, response);
        else
            throw new RuntimeException("Method not Supported!");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("HttpServlet doGet() Default Impl... ");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("HttpServlet doPost() Default Impl... ");
    }
    public void destroy() {
        System.out.println("HttpServlet destroy() Default Impl... ");
    }
}
