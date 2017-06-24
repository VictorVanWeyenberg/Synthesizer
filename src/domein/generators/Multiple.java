/**
 *
 * @date Apr 13, 2017
 *
 * @author Reznov
 * @website http://www.reznov.be/
 */
package domein.generators;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.UnitGenerator;
import domein.EnumContainingValue;
import domein.enums.SwitchState;
import java.util.ArrayList;
import java.util.List;

public class Multiple extends UnitGenerator {

    public List<UnitInputPort> inputPorts;
    public List<UnitOutputPort> outputPorts;
    public List<EnumContainingValue<SwitchState>> switchStates;

    private static final int NUM_PORTS = 8;

    public Multiple() {

        inputPorts = new ArrayList<>();
        outputPorts = new ArrayList<>();
        switchStates = new ArrayList<>();

        for (int i = 0; i < NUM_PORTS; i++) {
            switchStates.add(new EnumContainingValue<>(SwitchState.OFF));
            inputPorts.add(new UnitInputPort("Input" + i));
            outputPorts.add(new UnitOutputPort("Output" + i));
            addPort(inputPorts.get(i));
            addPort(outputPorts.get(i));
        }
    }

    public int getNumPorts() {
        return NUM_PORTS;
    }

    @Override
    public void generate(int start, int limit) {

        List<double[]> inputs = new ArrayList<>();
        List<double[]> outputs = new ArrayList<>();
        int numOfConnectedOnInputs = getNumConnectedInputs(SwitchState.ON);

        for (int i = 0; i < NUM_PORTS; i++) {
            inputs.add(inputPorts.get(i).getValues());
            outputs.add(outputPorts.get(i).getValues());
        }

        for (int i = start; i < limit; i++) {
            double multiple = 0.0;

            for (int j = 0; j < NUM_PORTS; j++) {
                switch (switchStates.get(j).getValue()) {
                    case ON:
                        multiple += inputs.get(j)[i];
                        break;
                }
            }
            for (int j = 0; j < NUM_PORTS; j++) {
                if (numOfConnectedOnInputs > 0) {
                    outputs.get(j)[i] = multiple / numOfConnectedOnInputs;
                } else {
                    outputs.get(j)[i] = 0.0;
                }
            }
        }

    }

    private int getNumConnectedInputs(SwitchState state) {
        int result = 0;
        for (int i = 0; i < NUM_PORTS; i++) {
            if (inputPorts.get(i).isConnected() && switchStates.get(i).getValue() == state) {
                result++;
            }
        }
        return result;
    }

}
