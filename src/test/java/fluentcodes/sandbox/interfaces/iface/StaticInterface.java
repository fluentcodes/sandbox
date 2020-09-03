package fluentcodes.sandbox.interfaces.iface;

public interface StaticInterface {
    public static void callStatic(int i){
        System.out.println("StaticInterface.callStatic(): Wert = "+i);
    }
}
