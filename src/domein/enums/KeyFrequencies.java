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
    
    Q(0), 
    Z(1), 
    S(2), 
    E(3), 
    D(4), 
    F(5), 
    T(6), 
    G(7), 
    Y(8), 
    H(9), 
    U(10), 
    J(11), 
    K(12);
    
    private int pitch;
    
    KeyFrequencies(int pitch) {
        this.pitch = pitch;
    }
    
    public int pitch() {
        return pitch + 60;
    }
}
