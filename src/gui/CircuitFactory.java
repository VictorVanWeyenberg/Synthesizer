/**
 *
 * @date May 9, 2017
 * 
 * @author Reznov
 * @website http://www.reznov.be/
 */

package gui;

import domein.generators.Ramp;
import domein.generators.Noise;
import domein.generators.GateSequencer;
import domein.generators.Envelope;
import domein.generators.LFO;
import domein.generators.Sequencer;
import domein.generators.Oscillator;
import domein.generators.Multiple;
import domein.generators.ClockDivider;
import domein.generators.Filter;
import com.jsyn.unitgen.InterpolatingDelay;
import com.jsyn.unitgen.MixerStereo;
import com.jsyn.unitgen.Multiply;
import domein.*;
import domein.enums.GeneratorType;
import gui.circuits.*;

public class CircuitFactory {
    
    public static CircuitPane create(GeneratorType type, Synth synth) {
        switch(type) {
            case LFO:
                return new LFOCircuit((LFO) synth.createGenerator(GeneratorType.LFO));
            case OSCILLATOR:
                return new OSCCircuit((Oscillator) synth.createGenerator(GeneratorType.OSCILLATOR));
            case NOISE:
                return new NoiseCircuit((Noise) synth.createGenerator(GeneratorType.NOISE));
            case ENVELOPE:
                return new EnvelopeCircuit((Envelope) synth.createGenerator(GeneratorType.ENVELOPE));
            case FILTER:
                return new FilterCircuit((Filter) synth.createGenerator(GeneratorType.FILTER));
            case CLOCKDIVIDER:
                return new ClockDividerCircuit((ClockDivider) synth.createGenerator(GeneratorType.CLOCKDIVIDER));
            case VCA:
                return new VCACircuit((MixerStereo) synth.createGenerator(GeneratorType.VCA));
            case MULTIPLE:
                return new MultipleCircuit((Multiple) synth.createGenerator(GeneratorType.MULTIPLE));
            case MULTIPLY:
                return new MCircuit((Multiply) synth.createGenerator(GeneratorType.MULTIPLY));
            case DELAY:
                return new DelayCircuit((InterpolatingDelay) synth.createGenerator(GeneratorType.DELAY));
            case SEQUENCER:
                return new SequencerCircuit((Sequencer) synth.createGenerator(GeneratorType.SEQUENCER));
            case GATESEQUENCER:
                return new GateSequencerCircuit((GateSequencer) synth.createGenerator(GeneratorType.GATESEQUENCER));
            case RAMP:
                return new RampCircuit((Ramp) synth.createGenerator(GeneratorType.RAMP));
            default:
                throw new IllegalArgumentException("PaneType " + type + " not yet supported in CircuitFactory.");
        }
    }

}
