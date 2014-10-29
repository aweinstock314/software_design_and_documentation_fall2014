package edu.rpi.csci.sdd.epic.webserver;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import edu.rpi.csci.sdd.epic.util.Util;

public class WebServer
{
    protected final int port;
    protected final FileServer fileServer;
    public WebServer(File f, int p)
    {
        port = p;
        fileServer = new FileServer(f, p);
        beginServing();
    }
    protected void beginServing()
    {
        new Thread(new Runnable() { @Override public void run() {
            try
            {
                HttpServer serv = HttpServer.create(new InetSocketAddress(port), -1);
                serv.start();
                serv.createContext("/", fileServer);
                serv.createContext("/needsauth/", fileServer).setAuthenticator(new UserSessions("epic_app"));
                serv.createContext("/clearcreds", fileServer).setAuthenticator(new ClearAuthenticator("epic_app"));
                serv.createContext("/createaccount", new CreateAccount());
                serv.createContext("/createevent", new CreateEvent());
                System.out.printf("Bound to port %s\n", port);
            }
            catch(Exception e) {e.printStackTrace();}
        }}).start();
    }

    public static void main(String[] args)
    {
        UserSessions.setSecurityManager();
        WebServer ws = new WebServer(new File("web/"), 8000);
    }
}
