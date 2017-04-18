/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import com.jsyn.ports.UnitInputPort;

/**
 *
 * @author Reznov
 */
public class LFO extends Oscillator {

    public UnitInputPort amount;

    public LFO() {
        frequency.setup(0, 5, 20);
        addPort(amount = new UnitInputPort("Amount"));
    }

    @Override
    public void generate(int start, int limit) {
        double[] outputs = output.getValues();
        double[] amounts = amount.getValues();

        super.generate(start, limit);
        for (int i = start; i < limit; i++) {
            outputs[i] = outputs[i] + (1 - outputs[i]) * (1 - amounts[i]);
            //System.out.println(outputs[i]);
        }
    }
    
}
