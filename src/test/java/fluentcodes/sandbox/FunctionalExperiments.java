package fluentcodes.sandbox;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;

/**
 * Created by Werner on 16.07.2016.
 * Originated from http://www.tutorialspoint.com/java8/java8_functional_interfaces.htm
 */
public class FunctionalExperiments {
    public static void main(String[] args) {
        System.out.println("Start Experiments with Functional:");
        startPredicate();
        System.out.println("Finished Experiments with Functional.");
    }
    public static void startPredicate() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);

        // Predicate<Integer> predicate = n -> true
        // n is passed as parameter to test method of Predicate interface
        // test method will always return true no matter what value n has.

        System.out.println("Print all numbers:");

        //pass n as parameter
        eval(list, n->true);

        // Predicate<Integer> predicate1 = n -> n%2 == 0
        // n is passed as parameter to test method of Predicate interface
        // test method will return true if n%2 comes to be zero

        System.out.println("Print even numbers:");
        eval(list, n-> n%2 == 0 );

        // Predicate<Integer> predicate2 = n -> n > 3
        // n is passed as parameter to test method of Predicate interface
        // test method will return true if n is greater than 3.

        System.out.println("Print numbers greater than 3:");
        eval(list, n-> n > 3 );
    }

    public static void eval(List<Integer> list, Predicate<Integer> predicate) {
        for(Integer n: list) {
           if(predicate.test(n)) {
                System.out.println(n + " ");
            }
        }
    }
}
