/**
 *
 * @date May 15, 2017
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

public class GateSequencer extends UnitGenerator {

    public UnitInputPort gateIn;
    public List<UnitOutputPort> outputs = new ArrayList<>();
    public UnitOutputPort gateOut;

    private static final int NUM_OUTPUTS = 8;
    private int index = 0;
    private static final double THRESHOLD = 0.01;

    public GateSequencer() {
        addPort(gateIn = new UnitInputPort("Gate In"));
        addPort(gateOut = new UnitOutputPort("Gate Out"));
        UnitOutputPort output;
        for (int i = 0; i < NUM_OUTPUTS; i++) {
            output = new UnitOutputPort("Output" + i);
            addPort(output);
            outputs.add(output);
        }
    }

    @Override
    public void generate(int start, int limit) {
        double[] gateIns = gateIn.getValues();
        double[] gateOuts = gateOut.getValues();
        double[][] outputss = new double[NUM_OUTPUTS][limit - start];

        for (int i = 0; i < NUM_OUTPUTS; i++) {
            outputss[i] = outputs.get(i).getValues();
        }

        for (int i = start; i < limit; i++) {
            if (gateIns[i] > THRESHOLD) {
                index++;
                if (index == NUM_OUTPUTS) {
                    index = 0;
                }
            }
            for (int j = 0; j < NUM_OUTPUTS; j++) {
                if (j == index) {
                    outputss[j][i] = gateIns[i];
                } else {
                    outputss[j][i] = 0.0;
                }
            }
            gateOuts[i] = gateIns[i];
        }
    }

}
