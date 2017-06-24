/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.circuits;

import domein.generators.Oscillator;
import domein.Synth;
import domein.enums.Wave;
import domein.interfaces.Observer;
import gui.components.Buchla;
import gui.components.Knob;
import gui.components.Plug;
import gui.components.Switch;
import java.lang.reflect.Field;

/**
 *
 * @author Reznov
 */
public class OSCCircuit extends CircuitPane {
    
    private Knob OSCAmplitude, OSCFrequency;
    private Plug OSCAmplitudeInput, OSCFrequencyInput, OSCFrequenctOutput;
    private Plug sine, triangle, square, saw, func;
    
    private static int index = 0;
    
    public OSCCircuit(Oscillator osc) {
        
        super("OSC" + String.valueOf(index++));
        
        add(OSCAmplitude = new Knob(osc.amplitude, 0, 1), 0, 0);
        add(OSCFrequency = new Knob(osc.frequency, 20, 1000), 1, 0);
        
        add(OSCAmplitudeInput = new Plug(osc, osc.amplitude), 0, 1);
        add(OSCFrequencyInput = new Plug(osc, osc.frequency), 1, 1);
        add(OSCFrequenctOutput = new Plug(osc, osc.freqOut), 2, 1);
        
        add(sine = new Plug(osc, osc.sineOut), 0, 2);
        add(triangle = new Plug(osc, osc.triangleOut), 1, 2);
        add(square = new Plug(osc, osc.squareOut), 2, 2);
        add(saw = new Plug(osc, osc.sawOut), 3, 2);
        add(func = new Plug(osc, osc.funcOut), 4, 2);
    }
    
}
