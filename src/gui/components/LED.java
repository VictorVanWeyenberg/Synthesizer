/**
 *
 * @date Apr 3, 2017
 *
 * @author Reznov
 * @website http://www.reznov.be/
 */
package gui.components;

import domein.Synth;
import java.io.IOException;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;

public class LED extends AnchorPane {
    
    @FXML
    private RadioButton checkbox;
    
    public BooleanProperty selectedProperty;

    public LED(Synth synth) {
        FXMLLoader loader = new FXMLLoader();

        loader.setController(this);
        loader.setLocation(this.getClass().getResource(this.getClass().getSimpleName() + ".fxml"));

        try {
            Node root = (Node) loader.load();
            this.getChildren().add(root);
        } catch (IOException ex) {
            System.err.println("Failed to load Knob fxml file.");
        }
        
        selectedProperty = checkbox.selectedProperty();
    }

}
