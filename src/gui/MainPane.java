/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.jsyn.unitgen.EnvelopeDAHDSR;
import com.jsyn.unitgen.MixerStereo;
import com.jsyn.unitgen.Multiply;
import com.jsyn.unitgen.UnitFilter;
import com.jsyn.unitgen.UnitGenerator;
import domein.*;
import gui.components.Knob;
import domein.Synth;
import domein.enums.KeyFrequencies;
import domein.interfaces.Observer;
import gui.circuits.CircuitPane;
import gui.circuits.ENVCircuit;
import gui.circuits.FCircuit;
import gui.circuits.KeyboardCircuit;
import gui.circuits.LFOCircuit;
import gui.circuits.MCircuit;
import gui.circuits.MultipleCircuit;
import gui.circuits.OSCCircuit;
import gui.circuits.SMCircuit;
import gui.components.Connection;
import gui.components.Plug;
import gui.components.PopOut;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 *
 * @author victor
 */
public class MainPane extends StackPane implements Observer<Object> {

    private TabPane tabs;
    private Tab modular, keyboard;

    private AnchorPane grid;
    private AnchorPane connections;

    private List<Plug> connecting;
    private Line connectingLine;
    private Set<Connection> connected;

    private Node focusOwner;
    private Synth synth;

    private KeyCombination selectLFO = new KeyCodeCombination(KeyCode.L, KeyCombination.ModifierValue.UP, KeyCombination.ModifierValue.DOWN, KeyCombination.ModifierValue.UP, KeyCombination.ModifierValue.UP, KeyCombination.ModifierValue.UP);

    public MainPane(Synth synth) {
        this.synth = synth;
        synth.addObserver(this);
        this.getStyleClass().add("mainPane");
        this.getStylesheets().add("gui/main.css");
        initNodes();
        addListeners();
        focusOwner = this;
    }

    private void initNodes() {

        grid = new AnchorPane();
        grid.getChildren().add(new PopOut(synth));

        connecting = new ArrayList<>();
        connected = new HashSet<>();

        connections = new AnchorPane();
        connections.setPickOnBounds(false);

        this.getChildren().addAll(grid, connections);
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

        this.setOnMouseClicked(e -> {
            connections.getChildren().remove(connectingLine);
            connecting.clear();
            if (e.getClickCount() == 3) {
                focusOwner = this;
                System.out.println(this);
            }
        });

        this.setOnMouseMoved(e -> {
            if (connecting.size() == 1) {
                connectingLine.setEndX(e.getScreenX());
                connectingLine.setEndY(e.getScreenY());
            }
        });

        this.setOnMouseDragged(e -> connected.forEach(c -> c.updateCoords()));

        this.setOnKeyPressed(e -> {

            System.out.println(e.getCode());

            try {
                synth.noteOn(KeyFrequencies.valueOf(e.getCode().getName()).pitch());
            } catch (IllegalArgumentException ex) {
                e.consume();
            }
        });

        this.setOnKeyReleased(e -> {
            try {
                synth.noteOff(KeyFrequencies.valueOf(e.getCode().getName()).pitch());
            } catch (IllegalArgumentException ex) {
                e.consume();
            }
        });
    }

    public void setFocusOwner(Node focusOwner) {
        this.focusOwner = focusOwner;
    }

    public Synth getSynthesizer() {
        return synth;
    }

    @Override
    public void update(Object obj) {
        if (obj instanceof Plug) {
            updatePlug((Plug) obj);
            return;
        } else if (obj instanceof Keyboard) {
            grid.getChildren().add(new KeyboardCircuit((Keyboard) obj, this, synth));
        } else if (obj instanceof UnitGenerator) {
            if (obj instanceof LFO) {
                grid.getChildren().add(new LFOCircuit((LFO) obj, this, synth));
                return;
            }
            if (obj instanceof Oscillator) {
                grid.getChildren().add(new OSCCircuit((Oscillator) obj, this, synth));
                return;
            }
            if (obj instanceof Multiply) {
                grid.getChildren().add(new MCircuit((Multiply) obj, this, synth));
                return;
            }
            if (obj instanceof UnitFilter) {
                grid.getChildren().add(new FCircuit((Filter) obj, this, synth));
                return;
            }
            if (obj instanceof MixerStereo) {
                grid.getChildren().add(new SMCircuit((MixerStereo) obj, this, synth));
                return;
            }
            if (obj instanceof Envelope) {
                grid.getChildren().add(new ENVCircuit((Envelope) obj, this, synth));
                return;
            }
            if (obj instanceof Multiple) {
                grid.getChildren().add(new MultipleCircuit((Multiple) obj, this, synth));;;
                return;
            }
        } else if (obj instanceof CircuitPane) {
            grid.getChildren().remove(obj);
            return;
        }
    }

    private void updatePlug(Plug plug) {
        if (!connecting.contains(plug)) {
            connecting.add(plug);
        }
        switch (connecting.size()) {
            case 1:
                if (connectingLine == null) {
                    connectingLine = new Line();
                    connectingLine.setStroke(Color.RED);
                    connectingLine.setStrokeWidth(5);
                }
                connectingLine.setStartX(plug.x());
                connectingLine.setStartY(plug.y());
                connections.getChildren().add(connectingLine);
                break;
            case 2:
                try {
                    Connection connection = new Connection(connecting.get(0), connecting.get(1));
                    connections.getChildren().add(connection);
                    connected.add(connection);
                } catch (IllegalArgumentException e) {
                    System.out.println("Can't connect " + connecting.get(0) + " to " + connecting.get(1));
                } finally {
                    connections.getChildren().remove(connectingLine);
                    connecting.clear();
                }
                break;
        }
    }

}

    /*private void updatePlug(Plug plug) {
        if (!connecting.contains(plug)) {
            connecting.add(plug);
        }
        switch (connecting.size()) {
            case 1:
                if (connectingLine == null) {
                    connectingLine = new Line();
                    connectingLine.setStroke(Color.RED);
                    connectingLine.setStrokeWidth(5);
                }
                connectingLine.setStartX(plug.x());
                connectingLine.setStartY(plug.y());
                connections.getChildren().add(connectingLine);
                break;
            case 2:
                try {
                    Connection connection = new Connection(connecting.get(0), connecting.get(1));
                    connections.getChildren().add(connection);
                    connected.add(connection);
                } catch (IllegalArgumentException e) {
                    connecting.get(0).disconnect();
                    connecting.get(1).disconnect();
                    System.out.println("IllegalArgumentException");
                } finally {
                    connections.ge*/
