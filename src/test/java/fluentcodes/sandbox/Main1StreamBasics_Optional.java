package fluentcodes.sandbox;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 *
 * @author Charles Ohene
 */
public class Main1StreamBasics_Optional {

    public static void main(String[] args) {
        Optional<String> halloOptionalString = Optional.ofNullable("Hallo");
        /* auch möglich:
         halloOptionalString = Optional.ofNullable(null);
         */

        //Zugriff:
        System.out.println(halloOptionalString);
        System.out.println(halloOptionalString.get());     //Exception falls null

        //falls null gibts Probleme, daher:
        if (halloOptionalString.isPresent()) {
            System.out.println(halloOptionalString.get());
        }

        halloOptionalString = Optional.of("Hi!");
        Optional<String> leerOptionalString = Optional.empty();     // explizit gewollt
        // nicht erlaubt: Optional.of(null)--> Wertzusicherung

        //Defaultwerte falls leerer Optional
        leerOptionalString.orElse("Einen wundervollen NICHTLEEREN Tag!");

        //Bedingte Verarbeitung via Consumer, falls Optional gefüllt
        Consumer<String> ausgeber = string -> System.out.println("Consumed: " + string);
        halloOptionalString.ifPresent(ausgeber);

        //Wertbereitstellung via Supplier, falls Optional leer
        Supplier<String> stringGeber = () -> "Gegeben vom Supplier";
        System.out.println(leerOptionalString.orElseGet(stringGeber));
        System.out.println(halloOptionalString.orElseGet(stringGeber));
    }
}
