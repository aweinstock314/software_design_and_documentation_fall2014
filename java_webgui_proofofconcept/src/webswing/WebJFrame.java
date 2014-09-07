package webswing;

import java.util.ArrayList;

public class WebJFrame extends WebAwtComponent
{
    private String frameTitle = "";
    private ArrayList<WebAwtComponent> frameContent = new ArrayList<WebAwtComponent>();
    public void setDefaultCloseOperation(int op) {} // currently a NOP for compatibility with JFrame interface, may eventually do something involving JS-spawned windows
    public void pack() {} public void setVisible(boolean vis) {} // TODO: figure out if non-NOPs make sense for these
    public String getTitle() { return frameTitle; }
    public String generateHTML()
    {
        StringBuilder sb = new StringBuilder();
        /*sb.append("<html><head><title>");
        sb.append(frameTitle); //TODO: escaping?
        sb.append("</title></head><body>");*/
        sb.append(String.format("<div id=\"%s\">", getID()));
        for(WebAwtComponent c : frameContent)
        {
            sb.append(c.generateHTML());
        }
        sb.append("</div>");
        sb.append("</html>");
        return sb.toString();
    }
    public String generateCSS()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("#%s {border: 2px solid; border-radius: 10px;}", getID()));
        return sb.toString();
    }
    //TODO: more constructors
    public WebJFrame(String title) { frameTitle = title; }
    public class ContentPane
    {
        public void add(WebAwtComponent c) { frameContent.add(c); }
    }
    private ContentPane frameContentPane = new ContentPane();
    public ContentPane getContentPane() { return frameContentPane; }
}
