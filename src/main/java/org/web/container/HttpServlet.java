package org.web.container;

public abstract class HttpServlet {

    public void init() {
        
    }

    public void service() { //TODO

    }

    public void doGet() {
        System.out.println("HttpServlet doGet() Default Impl... ");
    }

    public void doPost() {
        System.out.println("HttpServlet doPost() Default Impl... ");
    }
}
