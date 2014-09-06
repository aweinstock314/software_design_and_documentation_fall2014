//import java.net.ServerSocket;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

class HelloWorldResponse implements HttpHandler
{
    @Override
    public void handle(HttpExchange e) throws IOException
    {
        System.out.printf("Handling a request to %s\n", e.getRemoteAddress());
        e.sendResponseHeaders(200, 0);
        PrintStream ps = new PrintStream(e.getResponseBody());
        ps.println("<html><head><title>Hello world web page</title></head><body>Hello, World!</body></html>");
        e.close();
    }
}

public class WebWidgetFactory
{
    public WebWidgetFactory() {}
    public void beginServing(final int port)
    {
        new Thread(new Runnable() { @Override public void run() {
            try
            {
                //ServerSocket sock = new ServerSocket(port);
                HttpServer serv = HttpServer.create(new InetSocketAddress(port), -1);
                serv.start();
                serv.createContext("/", new HelloWorldResponse());
                System.out.printf("Bound to port %s\n", port);
                //serv.
            }
            catch(Exception e) {e.printStackTrace();}
        }}).start();
    }
    public static void main(String[] args)
    {
        new WebWidgetFactory().beginServing(8000);
    }
}
