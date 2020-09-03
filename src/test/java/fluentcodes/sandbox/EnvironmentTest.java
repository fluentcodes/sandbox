package fluentcodes.sandbox;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Werner on 23.10.2016.
 */
public class EnvironmentTest {

  //https://docs.oracle.com/javase/tutorial/essential/environment/env.html
  @Test
  public void testGetenv() {
    Map<String, String> env = System.getenv();
    List<String> keys = new ArrayList(env.keySet());
    Collections.sort(keys);
    for (String envName : keys) {
      System.out.format("%s=%s%n",
        envName,
        env.get(envName));
    }
  }

}
