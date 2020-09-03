package fluentcodes.sandbox.streams;

import fluentcodes.sandbox.SimpleLogger;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamBasicsTest {

    private static final fluentcodes.sandbox.SimpleLogger logger = System.out::println;
    private static final fluentcodes.sandbox.SimpleLogger peekLogger = System.out::println;
//    private static final SimpleLogger peekLogger = s -> {};


    private final Collection<String> testCollection = new ArrayList<>(Arrays.asList("1", "2.", "third", "4th", "more..."));

    @Test
    public void testSimpleIteration() throws Exception  {
        Stream.of("d2", "a2", "b1", "b3", "c")
                .forEach(System.out::println);
    }

    @Test
    public void testStream() throws Exception {
        final Predicate<String> leng = s -> s.length() > 2;
        final Predicate<String> more = s -> s.startsWith("more");
        testCollection
            .stream()
            .filter(leng.and(more.negate()))
            .map(s -> "Long item: " + s)
            .forEach(logger::log);
    }

    @Test
    public void testMatch() throws Exception {
        Assert.assertTrue(testCollection.stream().allMatch(s -> s.length() > 0));
        Assert.assertFalse(testCollection.stream().allMatch(s -> s.length() > 2));

        Assert.assertTrue(testCollection.stream().anyMatch(s -> s.length() > 2));
        Assert.assertFalse(testCollection.stream().anyMatch(s -> s.length() > 8));

        Assert.assertTrue(testCollection.stream().noneMatch(s -> s.length() > 8));
        Assert.assertFalse(testCollection.stream().noneMatch(s -> s.length() > 5));
    }

    @Test
    public void testOptionalResult() throws Exception {
        logger.log(testCollection
            .stream()
            .filter(s -> s.length() > 2)
            .map(s -> "Long item: " + s)
            .findFirst().get());
    }

    @Test(expected = NoSuchElementException.class)
    public void testOptionalResultThrows() throws Exception {
        logger.log(testCollection
            .stream()
            .filter(s -> s.length() > 20)
            .map(s -> "Long item: " + s)
            .findFirst().get());
    }

    @Test
    public void testOptionalResultElse() throws Exception {
        logger.log(testCollection
            .stream()
            .filter(s -> s.length() > 20)
            .map(s -> "Long item: " + s)
            .findFirst().orElse("No such result"));
    }

    @Test(expected = IllegalStateException.class)
    public void testOptionalResultElseThrow() throws Exception {
        logger.log(testCollection
            .stream()
            .filter(s -> s.length() > 20)
            .map(s -> "Long item: " + s)
            .findFirst().orElseThrow(IllegalStateException::new));
    }

    @Test
    public void testCollect() throws Exception {
        final Collection<Integer> result =
            testCollection.stream()
                .map(String::length)
                .collect(ArrayList<Integer>::new, List::add, List::addAll);
        logger.log(result.toString());
    }

    @Test
    public void testCollectToMap() throws Exception {
        logger.log(
            testCollection.stream()
                .collect(HashMap<String, String>::new,
                    (Map<String, String> m, String s) -> m.put(s, s.toUpperCase(Locale.getDefault())),
                    Map::putAll).toString()
        );
    }

    @Test
    public void testFoldLeft() throws Exception {
        logger.log(testCollection.stream().reduce("-", (s, i) -> s + i));
    }

    @Test
    public void testFoldRight() throws Exception {
        logger.log(testCollection.stream().reduce("-", (s, i) -> i + s));
    }

    /**
     * The following experiments examine the lazyness of intermediate operations
     */
    @Test
    public void testLazyness() {
        System.out.println("intermediate operations will only be executed when a terminal operation is present, so println within the filter will not be executed:");
        Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return true;
                });

        System.out.println("println within the filter will executed with terminal foreach:");
        Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return true;
                })
                .forEach(s -> System.out.println("forEach: " + s));
    }
    @Test
    public void testLazynessAnyMatch() {
        System.out.println("When a2 is reached, no operations will be executed any more");
        Stream.of("d2", "a2","a0", "b1", "b3", "c")
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .anyMatch(s -> {
                    System.out.println("anyMatch: " + s);
                    return s.startsWith("A");
                });
    }
    @Test
    public void testLazynessOrderOperations() {
        System.out.println("All mapping operation will be executed. ");
        Stream.of("d2", "a2", "b1", "b3", "c")
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("A");
                })
                .forEach(s -> System.out.println("forEach: " + s));

        //map is only called once so the operation pipeline performs much faster for larger numbers of input elements.
        System.out.println("Filter in front reduces the number of subsequent operations. ");
        Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("a");
                })
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.println("forEach: " + s));

        //First, the sort operation is executed on the entire input collection. In other words sorted is executed horizontally. So in this case sorted is called eight times for multiple combinations on every element in the input collection.
        System.out.println("With unoptimized sort operation");
        Stream.of("d2", "a2", "a1","b1", "b3", "c")
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .sorted((s1, s2) -> {
                    System.out.printf("sort: %s; %s\n", s1, s2);
                    return s1.compareTo(s2);
                })
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("a");
                })
                .forEach(s -> System.out.println("forEach: " + s));


        System.out.println("Optimize the performance by reordering the chain:");
        Stream.of("d2", "a2", "a1","b1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return s.startsWith("a");
                })
                .sorted((s1, s2) -> {
                    System.out.printf("sort: %s; %s\n", s1, s2);
                    return s1.compareTo(s2);
                })
                .map(s -> {
                    System.out.println("map: " + s);
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.println("forEach: " + s));
    }

    @Test
    public void testReusingStreams() {
        Stream<String> stream =
                Stream.of("d2", "a2", "b1", "b3", "c")
                        .filter(s -> s.startsWith("a"));

        stream.anyMatch(s -> true);    // ok
        try {
            stream.noneMatch(s -> true);   // exception
        }
        catch (Exception e) {
            System.out.println("Expected exception: Tried to operate on closed stream: " + e.getMessage());
        }

        //To overcome this limitation we have to to create a new stream chain for every terminal operation we want to execute, e.g. we could create a stream supplier to construct a new stream with all intermediate operations already set up:
        System.out.println("With a Stream Supplier with can reuse the stream");
        Supplier<Stream<String>> streamSupplier =
                () -> Stream.of("d2", "a2", "b1", "b3", "c")
                        .filter(s -> s.startsWith("a"));

        streamSupplier.get().anyMatch(s -> true);   // ok
        streamSupplier.get().noneMatch(s -> true);  // ok

    }



    @Test
    public void testIntRangeStream() throws Exception {

        final int maxTests = 200000;

        logger.log("\nResult: "
            + IntStream.range(0, maxTests)
            .mapToObj(i -> UUID.randomUUID().toString())
            .filter(s -> s.contains("affe"))
            .findFirst()
            .orElse("No match")
            + " found.");
    }



    private List<String> getRandomUids(final int count) {
        return Arrays.asList(IntStream.range(0, count).mapToObj(i -> UUID.randomUUID()).map(UUID::toString).toArray(String[]::new));
    }

}
