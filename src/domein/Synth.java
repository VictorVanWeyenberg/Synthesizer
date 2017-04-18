/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.BrownNoise;
import com.jsyn.unitgen.FilterBiquadCommon;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.MixerStereo;
import com.jsyn.unitgen.Multiply;
import com.jsyn.unitgen.PinkNoise;
import com.jsyn.unitgen.RedNoise;
import com.jsyn.unitgen.UnitGenerator;
import com.jsyn.unitgen.WhiteNoise;
import domein.interfaces.Observer;
import domein.interfaces.Subject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author victor
 */
public class Synth implements Subject<Object> {

    private Synthesizer synth;

    private final List<LFO> lowFrequencyOscillators;
    private final List<Oscillator> oscillators;
    private final List<UnitGenerator> noises;
    private final List<Multiply> multipliers;
    private final List<FilterBiquadCommon> filters;
    private final List<MixerStereo> sms;
    private final List<Envelope> envs;
    private final List<Multiple> multiples;
    private final List<Keyboard> keyboards;
    private LineOut lineOut;

    private final Map<UnitOutputPort, UnitInputPort> connections;
    private final Set<Observer> observers;

    public Synth() {
        addComponents();

        lowFrequencyOscillators = new ArrayList<>();
        oscillators = new ArrayList<>();
        noises = new ArrayList<>();
        multipliers = new ArrayList<>();
        filters = new ArrayList<>();
        sms = new ArrayList<>();
        envs = new ArrayList<>();
        multiples = new ArrayList<>();
        keyboards = new ArrayList<>();

        observers = new HashSet<>();
        connections = new HashMap<>();

        lineOut.start();
        synth.start();
    }

    private void addComponents() {
        synth = JSyn.createSynthesizer();
        synth.add(lineOut = new LineOut());
    }

    public void createLFO() {
        LFO lfo = new LFO();
        synth.add(lfo);
        lowFrequencyOscillators.add(lfo);
        notifyObservers(lfo);
    }

    public void createOsc() {
        Oscillator osc = new Oscillator();
        synth.add(osc);
        oscillators.add(osc);
        notifyObservers(osc);
    }

    public void createMultiply() {
        Multiply m = new Multiply();
        synth.add(m);
        multipliers.add(m);
        notifyObservers(m);
    }

    public void createFilter() {
        Filter f = new Filter();
        synth.add(f);
        filters.add(f);
        notifyObservers(f);
    }

    public void createSM() {
        MixerStereo sm = new MixerStereo(16);
        synth.add(sm);
        sms.add(sm);
        sm.output.connect(0, lineOut.input, 0);
        sm.output.connect(0, lineOut.input, 1);
        notifyObservers(sm);
    }

    public void createEnvelope() {
        Envelope env = new Envelope();
        synth.add(env);
        env.start();
        envs.add(env);
        notifyObservers(env);
    }
    
    public void createKeyboard() {
        Keyboard keyboard = new Keyboard();
        keyboards.add(keyboard);
        synth.add(keyboard);
        notifyObservers(keyboard);
    }

    public void createMultiple() {
        Multiple multiple = new Multiple();
        multiples.add(multiple);
        synth.add(multiple);
        notifyObservers(multiple);
    }
    
    public void createWhiteNoise() {
        WhiteNoise noise = new WhiteNoise();
        noises.add(noise);
        synth.add(noise);
        notifyObservers(noise);
    }
    
    public void createBrownNoise() {
        BrownNoise noise = new BrownNoise();
        noises.add(noise);
        synth.add(noise);
        notifyObservers(noise);
    }
    
    public void createRedNoise() {
        RedNoise noise = new RedNoise();
        noises.add(noise);
        synth.add(noise);
        notifyObservers(noise);
    }
    
    public void createPinkNoise() {
        PinkNoise noise = new PinkNoise();
        noises.add(noise);
        synth.add(noise);
        notifyObservers(noise);
    }
    
    public void removeCircuit(Object object) {
        synth.remove((UnitGenerator) object);
    }

    public void noteOn(int pitch) {
        keyboards.forEach(k -> k.noteOn(pitch));
    }

    public void noteOff(int pitch) {
        keyboards.forEach(k -> k.noteOff());
    }

    public void connect(UnitOutputPort output, UnitInputPort input) {
        output.connect(input);
        connections.put(output, input);
    }

    public void disconnect(UnitOutputPort output, UnitInputPort input) {
        output.disconnect(input);
        connections.remove(output, input);
    }

    public void set(UnitInputPort port, double value) {
        port.set(value);
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
    
    @Override
    public void notifyObservers(Object e) {
        observers.forEach(o -> o.update(e));
    }
    
}