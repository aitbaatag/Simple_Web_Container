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
        if (url.contains("?")) {
            parseParameters(url);
        } else
            path = url;
        while (!line.isEmpty())
        {
            if (!"".equals(line)) {
                String[] Headerpair = line.split(":");
                headers.put(Headerpair[0], Headerpair[1]);
                line = in.readLine();
            }
        }
        return true;
    }
}
