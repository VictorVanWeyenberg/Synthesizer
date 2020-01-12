/**
 *
 * @date May 2, 2017
 *
 * @author Reznov
 * @website http://www.reznov.be/
 */
package gui.layers;

import gui.circuits.CircuitPane;
import gui.circuits.CustomCircuit;
import gui.stages.MergeCircuitStage;
import java.util.List;
import java.util.stream.Collectors;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class ContextMenuHandler extends ContextMenu implements EventHandler<MouseEvent>, Controller {

    private RubberBandSelection rubberBandSelection;

    private Menu create;
    private MenuItem mergeCircuits, separateCircuits, removeCircuit;

    public ContextMenuHandler(RubberBandSelection selection) {
        add(create = initCreate());
        add(new SeparatorMenuItem());
        add(mergeCircuits = new MenuItem("Merge circuits"));
        add(separateCircuits = new MenuItem("Separate circuits"));
        add(removeCircuit = new MenuItem("Remove Circuits"));
        this.rubberBandSelection = selection;
        initListeners();
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY) {
            enableItems(event);
            show((Node) event.getSource(), event.getScreenX(), event.getScreenY());
        } else {
            hide();
        }
        event.consume();
    }
    
    private Menu initCreate() {
        Menu menu = new Menu("Create");
        menu.getItems().addAll(MenuBuilder.getMenus());
        return menu;
    }
    
    private void initListeners() {
        mergeCircuits.setOnAction(e -> new MergeCircuitStage(rubberBandSelection.getSelectedItems().stream().map(n -> (CircuitPane)n).collect(Collectors.toSet())));
        separateCircuits.setOnAction(e -> mainPane.separate((CustomCircuit)rubberBandSelection.getSelectedItems().iterator().next()));
        removeCircuit.setOnAction(e -> rubberBandSelection.getSelectedItems().forEach(c -> mainPane.hideChild(c)));
    }

    private void enableItems(MouseEvent event) {
        mergeCircuits.setDisable(!(rubberBandSelection.getSelectedItems().size() > 1));
        separateCircuits.setDisable(!(rubberBandSelection.getSelectedItems().size() == 1 && rubberBandSelection.getSelectedItems().iterator().next() instanceof CustomCircuit));
        removeCircuit.setDisable(!(rubberBandSelection.getSelectedItems().size() > 0));
    }
    
    private void add(MenuItem item) {
        getItems().add(item);
    }

}
