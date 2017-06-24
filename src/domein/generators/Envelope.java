/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein.generators;

import com.jsyn.data.SegmentedEnvelope;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.unitgen.VariableRateMonoReader;

/**
 *
 * @author reznov
 */
public class Envelope extends VariableRateMonoReader {

    private final static double THRESHOLD = 0.01;

    public UnitInputPort trigger;
    public UnitInputPort attack, decay, sustain, release;

    private SegmentedEnvelope env;

    public Envelope() {

        addPort(trigger = new UnitInputPort("Trigger"));

        addPort(attack = new UnitInputPort("Attack"));
        addPort(decay = new UnitInputPort("Decay"));
        addPort(sustain = new UnitInputPort("Sustain"));
        addPort(release = new UnitInputPort("Release"));

        attack.setup(0.0, 0.001, 0.1);
        decay.setup(0.0, 0.001, 0.1);
        sustain.setup(0.0, 0.001, 1);
        release.setup(0.0, 0.001, 0.1);
    }

    @Override
    public void generate(int start, int limit) {
        double[] envData = new double[]{
            0.0, 0.0, //IDLE
            attack.get(), 1.0, //ATTACK
            decay.get(), sustain.get(), //DECAY
            0.0, sustain.get(), //SUSTAIN
            release.get(), 0.0 //RELEASE
        };
        if (env == null) {
            env = new SegmentedEnvelope(envData);
        } else {
            env.write(envData);
        }
        double[] triggers = trigger.getValues();

        for (int i = start; i < limit; i++) {
            if (triggers[i] > THRESHOLD) {
                dataQueue.clear();
                dataQueue.queue(env);
            }
        }

        super.generate(start, limit);
    }

}
