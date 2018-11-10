package com.shaff.carshop.web.filters.wrappers;

import com.shaff.carshop.utils.compressing.GzipStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class GZIPResponseWrapper extends HttpServletResponseWrapper {
    private static final String ENCODING = "UTF-8";
    private HttpServletResponse resp;
    private ServletOutputStream outputStream = null;
    private PrintWriter printWriter = null;

    public GZIPResponseWrapper(HttpServletResponse response) {
        super(response);
        this.resp = response;
    }

    public void closeStreams() throws IOException {
        if (outputStream != null) {
            this.outputStream.close();
        }

        if (printWriter != null) {
            this.printWriter.close();
        }
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (outputStream != null) {
            return outputStream;
        }

        if (printWriter != null) {
            throw new IllegalStateException("PrintWriter has already opened");
        }

        outputStream = new GzipStream(resp);
        return outputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (printWriter != null) {
            return printWriter;
        }

        if (outputStream != null) {
            throw new IllegalStateException("OutputStream has already opened");
        }

        outputStream = new GzipStream(resp);
        printWriter = new PrintWriter(new OutputStreamWriter(outputStream, ENCODING));
        return printWriter;
    }
}