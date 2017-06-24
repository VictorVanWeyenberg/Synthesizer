/**
 *
 * @date Apr 24, 2017
 *
 * @author Reznov
 * @website http://www.reznov.be/
 */
package gui.layers;

import gui.circuits.CircuitPane;
import gui.components.Connection;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;

public class RubberBandSelection {

    private Pane circuits, connections;
    private final DragContext dragContext = new DragContext();
    private Rectangle rect;
    private SelectionModel selectionModel = new SelectionModel();

    private final class DragContext {

        public double mouseAnchorX;
        public double mouseAnchorY;
    }

    private class SelectionModel {

        protected Set<Node> selection = new HashSet<>();
        protected DropShadow shadow = new DropShadow(BlurType.GAUSSIAN, Color.DARKGREEN, 10, 0.5, 0, 0);

        protected void add(Node node) {
            node.setEffect(shadow);
            selection.add(node);
        }

        protected void remove(Node node) {
            node.setEffect(null);
            selection.remove(node);
        }

        protected void clear() {
            while (!selection.isEmpty()) {
                remove(selection.iterator().next());
            }
        }

        protected boolean contains(Node node) {
            return selection.contains(node);
        }

    }

    private EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (event.getButton() == MouseButton.PRIMARY) {
                dragContext.mouseAnchorX = event.getSceneX();
                dragContext.mouseAnchorY = event.getSceneY();

                rect.setX(dragContext.mouseAnchorX);
                rect.setY(dragContext.mouseAnchorY);
                rect.setWidth(0);
                rect.setHeight(0);

                circuits.getChildren().add(rect);
            }
        }
    };

    private EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {

            if (event.getButton() == MouseButton.PRIMARY) {

                double offsetX = event.getSceneX() - dragContext.mouseAnchorX;
                double offsetY = event.getSceneY() - dragContext.mouseAnchorY;

                if (offsetX > 0) {
                    rect.setWidth(offsetX);
                } else {
                    rect.setX(event.getSceneX());
                    rect.setWidth(dragContext.mouseAnchorX - rect.getX());
                }

                if (offsetY > 0) {
                    rect.setHeight(offsetY);
                } else {
                    rect.setY(event.getSceneY());
                    rect.setHeight(dragContext.mouseAnchorY - rect.getY());
                }

            }

        }
    };

    private EventHandler<MouseEvent> onMouseReleasedEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {

            if (!event.isShiftDown() && !event.isControlDown() && event.getButton() == MouseButton.PRIMARY) {
                selectionModel.clear();
            }

            Iterator<Node> circuitIterator = circuits.getChildren().iterator();
            Iterator<Connection> connectionIterator = connections.getChildren().stream().map(c -> (Connection) c).iterator();

            while (circuitIterator.hasNext() || connectionIterator.hasNext()) {

                if (circuitIterator.hasNext()) {
                    Node node = circuitIterator.next();
                    if (node instanceof CircuitPane) {
                        if (node.getBoundsInParent().intersects(rect.getBoundsInParent())) {

                            if (event.isShiftDown()) {

                                selectionModel.add(node);

                            } else if (event.isControlDown()) {

                                if (selectionModel.contains(node)) {
                                    selectionModel.remove(node);
                                } else {
                                    selectionModel.add(node);
                                }
                            } else {
                                selectionModel.add(node);
                            }

                        }
                    }
                }

            }

            rect.setX(0);
            rect.setY(0);
            rect.setWidth(0);
            rect.setHeight(0);

            if (circuits.getChildren().contains(rect)) {
                circuits.getChildren().remove(rect);
            }

            event.consume();

        }
    };

    public RubberBandSelection(Pane circuits, Pane connections) {

        //add connections
        this.circuits = circuits;
        this.connections = connections;

        rect = new Rectangle(0, 0, 0, 0);
        rect.setStroke(Color.GREEN);
        rect.setStrokeWidth(1);
        rect.setStrokeLineCap(StrokeLineCap.SQUARE);
        rect.setFill(Color.GREEN.deriveColor(0, 1.2, -0.75, 0.6));

        circuits.addEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressedEventHandler);
        circuits.addEventHandler(MouseEvent.MOUSE_DRAGGED, onMouseDraggedEventHandler);
        circuits.addEventHandler(MouseEvent.MOUSE_RELEASED, onMouseReleasedEventHandler);
    }

    public Set<Node> getSelectedItems() {
        return selectionModel.selection;
    }

}
