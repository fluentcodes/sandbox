package fluentcodes.sandbox.lang;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScalarConverterByteTest {
  private static final Double DOUBLE = 1.1;
  private static final Float FLOAT = 1.1F;

  private static final byte[] BYTES = new byte[]{97, 98, 99};
  private static final String BYTES_STRING = "abc";
  private static final List<Integer> BYTE_JSON = createJSONByte();

  private static List<Integer> createJSONByte() {
    List result = new ArrayList();
    result.add(97);
    result.add(98);
    result.add(99);
    return result;
  }

  @Ignore
  @Test
  public void byteTest() {
    String s1 = Arrays.toString(BYTES);
    String s2 = new String(BYTES);
    //byte mybytes = Byte.parseByte(s1);

    System.out.println(s1);        // -> "[97, 98, 99]"
    System.out.println(s2);        // -> "abc";
    Assert.assertEquals(BYTES_STRING, s2);

    String coolString = "cool string";
    byte[] byteArray = coolString.getBytes();
    String reconstitutedString = new String(byteArray);
    System.out.println(reconstitutedString);
    byte[] jsonBytes = new byte[3];
    for (int i = 0; i < BYTE_JSON.size(); i++) {
      //String x =(String) BYTE_JSON.get(i);
      Integer intValue = BYTE_JSON.get(i);
      byte j = (byte) BYTE_JSON.get(i).intValue();

      jsonBytes[i] = j;
    }
    Assert.assertEquals(BYTES_STRING, new String(jsonBytes));
  }
}