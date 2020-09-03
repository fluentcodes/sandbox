package fluentcodes.sandboxjava.reflection;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/*
 * https://stackoverflow.com/questions/17485297/how-to-instantiate-a-non-static-inner-class-with-reflection-in-java
 */
public class ReflectionSubClassTest {



    @Test
    public void callStaticMethods() throws Exception {
        Class parentClass = Parent.class;
        Class childClass = Class.forName(parentClass.getName() + "$Child");

        Method[] methods = childClass.getMethods();
        Method setter = childClass.getMethod("setVar",String.class);
        Method getter = childClass.getMethod("getVar",null);
        Constructor constructor = childClass.getConstructor(null);
        Object childObject = constructor.newInstance();
        setter.invoke(childObject, "4");
        Assert.assertEquals("4", getter.invoke(childObject,null));

        Method add = childClass.getMethod("add", new Class[] {Integer.class, Integer.class});
        Integer sum = (Integer) add.invoke(childObject, 1, 2);
        Assert.assertEquals(new Integer(3), sum);

    }
}
