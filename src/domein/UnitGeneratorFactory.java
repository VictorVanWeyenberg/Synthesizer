/**
 *
 * @date May 15, 2017
 *
 * @author Reznov
 * @website http://www.reznov.be/
 */
package domein;

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
import com.jsyn.unitgen.UnitGenerator;
import domein.enums.GeneratorType;

public class UnitGeneratorFactory {

    public static UnitGenerator createUnitGenerator(GeneratorType type) {
        switch (type) {
            case CLOCKDIVIDER:
                return new ClockDivider();
            case ENVELOPE:
                return new Envelope();
            case FILTER:
                return new Filter();
            case DELAY: 
                return new InterpolatingDelay();
            case LFO:
                return new LFO();
            case MULTIPLE:
                return new Multiple();
            case MULTIPLY:
                return new Multiply();
            case NOISE:
                return new Noise();
            case OSCILLATOR:
                return new Oscillator();
            case SEQUENCER:
                return new Sequencer();
            case GATESEQUENCER:
                return new GateSequencer();
            case RAMP:
                return new Ramp();
            case VCA:
                return new MixerStereo(24);
            default:
                throw new IllegalArgumentException("GeneratorType " + type.toString() + " is not supported in the UnitGeneratorFactory");
        }
    }

}
