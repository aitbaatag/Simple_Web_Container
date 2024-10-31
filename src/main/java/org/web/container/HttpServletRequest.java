package org.web.container;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class HttpServletRequest {
    private BufferedReader in;
    private String method;
    private String path;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> requestParameters = new HashMap<>();

    public HttpServletRequest(BufferedReader in) {
        this.in = in;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getRequestParameters() {
        return requestParameters;
    }

    public void parseParameters(String Parameters) throws IOException {
        String[] Parts = Parameters.split("\\?");
        path = Parts[0];
//        System.out.println("jj");
        String queryParams = path.length() > 1 ? Parts[1] : "";
        if (!queryParams.isEmpty()) {
            String[] keyValuePairs = queryParams.split("&");
            for (String Pair : keyValuePairs) {
                String[] keyValue = Pair.split("=");
                if (keyValue.length == 2) {
                    requestParameters.put(keyValue[0], keyValue[1]);
                }
            }
        }

    }

    public boolean parseRequest() throws IOException {
        String line = in.readLine(); //GET /hello?user=username&password=pass HTTP/1.1
        //GET /hello
        String[] S = line.split(" ");
        if (S.length != 3)
            return (false);
        method = S[0];
        String url = S[1];
//        System.out.println(url); //TEST
        if (url.contains("?")) {
            parseParameters(url);
        } else
            path = url;
        while ((line = in.readLine()) != null && !line.isEmpty()) {
            if (line.contains(":")) {
                String[] headerPair = line.split(":", 2); // Split into two parts (key and value)
                if (headerPair.length == 2) {
                    headers.put(headerPair[0].trim(), headerPair[1].trim());  // Trim whitespace
                }
            }
        }

        if ("POST".equals(method))
        {
            String body = in.readLine();
            System.out.println(body);
        }
        return true;
    }
}
