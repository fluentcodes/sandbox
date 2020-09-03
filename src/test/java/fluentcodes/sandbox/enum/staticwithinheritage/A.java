package fluentcodes.sandbox.enumexperiments.staticwithinheritage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by werner.diwischek on 08.05.17.
 */
public class A {
  public static final List<A> values = new ArrayList<>();
  public static final A a = new A(1);
  public static final A b = new A(2);
  public static final A c = new A(3);
  protected A() {

  }

  public A (int i) {
    this.id = i;
    values.add(this);
  }
  /*
   * In case you need to identify your constant
   * in different JVMs, you need an id. This is the case if
   * your object is transfered between
   * different JVM instances (eg. save/load, or network).
   * Also, switch statements don't work with
   * Objects, but work with int.
   */
  public int id ;
  public int getId() { return id; }
  public static List<A> values() {
    return values;
  }
}
