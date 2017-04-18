/**
 *
 * @date Mar 28, 2017
 * 
 * @author Reznov
 * @website http://www.reznov.be/
 */

package gui.circuits;

import domein.Keyboard;
import domein.Synth;
import domein.enums.KeyboardPlayMode;
import domein.interfaces.Observer;
import gui.MainPane;
import gui.components.Field;
import gui.components.Plug;
import gui.components.Switch;

public class KeyboardCircuit extends CircuitPane {
    
    private Plug frequencyOutput, triggerOutput;
    private Switch playModeSwitch;
    
    private static int index = 0;
    
    public KeyboardCircuit(Keyboard keyboard, Observer observer, Synth synth) {
        
        super(synth, observer, "Keyboard" + String.valueOf(index++));
        
        this.getChildren().add(field = new Field(name));
        addObserver(observer);
        
        field.add("playMode", playModeSwitch = new Switch(KeyboardPlayMode.values()), 0, 0);
        field.add("frequencyOut", frequencyOutput = new Plug(observer, keyboard.frequencyOut, synth), 0, 1);
        field.add("triggerOut", triggerOutput = new Plug(observer, keyboard.triggerOut, synth), 1, 1);
        
        playModeSwitch.valueProperty().addListener((obs, oldV, newV) -> keyboard.setPlayMode((KeyboardPlayMode) newV));
        
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