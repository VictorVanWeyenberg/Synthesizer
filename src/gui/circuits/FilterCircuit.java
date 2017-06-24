/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.circuits;

import domein.generators.Filter;
import domein.enums.Filters;
import gui.components.Buchla;
import gui.components.Knob;
import gui.components.Plug;
import gui.components.Switch;

/**
 *
 * @author Reznov
 */
public class FilterCircuit extends CircuitPane {
    
    private Knob FFrequency, FResonance;
    private Switch FType;
    private Plug FInput, FOutput;

    private static int index = 0;

    public FilterCircuit(Filter filter) {
        
        super("Filter" + String.valueOf(index++));

        this.getChildren().add(buchla = new Buchla(name));

        add(FFrequency = new Knob(filter.frequency, 20, 1000), 0, 0);
        add(FResonance = new Knob(filter.Q, 0, 20), 1, 0);
        
        add(FType = new Switch(Filters.values()), 0, 1);
        
        add(FOutput = new Plug(filter, filter.input), 0, 2);
        add(FOutput = new Plug(filter, filter.frequency), 1, 2);
        add(FOutput = new Plug(filter, filter.output), 2, 2);
        
        FType.valueProperty().addListener((obs, oldV, newV) -> filter.setFilter((Filters) newV));
    }
    
}
