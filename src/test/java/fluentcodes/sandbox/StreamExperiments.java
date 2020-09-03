package fluentcodes.sandbox;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Random;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;

/**
 * Created by Werner on 16.07.2016.
 * Examples originated from http://www.tutorialspoint.com/java8/java8_streams.htm
 * Stream represents a sequence of objects from a source, which supports aggregate operations. Following are the characteristics of a Stream −
 * <li>Sequence of elements − A stream provides a set of elements of specific type in a sequential manner. A stream gets/computes elements on demand. It never stores the elements.
 * <li> Source − Stream takes Collections, Arrays, or I/O resources as input source.
 * <li>Aggregate operations − Stream supports aggregate operations like filter, map, limit, reduce, find, match, and so on.
 * <li>Pipelining − Most of the stream operations return stream itself so that their result can be pipelined. These operations are called intermediate operations and their function is to take input, process them, and return output to the target. collect() method is a terminal operation which is normally present at the end of the pipelining operation to mark the end of the stream.
 * <li>Automatic iterations − Stream operations do the iterations internally over the source elements provided, in contrast to Collections where explicit iteration is required.
 */
public class StreamExperiments {
    public static void main(String[] args) {
        System.out.println("Start Experiments with Streams:");
        startListStreams();
        startForeachList();
        startStreamBuilder();
        startCreatedOperator();
        System.out.println("Finished Experiments with Streams.");
    }
    public static void startListStreams() {
        System.out.println("Start different experiments with stream: ");
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        System.out.println("List: " + strings);

        long count = strings.stream().filter(string -> string.isEmpty()).count();
        System.out.println("Empty Strings: " + count);

        count = strings.stream().filter(string -> string.length() == 3).count();
        System.out.println("Strings of length 3: " + count);

        List<String>filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
        System.out.println("Filtered List: " + filtered);

        String mergedString = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(", "));
        System.out.println("Merged String: " + mergedString);

        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        List<Integer> squaresList = numbers.stream().map(i -> i * i).distinct().collect(Collectors.toList());
        System.out.println("Squares List: " + squaresList);

        List<Integer> integers = Arrays.asList(1,2,13,4,15,6,17,8,19);
        System.out.println("List: " + integers);

        IntSummaryStatistics stats = integers.stream().mapToInt((x) -> x).summaryStatistics();

        System.out.println("Highest number in List : " + stats.getMax());
        System.out.println("Lowest number in List : " + stats.getMin());
        System.out.println("Sum of all numbers : " + stats.getSum());
        System.out.println("Average of all numbers : " + stats.getAverage());

        System.out.println("Now creating 10 random numbers:");
        Random random = new Random();
        random.ints().limit(10).sorted().forEach(System.out::println);

        //parallel processing
        count = strings.parallelStream().filter(string -> string.isEmpty()).count();
        System.out.println("Empty Strings: " + count);
    }
    // From http://www.angelikalanger.com/Articles/EffectiveJava/71.Java8.Lambdas/71.Java8.Lambdas.html
    public static void startForeachList() {
        System.out.println("A Integer List with foreach and print out Lamda i -> System.out.println(i)");
        List<Integer> numbers = Arrays.asList(new Integer[]{0,1,2});
        numbers.forEach( i -> System.out.println(i) );

        System.out.println("A Object List with foreach and print out Lamda i -> System.out.println(i)");
        List objects = Arrays.asList(new Object[]{0,1.1,"2S"});
        objects.forEach( i -> System.out.println(i) );

        System.out.println("A Object List with foreach and print out Lamda (i) -> System.out.println(i)");
        objects.forEach( (i) -> System.out.println(i) );

        System.out.println("A Object List with foreach and print out Lamda object reference 'System.out :: println':");
        objects.forEach( System.out :: println );
    }

    // From http://www.angelikalanger.com/Articles/EffectiveJava/71.Java8.Lambdas/71.Java8.Lambdas.html
    public static void startStreamBuilder() {
        System.out.println("Array stream with map and StringBuilder setting '.txt' as suffix");
        char[] suffix = new char[] {'.','t','x','t'};

        Arrays.stream(new String[] {"readme", "releasenotes"})
                .map( StringBuilder::new)
                .map(s->s.append(suffix))
                .forEach(System.out::println);

        StringBuilder builder = new StringBuilder("test");
        builder.append(suffix);
        System.out.println(builder);

        System.out.println("Array stream with map to Integer");
        Arrays.stream(new String[] {"1", "2"})
                .map( Integer::new)
                .map(i->i+1)
                .forEach(System.out::println);

        Integer value= new Integer("1");
    }

    /**
     * Mapping functional reference
     */
    public static void startCreatedOperator() {
        System.out.println("Use created unitary operator with factor 1000");
             final int  factor = 1000;
             IntUnaryOperator times1000 = x -> x *  factor ;
             Arrays.stream(new int[]{1, 2}).
                     map(times1000).
                     forEach(System.out::println);
            System.out.println("Use static class method with factor 100");
            Arrays.stream(new int[]{1, 2}).
                    map(StreamExperiments::multiplicate).
                    forEach(System.out::println);
   }

    public static final int multiplicate ( int value) {
        return value*100;
    }
}
