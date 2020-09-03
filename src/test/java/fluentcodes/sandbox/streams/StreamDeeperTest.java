package fluentcodes.sandbox.streams;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by werner/ecube on 05.08.16.
 * Some examples from shttp://winterbe.com/posts/2014/07/31/java8-stream-tutorial-examples/
 * Really interesting aspects for stream operations
 */
public class StreamDeeperTest {


    /**
     * A stream represents a sequence of elements and supports different kind of operations to perform computations upon those elements:
     * Stream operations are either intermediate or terminal.
     * Intermediate operations return a stream so we can chain multiple intermediate operations without using semicolons.
     * Terminal operations are either void or return a non-stream result.
     * In the above example filter, map and sorted are intermediate operations whereas forEach is a terminal operation.
     * For a full list of all available stream operations see the Stream Javadoc.
     * Such a chain of stream operations as seen in the example above is also known as operation pipeline.
     */
    @Test
    public void testSimple (){
        Arrays.asList("a1", "a2", "b1", "c2", "c1").stream()
                .filter(s -> s.startsWith("c"))
                .map(String::toUpperCase)
                .sorted()
                .forEach(System.out::println);

        System.out.println("stream only necessary for functional operations stream.foreach");
        Arrays.asList("a1", "a2").stream().forEach(System.out::println);

        System.out.println("direct with  foreach");
        Arrays.asList("a1", "a2").forEach(System.out::println);

    }

    @Test
    public void testFindFirst() {
        System.out.println("findFirst");
        Arrays.asList("a1", "a2", "a3")
                .stream()
                .findFirst()
                .ifPresent(System.out::println);

        System.out.println("Stream.of example: ");
        Stream.of("a1", "a2", "a3")
                .findFirst()
                .ifPresent(System.out::println);

        System.out.println("IntStream example: ");
        IntStream.range(1, 4)
                .forEach(System.out::println);

        System.out.println("All those primitive streams work just like regular object streams with the following differences: Primitive streams use specialized lambda expressions, e.g. IntFunction instead of Function or IntPredicate instead of Predicate. And primitive streams support the additional terminal aggregate operations sum() and average():");
        Arrays.stream(new int[] {1, 2, 3})
                .map(n -> 2 * n + 1)
                .average()
                .ifPresent(System.out::println);


    }





}
