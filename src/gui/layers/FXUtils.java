/**
 *
 * @date May 6, 2017
 *
 * @author Reznov
 * @website http://www.reznov.be/
 */
package gui.layers;

import java.awt.MouseInfo;
import java.awt.Point;
import javafx.geometry.Bounds;
import javafx.scene.Node;

public class FXUtils implements Controller {

    public static double[] getCoordinates(Node node) {
        Bounds bounds = node.getBoundsInLocal();
        Bounds screenBounds = node.localToScene(bounds, true);
        double x = screenBounds.getMinX();// + screenBounds.getWidth() / 2;
        double y = screenBounds.getMinY();// + screenBounds.getHeight() / 2;
        return new double[]{x, y};
    }
    
    public static Point getMouseCoordinates() {
        return MouseInfo.getPointerInfo().getLocation();
    }

}
