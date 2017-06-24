/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.circuits;

import gui.components.Connection;
import gui.components.Buchla;
import gui.layers.Controller;
import java.awt.Point;
import java.lang.reflect.Field;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 *
 * @author Reznov
 */
public abstract class CircuitPane extends Pane implements Controller {

    protected String name;
    protected Buchla buchla;

    public CircuitPane(String name) {
        this.name = name;
        this.getChildren().add(buchla = new Buchla(name));

        this.setOnMouseDragged(e -> {
            this.setLayoutX(e.getSceneX() - this.getWidth() / 2);
            this.setLayoutY(e.getSceneY() - this.getHeight() / 2);
            this.requestFocus();
            mainPane.updateConnectionCoordinates();
            e.consume();
        });
    }
    
    public void add(Node node, int x, int y) {
        buchla.add(node, x, y);
    }

    public String getName() {
        return name;
    }
    
    public Field[] getDeclaredFields() {
        Class<?> circuitClass = this.getClass();
        return circuitClass.getDeclaredFields();
    }
    
    public Field getDeclaredField(String name) throws NoSuchFieldException {
        Class<?> circuitClass = this.getClass();
        return circuitClass.getDeclaredField(name);
    }
    
    public void setCoordinates(Point point) {
        setLayoutX(point.x);
        setLayoutY(point.y);
    }
}
