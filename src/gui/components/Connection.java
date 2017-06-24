/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.components;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import gui.layers.FXUtils;
import gui.layers.Patchable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 *
 * @author Reznov
 */
public class Connection extends Line implements Patchable {

    private Plug output, input;

    public Connection(Plug output, Plug input) {
        this.output = output;
        this.input = input;
        setStroke(Color.RED);
        setStrokeWidth(5);
        this.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                try {
                    mainPane.removeConnection(this);
                    input.setConnected(false);
                    output.setConnected(false);
                    patchManager.deconnect(output);
                    this.finalize();
                    System.gc();
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        update();
    }
    
    public void update() {
        double[] inputCoordinates = FXUtils.getCoordinates(input.lookup(".radio-button"));
        double[] outputCoordinates = FXUtils.getCoordinates(output.lookup(".radio-button"));
        setStartX(inputCoordinates[0]);
        setStartY(inputCoordinates[1]);
        setEndX(outputCoordinates[0]);
        setEndY(outputCoordinates[1]);
    }
}
