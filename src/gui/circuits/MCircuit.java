/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.circuits;

import com.jsyn.unitgen.Multiply;
import domein.Synth;
import domein.interfaces.Observer;
import gui.MainPane;
import gui.components.Buchla;
import gui.components.Knob;
import gui.components.Plug;

/**
 *
 * @author Reznov
 */
public class MCircuit extends CircuitPane {
    
    private Knob MInputA, MInputB;
    private Plug MInputAPlug, MInputBPlug, MOutput;

    private static int index = 0;

    public MCircuit(Multiply m) {
        
        super("Multiply" + String.valueOf(index++));

        add(MInputA = new Knob(m.inputA, 20, 1000), 0, 0);
        add(MInputB = new Knob(m.inputB, 20, 1000), 1, 0);
        
        add(MInputAPlug = new Plug(m, m.inputA), 0, 1);
        add(MInputBPlug = new Plug(m, m.inputB), 1, 1);
        add(MOutput = new Plug(m, m.output), 2, 1);
    }
    
}
