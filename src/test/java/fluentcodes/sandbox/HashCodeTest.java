package fluentcodes.sandbox;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;

/**
 * Created by werner.diwischek on 24.01.18.
 */
public class HashCodeTest {
  @Test
  public void testHashSet() {
    String value = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
    LOG.info(value.hashCode());
  }

  @Test
  public void testSameHashSet() {
    int value = "Aa".hashCode();
    int value2 = "BB".hashCode();
    System.out.println(value + "==" + value2);
    Assert.assertEquals(value, value2);
    int value3 = getHashCode("Aa");
    int value4 = getHashCode2("Aa");
  }

  private int getHashCode(String value) {
    int code = 0;
    char[] chars = value.toCharArray();
    for (int i = 0; i < chars.length; i++) {
      LOG.info(i + " - " + chars[i] + ": " + chars[i] * 1);
      code = code * 31 + chars[i];
    }
    return code;
  }

  private int getHashCode2(String value) {
    int code = 0;
    for (int i = 0; i < value.length(); i++) {
      code = code * 31 + value.charAt(i);
    }
    return code;
  }

  @Test
  public void example2() {
    createHashSet("Nothing is as easy as it looks");
  }

  @Test
  public void example3() {
    createHashSet("Nothing is as easy as it looks Aa BB");
  }

  private final void createHashSet(String value) {
    String[] words = new String(value).split(" ");

    HashSet<String> hs = new HashSet<>();

    for (String x : words) {
      hs.add(x);
    }

    System.out.println(hs.size() + " distinct words detected.");
    System.out.println(hs);
  }
}
