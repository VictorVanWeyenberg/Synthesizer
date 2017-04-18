/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.circuits;

import com.jsyn.unitgen.MixerStereo;
import domein.Synth;
import domein.enums.Wave;
import domein.interfaces.Observer;
import gui.MainPane;
import gui.components.Field;
import gui.components.Knob;
import gui.components.Plug;
import gui.components.Switch;

/**
 *
 * @author Reznov
 */
public class SMCircuit extends CircuitPane {
    
    private Knob SMAmplitude, SMGain, SMPan;
    private Plug SMInput;
    
    private static int index = 0;

    public SMCircuit(MixerStereo sm, Observer observer, Synth synth) {
        
        super(synth, observer, "SM" + String.valueOf(index++));

        field.add("amplitude", SMAmplitude = new Knob(sm.amplitude, 0, 1, synth), 0, 0);
        field.add("gain", SMGain = new Knob(sm.gain, 0, 10, synth), 1, 0);
        field.add("pan", SMPan = new Knob(sm.pan, -1, 1, synth), 2, 0);
        
        field.add("inputplug", SMInput = new Plug(observer, sm.input, ((MainPane)observer).getSynthesizer()), 0, 1);
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
