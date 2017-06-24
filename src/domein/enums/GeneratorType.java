/**
 *
 * @date May 15, 2017
 * 
 * @author Reznov
 * @website http://www.reznov.be/
 */

package domein.enums;

public enum GeneratorType {
    
    //Generators
    LFO, OSCILLATOR, NOISE,
    
    //Sequential Data
    ENVELOPE, SEQUENCER, GATESEQUENCER, RAMP,
    
    //Filters
    FILTER, DELAY,
    
    //Tools
    CLOCKDIVIDER, MULTIPLE, MULTIPLY,
    
    //Output
    VCA;
}
