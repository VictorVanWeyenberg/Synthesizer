
/*
 * Copyright 2010 Phil Burk, Mobileer Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package test;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.devices.AudioDeviceFactory;
import com.jsyn.devices.AudioDeviceInputStream;
import com.jsyn.devices.AudioDeviceManager;
import com.jsyn.unitgen.EnvelopeDAHDSR;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.SquareOscillator;
import com.jsyn.unitgen.UnitVoice;
import com.jsyn.util.VoiceAllocator;
import com.softsynth.math.AudioMath;
import com.softsynth.shared.time.TimeStamp;
import domein.LFO;
import domein.Oscillator;
import domein.enums.Wave;
import java.util.Scanner;
import javafx.scene.layout.VBox;

/**
 * Play a tone using a JSyn oscillator. Modulate the amplitude using a DAHDSR envelope.
 * 
 * @author Phil Burk (C) 2010 Mobileer Inc
 */
public class test extends VBox {
    
    private Synthesizer synth;
    private LineOut lineOut;
    
    private LFO lfo, lfo2;
    private Oscillator sine, sine2, sine3;
    private SquareOscillator square, square2;
    private EnvelopeDAHDSR env;
    private VoiceAllocator allocator;
    
    private AudioDeviceManager mgr;
    
    private Scanner input = new Scanner(System.in);

    public void start() {
        synth = JSyn.createSynthesizer();
        synth.add(lineOut = new LineOut());
        
        mgr = AudioDeviceFactory.createAudioDeviceManager();
        AudioDeviceInputStream adis = null;
        
        for (int i = 0; i < mgr.getDeviceCount(); i++) {
            System.out.println(mgr.getDeviceName(i));
            if (mgr.getDeviceName(i).indexOf("Microphone") >= 0) {
                adis = mgr.createInputStream(i, 24, mgr.getMaxOutputChannels(i));
            }
        }
        
        System.out.println(adis);
        adis.start();
        
        while (true) {
            try {
                System.out.println(adis.read());
            } catch (Exception e) {
                
            }
        }
    }

    public void stop() {
        synth.stop();
    }

    /* Can be run as either an application or as an applet. */
    public static void main(String args[]) {
        new test().start();
    }

}