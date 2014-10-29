package edu.rpi.csci.sdd.epic.webserver;

import com.sun.net.httpserver.HttpExchange;
import edu.rpi.csci.sdd.epic.db.AccountModel;
import edu.rpi.csci.sdd.epic.db.EventModel;
import edu.rpi.csci.sdd.epic.webserver.UserSessions;
import edu.rpi.csci.sdd.epic.util.Util;

public class SimpleTemplater
{
    protected final int port;
    //protected UserSessions us = new UserSessions();
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
                page = page.replace("<!--USERS-TABLE-->", Util.joinIterable(AccountModel.getUsersTable(), "<br />\n"));
            }
            if(page.contains("<!--REQUESTED-PAGE-->"))
            {
                String requestPath = e.getRequestURI().getPath();
                page = page.replace("<!--REQUESTED-PAGE-->", "epic.server.name:"+port+requestPath);
            }
            if(page.contains("<!--HANDLE-LOGIN-->"))
            {
                //us.doLogin(e);
            }
            if(page.contains("<!--INSERT-EVENTS-DB-AS-JS-ARRAY-->"))
            {
                //[{id: 0, host: "RPI", source: "localhost", creator: "propernoun123", recurring: true, starttime: 100, endtime: 1000, location: "The Union's McNeil room", on_campus: true, tags: ["tag1"]}]
                page = page.replace("<!--INSERT-EVENTS-DB-AS-JS-ARRAY-->", EventModel.getEventsAsJSArray());
            }
        }
        catch(Exception ex) { ex.printStackTrace(); }
        return page;
    }
}
