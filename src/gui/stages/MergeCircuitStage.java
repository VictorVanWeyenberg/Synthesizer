/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.stages;

import gui.circuits.CircuitPane;
import gui.circuits.CustomCircuit;
import gui.circuits.CustomCircuit.CustomCircuitBuilder;
import gui.components.Knob;
import gui.components.Plug;
import gui.components.Switch;
import gui.layers.Controller;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author reznov
 */
public class MergeCircuitStage extends VBox implements Controller {

    @FXML
    private TextField txfTitle;
    @FXML
    private Accordion accCircuits;
    @FXML
    private Button btnCreate;
    @FXML
    private Button btnCancel;

    private Stage stage;
    private SelectionModel selectionModel;
    private Set<CircuitPane> selectedItems;

    private class SelectionModel extends Accordion {

        private List<Node> selection;
        List<TitledPane> panes;

        public SelectionModel(Set<CircuitPane> circuits) {
            selection = new ArrayList<>();
            panes = new ArrayList<>();
            setSpacing(10);
            setPadding(new Insets(10));
            for (CircuitPane circuit : circuits) {
                VBox vbox = new VBox();
                TitledPane pane = new TitledPane(circuit.getName(), new ScrollPane(vbox));
                for (Field field : circuit.getDeclaredFields()) {
                    field.setAccessible(true);
                    RadioButton rdbtn = new RadioButton(field.getName());
                    rdbtn.selectedProperty().addListener((obs, oldV, newV) -> {
                        try {
                            if (newV) {
                                selection.add((Node) field.get(circuit));
                            } else {
                                selection.remove((Node) field.get(circuit));
                            }
                        } catch (IllegalArgumentException ex) {
                            ex.printStackTrace();
                        } catch (IllegalAccessException ex) {
                            ex.printStackTrace();
                        }
                        System.out.println(selection.toString());
                    });
                    vbox.getChildren().add(rdbtn);
                }
                getPanes().add(pane);
            }
        }

        public boolean isEmpty() {
            return selection.isEmpty();
        }

        public List<Node> getSelection() {
            return selection;
        }

    }

    public MergeCircuitStage(Set<CircuitPane> selectedItems) {

        FXMLLoader loader = new FXMLLoader();

        loader.setController(this);
        loader.setRoot(this);
        loader.setLocation(this.getClass().getResource(this.getClass().getSimpleName() + ".fxml"));

        try {
            Parent root = (Parent) loader.load();
            stage = new Stage();
            stage.setTitle("Merge circuits");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            System.err.println("Failed to load MergeCircuitStage fxml file.");
            ex.printStackTrace();
        }

        btnCreate.setDisable(true);
        selectionModel = new SelectionModel(selectedItems);
        accCircuits.getPanes().setAll(selectionModel.getPanes());
        this.selectedItems = selectedItems;
        initListeners();
    }

    private void initListeners() {
        btnCancel.setOnAction(e -> stage.close());
        txfTitle.setOnKeyReleased(e -> btnCreate.setDisable(txfTitle.getText().isEmpty()));
        btnCreate.setOnAction(e -> {
            stage.close();
            if (!selectionModel.isEmpty()) {
                try {
                    createCircuit();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void createCircuit() throws NoSuchFieldException, IllegalArgumentException, InstantiationException, IllegalAccessException {
        CustomCircuitBuilder ccBuilder = new CustomCircuitBuilder(selectedItems);
        for (Node node : selectionModel.getSelection()) {
            switch (node.getClass().getSimpleName()) {
                case "Knob":
                    ccBuilder = ccBuilder.addKnob((Knob) node);
                    break;
                case "Switch":
                    ccBuilder = ccBuilder.addSwitch((Switch) node);
                    break;
                case "Plug":
                    ccBuilder = ccBuilder.addPlug((Plug) node);
                    break;
            }
        }
        CustomCircuit customCircuit = ccBuilder.setName(txfTitle.getText()).build();
        selectedItems.forEach(n -> mainPane.hideChild(n));
        mainPane.addChild(customCircuit);
        mainPane.updateConnectionCoordinates();
    }

}
