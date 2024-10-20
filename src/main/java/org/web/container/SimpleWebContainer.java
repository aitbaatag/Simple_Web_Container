package org.web.container;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SimpleWebContainer {
    private final int port;
    private final String confiFileName;
    private Map<String, HttpServlet> handler = new HashMap<>();

    public SimpleWebContainer(int port, String confiFileName) {
        this.port = port;
        this.confiFileName = confiFileName;
    }

    private void start() throws IOException {

        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Web server is running on port 8888...");
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Client connected!");
            Thread socketHandler = new SocketHandler(socket, handler);
            socketHandler.start();
        }
    }

    private InputStream getResourceAsStream(String path) {
        return getClass().getClassLoader().getResourceAsStream(path);
    }

    private HttpServlet GetServletInstance(String className) {
        try {
            return (HttpServlet) Class.forName(className).getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadPropertiesFile() {
        try (InputStream input = getResourceAsStream(confiFileName)) {
            if (input == null) {
                System.out.println("Unable to find file : " + confiFileName);
                return;
            }
            Properties properties = new Properties();
            properties.load(input);
            properties.forEach((key, value) -> {
                HttpServlet httpServlet = GetServletInstance((String) value);
                handler.put((String) key, httpServlet);
            });
        } catch (IOException e) {
            System.out.println("Error while loading properties file: " + e.getMessage());
        }

    }

    public static void main(String[] args) throws IOException {
        SimpleWebContainer WebContainer = new SimpleWebContainer(8888, "config.properties"); // Listen on port 8080
        WebContainer.loadPropertiesFile();
        WebContainer.start();
    }
}
