package fluentcodes.sandboxjava.designpattern.behavioural.bridge;

import org.junit.Test;

public class BridgeShapeTest {

	
	@Test
	    public  void  call(){
	        Shape[] shapes = new Shape[] {
	            new CircleShape(1, 2, 3, new DrawingAPI1()),
	            new CircleShape(5, 7, 11, new DrawingAPI2())
	        };

	        for (Shape shape : shapes) {
	            shape.resizeByPercentage(2.5);
	            shape.draw();
	        }
	    
	}	
	
	/** "Implementor" */
	private static interface DrawingAPI {
	    public void drawCircle(final double x, final double y, final double radius);
	}
	


	/** "ConcreteImplementor"  1/2 */
	private final static class DrawingAPI1 implements DrawingAPI {
	    public void drawCircle(final double x, final double y, final double radius) {
	        System.out.printf("API1.circle at %f:%f radius %f\n", x, y, radius);
	    }
	}

	/** "ConcreteImplementor" 2/2 */
	private final static class DrawingAPI2 implements DrawingAPI {
	    public void drawCircle(final double x, final double y, final double radius) {
	        System.out.printf("API2.circle at %f:%f radius %f\n", x, y, radius);
	    }
	}

	/** "Abstraction" */
	private static abstract class Shape {
	    protected DrawingAPI drawingAPI;

	    protected Shape(final DrawingAPI drawingAPI){
	        this.drawingAPI = drawingAPI;
	    }

	    public abstract void draw();                                 // low-level
	    public abstract void resizeByPercentage(final double pct);   // high-level
	}

	/** "Refined Abstraction" */
	private final static class CircleShape extends Shape {
	    private double x, y, radius;
	    public CircleShape(final double x, final double y, final double radius, final DrawingAPI drawingAPI) {
	        super(drawingAPI);
	        this.x = x;  this.y = y;  this.radius = radius;
	    }

	    // low-level i.e. Implementation specific
	    public void draw() {
	        drawingAPI.drawCircle(x, y, radius);
	    }
	    // high-level i.e. Abstraction specific
	    public void resizeByPercentage(final double pct) {
	        radius *= (1.0 + pct/100.0);
	    }
	}


}
