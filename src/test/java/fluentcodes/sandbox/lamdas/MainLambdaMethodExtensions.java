package fluentcodes.sandbox.lamdas;

import java.util.Arrays;
import java.util.List;

/**
 * @author Charles Ohene
 */
public class MainLambdaMethodExtensions {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("Joe", "Der Großvater", "Peter Pan", "Heidi");
        list.forEach(name -> System.out.println("LambdaName = "+name));
        
        /* Alternativ via MethodenReferenz: Erwartet wird ein Consumer, dem 
        jedes einzelne Element der List an accept() übergeben wird, void kommt zurück. 
        Konzeptionell wird nun jedes Element an an die println() übergeben, void kommt zurück
        --> die Funktionalität der Parameterverarbeitung aus Consumermethode accept() wird 
        nun von MethodenReferenz übernommen, die eigentliche Implementation ist egal
        */
        list.forEach(System.out::println);
        
        //Beispiel einer eigenen statischen MethodenReferenz
        list.forEach(MainLambdaMethodExtensions::myStaticPersonalAccept);
        
        //Beispiel einer eigenen Objekt-MethodenReferenz
        MainLambdaMethodExtensions myObj = new MainLambdaMethodExtensions();
        list.forEach(myObj::myPersonalAccept);
        
        //Bsp KonstruktorReferenz
        BelSamInterface<RefClass> constructedDefault = RefClass::new;
        BelSamInterfaceMitParam<RefClass,Integer> constructedWithParam = RefClass::new;
        System.out.println("constructedDefault "+constructedDefault.createMeNewObj());
        System.out.println("constructedWithParam "+constructedWithParam.createMeNewObj(42));
        
        // siehe auch ConstructorReference.java
    }
    
    static <T> void myStaticPersonalAccept(T t ){
        System.out.println("Personal static accepted "+t);
    }
    
     <T> void myPersonalAccept(T t ){
        System.out.println("Personal Instance accepted "+t);
    }
     
    interface BelSamInterface<T>{
        T createMeNewObj();
    }
    interface BelSamInterfaceMitParam<T, U>{
        T createMeNewObj(U arg);
    }
}