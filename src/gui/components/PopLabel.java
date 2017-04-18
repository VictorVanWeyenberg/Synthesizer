package gui.components;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 *
 * @author Reznov
 */
public class PopLabel extends Label {

    private TranslateTransition tt = new TranslateTransition(Duration.millis(250), this);
    private FadeTransition ft = new FadeTransition(Duration.millis(250), this);
    private RotateTransition rt = new RotateTransition(Duration.millis(250), this);
    private ParallelTransition pt = new ParallelTransition(tt, ft, rt);
    
    private static int numberOfLabels = 0;

    public PopLabel(String text) {
        setText(text);
        this.opacityProperty().set(0);
        this.textFillProperty().set(Color.RED);
        this.setOnMouseEntered(e -> this.textFillProperty().set(Color.LIGHTGRAY));
        this.setOnMouseExited(e -> this.textFillProperty().set(Color.RED));
        numberOfLabels++;
    }
    
    public void play(double x, double y, double o, int index) {
        tt.setToX(x);
        tt.setToY(y * index);
        ft.setToValue(o);
        pt.play();
    }

}
