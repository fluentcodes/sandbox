package fluentcodes.sandbox.chapter3_streams.create;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Beispielprogramm demonstiert die Verarbeitung von Werten primitiven Typs mit Streams.
 * 
 * @author Michael Inden
 * 
 * Copyright 2014 by Michael Inden 
 */
public class PrimitivesStreamExample 
{	
	public static void main(final String[] args) 
	{
		final List<String> names = Arrays.asList("Mike", "Stefan", "Nikolaos");
		final Stream<String> values = names.stream().   // Stream<String>
						mapToInt(String::length).  		// IntStream
						asLongStream().            		// LongStream
						boxed().                   		// Stream<Long>
						mapToDouble(x -> x * .75). 		// DoubleStream
						mapToObj(val -> "Val: " + val); // Stream<String>

		values.forEach(System.out::println);
	}
}