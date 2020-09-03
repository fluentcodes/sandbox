package fluentcodes.sandbox.streams;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by werner, ecube on 07.08.16.
 * From http://winterbe.com/posts/2014/07/31/java8-stream-tutorial-examples/
 */
public class FlatMapTest {
    @Test
    public void testFlatMap() {
        List<Foo> foos = new ArrayList<>();

        System.out.println("Creating 3 foos with 3 bars each");
        //----
        // create foos
        IntStream
            .range(1, 4)
            .forEach(i -> foos.add(new Foo("Foo" + i)));

        // create bars
        foos.forEach(f ->
            IntStream
                .range(1, 4)
                .forEach(i -> f.bars.add(new Bar("Bar" + i + " <- " + f.name))));

        System.out.println("Now streaming foos with all bars within: ");
        //---
        foos.stream()
            .flatMap(f -> f.bars.stream())
            .forEach(b -> System.out.println(b.name));

        System.out.println("Concating to a single pipeline:");
        //---
        IntStream.range(1, 4)
            .mapToObj(i -> new Foo("Foo" + i))
            .peek(f -> IntStream.range(1, 4)
                    .mapToObj(i -> new Bar("Bar" + i + " <- " + f.name))
                    .forEach(f.bars::add))
            .flatMap(f -> f.bars.stream())
            .forEach(b -> System.out.println(b.name));


    }

    class Foo {
        String name;
        List<Bar> bars = new ArrayList<>();

        Foo(String name) {
            this.name = name;
        }
    }

    class Bar {
        String name;

        Bar(String name) {
            this.name = name;
        }
    }

}
