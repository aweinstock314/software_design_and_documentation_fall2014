package edu.rpi.csci.sdd.epic.webserver;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import edu.rpi.csci.sdd.epic.util.Util;

public class WebServer
{
    // the port number that this webserver serves to
    protected final int port;
    // the FileServer object that this webserver delegates fileserving to
    protected final FileServer fileServer;
    // construct a webserver serving directory f on port p
    public WebServer(File f, int p)
    {
        // save a copy of the port in an instance variable
        port = p;
        // create a FileServer to delegate to
        fileServer = new FileServer(f, p);
        // begin serving on the specified port
        beginServing();
    }
    // begin serving this webserver's content on a new thread
    protected void beginServing()
    {
        // make a new thread
        new Thread(new Runnable() { @Override public void run() {
            try
            {
                // make an HttpServer object
                HttpServer serv = HttpServer.create(new InetSocketAddress(port), -1);
                // start the HttpServer
                serv.start();
                // arrange delegation to the fileserver for all urls without a more specific handler
                serv.createContext("/", fileServer);
                // arrange delegation to the fileserver for urls starting 
                // with "needsauth", with permissions handled by a 
                // UserSessions object
                serv.createContext("/needsauth/", fileServer).setAuthenticator(new UserSessions("epic_app"));
                // arrange for the "clearcreds" page to clear authentication credentials
                serv.createContext("/clearcreds", fileServer).setAuthenticator(new ClearAuthenticator("epic_app"));
                // arrange for the "createaccount" page to delegate to the CreateAccount POST processor
                serv.createContext("/createaccount", new CreateAccount());
                // arrange for the "createevent" page to delegate to the CreateEvent POST processor
                serv.createContext("/createevent", new CreateEvent());
                // arrange for the "signin" page to delegate to the TryLogin POST processor
                serv.createContext("/signin", new TryLogin());
                // announce to the console that this webserver is serving on the port it is serving on
                System.out.printf("Bound to port %s\n", port);
            }
            // print out a stack trace if there are any exceptions
            catch(Exception e) {e.printStackTrace();}
        }}).start();
    }

    // main is the starting point of the program
    public static void main(String[] args)
    {
        // initialize the Shiro SecurityManager
        UserSessions.setSecurityManager();
        // start a webserver that serves the "web" directory on port 8000
        WebServer ws = new WebServer(new File("web/"), 8000);
    }
}
