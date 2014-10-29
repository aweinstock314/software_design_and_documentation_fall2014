package edu.rpi.csci.sdd.epic.webserver;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import edu.rpi.csci.sdd.epic.util.Util;

public abstract class PostRequestProcessor implements HttpHandler
{
    private static final boolean DEBUG_MODE = true;
    protected abstract String processPostRequest(Map<String, String> postPairs) throws Exception;
    protected boolean parseCheckbox(String s) { return (s != null) && (s.startsWith("on")); }

    @Override
    public void handle(HttpExchange e) throws IOException
    {
        Map<String, String> postPairs = new TreeMap<String, String>();
        String postData = Util.slurpStream(e.getRequestBody());
        if(DEBUG_MODE) { System.out.printf("Post request for page \"%s\" (from \"%s\")\n", e.getRequestURI(), e.getRemoteAddress()); }
        for(String kvp : postData.trim().split("&"))
        {
            String[] kv = kvp.split("=");
            postPairs.put(kv[0], kv[1]);
            if(DEBUG_MODE) { System.out.printf("\t\"%s\": \"%s\"\n", kv[0], kv[1]); }
        }
        try
        {
            WSUtil.serveString(e, 200, processPostRequest(postPairs));
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            WSUtil.serveInternalError(e, ex);
        }
    }
}
