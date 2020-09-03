/* Copyright 2012 Werner Diwischek
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fluentcodes.sandbox.crypto;

import org.junit.Test;

import junit.framework.Assert;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;


/**
 * <p>
 * https://docs.oracle.com/javase/8/docs/api/java/util/Base64.Encoder.html
 * https://dzone.com/articles/base64-encoding-java-8
 * http://www.baeldung.com/java-base64-encode-and-decode
 * https://stackoverflow.com/questions/28584080/base64-java-lang-illegalargumentexception-illegal-character
 * http://tutorials.jenkov.com/java-cryptography/cipher.html
 * https://stackoverflow.com/questions/1760785/invalid-aes-key-length-error
 * https://docs.oracle.com/javase/8/docs/api/javax/crypto/Cipher.html
 * https://howtodoinjava.com/security/java-aes-encryption-example/
 * older:
 * 
 * http://javarevisited.blogspot.de/2012/02/how-to-encode-decode-string-in-java.html
 * http://stackoverflow.com/questions/7762771/how-do-i-encrypt-decrypt-a-string-with-another-string-as-a-password
 * http://www.simplecodestuffs.com/encryption-and-decryption-of-data-using-aes-algorithm-in-java-2/
 * </p>
 * @author Werner Diwischek
 * @version 0.1
 */

public class Base64Test {

  @Test
  public void encryptAndDecrypt() throws UnsupportedEncodingException {
	  final String encoded = Base64.getEncoder().encodeToString("some string".getBytes("utf-8"));
	  System.out.println("Encoded: " + encoded);
	  Assert.assertEquals("c29tZSBzdHJpbmc=", encoded);
	  final byte[] decodedBytes = Base64.getDecoder().decode(encoded);
	  final String decoded = new String(decodedBytes, "utf-8");
	  System.out.println("Decoded: " + decoded);
	  Assert.assertEquals("some string", decoded);
  }
  
  @Test
  public void encryptCypherAes() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
	MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
	byte[] key = messageDigest.digest("ASecretKey123456".getBytes("utf-8"));
	key = Arrays.copyOf(key, 16);
    SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
    
    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    byte[] encrypted = cipher.doFinal("message".getBytes("utf-8"));
    String encoded = Base64.getEncoder().encodeToString(encrypted);
    System.out.println("Encoded: " + encoded);
    Assert.assertEquals("meIt9AtY7e2grGlDnAj7Tw==", encoded);
    
    cipher.init(Cipher.DECRYPT_MODE, secretKey);
    byte[] base64Bytes = Base64.getDecoder().decode(encoded.getBytes("utf-8"));
    byte[] decryptedBytes = cipher.doFinal(base64Bytes);
    String decoded = new String(decryptedBytes, "utf-8");
    System.out.println("Decoded: " + decoded);
    Assert.assertEquals("message", decoded);
  }

}
