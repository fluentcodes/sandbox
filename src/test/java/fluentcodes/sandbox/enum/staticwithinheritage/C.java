package fluentcodes.sandbox.enumexperiments.staticwithinheritage;

/**
 * Created by werner.diwischek on 08.05.17.
 */
public class C extends fluentcodes.sandbox.enumexperiments.staticwithinheritage.A {

  public C (int i) {
    super(i);
  }
  /* Good: e.getId() != d.getId()
   * Bad: in different JVMs, C and B
   * might be initialized in different order,
   * resulting in different IDs.
   * Workaround: use a fixed int, or hash code.
   */

  public static final C e = new C(4);
  @Override
  public int getId() { return -32489132; };
}