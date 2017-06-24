/**
 *
 * @date Apr 16, 2017
 * 
 * @author Reznov
 * @website http://www.reznov.be/
 */

package gui.circuits;

import domein.generators.Noise;
import gui.components.Knob;
import gui.components.Plug;

public class NoiseCircuit extends CircuitPane {
    
    private static int index = 0;
    
    private Plug brown, pink, red, white;
    private Plug amplitude, frequency;
    
    private Knob amplitudeKnob, frequencyKnob, dampingKnob;
    
    public NoiseCircuit(Noise noise) {
        super("Noise" + index++);
        
        add(amplitudeKnob = new Knob(noise.amplitude), 0, 0);
        add(frequencyKnob = new Knob(noise.frequency), 1, 0);
        add(dampingKnob = new Knob(noise.damping), 2, 0);
        
        add(amplitude = new Plug(noise, noise.amplitude), 0, 1);
        add(frequency = new Plug(noise, noise.frequency), 1, 1);
        
        add(brown = new Plug(noise, noise.brown), 0, 2);
        add(pink = new Plug(noise, noise.pink), 1, 2);
        add(red = new Plug(noise, noise.red), 2, 2);
        add(white = new Plug(noise, noise.white), 3, 2);
    }

}
