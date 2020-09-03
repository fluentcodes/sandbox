package fluentcodes.sandbox;

/**
 * Created by Werner on 16.07.2016.
 * Lambda expressions  are touted to be the biggest feature of Java 8. Lambda expression facilitates functional programming.
 * A lambda expression is characterized by the following syntax −
 * parameter -> expression body
 * As an interface we have only one method to allow the simplified syntax in lamda expressions, also could be seen as a specialized anonymous method call.
 * <b>Following are the important characteristics of a lambda expression</b><br/>
 * <li>Optional type declaration − No need to declare the type of a parameter. The compiler can inference the same from the value of the parameter.
 * <li>Optional parenthesis around parameter − No need to declare a single parameter in parenthesis. For multiple parameters, parentheses are required.
 * <li>Optional curly braces − No need to use curly braces in expression body if the body contains a single statement.
 * <li>Optional return keyword − The compiler automatically returns the value if the body has a single expression to return the value. Curly braces are required to indicate that expression returns a value.
 */
public class LamdaExperiments {
    public static void main(String[] args) {
        System.out.println("Start Experiments with Lamda:");
        startIncrementor(5,3);
        startMathOperations(10,5);
        startGreeting();
        System.out.println("Finished Experiments with Lamda.");
    }

    /**
     * Examples of void lamda expression with integer return value
     * @param startValue
     * @param externalIncrementValue
     */
    public static void startIncrementor(int startValue,int externalIncrementValue) {
        System.out.println("Start increment " + startValue + " with external increment " + externalIncrementValue);

        VoidInput incrementor = () -> startValue + 1;
        System.out.println("add 1: " + startValue + "<" + incrementor.operate());

        VoidInput incrementorExternal = () -> startValue + externalIncrementValue;
        System.out.println("external value: " + startValue + "<" + incrementorExternal.operate());
   }

    private interface VoidInput {
        int operate();
    }

    public static void startMathOperations(int first, int second) {
        System.out.println("Start Math Operations with MathOperation");
        LamdaExperiments tester = new LamdaExperiments();

        //with type declaration
        MathOperation addition = (int a, int b) -> a + b;

        //with out type declaration
        MathOperation subtraction = (a, b) -> a - b;

        //with return statement along with curly braces
        MathOperation multiplication = (int a, int b) -> {
            return a * b;
        };
        //without return statement and without curly braces
        MathOperation division = (int a, int b) -> a / b;

        System.out.println(first + " + " + second + " = " + tester.operate(first, second, addition));
        System.out.println(first + " - " + second + " = " + tester.operate(first, second, subtraction));
        System.out.println(first + " * " + second + " = " + tester.operate(first, second, multiplication));
        System.out.println(first + " / " + second + " = " + tester.operate(first, second, division));

        System.out.println(first + " + " + second + " = " + addition.operation(first, second));
        System.out.println(first + " - " + second + " = " + subtraction.operation(first, second));
        System.out.println(first + " * " + second + " = " + multiplication.operation(first, second));
        System.out.println(first + " / " + second + " = " + division.operation(first, second));
    }

    interface MathOperation {
        int operation(int a, int b);
    }

    private int operate(int a, int b, MathOperation mathOperation){
        return mathOperation.operation(a, b);
    }

    public static void startGreeting () {
        System.out.println("Start greeting lamdas");
        GreetingService greetService1 = message ->
                System.out.println("Hello " + message);

        //without parenthesis
        GreetingService greetService2 = (message) ->
                System.out.println("(Hello) " + message);

        greetService1.sayMessage("Olga");
        greetService2.sayMessage("Rupert");
    }

    interface GreetingService {
        void sayMessage(String message);
    }

}
