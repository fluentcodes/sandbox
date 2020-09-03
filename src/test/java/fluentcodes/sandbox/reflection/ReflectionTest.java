package fluentcodes.sandboxjava.reflection;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReflectionTest {



    @Test
    public void callStaticMethods() throws Exception {
        Class myClass = ClassWithMethods.class;
        List list = new ArrayList();
        list.add("From List Entry");
        Method method = myClass.getMethod("method", List.class);
        method.invoke(null,list);

        //https://yourmitra.wordpress.com/2008/09/26/using-java-reflection-to-invoke-a-method-with-array-parameters/
        String [] stringArray = new String[]{"From String Array Entry"};
        method = myClass.getMethod("method", new Class[]{String[].class});
        method.invoke(null, new Object[]{stringArray});
    }

    protected static class ClassWithMethods {
        public static void method(List list) {
            for(Object entry:list) {
                System.out.println(entry);
            }
        }

        public static void method(String[] stringArray) {
            for(String entry:stringArray) {
                System.out.println(entry);
            }
        }
    }
}
