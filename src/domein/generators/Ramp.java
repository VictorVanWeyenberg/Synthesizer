/**
 *
 * @date Jun 2, 2017
 *
 * @author Reznov
 * @website http://www.reznov.be/
 */
package domein.generators;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitFilter;

public class Ramp extends UnitFilter {

    public UnitInputPort input;
    public UnitInputPort exponential, asymptotic, time;
    public UnitOutputPort output;

    private double origin, spent, target = 0, previousOutput = 0;

    public Ramp() {
        addPort(input = new UnitInputPort("Input"));
        addPort(time = new UnitInputPort("Time"));
        addPort(exponential = new UnitInputPort("Exponential"));
        addPort(asymptotic = new UnitInputPort("Asymptotic"));
        addPort(output = new UnitOutputPort("Output"));

        exponential.setup(1, 1, 5);
        asymptotic.setup(1, 1, 5);
        time.setup(0.01, 1, 5);
    }

    @Override
    public void generate(int start, int limit) {
        double[] inputs = input.getValues();
        double[] outputs = output.getValues();
        double power = exponential.getValue();
        double root = asymptotic.getValue();
        double duration = time.getValue();

        for (int i = start; i < limit; i++) {

            if (inputs[i] != target) {
                target = inputs[i];
                origin = previousOutput;
                spent = 0;
            }

            double outputje = 0;
            if (target > origin) {

                outputje = (target - origin) * Math.pow((spent / duration), (power / root)) + origin;
                if (outputje > target) {
                    outputje = target;
                }
            } else if (target < origin) {

                outputje = - (origin - target) * Math.pow((spent / duration), (power / root)) + origin;
                if (outputje < target) {
                    outputje = target;
                }
            } else {
                outputje = inputs[i];
            }
            previousOutput = outputs[i];
            outputs[i] = outputje;
        }

        if (spent
                + getFramePeriod()
                > duration) {
            spent = duration;
        } else {
            spent += getFramePeriod();
        }
    }

}
