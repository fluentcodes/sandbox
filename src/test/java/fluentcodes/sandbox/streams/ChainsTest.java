package fluentcodes.sandbox.streams;

import fluentcodes.sandbox.SimpleLogger;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChainsTest {

    private static final SimpleLogger logger = System.out::println;

    @Test
    public void testCollectionIterable() throws Exception {

        final Collection<Number> numbers = new ArrayList<>();
//        strings.forEach(s -> numbers.add(Double.valueOf(s)) );   // doesn't work, because Collection#add() is of type boolean while Consumer#accept is void
        Stream.of("d2", "a2", "b1", "b3", "c")
                .forEach(s -> { numbers.add(Double.valueOf(s)); } );
        logger.log("Numbers: " + numbers);
    }

    @Test
    public void testConsumerChain() throws Exception {
        final Consumer<String> consumer1 = s -> logger.log("1. " + s);
        final Consumer<String> consumer2 = s -> logger.log("2. " + s);
        final Consumer<String> chained = consumer1.andThen(consumer2);
        Stream.of("d2", "a2", "b1", "b3", "c")
                .forEach(chained);
        logger.log("");

        // same as:
        Stream.of("d2", "a2", "b1", "b3", "c")
                .forEach(((Consumer<String>)(s -> logger.log("1. " + s))).andThen(s -> logger.log("2. " + s)));
//        strings.forEach((s -> logger.log("1. " + s)).chain(s -> logger.log("2. " + s)));  // doesn't work without explicit cast
    }

    @Test
    public void toDouble()  throws Exception  {
        final Function<String,Double> functionToDouble = s -> {System.out.println("0. " + s);return new Double(s);};
        final Function<Double,Double> functionAdd100 = d -> {System.out.println("1. " + d);return d+100.0;};
        final Function<String,Double> chained = functionToDouble.andThen(functionAdd100);
        Stream.of("d2", "a2", "b1", "b3", "c")
                .map(chained);
        Stream.of("d2", "a2", "b1", "b3", "c")
                .map(Double::parseDouble).collect(Collectors.toList()).forEach(x->System.out.println("0: " + x));
        Stream.of("d2", "a2", "b1", "b3", "c")
                .map(functionToDouble).collect(Collectors.toSet()).forEach(x->System.out.println("1: " + x));
        Stream.of("d2", "a2", "b1", "b3", "c")
                .map(chained).collect(Collectors.toSet()).forEach(x->System.out.println("2: " + x));

        // Problems with elementary types.
        double [] array=Stream.of("d2", "a2", "b1", "b3", "c")
        .map(Double::parseDouble).mapToDouble(d->d).toArray();
        Arrays.asList(array).forEach(x->System.out.println("elementary: " + x));
      }




}
