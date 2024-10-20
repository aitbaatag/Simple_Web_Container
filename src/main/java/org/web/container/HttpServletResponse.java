package org.web.container;

import java.io.OutputStream;
import java.io.PrintWriter;

public class HttpServletResponse {
    private OutputStream out;
    private PrintWriter printWriter;

    public HttpServletResponse(OutputStream out) {
        this.printWriter = new PrintWriter(out);
        this.out = out;
    }

    public OutputStream getOutPutstream() {
        return out;
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }
}
