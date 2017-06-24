/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.circuits;

import com.jsyn.unitgen.MixerStereo;
import domein.Synth;
import domein.interfaces.Observer;
import gui.MainPane;
import gui.components.Knob;
import gui.components.Plug;

/**
 *
 * @author Reznov
 */
public class VCACircuit extends CircuitPane {
    
    private Knob SMAmplitude, SMGain, SMPan;
    private Plug SMInput, SMGainPlug;
    
    private static int index = 0;

    public VCACircuit(MixerStereo sm) {
        
        super("VCA" + String.valueOf(index++));

        add(SMAmplitude = new Knob(sm.amplitude, 0, 1), 0, 0);
        add(SMGain = new Knob(sm.gain, 0, 10), 1, 0);
        add(SMPan = new Knob(sm.pan, -1, 1), 2, 0);
        
        add(SMInput = new Plug(sm, sm.input), 0, 1);
        add( SMGainPlug = new Plug(sm, sm.gain), 1, 1);
    }
    
}
