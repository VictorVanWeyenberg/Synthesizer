/**
 *
 * @date May 9, 2017
 *
 * @author Reznov
 * @website http://www.reznov.be/
 */
package domein.generators;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;
import com.softsynth.math.AudioMath;
import domein.enums.GateMode;
import domein.enums.KeyFrequencies;
import java.util.ArrayList;
import java.util.List;

public class Sequencer extends UnitGenerator {

    private static final double THRESHOLD = 0.01;
    private int NUM_NOTES = 8;
    private int currentPlaying = 0, countdown = 1;

    public UnitInputPort gateIn, baseFrequency;
    public UnitOutputPort gateOut, frequencyOut;

    public List<UnitInputPort> frequencies;

    public Sequencer() {
        addPort(gateIn = new UnitInputPort("Gate In"));
        addPort(baseFrequency = new UnitInputPort("Base Frequency"));
        addPort(gateOut = new UnitOutputPort("Gate Out"));
        addPort(frequencyOut = new UnitOutputPort("Frequency Out"));

        frequencies = new ArrayList<>();

        for (int i = 0; i < NUM_NOTES; i++) {
            frequencies.add(new UnitInputPort("Note"));
            frequencies.get(i).setup(0, 0, 800);
            frequencies.get(i).set(AudioMath.pitchToFrequency(KeyFrequencies.C.pitch()));
            addPort(frequencies.get(i));
        }
    }

    public int getNumNotes() {
        return NUM_NOTES;
    }

    @Override
    public void generate(int start, int limit) {
        double[] gateIns = gateIn.getValues();
        double[] gateOuts = gateOut.getValues();
        double[] frequencyOuts = frequencyOut.getValues();
        double[] baseFrequencies = baseFrequency.getValues();
        double[][] frequenciess = new double[NUM_NOTES][];

        for (int i = 0; i < NUM_NOTES; i++) {
            frequenciess[i] = frequencies.get(i).getValues();
        }

        for (int i = start; i < limit; i++) {

            gateOuts[i] = gateIns[i];
            frequencyOuts[i] = baseFrequency.isConnected() ? addPitches(frequenciess[currentPlaying][i], baseFrequencies[i]) : frequenciess[currentPlaying][i];

            if (gateIns[i] > THRESHOLD) {
                currentPlaying++;
                if (currentPlaying == NUM_NOTES) {
                    currentPlaying = 0;
                }
            }
        }
    }

    private double addPitches(double frequency, double baseFrequency) {
        return AudioMath.pitchToFrequency(AudioMath.frequencyToPitch(frequency) + AudioMath.frequencyToPitch(baseFrequency));
    }

}
