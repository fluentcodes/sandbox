package fluentcodes.sandboxjava.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Werner on 10.07.2017.
 */
public class LinkedHashMapTest {
  @Test
  public void removeEntry() {
    Map<String, Object> map = new LinkedHashMap();
    map.put("test", "value");
    map.remove("test");
    Assert.assertEquals(0, map.size());
  }
}
