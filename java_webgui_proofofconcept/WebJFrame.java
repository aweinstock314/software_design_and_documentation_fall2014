import java.util.ArrayList;

public class WebJFrame extends WebAwtComponent
{
    private String frameTitle = "";
    private ArrayList<WebAwtComponent> frameContent = new ArrayList<WebAwtComponent>();
    public void setDefaultCloseOperation(int op) {} // currently a NOP for compatibility with JFrame interface, may eventually do something involving JS-spawned windows
    public void pack() {} public void setVisible(boolean vis) {} // TODO: figure out if non-NOPs make sense for these
    public String generateHTML()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><head><title>");
        sb.append(frameTitle); //TODO: escaping?
        sb.append("</title></head><body>");
        for(WebAwtComponent c : frameContent)
        {
            sb.append(c.generateHTML());
        }
        sb.append("</html>");
        return sb.toString();
    }
    //TODO: more constructors
    public WebJFrame(String title) { frameTitle = title; }
    public class ContentPane
    {
        void add(WebAwtComponent c) { frameContent.add(c); }
    }
    private ContentPane frameContentPane = new ContentPane();
    public ContentPane getContentPane() { return frameContentPane; }
}
