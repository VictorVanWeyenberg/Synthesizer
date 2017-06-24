/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein.generators;

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
public class Oscillator extends UnitOscillator {
    
    public UnitOutputPort sineOut, triangleOut, squareOut, sawOut, funcOut, freqOut;
    
    private final BiFunction<Double, Double, Double> sine = (a, p) -> Math.sin(p * Math.PI) * a;
    private final BiFunction<Double, Double, Double> triangle = (a, p) -> ((p >= 0.0) ? (0.5 - p) : (0.5 + p)) * 2 * a;
    private final BiFunction<Double, Double, Double> square = (a, p) -> (p < 0) ? -a : a;
    private final BiFunction<Double, Double, Double> saw = (a, p) -> p * a;
    private final BiFunction<Double, Double, Double> func = (a, p) -> Math.tan(p * Math.PI) * a;
    
    private int counter = 0;

    public Oscillator() {
        addPort(freqOut = new UnitOutputPort("Frequency Out"));
        addPort(sineOut = new UnitOutputPort("Sine"));
        addPort(triangleOut = new UnitOutputPort("Triangle"));
        addPort(squareOut = new UnitOutputPort("Square"));
        addPort(sawOut = new UnitOutputPort("Saw"));
        addPort(funcOut = new UnitOutputPort("Function"));
    }

    @Override
    public void generate(int start, int limit) {

        double[] frequencies = frequency.getValues();
        double[] amplitudes = amplitude.getValues();
        double currentPhase = phase.getValue();
        
        double[] sineOuts = sineOut.getValues();
        double[] squareOuts = squareOut.getValues();
        double[] triangleOuts = triangleOut.getValues();
        double[] sawOuts = sawOut.getValues();
        double[] funcOuts = funcOut.getValues();
        double[] freqOuts = freqOut.getValues();

        for (int i = start; i < limit; i++) {
            double phaseIncrement = convertFrequencyToPhaseIncrement(frequencies[i]);
            currentPhase = incrementWrapPhase(currentPhase, phaseIncrement);
            double modulatedPhase = currentPhase;

            double ampl = amplitudes[i];
            sineOuts[i] = frequencies[i] == 0 ? 0 : sine.apply(ampl, modulatedPhase);
            squareOuts[i] = frequencies[i] == 0 ? 0 : square.apply(ampl, modulatedPhase);
            triangleOuts[i] = frequencies[i] == 0 ? 0 : triangle.apply(ampl, modulatedPhase);
            sawOuts[i] = frequencies[i] == 0 ? 0 : saw.apply(ampl, modulatedPhase);
            funcOuts[i] = frequencies[i] == 0 ? 0 : func.apply(ampl, modulatedPhase);
            
            freqOuts[i] = frequencies[i];
        }

        phase.setValue(currentPhase);
    }

}
