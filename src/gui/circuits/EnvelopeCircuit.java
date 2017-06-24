/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.circuits;

import domein.generators.Envelope;
import gui.components.Buchla;
import gui.components.Knob;
import gui.components.Plug;
import gui.components.Switch;

/**
 *
 * @author Reznov
 */
public class EnvelopeCircuit extends CircuitPane {
    
    private Knob ENVAmplitude, ENVDelay, ENVAttack, ENVHold, ENVDecay, ENVSustain, ENVRelease;
    private Switch ENVPlayMode;
    private Plug ENVInput, ENVTrigger, ENVOutput, ENVAmplitudeInput;
    
    private static int index = 0;
    
    public EnvelopeCircuit(Envelope env) {
        
        super("ENV" + index++);
        
        this.getChildren().add(buchla = new Buchla(name));
        
        add(ENVAttack = new Knob(env.attack, 0.01, 8), 0, 0);
        add(ENVDecay = new Knob(env.decay, 0.01, 8), 1, 0);
        add(ENVSustain = new Knob(env.sustain, 0, 1), 2, 0);
        add(ENVRelease = new Knob(env.release, 0.01, 8), 3, 0);
        
        add(ENVTrigger = new Plug(env, env.trigger), 0, 2);
        add(ENVOutput = new Plug(env, env.output), 1, 2);
    }
    
}
