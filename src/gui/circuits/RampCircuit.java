/**
 *
 * @date Jun 9, 2017
 * 
 * @author Reznov
 * @website http://www.reznov.be/
 */

package gui.circuits;

import domein.generators.Ramp;
import gui.components.Buchla;
import gui.components.Knob;
import gui.components.Plug;

public class RampCircuit extends CircuitPane {
    
    private static int index = 0;
    
    private Knob time, exponential, asymptotic;
    private Plug input, output;

    public RampCircuit(Ramp ramp) {
        super("Ramp" + index++);
        
        add(time = new Knob(ramp.time, 0.01, 5), 0, 0);
        add(exponential = new Knob(ramp.exponential, 1, 5), 1, 0);
        add(asymptotic = new Knob(ramp.asymptotic, 1, 5), 2, 0);
        
        add(input = new Plug(ramp, ramp.input), 0, 1);
        add(output = new Plug(ramp, ramp.output), 1, 1);
    }

}
