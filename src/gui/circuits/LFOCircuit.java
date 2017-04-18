/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.circuits;

import domein.LFO;
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
public class LFOCircuit extends CircuitPane {

    private Knob LFOFrequency, LFOAmount;
    private Switch LFOWave;
    private Plug LFOOutput;

    private static int index = 0;

    public LFOCircuit(LFO lfo, Observer observer, Synth synth) {
        
        super(synth, observer, "LFO" + String.valueOf(index++));

        field.add("frequency", LFOFrequency = new Knob(lfo.frequency, 0, 20, synth), 0, 0);
        field.add("amount", LFOAmount = new Knob(lfo.amount, 0, 1, synth), 1, 0);
        
        field.add("wave", LFOWave = new Switch(Wave.values()), 0, 1);
        
        field.add("outputplug", LFOOutput = new Plug(observer, lfo.output, ((MainPane)observer).getSynthesizer()), 0, 2);
        LFOWave.valueProperty().addListener((obs, oldV, newV) -> lfo.setWave((Wave) newV));
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
