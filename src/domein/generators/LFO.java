/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein.generators;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;

/**
 *
 * @author Reznov
 */
public class LFO extends Oscillator {

    public UnitOutputPort gateOut;
    public UnitInputPort amount;

    private double previous;

    public LFO() {
        addPort(amount = new UnitInputPort("Amount"));
        addPort(gateOut = new UnitOutputPort("Gate Out"));
        
        frequency.setup(0, 5, 20);
        amount.setup(0, 1.0, 1.0);
    }

    @Override
    public void generate(int start, int limit) {
        double[] amounts = amount.getValues();
        double[] freqOuts = freqOut.getValues();
        double[] freqs = frequency.getValues();
        double[] gates = gateOut.getValues();
        
        double[] sineOuts = sineOut.getValues();
        double[] squareOuts = squareOut.getValues();
        double[] triangleOuts = triangleOut.getValues();
        double[] sawOuts = sawOut.getValues();
        double[] funcOuts = funcOut.getValues();

        super.generate(start, limit);
        for (int i = start; i < limit; i++) {
            //outputs[i] = outputs[i] + (1 - outputs[i]) * (1 - amounts[i]);
            sineOuts[i] = 1 + amounts[i] * (sineOuts[i] - 1);
            squareOuts[i] = 1 + amounts[i] * (squareOuts[i] - 1);
            triangleOuts[i] = 1 + amounts[i] * (triangleOuts[i] - 1);
            sawOuts[i] = 1 + amounts[i] * (sawOuts[i] - 1);
            funcOuts[i] = 1 + amounts[i] * (funcOuts[i] - 1);
            
            //sineOuts[i] = sineOuts[i] + (1 - sineOuts[i]) * (1 - amounts[i]);
//            squareOuts[i] = squareOuts[i] + (1 - squareOuts[i]) * (1 - amounts[i]);
//            triangleOuts[i] = triangleOuts[i] + (1 - triangleOuts[i]) * (1 - amounts[i]);
//            sawOuts[i] = sawOuts[i] + (1 - sawOuts[i]) * (1 - amounts[i]);
//            funcOuts[i] = funcOuts[i] + (1 - funcOuts[i]) * (1 - amounts[i]);
            
            freqOuts[i] = freqs[i];
            gates[i] = (sineOuts[i] >= 0.0 && previous < 0.0) ? 1.0 : 0.0;
            previous = sineOuts[i];
        }
    }
    
}
