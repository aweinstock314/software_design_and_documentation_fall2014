package edu.rpi.csci.sdd.epic.webserver;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class WebServer implements HttpHandler
{
    protected final File directoryToServe;
    protected final int port;
    public WebServer(File f, int p)
    {
        File dir = (f.isDirectory()) ? f : f.getParentFile();
        directoryToServe = dir;
        port = p;
        beginServing();
    }
    @Override
    public void handle(HttpExchange e) throws IOException
    {
        try
        {
            System.out.printf("Handling a request to %s\n", e.getRemoteAddress());
            String requestPath = e.getRequestURI().getPath();
            System.out.printf("Request URI: \"%s\"\n", requestPath);
            String[] contentPtr = new String[1];
            int[] codePtr = new int[1];
            Exception ex = getContentToServe(requestPath, contentPtr, codePtr);
            if(ex == null) { serveString(e, codePtr[0], contentPtr[0]); }
            else { serveInternalError(e, ex); }
        }
        catch(IOException ex) { ex.printStackTrace(); throw ex; }
        finally { e.close(); }
    }
    protected void serveString(HttpExchange e, int code, String s) throws IOException
    {
        e.sendResponseHeaders(code, s.length());
        PrintStream ps = new PrintStream(e.getResponseBody());
        ps.print(s);
    }
    protected String throwableToString(Throwable t)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        t.printStackTrace(new PrintStream(baos));
        return baos.toString();
    }
    protected void serveInternalError(HttpExchange exchange, Exception exception) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><head><title>Error 500 - Internal Error</title></head><body>");
        sb.append("An internal error occurred: <br /><pre>\n");
        sb.append(throwableToString(exception));
        sb.append("\n</pre></body></html>");
        serveString(exchange, 500, sb.toString());
    }
    protected Exception getContentToServe(String requestPath, String[] outContent, int[] outCode)
    {
        Exception ex = null;
        try
        {
            File fileToServe = new File(directoryToServe, requestPath);
            outContent[0] = slurpFile(fileToServe);
            outCode[0] = 200;
            if(outContent[0] == null)
            {
                outCode[0] = 404;
                outContent[0] = slurpFile(new File(directoryToServe, "notfound404.html")).replace("$REQUESTED_PAGE", "epic.server.name:"+port+requestPath);
            }
        }
        catch(Exception e)
        {
            ex = e;
            e.printStackTrace();
        }
        return ex;
    }
    protected String slurpFile(File f) throws IOException
    {
        if(!f.exists()) { return null; }
        BufferedReader r = new BufferedReader(new FileReader(f));
        StringBuilder sb = new StringBuilder();
        String tmp;
        while((tmp = r.readLine()) != null) { sb.append(tmp); sb.append("\n"); }
        r.close();
        return sb.toString();
    }
    protected void beginServing()
    {
        final HttpHandler outerThis = this; // "this" inside the runnable definition refers to the runnable
        new Thread(new Runnable() { @Override public void run() {
            try
            {
                HttpServer serv = HttpServer.create(new InetSocketAddress(port), -1);
                serv.start();
                serv.createContext("/", outerThis);
                System.out.printf("Bound to port %s\n", port);
            }
            catch(Exception e) {e.printStackTrace();}
        }}).start();
    }

    public static void main(String[] args)
    {
        WebServer ws = new WebServer(new File("web/"), 8000);
    }
}
