package webswing;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

public class HTMLEmissionVisitor
{
    public String visit(Object obj)
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
                    System.out.println(iface);
                    Method dynamicallyReleventVisit = this.getClass().getMethod("visit", iface);
                    System.out.printf("Attempting to call %s\n", dynamicallyReleventVisit.toString());
                    return (String)dynamicallyReleventVisit.invoke(this, obj);
                }
                catch(NoSuchMethodException e) { continue; }
            }
            throw new NoSuchMethodException(String.format("String visit(%s)", dynamicClassOfObj.getName()));
        }
        catch(NoSuchMethodException e) { throw new RuntimeException(String.format("%s has no visit(%s) method.", this.getClass().getName(), dynamicClassOfObj.getName()), e); }
        catch(IllegalAccessException e) { throw new RuntimeException(e); }
        catch(InvocationTargetException e) { throw new RuntimeException(e); }
    }
    public String visit(WebAwtComponent c)
    {
        return c.generateHTML();
    }
}
