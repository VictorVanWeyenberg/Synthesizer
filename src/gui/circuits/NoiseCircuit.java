/**
 *
 * @date Apr 16, 2017
 * 
 * @author Reznov
 * @website http://www.reznov.be/
 */

package gui.circuits;

import com.jsyn.unitgen.PinkNoise;
import com.jsyn.unitgen.UnitGenerator;
import com.jsyn.unitgen.WhiteNoise;
import domein.Multiple;
import domein.Synth;
import domein.interfaces.Observer;
import gui.components.Field;
import gui.components.Knob;
import gui.components.Plug;

public class NoiseCircuit extends CircuitPane {
    
    private static int index = 0;
    
    public NoiseCircuit(WhiteNoise noise, Observer observer, Synth synth) {
        super(synth, observer, "WhiteNoise" + index++);
        
        field.add("amplitude", new Knob(noise.amplitude, synth), 0, 0);
        
        field.add("amplitudePlug", new Plug(observer, noise.amplitude, synth), 0, 1);
        field.add("output", new Plug(observer, noise.output, synth), 1, 1);
    }
    
    public NoiseCircuit(PinkNoise noise, Observer observer, Synth synth) {
        super(synth, observer, "PinkNoise" + index++);
        
        field.add("amplitude", new Knob(noise.amplitude, synth), 0, 0);
        
        field.add("amplitudePlug", new Plug(observer, noise.amplitude, synth), 0, 1);
        field.add("output", new Plug(observer, noise.output, synth), 1, 1);
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
