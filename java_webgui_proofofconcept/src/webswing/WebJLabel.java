package webswing;

public class WebJLabel extends WebAwtComponent
{
    private String labeldata = "";
    public String generateHTML()
    {
        StringBuilder sb = new StringBuilder();
        //TODO: either escape or verify as balanced the labeldata
        sb.append(String.format("<span id=\"%d\">%s</span>", getID(), labeldata));
        return sb.toString();
    }
    public WebJLabel(String val) { labeldata = val; }
}
