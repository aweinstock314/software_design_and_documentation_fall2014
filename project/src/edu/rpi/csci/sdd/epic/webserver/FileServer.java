package edu.rpi.csci.sdd.epic.webserver;

import java.io.File;
import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import edu.rpi.csci.sdd.epic.util.Util;

public class FileServer implements HttpHandler
{
    protected final File directoryToServe;
    protected final SimpleTemplater templater;
    public FileServer(File f, int p)
    {
        File dir = (f.isDirectory()) ? f : f.getParentFile();
        directoryToServe = dir;
        templater = new SimpleTemplater(p);
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
            Exception ex = getContentToServe(e, contentPtr, codePtr);
            if(ex == null) { WSUtil.serveString(e, codePtr[0], templater.template(e, contentPtr[0])); }
            else { WSUtil.serveInternalError(e, ex); }
        }
        catch(IOException ex) { ex.printStackTrace(); throw ex; }
        finally { e.close(); }
    }
    protected Exception getContentToServe(HttpExchange e, String[] outContent, int[] outCode)
    {
        Exception ex = null;
        try
        {
            String requestPath = e.getRequestURI().getPath();
            File fileToServe = new File(directoryToServe, requestPath);
            if(fileToServe.isDirectory()) { fileToServe = new File(fileToServe, "index.html"); }
            outContent[0] = Util.slurpFile(fileToServe);
            outCode[0] = 200;
            if(outContent[0] == null)
            {
                outCode[0] = 404;
                outContent[0] = templater.template(e, Util.slurpFile(new File(directoryToServe, "notfound404.html")));
            }
        }
        catch(Exception exc)
        {
            ex = exc;
            exc.printStackTrace();
        }
        return ex;
    }
}
