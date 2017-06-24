/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import gui.components.Knob;
import domein.Synth;
import domein.enums.GeneratorType;
import gui.circuits.*;
import gui.components.Connection;
import gui.components.Plug;
import gui.layers.ContextMenuHandler;
import gui.layers.FXUtils;
import gui.layers.RubberBandSelection;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import java.util.Set;

/**
 *
 * @author victor
 */
public class MainPane extends StackPane {

    public MenuBar menuBar;
    private Pane grid, connections;

    private RubberBandSelection selection;
    private ContextMenuHandler contextMenuHandler;

    private Node focusOwner;
    private Synth synth;

    public MainPane(Synth synth) {
        this.synth = synth;
        this.getStyleClass().add("mainPane");
        this.getStylesheets().add("gui/main.css");
        initNodes();
        addListeners();
        focusOwner = this;
    }

    private void initNodes() {

        grid = new AnchorPane();
        connections = new AnchorPane();
        connections.setPickOnBounds(false);
        selection = new RubberBandSelection(grid, connections);
        contextMenuHandler = new ContextMenuHandler(selection);

        getChildren().addAll(grid, connections);
    }

    private void addListeners() {

        this.setOnScroll(e -> {
            if (focusOwner instanceof Knob) {
                if (e.getDeltaY() < 0) {
                    ((Knob) focusOwner).incrementKnob();
                }
                if (e.getDeltaY() > 0) {
                    ((Knob) focusOwner).decrementKnob();
                }
            } else {
                e.consume();
            }
        });

        this.setOnMousePressed(e -> {
            contextMenuHandler.handle(e);
        });
    }

    public void setFocusOwner(Node focusOwner) {
        this.focusOwner = focusOwner;
    }

    public Synth getSynthesizer() {
        return synth;
    }
    
    public void create(GeneratorType type) {
        CircuitPane pane = CircuitFactory.create(type, synth);
        pane.setCoordinates(FXUtils.getMouseCoordinates());
        grid.getChildren().add(pane);
    }

    public void addChild(Node node) {
        grid.getChildren().add(node);
    }

    public void addConnection(Plug output, Plug input) {
        connections.getChildren().add(new Connection(output, input));
    }
    
    public void removeConnection(Connection connection) {
        connections.getChildren().remove(connection);
    }

    public void hideChild(Node node) {
        grid.getChildren().remove(node);
    }
    
    public void updateConnectionCoordinates() {
        connections.getChildren().forEach(c -> ((Connection)c).update());
    }

    public void separate(CustomCircuit customCircuit) {
        Set<CircuitPane> panes = customCircuit.getPanes();
        hideChild(customCircuit);
        panes.forEach(c -> addChild(c));
    }

}
