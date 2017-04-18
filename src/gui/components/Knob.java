/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.components;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitPort;
import domein.Synth;
import java.io.IOException;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author victor
 */
public class Knob extends VBox {

    @FXML
    private Slider knob;
    @FXML
    private Label knobTitle;

    private Double tick;
    private UnitInputPort port;
    private Synth synth;

    public Knob(UnitInputPort port, Synth synth) {
        FXMLLoader loader = new FXMLLoader();

        loader.setController(this);
        loader.setLocation(this.getClass().getResource(this.getClass().getSimpleName() + ".fxml"));

        try {
            Node root = (Node) loader.load();
            this.getChildren().add(root);
        } catch (IOException ex) {
            System.err.println("Failed to load Knob fxml file.");
        }
        
        this.port = port;
        this.synth = synth;
        setText(port.getName());
        
        addListeners();
    }

    public Knob(UnitInputPort port, double min, double max, Synth synth) {
        this(port, synth);
        setMin(min);
        setMax(max);
    }

    public void setText(String text) {
        knobTitle.setText(text);
    }

    public void setMin(double min) {
        knob.setMin(min);
    }

    public void setMax(double max) {
        knob.setMax(max);
    }

    public DoubleProperty getValueProperty() {
        return knob.valueProperty();
    }

    public double getValue() {
        return knob.getValue();
    }

    public String getName() {
        return knobTitle.getText();
    }

    public DoubleProperty valueProperty() {
        return knob.valueProperty();
    }

    public void incrementKnob() {
        knob.valueProperty().set(knob.valueProperty().get() + tick());
    }

    public void decrementKnob() {
        knob.valueProperty().set(knob.valueProperty().get() - tick());
    }

    private double tick() {
        if (tick == null) {
            tick = (knob.getMax() - knob.getMin()) / 100;
        }
        return tick;
    }

    private void addListeners() {

        getValueProperty().addListener((obs, oldV, newV) -> {
            knob.rotateProperty().setValue(270 * ((double) newV / (knob.getMax() - knob.getMin())) - 135);
            synth.set(port, newV.doubleValue());
        });

        knob.setOnKeyPressed(e -> e.consume());
        this.setOnMouseClicked(e -> this.requestFocus());
        this.setFocusTraversable(true);
        knob.setFocusTraversable(false);
        this.focusedProperty().addListener((obs, oldV, newV) -> {
            if (newV) {
                knobTitle.setTextFill(Color.RED);
            } else {
                knobTitle.setTextFill(Color.WHITE);
            }
        });
    }
}
