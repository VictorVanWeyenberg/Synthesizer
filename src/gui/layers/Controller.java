/**
 *
 * @date May 3, 2017
 * 
 * @author Reznov
 * @website http://www.reznov.be/
 */

package gui.layers;

import domein.Synth;
import gui.MainPane;

public interface Controller {
    
    MainPane mainPane = new MainPane(new Synth());

}
