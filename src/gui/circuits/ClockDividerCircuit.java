package gui.circuits;


import domein.generators.ClockDivider;
import domein.generators.LFO;
import domein.Synth;
import domein.interfaces.Observer;
import gui.circuits.CircuitPane;
import gui.components.Plug;

/**
 *
 * @date Apr 19, 2017
 * 
 * @author Reznov
 * @website http://www.reznov.be/
 */


public class ClockDividerCircuit extends CircuitPane {
    
    private static int index = 0;
    
    public ClockDividerCircuit(ClockDivider clock) {
        super("Clock Divider" + String.valueOf(index++));
        
        add(new Plug(clock, clock.input), 0, 0);
        clock.outputPorts.forEach(p -> add(new Plug(clock, p), clock.outputPorts.indexOf(p), 1));
    }

}
