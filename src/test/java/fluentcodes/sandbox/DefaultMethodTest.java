package fluentcodes.sandbox;

import org.junit.Test;

public class DefaultMethodTest {

    private static final fluentcodes.sandbox.SimpleLogger logger = System.out::println;

    @Test
    public void testBaseInterfaceWithDefaultMethods() {

        final BaseImpl impl = new BaseImpl();
        logger.log(impl.method1());
        logger.log(impl.method2());
        logger.log(impl.method3());
        logger.log("");
    }

    private interface BaseInterface {
        String method1();

        default String method2() {
            return "method2 default from BaseInterface";
        }

        default String method3() {
            return "method3 default from BaseInterface";
        }
    }

    private static class BaseImpl implements BaseInterface {
        @Override
        public String method1() {
            return "method1 from impl class BaseImpl";
        }
    }

    @Test
    public void testDerivedInterfaceWithDefaultMethods() {

        final DerivedImpl impl2 = new DerivedImpl();
        logger.log(impl2.method1());
        logger.log(impl2.method2());
        logger.log(impl2.method3());
        logger.log("");
    }

    private interface DerivedInterface extends BaseInterface {
        default String method1() {
            return "method1 default added on DerivedInterface" ;
        }

        String method2(); // removed default from BaseInterface

        default String method3() {
            return "method3 default modified in DerivedInterface";
        }
    }

    private static class DerivedImpl implements DerivedInterface {
        @Override
        public String method2() {
            return "method2 from impl class DerivedImpl";
        }
    }


}
