/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitOscillator;
import domein.enums.Wave;
import java.util.function.BiFunction;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reznov
 */
public class Oscillator extends UnitOscillator implements Cloneable {

    public UnitInputPort width = new UnitInputPort("WIDTH");

    private Wave wave;
    private BiFunction<Double, Double, Double> function;

    private final BiFunction<Double, Double, Double> sine = (a, p) -> Math.sin(p * Math.PI) * a;
    private final BiFunction<Double, Double, Double> triangle = (a, p) -> ((p >= 0.0) ? (0.5 - p) : (0.5 + p)) * 2 * a;
    private final BiFunction<Double, Double, Double> square = (a, p) -> (p < 0) ? -a : a;
    private final BiFunction<Double, Double, Double> saw = (a, p) -> p * a;
    private final BiFunction<Double, Double, Double> func = (a, p) -> Math.tan(p * Math.PI) * a;

    public Oscillator() {
        this(Wave.SINE);
    }

    public Oscillator(Wave wave) {
        addPort(width);
        setWave(wave);
    }

    public void setWave(Wave wave) {
        this.wave = wave;
        function = assignFunction();
    }

    public void setWave(String wave) {
        System.out.println(wave);
        this.wave = Wave.valueOf(wave.toUpperCase());
        function = assignFunction();
    }

    @Override
    public void generate(int start, int limit) {

        double[] frequencies = frequency.getValues();
        double[] amplitudes = amplitude.getValues();
        double[] outputs = output.getValues();
        double[] widths = width.getValues();
        double currentPhase = phase.getValue();

        for (int i = start; i < limit; i++) {
            double phaseIncrement = convertFrequencyToPhaseIncrement(frequencies[i]);
            currentPhase = incrementWrapPhase(currentPhase, phaseIncrement);
            double modulatedPhase = currentPhase;

            double ampl = amplitudes[i];
            outputs[i] = frequencies[i] == 0 ? 0 : function.apply(ampl, modulatedPhase);
            //System.out.println(outputs[i]);
        }

        phase.setValue(currentPhase);
    }

    private BiFunction<Double, Double, Double> assignFunction() {
        switch (wave) {
            case SINE:
                return sine;
            case TRIANGLE:
                return triangle;
            case SQUARE:
                return square;
            case SAW:
                return saw;
            case FUNCTION:
                return func;
            default:
                return sine;
        }
    }
    
    public void connect(UnitOutputPort output, UnitInputPort input) {
        
    }

    @Override
    public Oscillator clone() {
        try {
            return (Oscillator) super.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Oscillator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
