package fluentcodes.sandbox;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.function.IntUnaryOperator;

/**
 * Created by Werner on 16.07.2016.
 * Based on  http://www.tutorialspoint.com/java8/java8_base64.htm
 * Java 8 now has inbuilt encoder and decoder for Base64 encoding. In Java 8, we can use three types of Base64 encoding −
 * <li>Simple − Output is mapped to a set of characters lying in A-Za-z0-9+/. The encoder does not add any line feed in output, and the decoder rejects any character other than A-Za-z0-9+/.
 * <li> URL − Output is mapped to set of characters lying in A-Za-z0-9+_. Output is URL and filename safe.
 * <li>MIME − Output is mapped to MIME friendly format. Output is represented in lines of no more than 76 characters each, and uses a carriage return '\r' followed by a linefeed '\n' as the line separator. No line separator is present to the end of the encoded output.
 */
public class Base64Experiments {
    public static void main(String[] args) {
        System.out.println("Start Experiments with Base64:");
        startBase64();
        System.out.println("Finished Experiments with Base64.");
    }


    public static void startBase64() {
        System.out.println("A Integer List with foreach and print out Lamda i -> System.out.println(i)");
        // Encode using basic encoder
        try {
        String base64encodedString = Base64.getEncoder().encodeToString("TestString".getBytes("utf-8"));
        System.out.println("Base64 Encoded String (Basic) :" + base64encodedString);

        // Decode
        byte[] base64decodedBytes = Base64.getDecoder().decode(base64encodedString);

        System.out.println("Original String: " + new String(base64decodedBytes, "utf-8"));
        base64encodedString = Base64.getUrlEncoder().encodeToString("TestString".getBytes("utf-8"));
        System.out.println("Base64 Encoded String (URL) :" + base64encodedString);

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 10; ++i) {
            stringBuilder.append(UUID.randomUUID().toString());
        }

        byte[] mimeBytes = stringBuilder.toString().getBytes("utf-8");
        String mimeEncodedString = Base64.getMimeEncoder().encodeToString(mimeBytes);
        System.out.println("Base64 Encoded String (MIME) :" + mimeEncodedString);

    }
    catch(UnsupportedEncodingException e){
        System.out.println("Error :" + e.getMessage());
    }
    }


}
