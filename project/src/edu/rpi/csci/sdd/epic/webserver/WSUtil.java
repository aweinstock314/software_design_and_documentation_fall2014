package edu.rpi.csci.sdd.epic.webserver;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import com.sun.net.httpserver.HttpExchange;

import edu.rpi.csci.sdd.epic.util.Util;

// Web Server utils used to fetch strings or serve internal errors (handle internal messaging)
public class WSUtil
{
    // Serve string based on HTTP request.
    public static void serveString(HttpExchange e, int code, String s) throws IOException
    {
        e.sendResponseHeaders(code, s.length());
        // print appropriate string.
        PrintStream ps = new PrintStream(e.getResponseBody());
        ps.print(s);
    }
    // Serve internal error based on exception and HTTP exchange.
    public static void serveInternalError(HttpExchange exchange, Exception exception) throws IOException
    {
        // Based on exception and exchange determine error to serve, construct appropriate html, and serve it.
        StringBuilder sb = new StringBuilder();
        sb.append("<html><head><title>Error 500 - Internal Error</title></head><body>");
        sb.append("An internal error occurred: <br /><pre>\n");
        sb.append(Util.throwableToString(exception));
        sb.append("\n</pre></body></html>");
        serveString(exchange, 500, sb.toString());
    }
}
