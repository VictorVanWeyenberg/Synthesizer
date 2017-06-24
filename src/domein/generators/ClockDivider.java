/**
 *
 * @date Apr 19, 2017
 * 
 * @author Reznov
 * @website http://www.reznov.be/
 */

package domein.generators;

import com.jsyn.ports.UnitGatePort;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;
import java.util.ArrayList;
import java.util.List;

public class ClockDivider extends UnitGenerator {
    
    public UnitInputPort input;
    public List<UnitOutputPort> outputPorts;
    
    private int counter = 1;
    
    private static final double THRESHOLD = 0.01;
    private static final int NUM_OF_DIVIDERS = 7;
    
    public ClockDivider() {
        addPort(input = new UnitInputPort("Gate"));
        outputPorts = new ArrayList<>();
        
        UnitOutputPort output;
        for (int i = 0; i < NUM_OF_DIVIDERS; i++) {
            output = new UnitOutputPort("Output" + i);
            addPort(output);
            outputPorts.add(output);
        }
    }

    @Override
    public void generate(int start, int limit) {
        double[] inputs = input.getValues();
        double[][] outputs = new double[NUM_OF_DIVIDERS][limit - start];
        
        for (int i = 0; i < NUM_OF_DIVIDERS; i++) {
            outputs[i] = outputPorts.get(i).getValues();
        }
        
        for (int i = start; i < limit; i++) {
            if (inputs[i] > THRESHOLD) counter++;
            if (counter > NUM_OF_DIVIDERS + 1) counter = 1;
            for (int j = 2; j < NUM_OF_DIVIDERS + 2; j++) {
                outputs[j - 2][i] = (counter % j == 0) ? inputs[i] : 0.0;
            }
        }
    }

}
