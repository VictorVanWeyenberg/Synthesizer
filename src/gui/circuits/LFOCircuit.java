/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.circuits;

import domein.generators.LFO;
import gui.components.Knob;
import gui.components.Plug;

/**
 *
 * @author Reznov
 */
public class LFOCircuit extends CircuitPane {

    private Knob LFOFrequency, LFOAmount;
    private Plug LFOGate, LFOFreqOutPlug;
    private Plug sine, triangle, square, saw, func;

    private static int index = 0;

    public LFOCircuit(LFO lfo) {
        
        super("LFO" + String.valueOf(index++));

        add(LFOFrequency = new Knob(lfo.frequency, 0, 20), 0, 0);
        add(LFOAmount = new Knob(lfo.amount, 0, 1), 1, 0);
        
        add(LFOGate = new Plug(lfo, lfo.gateOut), 0, 1);
        add(LFOFreqOutPlug = new Plug(lfo, lfo.freqOut), 0, 2);
        
        add(sine = new Plug(lfo, lfo.sineOut), 0, 2);
        add(triangle = new Plug(lfo, lfo.triangleOut), 1, 2);
        add(square = new Plug(lfo, lfo.squareOut), 2, 2);
        add(saw = new Plug(lfo, lfo.sawOut), 3, 2);
        add(func = new Plug(lfo, lfo.funcOut), 4, 2);
    }

}
