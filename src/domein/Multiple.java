/**
 *
 * @date Apr 13, 2017
 *
 * @author Reznov
 * @website http://www.reznov.be/
 */
package domein;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;
import java.util.ArrayList;
import java.util.List;

public class Multiple extends UnitGenerator {

    public List<UnitInputPort> inputPorts;
    public List<UnitOutputPort> outputPorts;

    private static final int NUM_PORTS = 4;

    public Multiple() {

        UnitInputPort input;
        UnitOutputPort output;

        inputPorts = new ArrayList<>();
        outputPorts = new ArrayList<>();

        for (int i = 0; i < NUM_PORTS; i++) {
            input = new UnitInputPort("Input");
            output = new UnitOutputPort("Output");
            addPort(input);
            addPort(output);
            inputPorts.add(input);
            outputPorts.add(output);
        }
    }

    public int getNumPorts() {
        return NUM_PORTS;
    }

    @Override
    public void generate(int start, int limit) {
        double[][] inputs = new double[NUM_PORTS][];
        double[][] outputs = new double[NUM_PORTS][];

        for (int i = 0; i < NUM_PORTS; i++) {
            inputs[i] = inputPorts.get(i).getValues();
            outputs[i] = outputPorts.get(i).getValues();
        }

        for (int i = start; i < limit; i++) {
            double multiple = 1;
            for (int j = 0; j < NUM_PORTS; j++) {
                if (inputPorts.get(j).isConnected()) {
                    multiple *= inputs[j][i];
                    //System.out.println(j + ": " + inputs[j][i] + " => " + multiple);
                }
            }
            //System.out.println(multiple);
            for (int j = 0; j < NUM_PORTS; j++) {
                outputs[j][i] = multiple;
            }
        }

    }

}
