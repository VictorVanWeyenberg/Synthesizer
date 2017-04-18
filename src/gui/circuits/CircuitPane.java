/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.circuits;

import domein.Synth;
import domein.interfaces.Observer;
import domein.interfaces.Subject;
import gui.MainPane;
import gui.components.Field;
import java.util.HashSet;
import java.util.Set;
import javafx.scene.layout.Pane;

/**
 *
 * @author Reznov
 */
public abstract class CircuitPane extends Pane implements Subject<Object> {

    protected Set<Observer> observers;
    private Synth synth;

    protected String name;
    
    protected Field field;

    public CircuitPane(Synth synth, Observer observer, String name) {

        observers = new HashSet<>();
        this.name = name;

        this.getChildren().add(field = new Field(name));
        addObserver(observer);

        this.setOnMouseDragged(e -> {
            this.setLayoutX(e.getSceneX() - this.getWidth() / 2);
            this.setLayoutY(e.getSceneY() - this.getHeight() / 2);
            this.requestFocus();
        });
    }

    public String getName() {
        return name;
    }

}
