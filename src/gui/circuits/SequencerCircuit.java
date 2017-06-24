/**
 *
 * @date May 9, 2017
 * 
 * @author Reznov
 * @website http://www.reznov.be/
 */

package gui.circuits;

import com.jsyn.ports.UnitInputPort;
import com.softsynth.math.AudioMath;
import domein.generators.Sequencer;
import domein.enums.GateMode;
import domein.enums.KeyFrequencies;
import gui.components.Knob;
import gui.components.Plug;
import gui.components.SwitchV;
import java.util.ArrayList;
import java.util.List;

public class SequencerCircuit extends CircuitPane {
    
    private static int index = 0;
    
    private List<SwitchV> notes;
    private Plug gateIn, baseFrequency, frequencyOut, gateOut;
    
    private Sequencer sequencer;

    public SequencerCircuit(Sequencer sequencer) {
        super("Sequencer" + index++);
        notes = new ArrayList<>();
        
        for (UnitInputPort input : sequencer.frequencies) {
            SwitchV switcheroo = new SwitchV(KeyFrequencies.values());
            notes.add(switcheroo);
            switcheroo.valueProperty().addListener((obs, oldV, newV) -> input.set(AudioMath.pitchToFrequency(((KeyFrequencies)newV).pitch())));
            add(switcheroo, sequencer.frequencies.indexOf(input), 0);
        }
        
        add(gateIn = new Plug(sequencer, sequencer.gateIn), 0, 3);
        add(baseFrequency = new Plug(sequencer, sequencer.baseFrequency), 1, 3);
        add(frequencyOut = new Plug(sequencer, sequencer.frequencyOut), 2, 3);
        add(gateOut = new Plug(sequencer, sequencer.gateOut), 3, 3);
        
        this.sequencer = sequencer;
    }

}
