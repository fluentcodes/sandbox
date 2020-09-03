package fluentcodes.sandbox.lamdas;

/**
 * @author Charles Ohene
 */
public class RefClass {

    int a;

    RefClass() {
    }

    RefClass(int a) {
        this.a = a;
    }

    public String toString() {
        return "RefClass: " + a;
    }
}
