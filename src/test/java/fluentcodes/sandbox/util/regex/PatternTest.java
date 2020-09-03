package fluentcodes.sandbox.util.regex;

import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternTest {
    @Test
    public void protocol() {
        final Pattern linkPattern = Pattern.compile("(.*)://(.*)");
        Matcher matcher = linkPattern.matcher("http://test");
        Assert.assertTrue(matcher.find());
        Assert.assertTrue(matcher.matches());
        Assert.assertEquals("http", matcher.group(1));
        Assert.assertEquals("test", matcher.group(2));
    }

}
