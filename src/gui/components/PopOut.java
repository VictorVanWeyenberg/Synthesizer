/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.components;

import domein.Synth;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Reznov
 */
public class PopOut extends AnchorPane {

    @FXML
    private RadioButton popButton;
    private List<PopLabel> labels;
    
    PopLabel osc, lfo, multiply, kb, filt, sm, env, multiple;

    public PopOut(Synth synth) {

        FXMLLoader loader = new FXMLLoader();

        loader.setController(this);
        loader.setLocation(this.getClass().getResource(this.getClass().getSimpleName() + ".fxml"));

        try {
            Node root = (Node) loader.load();
            this.getChildren().add(root);
        } catch (IOException ex) {
            System.err.println("Failed to load Knob fxml file.");
        }

        popButton.selectedProperty().setValue(Boolean.TRUE);
        popButton.setOnAction(e -> e.consume());
        popButton.setOnMouseEntered(e -> IntStream.range(0, labels.size()).forEach(i -> labels.get(i).play(x() + 20, y() + 5, 1, i)));
        this.setOnMouseExited(e -> IntStream.range(0, labels.size()).forEach(i -> labels.get(i).play(x(), y(), 0, i)));
        
        labels = new ArrayList<>();
        
        labels.add(lfo = new PopLabel("Low Frequency Oscillator"));
        lfo.setOnMouseClicked(e -> synth.createLFO());
        labels.add(osc = new PopLabel("Oscillator"));
        osc.setOnMouseClicked(e -> synth.createOsc());
        labels.add(multiply = new PopLabel("Multiply"));
        multiply.setOnMouseClicked(e -> synth.createMultiply());
        labels.add(filt = new PopLabel("Filter"));
        filt.setOnMouseClicked(e -> synth.createFilter());
        labels.add(kb = new PopLabel("Keyboard"));
        kb.setOnMouseClicked(e -> synth.createKeyboard());
        labels.add(env = new PopLabel("Envelope"));
        env.setOnMouseClicked(e -> synth.createEnvelope());
        labels.add(multiple = new PopLabel("Multiple"));
        multiple.setOnMouseClicked(e -> synth.createMultiple());
        labels.add(sm = new PopLabel("Stereo Mixer"));
        sm.setOnMouseClicked(e -> synth.createSM());
        
        this.getChildren().addAll(labels);
    }

    public double x() {
        return popButton.localToScene(popButton.getBoundsInLocal()).getMinX() + popButton.localToScene(popButton.getBoundsInLocal()).getWidth() / 2;
    }

    public double y() {
        return popButton.localToScene(popButton.getBoundsInLocal()).getMinY() + popButton.localToScene(popButton.getBoundsInLocal()).getWidth() / 2;
    }
}