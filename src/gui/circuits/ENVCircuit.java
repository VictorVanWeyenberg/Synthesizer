/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.circuits;

import com.jsyn.unitgen.EnvelopeDAHDSR;
import domein.Envelope;
import domein.Oscillator;
import domein.Synth;
import domein.enums.EnvelopePlayMode;
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
public class ENVCircuit extends CircuitPane {
    
    private Knob ENVAmplitude, ENVDelay, ENVAttack, ENVHold, ENVDecay, ENVSustain, ENVRelease;
    private Switch ENVPlayMode;
    private Plug ENVInput, ENVTrigger, ENVOutput, ENVAmplitudeInput;
    
    private static int index = 0;
    
    public ENVCircuit(Envelope env, Observer observer, Synth synth) {
        
        super(synth, observer, "ENV" + index++);
        
        this.getChildren().add(field = new Field(name));
        addObserver(observer);
        
        field.add("attack", ENVAttack = new Knob(env.attack, 0.01, 8, synth), 0, 0);
        field.add("decay", ENVDecay = new Knob(env.decay, 0.01, 8, synth), 1, 0);
        field.add("sustain", ENVSustain = new Knob(env.sustain, 0, 1, synth), 2, 0);
        field.add("release", ENVRelease = new Knob(env.release, 0.01, 8, synth), 3, 0);
        
        field.add("playmode", ENVPlayMode = new Switch(EnvelopePlayMode.values()), 0, 1);
        
        field.add("trigger", ENVTrigger = new Plug(observer, env.trigger, ((MainPane)observer).getSynthesizer()), 0, 2);
        field.add("output", ENVOutput = new Plug(observer, env.output, ((MainPane)observer).getSynthesizer()), 1, 2);
        
        ENVPlayMode.valueProperty().addListener((obs, oldV, newV) -> {
            env.setPlayMode((EnvelopePlayMode) newV);
        });
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
