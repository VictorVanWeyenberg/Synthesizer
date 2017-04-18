/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.components;

import com.jsyn.ports.ConnectableInput;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import domein.Synth;
import domein.interfaces.Observer;
import domein.interfaces.Subject;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Reznov
 */
public class Plug<E extends UnitPort> extends VBox implements Subject<Plug> {

    @FXML
    private RadioButton rdbPlug;
    @FXML
    private Label lblPlug;
    private UnitPort port;
    private Set<Observer> observers;
    
    private Synth synth;

    public Plug(Observer obs, E port, Synth synth) {
        FXMLLoader loader = new FXMLLoader();

        loader.setController(this);
        loader.setLocation(this.getClass().getResource(this.getClass().getSimpleName() + ".fxml"));

        try {
            Node root = (Node) loader.load();
            this.getChildren().add(root);
        } catch (IOException ex) {
            System.err.println("Failed to load Knob fxml file.");
        }

        observers = new HashSet<>();
        addObserver(obs);
        lblPlug.setText(port.getName());
        this.port = port;
        rdbPlug.addEventFilter(ActionEvent.ANY, e -> e.consume());
        rdbPlug.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
            e.consume();
            notifyObservers(this);
        });
        
        this.synth = synth;
    }
    
    public boolean getConnectionStatus() {
        return rdbPlug.selectedProperty().getValue().booleanValue();
    }

    public void connect(Plug plug) throws IllegalArgumentException {
        if (port instanceof UnitOutputPort && plug.getPort() instanceof UnitOutputPort) {
            throw new IllegalArgumentException("Can't connect output to output.");
        }
        
        if (port instanceof UnitOutputPort) {
            synth.connect((UnitOutputPort)port, (UnitInputPort)plug.getPort());
            System.out.println(port.getName() + " -> " + plug.getPort().getName());
        } else if (plug.getPort() instanceof UnitOutputPort) {
            synth.connect((UnitOutputPort)plug.getPort(), (UnitInputPort)port);
            System.out.println(plug.getPort().getName() + " -> " + port.getName());
        } else {
            throw new IllegalArgumentException("Can't connect input to input.");
        }
        
        rdbPlug.selectedProperty().setValue(Boolean.TRUE);
    }

    public void disconnect(Plug plug) throws IllegalArgumentException {
        if (port instanceof UnitOutputPort && plug.getPort() instanceof UnitOutputPort) {
            throw new IllegalArgumentException("Can't connect output to output.");
        }
        
        if (port instanceof UnitOutputPort) {
            synth.disconnect((UnitOutputPort) port, (UnitInputPort) plug.getPort());
        } else if (plug.getPort() instanceof UnitOutputPort) {
            synth.disconnect((UnitOutputPort) plug.getPort(), (UnitInputPort) port);
        } else {
            throw new IllegalArgumentException("Can't connect input to input.");
        }
        
        rdbPlug.selectedProperty().setValue(Boolean.FALSE);
    }

    public UnitPort getPort() {
        return port;
    }

    public double x() {
        Bounds bounds = rdbPlug.localToScene(rdbPlug.getBoundsInLocal());
        return bounds.getMinX();
    }

    public double y() {
        Bounds bounds = rdbPlug.localToScene(rdbPlug.getBoundsInLocal());
        return bounds.getMinY();
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
    public void notifyObservers(Plug plug) {
        observers.forEach(o -> o.update(plug));
    }

}
