/**
 *
 * @date Apr 21, 2017
 *
 * @author Reznov
 * @website http://www.reznov.be/
 */
package gui;

import gui.layers.MenuBuilder;
import javafx.scene.control.MenuBar;

public class SynthMenu extends MenuBar {

    public SynthMenu() {
        getMenus().addAll(MenuBuilder.getMenus());
    }
    
}
