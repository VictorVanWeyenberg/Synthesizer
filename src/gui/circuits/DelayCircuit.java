/**
 *
 * @date May 9, 2017
 * 
 * @author Reznov
 * @website http://www.reznov.be/
 */

package gui.circuits;

import com.jsyn.unitgen.InterpolatingDelay;
import gui.components.Knob;
import gui.components.Plug;

public class DelayCircuit extends CircuitPane {
    
    private static int index = 0;
    
    private Knob delay;
    private Plug input, output;

    public DelayCircuit(InterpolatingDelay delay) {
        super("Delay" + index++);
        
        add(this.delay = new Knob(delay.delay, 0, 5), 0, 0);
        add(input = new Plug(delay, delay.input), 0, 1);
        add(output = new Plug(delay, delay.output), 1, 1);
    }

}
