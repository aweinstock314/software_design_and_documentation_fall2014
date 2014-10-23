package edu.rpi.csci.sdd.epic.webserver;

import com.sun.net.httpserver.BasicAuthenticator;

public class ClearAuthenticator extends BasicAuthenticator
{
    public ClearAuthenticator(String realm) { super(realm); }
    public boolean checkCredentials(String username, String password) { return false; }
}
