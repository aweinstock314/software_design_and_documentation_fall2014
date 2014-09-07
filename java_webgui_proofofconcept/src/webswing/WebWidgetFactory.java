package webswing;

//import java.net.ServerSocket;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/*class HelloWorldResponse implements HttpHandler
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
}*/

public class WebWidgetFactory implements HttpHandler
{
    private ArrayList<Object> widgets = new ArrayList<Object>();
    @Override
    public void handle(HttpExchange e) throws IOException
    {
        try
        {
            System.out.printf("Handling a request to %s\n", e.getRemoteAddress());
            HTMLEmissionVisitor emitter = new HTMLEmissionVisitor();
            for(Object widget : widgets) { emitter.visit(widget); }
            String response = emitter.getGeneratedHtml();
            e.sendResponseHeaders(200, response.length());
            PrintStream ps = new PrintStream(e.getResponseBody());
            ps.print(response);
            e.close();
        }
        catch(IOException ex) { throw ex; }
        catch(Exception ex) { ex.printStackTrace(); }
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
        widgets.add(jf);
        return jf;
    }
    public WebJLabel JLabel(String val)
    {
        WebJLabel jl = new WebJLabel(val);
        return jl;
    }
    //public static void main(String[] args) { new WebWidgetFactory().beginServing(8000); }
}
