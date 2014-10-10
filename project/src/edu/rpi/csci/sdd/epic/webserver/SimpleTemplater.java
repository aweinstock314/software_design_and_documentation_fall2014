package edu.rpi.csci.sdd.epic.webserver;

import edu.rpi.csci.sdd.epic.util.Util;

public class SimpleTemplater
{
    public String template(String page)
    {
        try
        {
            if(page.contains("<!--USERS-TABLE-->"))
            {
                page = page.replace("<!--USERS-TABLE-->", Util.joinList(Util.getUsersTable(), "<br />\n"));
            }
        }
        catch(Exception e) { e.printStackTrace(); }
        return page;
    }
}
