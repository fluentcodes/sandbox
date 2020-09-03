package fluentcodes.sandboxjava.designpattern.behavioural.visitor;

import org.junit.Test;
/**
 * Example from https://en.wikipedia.org/wiki/Visitor_pattern
 * https://en.wikibooks.org/wiki/Computer_Science_Design_Patterns/Visitor
 * @author Werner Diwischek
 *
 */
public class VisitorCarTest {
	
	@Test
	public void inspect() {
	        final Car car = new Car();
	        car.accept(new CarElementPrintVisitor());
	        car.accept(new CarElementDoVisitor());
	}
	
	interface CarElement {
	    void accept(CarElementVisitor visitor);
	}

	interface CarElementVisitor {
	    void visit(Body body);
	    void visit(Car car);
	    void visit(Engine engine);
	    void visit(Wheel wheel);
	}

	private static final class Car implements CarElement {
	    CarElement[] elements;

	    public Car() {
	        this.elements = new CarElement[] {
	            new Wheel("front left"), new Wheel("front right"),
	            new Wheel("back left"), new Wheel("back right"),
	            new Body(), new Engine()
	        };
	    }

	    public void accept(final CarElementVisitor visitor) {
	        for (CarElement elem : elements) {
	            elem.accept(visitor);
	        }
	        visitor.visit(this);
	    }
	}

	private static final class Body implements CarElement {
	    public void accept(final CarElementVisitor visitor) {
	        visitor.visit(this);
	    }
	}

	private static final class Engine implements CarElement {
	    public void accept(final CarElementVisitor visitor) {
	        visitor.visit(this);
	    }
	}

	private static final class Wheel implements CarElement {
	    private String name;

	    public Wheel(final String name) {
	        this.name = name;
	    }

	    public String getName() {
	        return name;
	    }

	    public void accept(final CarElementVisitor visitor) {
	        /*
	         * accept(CarElementVisitor) in Wheel implements
	         * accept(CarElementVisitor) in CarElement, so the call
	         * to accept is bound at run time. This can be considered
	         * the *first* dispatch. However, the decision to call
	         * visit(Wheel) (as opposed to visit(Engine) etc.) can be
	         * made during compile time since 'this' is known at compile
	         * time to be a Wheel. Moreover, each implementation of
	         * CarElementVisitor implements the visit(Wheel), which is
	         * another decision that is made at run time. This can be
	         * considered the *second* dispatch.
	         */
	        visitor.visit(this);
	    }
	}

	private static final class CarElementDoVisitor implements CarElementVisitor {
	    public void visit(final Body body) {
	        System.out.println("Moving my body");
	    }

	    public void visit(final Car car) {
	        System.out.println("Starting my car");
	    }

	    public void visit(final Wheel wheel) {
	        System.out.println("Kicking my " + wheel.getName() + " wheel");
	    }

	    public void visit(final Engine engine) {
	        System.out.println("Starting my engine");
	    }
	}

	private static final class CarElementPrintVisitor implements CarElementVisitor {
	    public void visit(final Body body) {
	        System.out.println("Visiting body");
	    }

	    public void visit(final Car car) {
	        System.out.println("Visiting car");
	    }

	    public void visit(final Engine engine) {
	        System.out.println("Visiting engine");
	    }

	    public void visit(final Wheel wheel) {
	        System.out.println("Visiting " + wheel.getName() + " wheel");
	    }
	}

}
