package webswing;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

public class HTMLEmissionVisitor
{
    private StringBuilder bodyHTML = new StringBuilder();
    private String title = "";
    public void visit(Object obj)
    {
        Class<?> dynamicClassOfObj = obj.getClass();
        ArrayList<Class<?>> interfacesToTry = new ArrayList<Class<?>>();
        interfacesToTry.add(dynamicClassOfObj);
        interfacesToTry.addAll(Arrays.asList(dynamicClassOfObj.getInterfaces()));
        interfacesToTry.add(dynamicClassOfObj.getSuperclass()); //TODO: recursively walk object hierarchy properly
        try
        {
            for(Class<?> iface : interfacesToTry)
            {
                try
                {
                    //System.out.println(iface);
                    Method dynamicallyReleventVisit = this.getClass().getMethod("visit", iface);
                    //System.out.printf("Attempting to call %s\n", dynamicallyReleventVisit.toString());
                    dynamicallyReleventVisit.invoke(this, obj);
                    return;
                }
                catch(NoSuchMethodException e) { continue; }
            }
            throw new NoSuchMethodException(String.format("String visit(%s)", dynamicClassOfObj.getName()));
        }
        catch(NoSuchMethodException e) { throw new RuntimeException(String.format("%s has no visit(%s) method.", this.getClass().getName(), dynamicClassOfObj.getName()), e); }
        catch(IllegalAccessException e) { throw new RuntimeException(e); }
        catch(InvocationTargetException e) { throw new RuntimeException(e); }
    }
    public void visit(WebAwtComponent c)
    {
        bodyHTML.append(c.generateHTML());
    }
    public void visit(WebJFrame jf)
    {
        title = jf.getTitle();
        bodyHTML.append(jf.generateHTML());
    }
    public String getGeneratedHtml()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><head><title>");
        sb.append(title);
        sb.append("</title></head><body>"); //TODO: script section (websockets, etc), css section (borders around frames, etc)
        sb.append(bodyHTML.toString());
        sb.append("</body></html>");
        return sb.toString();
    }
}
