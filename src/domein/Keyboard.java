/**
 *
 * @date Mar 31, 2017
 *
 * @author Reznov
 * @website http://www.reznov.be/
 */
package domein;

import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;
import com.softsynth.math.AudioMath;
import domein.enums.KeyboardPlayMode;

public class Keyboard extends UnitGenerator {

    public UnitOutputPort frequencyOut, triggerOut;
    private double frequency, trigger;

    private KeyboardPlayMode playMode;

    private int octave;

    public Keyboard() {
        octave = 0;
        frequency = trigger = 0.0;
        addPort(frequencyOut = new UnitOutputPort("Freq. Out"));
        addPort(triggerOut = new UnitOutputPort("Trigger Out"));
    }

    public void noteOn(int pitch) {
        frequency = AudioMath.pitchToFrequency(pitch);
        trigger = 1.0;
    }

    public void noteOff() {
        if (playMode == KeyboardPlayMode.PLAY) {
            frequency = 0.0;
        }
        trigger = 0.0;
    }

    public void incrementOctave() {
        octave = octave + 1;
    }

    public void decrementOctave() {
        octave = octave - 1;
    }

    @Override
    public void generate(int start, int limit) {
        double[] outputs = frequencyOut.getValues();
        double[] triggerOuts = triggerOut.getValues();
        for (int i = start; i < limit; i++) {
            outputs[i] = frequency;
            triggerOuts[i] = trigger;
        }
    }

    public void setPlayMode(KeyboardPlayMode playMode) {
        this.playMode = playMode;
    }

    public void setPlayMode(String playMode) {
        this.playMode = KeyboardPlayMode.valueOf(playMode);
    }

}
