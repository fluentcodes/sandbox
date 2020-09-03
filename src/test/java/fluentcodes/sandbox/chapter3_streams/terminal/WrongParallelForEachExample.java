package fluentcodes.sandbox.chapter3_streams.terminal;

import java.util.Arrays;
import java.util.List;

/**
 * Beispielprogram illustriert, welche Fehler auftreten k�nnen, wenn man
 * forEach() in Kombination mit parallelen Streams nutzt.
 *
 * @author Michael Inden
 *
 * Copyright 2014 by Michael Inden
 */
public class WrongParallelForEachExample {

    public static void main(final String[] args) {
        final List<String> names = Arrays.asList("Stefan", "Ralph", "Andi", "Mike");

        names.parallelStream().sorted().forEach(System.out::println);
        /* deterministische Variante:
         names.parallelStream().sorted().forEachOrdered(System.out::println);
         */
    }
}
