package fluentcodes.sandbox;

import com.lexicalscope.jewel.cli.CliFactory;
import org.junit.Assert;
import org.junit.Test;

public class CliFactoryParseArgumentsTest {
    @Test
    public void call_with_full_names () {
        String[] input = new String[]{"--call", "c", "--filter", "f", "--number", "3"};
        Params params = CliFactory.parseArguments(Params.class, input);
        Assert.assertEquals("f", params.getFilter());
        Assert.assertEquals("c", params.getCall());
        Assert.assertEquals(new Integer(3), params.getNumber());
    }

    @Test
    public void call_with_short_names () {
        String [] input = new String[] {"-c","c", "-f","f","-n", "3"};
        Params params = CliFactory.parseArguments(Params.class, input);
        Assert.assertEquals("f", params.getFilter());
        Assert.assertEquals("c", params.getCall());
        Assert.assertEquals(new Integer(3), params.getNumber());

    }

    @Test
    public void call_with_empty_parameters_result__setting_the_default_values () {
        String [] input = new String[0];
        Params params = CliFactory.parseArguments(Params.class, input);
        Assert.assertEquals("defaultFilter", params.getFilter());
        Assert.assertEquals("defaultCall", params.getCall());
        Assert.assertEquals(new Integer(1), params.getNumber());
    }

    @Test
    public void call_with_only_one_value__sets_this_value__but_the_rest_is_default () {
        String [] input = new String[]{"--call","c"};
        Params params = CliFactory.parseArguments(Params.class, input);
        Assert.assertEquals("c", params.getCall());
        Assert.assertEquals(new Integer(1), params.getNumber());
    }

    @Test
    public void call_with_name_and_null_value__throws_Exception () {
        try {
            String [] input = new String[]{"--call",null};
            CliFactory.parseArguments(Params.class, input);
            Assert.fail("null values should throw an Exception.");
        }
        catch (Exception e) {
            System.out.println("An expected execption:" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void call_with_undefined_name__throws_Exception() {
        try {
            String[] input = new String[]{"--undefined","u"};
            CliFactory.parseArguments(Params.class, input);
            Assert.fail("Undefined values should throw an Exception.");
        }
        catch (Exception e) {
            System.out.println("An expected execption:" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void call_with_null_input__throws_Exception () {
        try {
            Params params = CliFactory.parseArguments(Params.class, null);
            Assert.fail("null should throw exception");
        }
        catch (Exception e) {
            System.out.println("An expected execption:" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void call_with_number_name_value__not_a_number__throws_Exception () {
        try {
            String []input = new String[]{"--number","x"};
            CliFactory.parseArguments(Params.class, input);
            Assert.fail("Unparsable integer values should throw an Exception.");
        }
        catch (Exception e) {
            System.out.println("An expected execption:" + e.getMessage());
            e.printStackTrace();
        }
    }
}
