package fluentcodes.sandboxjava.designpattern.behavioural.chainofresponsibility;

import org.junit.Test;
/**
 * Example from https://en.wikipedia.org/wiki/Chain-of-responsibility_pattern
 * @author Werner Diwischek
 *
 */

public class PurchasePowerExampleTest {

	@Test
	public void checkAuthorities() {
        ManagerPPower manager = new ManagerPPower();
        DirectorPPower director = new DirectorPPower();
        VicePresidentPPower vp = new VicePresidentPPower();
        PresidentPPower president = new PresidentPPower();
        
        manager.setSuccessor(director);
        director.setSuccessor(vp);
        vp.setSuccessor(president);
        
        Double[] purchaseAmounts = new Double[] {0.0,200.0,500.0,1000.0,5000.0,6000.0,10000.0,11000.0,20000.0,21000.0,30000.0, 31000.0};
        for (Double amount: purchaseAmounts) {
            manager.processRequest(new PurchaseRequest(amount, "General"));
        }
	}
	
	abstract class PurchasePower {
	    protected static final double BASE = 500;
	    protected PurchasePower successor;

	    abstract protected double getAllowable();
	    abstract protected String getRole();

	    public void setSuccessor(PurchasePower successor) {
	        this.successor = successor;
	    }

	    public void processRequest(PurchaseRequest request) {
	        if (request.getAmount() < this.getAllowable()) {
	            System.out.println(this.getRole() + " will approve $" + request.getAmount());
	        } else if (successor != null) {
	            successor.processRequest(request);
	        }
	        else {
	        	System.out.println( "No one will will approve $" + request.getAmount());
	        }
	    }
	}
	
	class ManagerPPower extends PurchasePower {
	    
	    protected double getAllowable() {
	        return BASE * 10;
	    }

	    protected String getRole() {
	        return "Manager";
	    }
	}

	class DirectorPPower extends PurchasePower {
	    
	    protected double getAllowable() {
	        return BASE * 20;
	    }

	    protected String getRole() {
	        return "Director";
	    }
	}

	class VicePresidentPPower extends PurchasePower {
	    
	    protected double getAllowable() {
	        return BASE * 40;
	    }

	    protected String getRole() {
	        return "Vice President";
	    }
	}

	class PresidentPPower extends PurchasePower {

	    protected double getAllowable() {
	        return BASE * 60;
	    }

	    protected String getRole() {
	        return "President";
	    }
	}
	
	class PurchaseRequest {

	    private double amount;
	    private String purpose;

	    public PurchaseRequest(double amount, String purpose) {
	        this.amount = amount;
	        this.purpose = purpose;
	    }

	    public double getAmount() {
	        return this.amount;
	    }

	    public void setAmount(double amount)  {
	        this.amount = amount;
	    }

	    public String getPurpose() {
	        return this.purpose;
	    }
	    public void setPurpose(String purpose) {
	        this.purpose = purpose;
	    }
	}
}
