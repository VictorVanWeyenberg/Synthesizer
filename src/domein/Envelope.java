/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import com.jsyn.data.SegmentedEnvelope;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.unitgen.VariableRateMonoReader;
import domein.enums.EnvelopePlayMode;

/**
 *
 * @author reznov
 */
public class Envelope extends VariableRateMonoReader {

    private final static double THRESHOLD = 0.01;

    public UnitInputPort trigger;
    public UnitInputPort attack, decay, sustain, release;

    private SegmentedEnvelope env;

    private State state;
    private EnvelopePlayMode playMode;

    private enum State {
        IDLE, ATTACKING, DECAYING, SUSTAINING, RELEASING;
    }

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

        state = State.IDLE;
        playMode = EnvelopePlayMode.TRIGGER;
    }

    public void setPlayMode(EnvelopePlayMode playMode) {
        this.playMode = playMode;
        dataQueue.clear();
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
        if (playMode == EnvelopePlayMode.TRIGGER) {
            for (int i = start; i < limit; i++) {
                switch (state) {
                    case IDLE:
                        dataQueue.queue(env, 0, 1);
                        if (triggers[i] > THRESHOLD) {
                            state = State.ATTACKING;
                        }
                        break;
                    case ATTACKING:
                        if (triggers[i] > THRESHOLD) {
                            dataQueue.clear();
                            dataQueue.queue(env, 1, 1);
                            state = State.DECAYING;
                        } else {
                            state = State.RELEASING;
                        }
                        break;
                    case DECAYING:
                        if (triggers[i] > THRESHOLD) {
                            dataQueue.queue(env, 2, 1);
                            state = State.SUSTAINING;
                        } else {
                            if (state != State.ATTACKING) {
                                state = State.RELEASING;
                            }
                        }
                        break;
                    case SUSTAINING:
                        if (triggers[i] > THRESHOLD) {
                            if (!dataQueue.hasMore()) {
                                dataQueue.queue(env, 3, 1);
                            }
                        } else {
                            if (state != State.ATTACKING) {
                                state = State.RELEASING;
                            }
                        }
                        break;
                    case RELEASING:
                        if (triggers[i] < THRESHOLD) {
                            dataQueue.clear();
                            dataQueue.queue(env, 4, 1);
                            state = State.IDLE;
                        } else {
                            state = State.ATTACKING;
                        }
                        break;
                }
            }
        } else {
            dataQueue.queue(env);
        }

        super.generate(start, limit);
    }

}
