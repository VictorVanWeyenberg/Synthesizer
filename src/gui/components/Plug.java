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
import com.jsyn.unitgen.UnitGenerator;
import domein.Synth;
import domein.interfaces.Observer;
import domein.interfaces.Subject;
import gui.layers.Patchable;
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
public class Plug extends VBox implements Patchable {

    @FXML
    private RadioButton rdbPlug;
    @FXML
    private Label lblPlug;
    private UnitPort port;
    public Type type;

    public enum Type {
        INPUT, OUTPUT;
    }

    public Plug(UnitGenerator generator, UnitPort port) {
        FXMLLoader loader = new FXMLLoader();

        loader.setController(this);
        loader.setLocation(this.getClass().getResource(this.getClass().getSimpleName() + ".fxml"));

        try {
            Node root = (Node) loader.load();
            this.getChildren().add(root);
        } catch (IOException ex) {
            System.err.println("Failed to load Knob fxml file.");
        }

        if (port instanceof UnitOutputPort) {
            type = Type.OUTPUT;
        } else if (port instanceof UnitInputPort) {
            type = Type.INPUT;
        } else {
            throw new IllegalArgumentException("Only input and output ports are suported in Plug");
        }

        lblPlug.setText(port.getName());
        this.port = port;
        this.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            patchManager.connect(this);
        });
    }

    public Type getType() {
        return type;
    }

    public UnitPort getPort() {
        return port;
    }

    public void setConnected(boolean connected) {
        rdbPlug.selectedProperty().setValue(connected);
    }

}
