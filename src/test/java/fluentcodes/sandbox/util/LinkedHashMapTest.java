package fluentcodes.sandboxjava.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
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

    @Test
    public void test() {
      Map<String,Integer> linkedHashMap = new LinkedHashMap<>();
      linkedHashMap.put("a",1);
      linkedHashMap.put("c",2);
      linkedHashMap.put("b",3);
      linkedHashMap.put("x",4);
      linkedHashMap.put("z",5);
      linkedHashMap.put("y",6);
      for (int i=0;i<1000;i++) {
        linkedHashMap.put(new Integer(i).toString(),i);
      }
      for (String key:linkedHashMap.keySet()) {
        System.out.println(key + "=" + linkedHashMap.get(key));
      }

      Map<String,Integer> map = new HashMap<>();
      map.put("a",1);
      map.put("c",2);
      map.put("b",3);
      map.put("x",4);
      map.put("z",5);
      map.put("y",6);
      for (int i=0;i<1000;i++) {
        map.put(new Integer(i).toString(),i);
      }
      for (String key:map.keySet()) {
        System.out.println(key + "=" + map.get(key));
      }
    }
}
