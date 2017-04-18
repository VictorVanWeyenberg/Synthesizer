/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.circuits;

import domein.Oscillator;
import domein.Synth;
import domein.enums.Wave;
import domein.interfaces.Observer;
import gui.components.Field;
import gui.components.Knob;
import gui.components.Plug;
import gui.components.Switch;

/**
 *
 * @author Reznov
 */
public class OSCCircuit extends CircuitPane {
    
    private Knob OSCAmplitude, OSCFrequency;
    private Switch OSCWave;
    private Plug OSCAmplitudeInput, OSCFrequencyInput, OSCOutput;
    
    private static int index = 0;
    
    public OSCCircuit(Oscillator osc, Observer observer, Synth synth) {
        
        super(synth, observer, "OSC" + String.valueOf(index++));
        
        field.add("amplitude", OSCAmplitude = new Knob(osc.amplitude, 0, 1, synth), 0, 0);
        field.add("frequency", OSCFrequency = new Knob(osc.frequency, 20, 1000, synth), 1, 0);
        
        field.add("wave", OSCWave = new Switch(Wave.values()), 0, 1);
        
        field.add("amplitudePlug", OSCAmplitudeInput = new Plug(observer, osc.amplitude, synth), 0, 2);
        field.add("frequencyPlug", OSCFrequencyInput = new Plug(observer, osc.frequency, synth), 1, 2);
        field.add("outputPlug", OSCOutput = new Plug(observer, osc.output, synth), 2, 2);
        
        OSCWave.valueProperty().addListener((obs, oldV, newV) -> osc.setWave((Wave) newV));
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
