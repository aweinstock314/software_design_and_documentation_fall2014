package webswing;

public abstract class WebAwtComponent
{
    // ID mechanics to allow referring to specific components for JS/CSS
    private static long IDGensymCounter = 0;
    private long ID = ++IDGensymCounter;
    public String getID() { return String.format("id%d", ID); }

    public abstract String generateHTML();
    public String generateCSS() { return ""; }
}
