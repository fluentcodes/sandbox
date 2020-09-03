package fluentcodes.sandboxjava.reflection;

import fluentcodes.sandboxjava.helper.BasicTest;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BasicGenericTest {
    List<String> stringList = new ArrayList<String>();
    List<Integer> integerList = new ArrayList<Integer>();

    @Test
    public void genericObjectListTest() throws Exception {
        Field stringListField = BasicGenericTest.class.getDeclaredField("stringList");
        ParameterizedType listType = (ParameterizedType) stringListField.getGenericType();
        Class<?> genericClass = (Class<?>) listType.getActualTypeArguments()[0];
        Assert.assertEquals(String.class, genericClass); // class java.lang.String.

        Field integerListField = BasicGenericTest.class.getDeclaredField("integerList");
        listType = (ParameterizedType) integerListField.getGenericType();
        genericClass = (Class<?>) listType.getActualTypeArguments()[0];
        Assert.assertEquals(Integer.class, genericClass);
    }

    private void analyzeField(String name) {
        Field field = null;
        try {
            field = BasicTest.class.getDeclaredField(name);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        Class<?> fType = field.getType();

        System.out.println("FieldClass for '" + name + "': " + fType.getName());
        Object value = field.getGenericType();
        if (value instanceof Class) {
            System.out.println("Class for '" + name + "': " + ((Class) value).getName());
        } else if (value instanceof ParameterizedType) {
            ParameterizedType type = (ParameterizedType) field.getGenericType();
            Object[] generics = type.getActualTypeArguments();
            for (Object generic : generics) {
                System.out.println("Generic Class for '" + name + "': " + ((Class) generic).getName());
            }
        }
    }

    @Test
    public void infoListInterfaces() {
        List list1 = Arrays.asList("test", 1, 1.1);
        List list2 = new ArrayList();
        Class[] list1Classes = list1.getClass().getInterfaces();
        for (Class list1Class : list1Classes) {
            System.out.println(list1Class.getSimpleName());
        }
        Class[] list2Classes = list2.getClass().getInterfaces();
        for (Class list2Class : list2Classes) {
            System.out.println(list2Class.getSimpleName());
        }
    }


}