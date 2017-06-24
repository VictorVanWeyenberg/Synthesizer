/**
 *
 * @date Apr 3, 2017
 *
 * @author Reznov
 * @website http://www.reznov.be/
 */
package gui.components;

import domein.EnumContainingValue;
import java.io.IOException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;

public class LED<E extends Enum<E>> extends AnchorPane {
    
    @FXML
    private CheckBox checkbox;
    private E[] values;
    
    private EnumContainingValue ecv;

    public LED(final E[] values, EnumContainingValue ecv) {
        FXMLLoader loader = new FXMLLoader();

        loader.setController(this);
        loader.setLocation(this.getClass().getResource(this.getClass().getSimpleName() + ".fxml"));

        try {
            Node root = (Node) loader.load();
            this.getChildren().add(root);
        } catch (IOException ex) {
            System.err.println("Failed to load Knob fxml file.");
        }
        
        this.values = values;
        this.ecv = ecv;
        
        checkbox.setText(null);
        //checkbox.allowIndeterminateProperty();
        addListeners();
    }

    private void addListeners() {
        checkbox.selectedProperty().addListener((obs, oldV, newV) -> {
            if (newV) {
                ecv.setValue(values[0]);
            } else {
                ecv.setValue(values[1]);
            }
        });
    }

}
