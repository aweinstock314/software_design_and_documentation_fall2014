package edu.rpi.csci.sdd.epic.webserver;

import java.io.BufferedReader;
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
            //String response = "<html><head><title>Hello</title></head><body>world</body></html>";
            File fileToServe = new File(directoryToServe, requestPath);
            String response = slurpFile(fileToServe);
            int code = 200;
            if(response == null)
            {
                code = 404;
                response = slurpFile(new File(directoryToServe, "notfound404.html")).replace("$REQUESTED_PAGE", "epic.server.name:"+port+requestPath);
            }
            e.sendResponseHeaders(code, response.length());
            PrintStream ps = new PrintStream(e.getResponseBody());
            ps.print(response);
            e.close();
        }
        catch(IOException ex) { ex.printStackTrace(); throw ex; }
        catch(Exception ex) { ex.printStackTrace(); }
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
