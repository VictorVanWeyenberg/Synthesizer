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
import gui.components.Field;
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

    public MCircuit(Multiply m, Observer observer, Synth synth) {
        
        super(synth, observer, "Multiply" + String.valueOf(index++));

        field.add("frequency", MInputA = new Knob(m.inputA, 20, 1000, synth), 0, 0);
        field.add("resonance", MInputB = new Knob(m.inputB, 20, 1000, synth), 1, 0);
        
        field.add("inputpluga", MInputAPlug = new Plug(observer, m.inputA, ((MainPane)observer).getSynthesizer()), 0, 1);
        field.add("inputplugb", MInputBPlug = new Plug(observer, m.inputB, ((MainPane)observer).getSynthesizer()), 1, 1);
        field.add("inputplug", MOutput = new Plug(observer, m.output, ((MainPane)observer).getSynthesizer()), 2, 1);
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Object e) {
        observers.forEach(o -> o.update(e));
    }
    
}
