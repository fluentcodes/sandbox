package fluentcodes.sandbox;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class Main_1Predicates_UnaryOps {
    public static void main(String[] args) {
        final Predicate<String> isNull = str -> str == null;
        final Predicate<String> isEmpty = String::isEmpty;
        final Predicate<String> isBlankEmpty = str -> str.trim().isEmpty();
        final Predicate<Person> isAdult = person -> person.getAge() >= 18;
        final Predicate<Person> isMale = person -> person.isMale();
        
        System.out.println("Is null? "+isNull.test(null));
        System.out.println("Is empty? "+isEmpty.test("   "));
        System.out.println("Is blankEmpty? "+isBlankEmpty.test("   "));
        System.out.println("Is adult? "+isAdult.test(new fluentcodes.sandbox.Person(17,true)));
        System.out.println("Is male? "+isMale.test(new fluentcodes.sandbox.Person(1,true)));


        // System.out.println("is obj = " + ((System.out::println) instanceof Object));
        
        // Kombinationen mit and, or, negate
        final Predicate<Person> isRealMan = isMale.and(isAdult); //Männlich, mind. 18
        final Predicate<Person> isGirl = isMale.negate().and(isAdult.negate()); //Weiblich, höchstens 17
        final Predicate<Person> allowedToCryInPublic = isRealMan.or(isGirl);
        System.out.println("allowedToCryInPublic? "+allowedToCryInPublic.test(new fluentcodes.sandbox.Person(35,true)));    //Kerl
        System.out.println("allowedToCryInPublic? "+allowedToCryInPublic.test(new fluentcodes.sandbox.Person(15,true)));    //Junge
        System.out.println("allowedToCryInPublic? "+allowedToCryInPublic.test(new fluentcodes.sandbox.Person(58,false)));   //Reife Dame
        
        // Bulk Ops auf Collections
        ArrayList<String> al = new ArrayList<>();
        al.add("Joe");
        al.add("");
        //Problematisch al.add(null);
        al.add("Heidi");
        al.add("   ");
        al.add("Der Großvater");
        al.removeIf(isBlankEmpty);
        // al.removeIf(isNull.or(isBlankEmpty));
        System.out.println("Prädikats-Liste: "+al);
        
        //Unäre Operatoren: Ein-/Ausgabetyp sind gleich, Wert wird ggf. verändert
        final UnaryOperator<String> trimmer = str -> str.trim();
        final UnaryOperator<String> mapNullToEmpty = str -> str == null? "" : str;
        // Effizientes Verarbeiten der Liste:
        al = new ArrayList<String>();
        al.add("  Joe");
        al.add("");
        al.add(null);   //zuvor problematisch 
        al.add("Heidi");
        al.add("   ");
        al.add("Der Großvater");
        al.replaceAll(mapNullToEmpty);  // Unäre Op
        al.replaceAll(trimmer);         // Unäre Op
        al.removeIf(String::isEmpty);   // Prädikat
        
        System.out.println("UnäreOps-Liste: "+al);

        // ok: Supplier<String> sup = String::new;
    }
}

// Hilfsklasse
class Person{
    int age;
    boolean male;
    Person(int age, boolean male){
        this.age = age;
        this.male = male;
    }

    public int getAge() {
        return age;
    }

    public boolean isMale() {
        return male;
    }
}