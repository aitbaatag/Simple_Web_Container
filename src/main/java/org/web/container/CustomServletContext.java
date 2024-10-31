package org.web.container;


import java.nio.file.Path;
import java.nio.file.Paths;

public class CustomServletContext {

    // Define the base path to mimic the web root directory.
    private static final String BASE_PATH = "src/main/webapp";

    /**
     * This method simulates getServletContext().getRealPath()
     * by appending the relative path to the BASE_PATH.
     *
     * @param relativePath the relative path within the web root directory.
     * @return the absolute path as a String.
     */
    public String getRealPath(String relativePath) {
        // Combine BASE_PATH with the provided relative path.
        Path absolutePath = Paths.get(BASE_PATH, relativePath);
        return absolutePath.toAbsolutePath().toString();
    }
}
