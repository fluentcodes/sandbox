package fluentcodes.sandbox.chapter3_streams.external_internal_iteration;

import java.util.Arrays;
import java.util.List;

/**
 * Beispielprogramm zur Demonstration der internen Itration auf 3 verschiedene Arten.
 * 
 * @author Michael Inden
 * 
 * Copyright 2014 by Michael Inden 
 */
public class InternalIterationExample 
{
	public static void main(final String[] args) 
	{
		final List<String> names = Arrays.asList("Andi", "Mike", "Ralph", "Stefan" );
		
		// Interne Iteration in drei Varianten
		names.forEach((String name) -> { System.out.println(name); });
		names.forEach(name -> System.out.println(name) );
		names.forEach(System.out::println);
	}
}