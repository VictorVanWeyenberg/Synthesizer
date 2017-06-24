/**
 *
 * @date Apr 12, 2017
 *
 * @author Reznov
 * @website http://www.reznov.be/
 */
package domein.generators;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitOscillator;
import com.jsyn.util.PseudoRandom;

public class Noise extends UnitOscillator {

    public UnitInputPort amplitude, frequency, damping;
    public UnitOutputPort brown, pink, red, white;

    private final int NUM_ROWS = 16;
    private final int RANDOM_BITS = 24;
    private final int RANDOM_SHIFT = 32 - RANDOM_BITS;

    private PseudoRandom random;
    private double previousBrown = 0.0, previousRed = 0.0, currentRed = 0.0;
    private long[] rows = new long[NUM_ROWS];
    private int index, indexMask, runningSum;
    private double scalar;

    public Noise() {

        random = new PseudoRandom();

        addPort(amplitude = new UnitInputPort("Amplitude"));
        addPort(frequency = new UnitInputPort("Frequency"));
        addPort(damping = new UnitInputPort("Damping"));
        frequency.setup(20.0, 440.0, 20000.0);
        damping.setup(0.0001, 0.01, 0.1);

        addPort(brown = new UnitOutputPort("Brown"));
        addPort(pink = new UnitOutputPort("Pink"));
        addPort(red = new UnitOutputPort("Red"));
        addPort(white = new UnitOutputPort("White"));

        // set up for N rows of generators
        index = 0;
        indexMask = (1 << NUM_ROWS) - 1;

        // Calculate maximum possible signed random value. Extra 1 for white
        // noise always added.
        int pmax = (NUM_ROWS + 1) * (1 << (RANDOM_BITS - 1));
        scalar = 1.0 / pmax;

        // initialize rows
        for (int i = 0; i < NUM_ROWS; i++) {
            rows[i] = 0;
        }

        runningSum = 0;
    }

    @Override
    public void generate(int start, int limit) {

        double[] amplitudes = amplitude.getValues();

        double[] browns = brown.getValues();
        double[] pinks = pink.getValues();
        double[] reds = red.getValues();
        double[] whites = white.getValues();

        double damper = damping.getValue();

        for (int i = start; i < limit; i++) {
            browns[i] = previousBrown = (damper * previousBrown) + random.nextRandomDouble() * amplitudes[i];
            pinks[i] = generatePinkNoise() * amplitudes[i];
            reds[i] = generateRedNoise(i) * amplitudes[i];
            whites[i] = random.nextRandomDouble() * amplitudes[i];
        }
    }

    private double generateRedNoise(int i) {

        double phaseIncrement, currOutput;
        double frequencytje = frequency.getValues()[i];
        double currPhase = phase.getValue();

        phaseIncrement = frequencytje * getFramePeriod();

        // verify that phase is within minimums and is not negative
        if (phaseIncrement < 0.0) {
            phaseIncrement = 0.0 - phaseIncrement;
        }
        if (phaseIncrement > 1.0) {
            phaseIncrement = 1.0;
        }

        currPhase += phaseIncrement;

        // calculate new random whenever phase passes 1.0
        if (currPhase > 1.0) {
            previousRed = currentRed;
            currentRed = random.nextRandomDouble();
            // reset phase for interpolation
            currPhase -= 1.0;
        }

        // interpolate current
        currOutput = previousRed + (currPhase * (currentRed - previousRed));
        previousRed = currentRed;
        phase.setValue(currPhase);
        return currOutput;
    }

    private double generatePinkNoise() {
        index = (index + 1) & indexMask;

        // If index is zero, don't update any random values.
        if (index != 0) {
            // Determine how many trailing zeros in PinkIndex.
            // This algorithm will hang of n==0 so test first
            int numZeros = 0;
            int n = index;

            while ((n & 1) == 0) {
                n = n >> 1;
                numZeros++;
            }

            // Replace the indexed ROWS random value.
            // Subtract and add back to RunningSum instead of adding all the
            // random values together. Only one changes each time.
            runningSum -= rows[numZeros];
            int newRandom = random.nextRandomInteger() >> RANDOM_SHIFT;
            runningSum += newRandom;
            rows[numZeros] = newRandom;
        }

        // Add extra white noise value.
        int newRandom = random.nextRandomInteger() >> RANDOM_SHIFT;
        int sum = runningSum + newRandom;

        // Scale to range of -1.0 to 0.9999.
        return scalar * sum;
    }

}
