package fluentcodes.sandbox.streams;

import fluentcodes.sandbox.models.Person;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by werner/ecube on 07.08.16.
 * Again examples from http://winterbe.com/posts/2014/07/31/java8-stream-tutorial-examples/
 * Change number of fork pool size with -Djava.util.concurrent.ForkJoinPool.common.parallelism=5
 */
public class ParallelTest {
    @Test
    public void testParallel() {
        System.out.println("Write out what happened: ");
        Arrays.asList("a1", "a2", "b1", "c2", "c1")
            .parallelStream()
            .filter(s -> {
                System.out.format("filter: %s [%s]\n",
                        s, Thread.currentThread().getName());
                return true;
            })
            .map(s -> {
                System.out.format("map: %s [%s]\n",
                        s, Thread.currentThread().getName());
                return s.toUpperCase();
            })
            .forEach(s -> System.out.format("forEach: %s [%s]\n",
                    s, Thread.currentThread().getName()));

    }
    @Test
    public void testParallelWithSort() {
        System.out.println("Write out what happened: Sort will always be in the main thread.");
        Arrays.asList("a1", "a2", "b1", "c2", "c1")
                .parallelStream()
                .filter(s -> {
                    System.out.format("filter: %s [%s]\n",
                            s, Thread.currentThread().getName());
                    return true;
                })
                .map(s -> {
                    System.out.format("map: %s [%s]\n",
                            s, Thread.currentThread().getName());
                    return s.toUpperCase();
                })
                .sorted((s1, s2) -> {
                    System.out.format("sort: %s <> %s [%s]\n",
                            s1, s2, Thread.currentThread().getName());
                    return s1.compareTo(s2);
                })
                .forEach(s -> System.out.format("forEach: %s [%s]\n",
                        s, Thread.currentThread().getName()));

    }

    @Test
    public void testParallelReducePerson () {
        System.out.println("Now reducing with parallel stream:");
        Person.getPersonList()
            .parallelStream()
            .reduce(0,
                (sum, p) -> {
                    System.out.format("accumulator: sum=%s; person=%s [%s]\n",
                            sum, p, Thread.currentThread().getName());
                    return sum += p.age;
                },
                (sum1, sum2) -> {
                    System.out.format("combiner: sum1=%s; sum2=%s [%s]\n",
                            sum1, sum2, Thread.currentThread().getName());
                    return sum1 + sum2;
                });
    }
}
