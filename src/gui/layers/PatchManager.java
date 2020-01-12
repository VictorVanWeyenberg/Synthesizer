/**
 *
 * @date May 2, 2017
 *
 * @author Reznov
 * @website http://www.reznov.be/
 */
package gui.layers;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import gui.MainPane;
import gui.components.Connection;
import gui.components.Plug;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class PatchManager {

    private List<Plug> connecting;
    private Set<Connection> connections;
    private Line line;

    private MainPane mainPane;

    private EventHandler mouseMove = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            line.setEndX(event.getSceneX());
            line.setEndY(event.getSceneY());
        }
    };

    public PatchManager(MainPane mainPane) {
        connecting = new ArrayList<>();
        connections = new HashSet<>();
        line = new Line();
        line.setStrokeWidth(5);
        line.setStroke(Color.RED);

        this.mainPane = mainPane;
    }

    public void connect(Plug plug) {
        connecting.add(plug);
        plug.setConnected(true);
        double[] coords = FXUtils.getCoordinates(plug.lookup(".radio-button"));

        switch (connecting.size()) {
            case 1:
                line.setStartX(coords[0]);
                line.setStartY(coords[1]);
                mainPane.addEventHandler(MouseEvent.MOUSE_MOVED, mouseMove);
                mainPane.addChild(line);
                break;
            case 2:
                mainPane.removeEventHandler(MouseEvent.MOUSE_MOVED, mouseMove);
                line.setEndX(coords[0]);
                line.setEndY(coords[1]);
                mainPane.hideChild(line);
                connectBoth();
                break;
        }
    }

    private void connectBoth() {
        if (connecting.size() != 2) {
            throw new IllegalStateException("Two plugs needed to patch.");
        }

        try {
            Plug plug1 = connecting.get(0);
            Plug plug2 = connecting.get(1);

            if (plug1.getType() == Plug.Type.INPUT && plug2.getType() == Plug.Type.OUTPUT) {
                connect(plug2, plug1);
            } else if (plug2.getType() == Plug.Type.INPUT && plug1.getType() == Plug.Type.OUTPUT) {
                connect(plug1, plug2);
            } else {
                throw new IllegalArgumentException("Faulty patch.");
            }
        } catch (IllegalArgumentException ex) {
            connecting.forEach(p -> p.setConnected(false));
        }

        connecting.clear();
    }
    
    private void connect(Plug output, Plug input) {
        ((UnitOutputPort)output.getPort()).connect((UnitInputPort)input.getPort());
        mainPane.addConnection(output, input);
    }

    public void deconnect(Plug output) {
        ((UnitOutputPort) output.getPort()).disconnectAll();
    }

}
