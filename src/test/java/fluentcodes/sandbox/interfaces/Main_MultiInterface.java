package fluentcodes.sandbox.interfaces;


import fluentcodes.sandbox.interfaces.iface.Black;
import fluentcodes.sandbox.interfaces.iface.StaticInterface;
import fluentcodes.sandbox.interfaces.iface.White;

public class Main_MultiInterface implements Black, White {
    // Problem: 2 x writeColor() mit default-Body aus den Interfaces Black, White geerbt
    
    // Alternative 1 - selbst definieren
    public void writeColor(){
        System.out.println("Default is black");
    };
    
    
    
    // Alternative 2 - selbst definieren, aber explizites Default-Verhalten aus Interface nutzen
    public void writeColor2(){
        Black.super.writeColor();
    };
    
    public static void main(String[] args) {
        // Aufruf einer statischen Methode via Interface
        StaticInterface.callStatic(42);
        
    }
    
    
    
}
