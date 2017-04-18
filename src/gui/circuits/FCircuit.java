/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.circuits;

import domein.Filter;
import domein.Synth;
import domein.enums.Filters;
import domein.interfaces.Observer;
import gui.MainPane;
import gui.components.Field;
import gui.components.Knob;
import gui.components.Plug;
import gui.components.Switch;

/**
 *
 * @author Reznov
 */
public class FCircuit extends CircuitPane {
    
    private Knob FFrequency, FResonance;
    private Switch FType;
    private Plug FInput, FOutput;

    private static int index = 0;

    public FCircuit(Filter filter, Observer observer, Synth synth) {
        
        super(synth, observer, "Filter" + String.valueOf(index++));

        this.getChildren().add(field = new Field(name));

        field.add("frequency", FFrequency = new Knob(filter.frequency, 20, 1000, synth), 0, 0);
        field.add("resonance", FResonance = new Knob(filter.Q, 0, 20, synth), 1, 0);
        
        field.add("type", FType = new Switch(Filters.values()), 0, 1);
        
        field.add("outputplug", FOutput = new Plug(observer, filter.input, synth), 0, 2);
        field.add("frequencyplug", FOutput = new Plug(observer, filter.frequency, synth), 1, 2);
        field.add("inputplug", FOutput = new Plug(observer, filter.output, synth), 2, 2);
        
        FType.valueProperty().addListener((obs, oldV, newV) -> filter.setFilter((Filters) newV));
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
