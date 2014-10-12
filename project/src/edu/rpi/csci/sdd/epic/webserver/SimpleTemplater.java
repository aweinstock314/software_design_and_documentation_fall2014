package edu.rpi.csci.sdd.epic.webserver;

import com.sun.net.httpserver.HttpExchange;
import edu.rpi.csci.sdd.epic.util.Util;

public class SimpleTemplater
{
    protected final int port;
    public SimpleTemplater(int p)
    {
        port = p;
    }
    public String template(HttpExchange e, String page)
    {
        try
        {
            if(page.contains("<!--USERS-TABLE-->"))
            {
                page = page.replace("<!--USERS-TABLE-->", Util.joinList(Util.getUsersTable(), "<br />\n"));
            }
            if(page.contains("<!--REQUESTED-PAGE-->"))
            {
                String requestPath = e.getRequestURI().getPath();
                page = page.replace("<!--REQUESTED-PAGE-->", "epic.server.name:"+port+requestPath);
            }
        }
        catch(Exception ex) { ex.printStackTrace(); }
        return page;
    }
}
