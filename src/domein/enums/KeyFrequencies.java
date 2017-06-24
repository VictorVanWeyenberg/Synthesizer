/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein.enums;

/**
 *
 * @author victor
 */
public enum KeyFrequencies {
    
    c(0), 
    C$(1), 
    D(2), 
    D$(3), 
    E(4), 
    F(5), 
    F$(6), 
    G(7), 
    G$(8), 
    A(9), 
    A$(10), 
    B(11), 
    C(12);
    
    private int pitch;
    
    KeyFrequencies(int pitch) {
        this.pitch = pitch;
    }
    
    public int pitch() {
        return pitch;
    }
}
