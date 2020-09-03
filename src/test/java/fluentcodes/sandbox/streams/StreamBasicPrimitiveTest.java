package fluentcodes.sandbox.streams;

import fluentcodes.sandbox.TestBasics;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Created by werner/ecube on 05.08.16.
 * Some examples from shttp://winterbe.com/posts/2014/07/31/java8-stream-tutorial-examples/
 * Topics are the possibilities creating streams and the different intermediate operation mapToObject, mapToInt, mapToDouble and
 */
public class StreamBasicPrimitiveTest extends TestBasics {


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
    public void testStreamClasses() {
        info.log("Test Streaming classes");
        info.log("IntStream");
        IntStream.of(0,2,13).forEach(print::log);

        info.log("LongStream");
        LongStream.of(0,2,13).forEach(print::log);

        info.log("DoubleStream");
        DoubleStream.of(0.1,2.2,13.3).forEach(print::log);

        info.log("Stream");
        Stream.of(1,2.2,3L,"test").forEach(print::log);
    }

    @Test
    public void testArraysStaticStream() {
        info.log("Test Arrays static stream methos");
        info.log("Int Array stream");
        Arrays.stream(new int[]{0,2,13}).forEach(print::log);

        info.log("Long Array stream");
        Arrays.stream(new long[]{0,2,13}).forEach(print::log);


        info.log("Double Array stream");
        Arrays.stream(new double[]{0.1,2.2,13.3}).forEach(print::log);

        info.log("Object Array stream");
        Arrays.stream(new Object[]{1,2.2,3L,"test"}).forEach(print::log);
    }


    @Test
    public void testIntStreamRange() {
        System.out.println("IntStream example: ");
        IntStream.range(1, 4)
            .forEach(System.out::println);
        System.out.println("All those primitive streams work just like regular object streams with the following differences: Primitive streams use specialized lambda expressions, e.g. IntFunction instead of Function or IntPredicate instead of Predicate. And primitive streams support the additional terminal aggregate operations sum() and average():");
        Arrays.stream(new int[] {1, 2, 3})
            .map(n -> 2 * n + 1)
            .average()
            .ifPresent(System.out::println);
    }

    @Test
    public void testConvert() {
        IntStream.of(0,2,13).mapToDouble(Double::new).forEach(System.out::println);

        System.out.println("Sometimes it's useful to transform a regular object stream to a primitive stream or vice versa. For that purpose object streams support the special mapping operations mapToInt(), mapToLong() and mapToDouble:");
        Stream.of("a1", "a2", "a3")
            .map(s -> s.substring(1))
            .mapToInt(Integer::parseInt)
            .max()
            .ifPresent(System.out::println);


        System.out.println("Primitive streams can be transformed to object streams via mapToObj():");
        IntStream.range(1, 4)
            .mapToObj(i -> "a" + i)
            .forEach(System.out::println);

        System.out.println("Convert a double to int and then to String object:");
        Stream.of(1.0, 2.0, 3.0)
            .mapToInt(Double::intValue)
            .mapToObj(i -> "a" + i)
            .forEach(System.out::println);
    }





}
