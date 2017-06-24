/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.components;

import java.io.IOException;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Reznov
 */
public class SwitchV<E extends Enum<E>> extends VBox {

    @FXML
    private Label label;

    @FXML
    private Slider sldSwitch;

    private E[] enumeration;
    
    private SimpleObjectProperty<E> valueProperty;

    public SwitchV(final E[] enumeration) {
        FXMLLoader loader = new FXMLLoader();

        loader.setController(this);
        loader.setLocation(this.getClass().getResource(this.getClass().getSimpleName() + ".fxml"));

        try {
            Node root = (Node) loader.load();
            this.getChildren().add(root);
        } catch (IOException ex) {
            System.err.println("Failed to load Knob fxml file.");
        }
        
        valueProperty = new SimpleObjectProperty();

        this.enumeration = enumeration;
        E initValue = enumeration[sldSwitch.valueProperty().getValue().intValue()];
        valueProperty.setValue(initValue);
        sldSwitch.setMax(this.enumeration.length - 1);
        label.setText(initValue.toString());

        addListeners();
    }
    
    public SimpleObjectProperty<E> valueProperty() {
        return valueProperty;
    }

    private void addListeners() {
        sldSwitch.valueProperty().addListener((obs, oldV, newV) -> {
            valueProperty.setValue(enumeration[newV.intValue()]);
            String text = capitalize(valueProperty.getValue().toString());
            label.setText(text);
        });
        this.setOnMouseClicked(e -> this.requestFocus());
    }

    private String capitalize(String text) {
        return Character.toUpperCase(text.charAt(0)) + text.substring(1);
    }

}
