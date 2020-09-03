package fluentcodes.sandbox.enumexperiments.staticwithinheritage;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by werner.diwischek on 08.05.17.
 */

public class EnumLikeClassesTest {
  @Test
  public void test() {
    Assert.assertEquals(1, fluentcodes.sandbox.enumexperiments.staticwithinheritage.A.a.getId());
    Assert.assertEquals(0, fluentcodes.sandbox.enumexperiments.staticwithinheritage.B.d.getId());
    Assert.assertEquals(-32489132, fluentcodes.sandbox.enumexperiments.staticwithinheritage.C.e.getId());
    Assert.assertEquals(1, fluentcodes.sandbox.enumexperiments.staticwithinheritage.B.a.getId());

    for (fluentcodes.sandbox.enumexperiments.staticwithinheritage.A a: fluentcodes.sandbox.enumexperiments.staticwithinheritage.A.values()) {
      System.out.println("" + a.getId());
    }
  }
}
