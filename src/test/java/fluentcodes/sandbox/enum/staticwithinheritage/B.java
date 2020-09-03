package fluentcodes.sandbox.enumexperiments.staticwithinheritage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by werner.diwischek on 08.05.17.
 */
public class B extends fluentcodes.sandbox.enumexperiments.staticwithinheritage.A {
  public static final List<fluentcodes.sandbox.enumexperiments.staticwithinheritage.A> values = new ArrayList<>();
  public static final B d = new B(4);
  private B () {
    super();
  }
  public B (int i) {
    this();
    values.add(this);
  }
  /*
   * good: you can do like
   * A x = getYourEnumFromSomeWhere();
   * if(x instanceof B) ...;
   * to identify which enum x
   * is of.
   */

}