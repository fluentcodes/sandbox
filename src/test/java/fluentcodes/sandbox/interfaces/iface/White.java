/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fluentcodes.sandbox.interfaces.iface;

/**
 *
 * @author joecool
 */
public interface White {
    default void writeColor(){
        System.out.println("Default is white");
    };
}
