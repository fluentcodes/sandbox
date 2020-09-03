package fluentcodes.sandbox.chapter3_streams.external_internal_iteration;

import java.util.ArrayList;
import java.util.List;

/**
 * Beispielprogramm zur Demonstration der Methode removeIf()
 * 
 * @author Michael Inden
 * 
 * Copyright 2014 by Michael Inden 
 */
public class CollectionsRemoveIfExample 
{
	public static void main(final String[] args) 
	{
	   final List<String> names = createDemoNames();

	   // $\mbox{\bfseries L�schaktionen ausf�hren 	
	   names.removeIf(String::isEmpty);

	   System.out.println(names);
	}

	private static List<String> createDemoNames() 
	{
	   final List<String> names = new ArrayList<>();
	   names.add("Max");
	   names.add("");             // Leereintrag  
	   names.add("Andy");
	   names.add("Michael");
	   names.add("   ");          // potenziell auch ein "Leereintrag" 
	   names.add("Stefan");
	   return names;
	}
}