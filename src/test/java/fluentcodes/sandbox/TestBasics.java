package fluentcodes.sandbox;

import fluentcodes.sandbox.ObjectLogger;
import fluentcodes.sandbox.SimpleLogger;

/**
 * Created by werner/ecube on 08.08.16.
 * Some examples from shttp://winterbe.com/posts/2014/07/31/java8-stream-tutorial-examples/
 * Topics are the possibilities creating streams and the different intermediate operation mapToObject, mapToInt, mapToDouble and
 */
public class TestBasics {


    public fluentcodes.sandbox.ObjectLogger print = x->{System.out.println(x + " " + x.getClass().getName());};
    public fluentcodes.sandbox.SimpleLogger info  = System.out::println;

}
