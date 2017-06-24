/**
 *
 * @date Apr 14, 2017
 * 
 * @author Reznov
 * @website http://www.reznov.be/
 */

package gui.circuits;

import domein.generators.Multiple;
import domein.enums.SwitchState;
import gui.components.LED;
import gui.components.Plug;
import java.util.ArrayList;
import java.util.List;

public class MultipleCircuit extends CircuitPane {
    
    private static int index = 0;
    
    private Multiple multiple;

    public MultipleCircuit(Multiple multiple) {
        super("Multiple" + index++);
        this.multiple = multiple;
        
        for (int i = 0; i < multiple.getNumPorts(); i++) {
            buchla.add(new Plug(multiple, multiple.inputPorts.get(i)), 0, i);
            buchla.add(new LED(SwitchState.values(), multiple.switchStates.get(i)), 1, i);
            buchla.add(new Plug(multiple, multiple.outputPorts.get(i)), 2, i);
        }
    }

}
