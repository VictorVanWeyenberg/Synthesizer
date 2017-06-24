/**
 *
 * @date May 8, 2017
 * 
 * @author Reznov
 * @website http://www.reznov.be/
 */

package gui.circuits;

import gui.components.Knob;
import gui.components.Plug;
import gui.components.Switch;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javafx.scene.Node;

public class CustomCircuit extends CircuitPane {
    
    private Set<CircuitPane> panes;
    
    public static class CustomCircuitBuilder {
        
        private List<Knob> knobs;
        private List<Switch> switches;
        private List<Plug> plugs;
        
        private String name;
        private Set<CircuitPane> panes;
        
        public CustomCircuitBuilder(Set<CircuitPane> panes) {
            knobs = new ArrayList<>();
            switches = new ArrayList<>();
            plugs = new ArrayList<>();
            
            this.panes = panes;
        }
        
        public CustomCircuitBuilder addKnob(Knob knob) {
            knobs.add(knob);
            return this;
        }
        
        public CustomCircuitBuilder addSwitch(Switch switcheroo) {
            switches.add(switcheroo);
            return this;
        }
        
        public CustomCircuitBuilder addPlug(Plug plug) {
            plugs.add(plug);
            return this;
        }
        
        public CustomCircuitBuilder setName(String name) {
            this.name = name;
            return this;
        }
        
        public CustomCircuit build() {
            return new CustomCircuit(name, panes, knobs, switches, plugs);
        }
        
    }
    
    private CustomCircuit(String name, Set<CircuitPane> panes, List<? extends Node>... nodes) {
        super(name);
        this.panes = panes;
        int row = 0;
        for (List<? extends Node> list : nodes) {
            addRow(list, row);
            row++;
        }
    }
    
    private void addRow(List<? extends Node> nodes, int row) {
        if (!nodes.isEmpty()) {
            int column = 0;
            for (Node node : nodes) {
                add(node, column, row);
                column++;
            }
        }
    }
    
    public Set<CircuitPane> getPanes() {
        return panes;
    }
}
