/**
 *
 * @date Apr 12, 2017
 * 
 * @author Reznov
 * @website http://www.reznov.be/
 */

package domein;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

public class Noise extends UnitGenerator {
    
    public UnitInputPort amplitude, damping;
    public UnitOutputPort output;

    @Override
    public void generate(int i, int i1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
