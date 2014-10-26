package edu.rpi.csci.sdd.epic.webserver;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import edu.rpi.csci.sdd.epic.db.AccountModel;
import edu.rpi.csci.sdd.epic.util.Util;

public class CreateAccount implements HttpHandler
{
    @Override
    public void handle(HttpExchange e) throws IOException
    {
        //Headers headers = e.getRequestHeaders();
        //System.out.printf("url: %s\n", e.getRequestURI().toString());
        //System.out.printf("requestBody: \"%s\"\n", Util.slurpStream(e.getRequestBody()));
        //for(Map.Entry<String, List<String>> entry : headers.entrySet())
        //{
        //    System.out.printf("key: %s; values: %s\n", entry.getKey(), Util.joinList(entry.getValue(), ", "));
        //}
        TreeMap<String, String> theMap = new TreeMap<String, String>();
        String postData = Util.slurpStream(e.getRequestBody());
        for(String kvp : postData.split("&"))
        {
            String[] kv = kvp.split("=");
            theMap.put(kv[0], kv[1]);
        }
        try
        {
            String id = theMap.get("createaccount_id");
            boolean event_provider = Boolean.valueOf(theMap.get("createaccount_eventprovider"));
            String email_address = theMap.get("createaccount_email_address");
            String username = theMap.get("createaccount_username");
            String password = theMap.get("createaccount_password");
            AccountModel.createAccount(id, event_provider, email_address, username, password);
            WebServer.serveString(e, 200, "<html><head><title>Account created</title></head><body>Account created successfully.</body></html>");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            WebServer.serveInternalError(e, ex);
        }
    }
}
