package edu.rpi.csci.sdd.epic.webserver;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import com.sun.net.httpserver.HttpExchange;

import edu.rpi.csci.sdd.epic.util.Util;

public class WSUtil
{
    public static void serveString(HttpExchange e, int code, String s) throws IOException
    {
        e.sendResponseHeaders(code, s.length());
        PrintStream ps = new PrintStream(e.getResponseBody());
        ps.print(s);
    }
    public static void serveInternalError(HttpExchange exchange, Exception exception) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><head><title>Error 500 - Internal Error</title></head><body>");
        sb.append("An internal error occurred: <br /><pre>\n");
        sb.append(Util.throwableToString(exception));
        sb.append("\n</pre></body></html>");
        serveString(exchange, 500, sb.toString());
    }
}
