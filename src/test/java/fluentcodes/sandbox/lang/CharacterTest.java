package fluentcodes.sandboxjava.lang;

import org.junit.Test;

/**
 * Created 5.6.2018
 * Some tests for the characters
 */
public class CharacterTest {
    @Test
    public void letters () {
        String value = "abcdefghijklmnopqrstuvwxyzäüö";
        printout(value);
        String upper = value.toUpperCase();
        printout(upper);
        String numbers = "1234567890";
        printout(numbers);
        String special = "-_(){}[]\\/.,\"'?!";
        printout(special);
    }
    //https://stackoverflow.com/questions/16458564/convert-character-to-ascii-numeric-value-in-java
    //https://stackoverflow.com/questions/7693994/how-to-convert-ascii-code-0-255-to-a-string-of-the-associated-character
    private void printout(String input) {
        char[] chars = input.toCharArray();
        for (char entry: chars) {
            if ((entry>96 && entry<123)||entry==228 || entry == 246 || entry == 252 ) {
                int entryAsInt = (int) entry;
                int upper = entryAsInt - 32;
                System.out.println((int) entry + "=" + new Character(entry).toString() + " --- " + new Character((char) upper).toString());
            }
            else if ((entry>64 && entry<91) ||entry==196 || entry == 214 || entry == 220 ) {
                int entryAsInt = (int) entry;
                int lower = entryAsInt + 32;
                System.out.println((int) entry + "=" + new Character(entry).toString() + " --- " + new Character((char) lower).toString());
            }
            else {
                System.out.println((int) entry + "=" + new Character(entry).toString());
            }
        }
    }


}
