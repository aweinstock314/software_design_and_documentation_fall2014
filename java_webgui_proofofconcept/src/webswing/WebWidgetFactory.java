package webswing;

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

public class WebWidgetFactory implements HttpHandler
{
    private WebAwtComponent mainWidget = null;
    @Override
    public void handle(HttpExchange e) throws IOException
    {
        System.out.printf("Handling a request to %s\n", e.getRemoteAddress());
        String response = mainWidget.generateHTML();
        e.sendResponseHeaders(200, response.length());
        PrintStream ps = new PrintStream(e.getResponseBody());
        ps.print(response);
        e.close();
    }
    public WebWidgetFactory() {}
    public void beginServing(final int port)
    {
        final HttpHandler outerThis = this; // "this" inside the runnable definition refers to the runnable
        new Thread(new Runnable() { @Override public void run() {
            try
            {
                //ServerSocket sock = new ServerSocket(port);
                HttpServer serv = HttpServer.create(new InetSocketAddress(port), -1);
                serv.start();
                //serv.createContext("/", new HelloWorldResponse());
                serv.createContext("/", outerThis);
                System.out.printf("Bound to port %s\n", port);
                //serv.
            }
            catch(Exception e) {e.printStackTrace();}
        }}).start();
    }
    public WebJFrame JFrame(String title)
    {
        WebJFrame jf = new WebJFrame(title);
        if(mainWidget != null) { ((WebJFrame)mainWidget).getContentPane().add(jf); }
        else { mainWidget = jf; }
        return jf;
    }
    public WebJLabel JLabel(String val)
    {
        WebJLabel jl = new WebJLabel(val);
        return jl;
    }
    //public static void main(String[] args) { new WebWidgetFactory().beginServing(8000); }
}
