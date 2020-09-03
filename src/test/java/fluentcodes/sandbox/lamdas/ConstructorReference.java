package fluentcodes.sandbox.lamdas;

import java.util.ArrayList;
import java.util.List;

interface ConstructorReferenceSAM<T> {

    T whatEverMethodName();
}

interface ConstructorReferenceSAMWithArgs<T, U> {

    T createMeANewObject(U arg);
}

interface ConstructorReferenceSAMWithParameterizedArg<T, U> {

    T magic(List<U> arg);
}

public class ConstructorReference {

    private String content;

    private List<String> contents;

    public ConstructorReference() {
        this.content = "created by constructor reference";
        System.out.println("ConstructorReference.DefaultConstructor finished.");
    }

    public ConstructorReference(String content) {
        this.content = content;
    }

    public ConstructorReference(List<String> contents) {
        this.contents = contents;
    }

    public String getContent() {
        return content;
    }

    public List<String> getContents() {
        return contents;
    }

    public static void main(String... args) {
        ConstructorReferenceSAM<ConstructorReference> constructorSam = ConstructorReference::new;
        System.out.println("nach Ref..");
        System.out.println("\n\n\tcontent = " + constructorSam.whatEverMethodName().getContent());

        ConstructorReferenceSAMWithArgs<ConstructorReference, String> constructorSamWithArg = ConstructorReference::new;
        System.out.println("\n\n\tcontent by arg = " + constructorSamWithArg.createMeANewObject("created by constructor reference with arg").getContent());

        ConstructorReferenceSAMWithParameterizedArg<ConstructorReference, String> constructorSamWithParameterizedArg = ConstructorReference::<String>new;
        System.out.println("\n\n\tcontents size by parameterized arg = " + constructorSamWithParameterizedArg.magic(new ArrayList<String>()).getContents().size());
    }
}
