package fluentcodes.sandbox;

import java.util.Optional;

/**
 * Created by Werner on 16.07.2016.
 * Originated from http://www.tutorialspoint.com/java8/java8_optional_class.htm
 * Optional is a container object which is used to contain not-null objects. Optional object is used to represent null with absent value.
 * This class has various utility methods to facilitate code to handle values as ‘available’ or ‘not available’ instead of checking null values.
 * */
public class OptionalExperiments {
    public static void main(String[] args) {
        System.out.println("Start Experiments with Optional:");
        new OptionalExperiments().startOptionalInteger();
        System.out.println("Finished Experiments with Optional.");
    }
        public void startOptionalInteger (){
            Integer value1 = null;
            Integer value2 = new Integer(10);

            //Optional.ofNullable - allows passed parameter to be null.
            Optional<Integer> a = Optional.ofNullable(value1);

            //Optional.of - throws NullPointerException if passed parameter is null
            Optional<Integer> b = Optional.of(value2);
            System.out.println(sum(a,b));
        }

    public Integer sum(Optional<Integer> a, Optional<Integer> b){

        System.out.println("First parameter is present: " + a.isPresent());
        System.out.println("Second parameter is present: " + b.isPresent());

        //Optional.orElse - returns the value if present otherwise returns
        //the default value passed.
        Integer value1 = a.orElse(new Integer(0));

        //Optional.get - gets the value, value should be present
        Integer value2 = b.get();
        return value1 + value2;
    }


}
