package fr.istic.synthlab.module.abstraction;

import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.WhiteNoise;

/**
 * 
 * Module
 * White noise
 * 
 * @author Favereau
 * 
 */
public class AWhiteNoise implements IAWhiteNoise {

    /**
     * @category CONSTANT
     */
    final double AMPL = 0.1;
    
    private Synthesizer synth;
    private WhiteNoise noise;
    
    
    
    /**
     * Constructor for AWhiteNoise.
     * @param s Synthesizer
     */
    public AWhiteNoise(Synthesizer s){
        
        synth = s;
        
        noise = new WhiteNoise();
        synth.add(noise);
        
        noise.amplitude.set(AMPL);
        noise.start();
    }
    
    /**
     * Method getOutputPort.
     * @return UnitOutputPort
     * @see fr.istic.synthlab.module.abstraction.IAWhiteNoise#getOutputPort()
     */
    @Override
    public UnitOutputPort getOutputPort() {
        
        return noise.output;
    }

    @Override
    public boolean getEnable() {
        return noise.isEnabled();
    }

    @Override
    public void setEnable(boolean value) {
        noise.setEnabled(value);
    }
}
