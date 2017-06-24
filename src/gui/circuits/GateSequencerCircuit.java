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
import domein.generators.GateSequencer;
import domein.generators.Sequencer;
import domein.enums.KeyFrequencies;
import gui.components.Knob;
import gui.components.Plug;
import gui.components.SwitchV;
import java.util.ArrayList;
import java.util.List;

public class GateSequencerCircuit extends CircuitPane {
    
    private static int index = 0;
    
    private List<Plug> outputs;
    private Plug gateIn, gateOut;

    public GateSequencerCircuit(GateSequencer sequencer) {
        super("Gate Sequencer" + index++);
        
        outputs = new ArrayList<>();
        sequencer.outputs.forEach(output -> outputs.add(new Plug(sequencer, output)));
        
        add(gateIn = new Plug(sequencer, sequencer.gateIn), 0, 0);
        add(gateOut = new Plug(sequencer, sequencer.gateOut), 1, 0);
        
        outputs.forEach(output -> add(output, outputs.indexOf(output), 1));
    }

}
