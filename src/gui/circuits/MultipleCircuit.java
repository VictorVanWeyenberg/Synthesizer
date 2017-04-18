/**
 *
 * @date Apr 14, 2017
 * 
 * @author Reznov
 * @website http://www.reznov.be/
 */

package gui.circuits;

import domein.Multiple;
import domein.Synth;
import domein.interfaces.Observer;
import gui.components.Field;
import gui.components.Plug;

public class MultipleCircuit extends CircuitPane {
    
    private static int index = 0;

    public MultipleCircuit(Multiple multiple, Observer observer, Synth synth) {
        super(synth, observer, "Multiple" + index++);
        
        for (int i = 0; i < multiple.getNumPorts(); i++) {
            field.add("input", new Plug(observer, multiple.inputPorts.get(i), synth), i, 0);
            field.add("output", new Plug(observer, multiple.outputPorts.get(i), synth), i, 1);
        }
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
