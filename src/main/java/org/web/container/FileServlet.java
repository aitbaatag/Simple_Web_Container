package org.web.container;

import java.io.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileServlet {
    private final String fileName;
    private PrintWriter printWriter;
    private OutputStream outputStream;
    private byte[] fileContent;

    public FileServlet(String fileName, OutputStream outputStream) {
        this.printWriter = new PrintWriter(outputStream);
        this.fileName = fileName;
        this.outputStream = outputStream;
    }

    private InputStream getResourceAsStream(String path) {
        return getClass().getClassLoader().getResourceAsStream(path);
    }

    private String GetContentType() {
        if (fileName.endsWith(".html")) {
            return "text/html";
        } else if (fileName.endsWith(".css")) {
            return "text/css";
        } else if (fileName.endsWith(".js")) {
            return "application/javascript";
        } else if (fileName.endsWith(".png")) {
            return "image/png";
        } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (fileName.endsWith(".gif")) {
            return "image/gif";
        }
        return "application/octet-stream";
    }

    private void ReadFile() {
        try {
            CustomServletContext customServletContext = new CustomServletContext();
            String realPath = customServletContext.getRealPath(fileName);
            System.out.println("Attempting to read file from path: " + realPath);

            // Check if the file exists
            if (!Files.exists(Paths.get(realPath))) {
                System.out.println("File does not exist at: " + realPath);
                return;
            }

            // Proceed to read the file
            InputStream input = new FileInputStream(realPath);
            fileContent = input.readAllBytes();
            System.out.println("File successfully read!");
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void WriteToStream() {
        String contentType;
        ReadFile();
        try {
            if (fileContent == null || fileContent.length == 0) {
                String responseHeader = "HTTP/1.1 404 Not Found\r\n" +
                        "Content-Type: text/html\r\n\r\n" +
                        "<h1>404 Not Found</h1>";
                outputStream.write(responseHeader.getBytes());
                printWriter.flush();
                return;
            }
            contentType = GetContentType();
            String responseHeader = "HTTP/1.1 200 OK\r\n" + "Content-Length: " + fileContent.length + "\r\n" + "Content-Type: " + contentType + "\r\n\r\n";
            outputStream.write(responseHeader.getBytes());
            outputStream.write(fileContent);
            printWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
