package fluentcodes.sandboxjava.designpattern.misc.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Test;
/**
 * Example from 
 * https://dzone.com/articles/avoiding-many-if-blocks
 * Not so nice since it just throws Exceptions and is not generic. 
 *
 */
public class InputValidation { 
	
	
	@Test
	public void testInput() throws Exception {
		List<RegistrationRule> ruleList = new ArrayList<>();
		 ruleList.add(new EmailEmptinessRule());
		 ruleList.add(new EmailValidatationRule());
		 ruleList.add(new ForbiddenEmailDomainsRule());
		 ruleList.add(new NameEmptinessRule());
		 ruleList.add(new AlphabeticNameRule());
		 ruleList.add(new AgeRule());
		
		RegistrationDataChecker checker = new RegistrationDataChecker(ruleList);
		RegistrationData register = new RegistrationData("name", "x.mail@test.de", 18);
		checker.validate(register);
		
	}

	protected static class RegistrationDataChecker {
		 private final List<RegistrationRule> ruleList;
		 
		 protected RegistrationDataChecker (List<RegistrationRule> ruleList) {
			 this.ruleList = ruleList;
		 }
	
		protected void validate(RegistrationData regData) throws Exception{
			for ( RegistrationRule rule : ruleList){
				  rule.validate(regData);
			}
		}	
	}
	
	private static class RegistrationData{
	 private String name;
	 private String email;
	 private int age;
	 protected RegistrationData (String name, String email, int age) {
		 this.name = name;
		 this.email = email;
		 this.age = age;
	 }
	}
	
	private static interface RegistrationRule {
		void validate(RegistrationData regData)  throws Exception;
	}
	
	
	
	private static class AlphabeticNameRule implements RegistrationRule {
		@Override
		public void validate(RegistrationData regData)  throws Exception {
			// TODO Auto-generated method stub
			
		}		
	}
	
	private static class ForbiddenEmailDomainsRule implements RegistrationRule {
		@Override
		public void validate(RegistrationData regData) throws Exception {
			// TODO Auto-generated method stub
			
		}		
	}
	
	private static class AgeRule implements RegistrationRule {
		@Override
		public void validate(RegistrationData regData) throws Exception {
			if (regData.age < 18) {
				throw new Exception("Age is smaller than 18: " + regData.age);
			}
			
		}		
	}
	
	private static class NameEmptinessRule implements RegistrationRule {
		@Override
		public void validate(RegistrationData regData) throws Exception{
			if (regData.name ==null||regData.name.isEmpty()) {
				throw new Exception("Name is empty");
			}
			
		}		
	}
	
	private static class EmailEmptinessRule implements RegistrationRule{
		@Override
		public void validate(RegistrationData regData) throws Exception{
			if (regData.email ==null||regData.email.isEmpty()) {
				throw new Exception("Email is empty");
			}
			
		}		
	}
	

	private static class EmailValidatationRule implements RegistrationRule {
		 //private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		 private static final String EMAIL_PATTERN = "^.+@+.+\\..+$";
		 private final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		
		 @Override
		 public void validate(RegistrationData regData) throws Exception {
			 if (!(regData.email.matches(EMAIL_PATTERN))) {
				 throw new IllegalArgumentException("Email is not a valid email! " + regData.email);
			 }
			 /*if ( !pattern.matcher(regData.email).matches()) {
			   throw new IllegalArgumentException("Email is not a valid email! " + regData.email);
			 }*/
		}
	}
}
