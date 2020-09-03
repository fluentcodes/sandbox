package fluentcodes.sandboxjava.lang;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Werner on 08.11.2016.
 */
public class StringRegExpTest {
  /*
  Just checks the weird replacement of quotes with java replaceAll
   */
  @Test
  public void testReplaceQuotes() {
    String valueWithQuotes = "test \"quoted\"";
    String replaceWithQuotes = valueWithQuotes.replaceAll("\"", "x");
    Assert.assertEquals("test xquotedx", replaceWithQuotes);
    replaceWithQuotes = valueWithQuotes.replaceAll("\"", "\\\\\"");
    Assert.assertEquals("test \\\"quoted\\\"", replaceWithQuotes);
  }
}
