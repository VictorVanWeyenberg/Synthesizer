/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.components;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 *
 * @author Reznov
 */
public class Connection extends Line {

    private Plug output, input;

    public Connection(Plug output, Plug input) {
        this.output = output;
        this.input = input;
        setStroke(Color.RED);
        setStrokeWidth(5);
        updateCoords();
        output.connect(input);
        this.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                System.out.println("lol");
                try {
                    this.finalize();
                    System.gc();
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void updateCoords() {
        setStartX(output.x());
        setStartY(output.y());
        setEndX(input.x());
        setEndY(input.y());
    }

    @Override
    protected void finalize() throws Throwable {
        setStrokeWidth(0);
        output.disconnect(input);
    }
}
