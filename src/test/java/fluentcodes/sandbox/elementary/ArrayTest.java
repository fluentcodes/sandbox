package fluentcodes.sandboxjava.elementary;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class ArrayTest {
    @Test
    public void createArrayWithInitialsSize() {
        String[] strings = new String[2];
        Assert.assertEquals(2, strings.length);
        for (String string:strings) {
            Assert.assertEquals(null, string);
        }
        strings[1] = "second";
        Assert.assertEquals("second", strings[1]);
        Assert.assertEquals(null, strings[0]);
        strings[0] = "first";
        Assert.assertEquals("first", strings[0]);
        try {
            strings[2] = "out of range";
            Assert.fail("Exception expected");
        }
        catch (Exception e) {
            System.out.println("Expected Exception: " + e.getMessage());
            e.printStackTrace();
        }

        // nice copy of array:
        String[] copyOfStrings = Arrays.copyOf(strings, 10);
        Assert.assertEquals("first", copyOfStrings[0]);
        Assert.assertEquals("second", copyOfStrings[1]);
        Assert.assertEquals(null, copyOfStrings[2]);
    }
}
