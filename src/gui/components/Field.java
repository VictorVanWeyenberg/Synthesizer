/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.components;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author victor
 */
public class Field extends StackPane {

    @FXML
    private VBox container;
    @FXML
    private Label label;
    
    private Map<String, Node> nodes;

    public Field() {
        FXMLLoader loader = new FXMLLoader();

        loader.setController(this);
        loader.setLocation(this.getClass().getResource(this.getClass().getSimpleName() + ".fxml"));

        try {
            Node root = (Node) loader.load();
            this.getChildren().add(root);
        } catch (IOException ex) {
            System.err.println("Failed to load Knob fxml file.");
        }

        nodes = new TreeMap();
    }

    public Field(String text) {
        this();
        setText(text);
    }
    
    public void setText(String text) {
        label.setText(text);
    }
    
    public void add(String name, Node node, int col, int row) {
        while (container.getChildren().size() < row + 1) {
            HBox hbox = new HBox();
            hbox.setAlignment(Pos.CENTER);
            container.getChildren().add(hbox);
        }
        
        HBox hrow = (HBox) container.getChildren().get(row);
        hrow.getChildren().add(col, node);
        
        nodes.put(name, node);
    }

    public Node getByName(String name) {
        return nodes.get(name);
    }
}
