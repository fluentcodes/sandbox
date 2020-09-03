package fluentcodes.sandbox.optionals;

import org.junit.Test;

import java.util.Optional;

/**
 * Created by werner/ecube on 07.08.16.
 * http://winterbe.com/posts/2014/07/31/java8-stream-tutorial-examples/
 */
public class OptionalFlatMapTest {
    @Test
    public void flatMapOptional() {
        Outer outer = new Outer();
        System.out.println("Empty outer classical:");
        if (outer != null && outer.nested != null && outer.nested.inner != null) {
            System.out.println(outer.nested.inner.foo);
        }
        System.out.println("Empty outer optional with flatMap:");
        Optional.of(outer)
                .flatMap(o -> Optional.ofNullable(o.nested))
                .flatMap(n -> Optional.ofNullable(n.inner))
                .flatMap(i -> Optional.ofNullable(i.foo))
                .ifPresent(System.out::println);

    }
    @Test
    public void flatMapOptionalNonEmpty() {
        Outer outer = new Outer("test");
        System.out.println("Non empty outer classical:");
        if (outer != null && outer.nested != null && outer.nested.inner != null) {
            System.out.println(outer.nested.inner.foo);
        }
        System.out.println("Non empty outer optional with flatMap:");
        Optional.of(outer)
                .flatMap(o -> Optional.ofNullable(o.nested))
                .flatMap(n -> Optional.ofNullable(n.inner))
                .flatMap(i -> Optional.ofNullable(i.foo))
                .ifPresent(System.out::println);

    }

    class Outer {
        public Outer() {
        }
        public Outer(String inner) {
            nested=new Nested(inner);
        }
        Nested nested;
    }

    class Nested {
        public Nested(String inner) {
            this.inner=new Inner();
            this.inner.foo=inner;
        }
        Inner inner;
    }

    class Inner {
        String foo;
    }

}
