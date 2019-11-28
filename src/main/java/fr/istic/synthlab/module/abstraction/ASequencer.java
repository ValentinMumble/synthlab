package fr.istic.synthlab.module.abstraction;

import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.EdgeDetector;

import fr.istic.synthlab.filter.PitchFilter;

/**
 * A class to represent the Sequencer module.
 * 
 * @author valentinmumble
 * 
 */
public class ASequencer implements IASequencer {

    /**
     * Default value for the pitch in Volts.
     */
    public static final double DEFAULT_PITCH_VOLTS = 0;
    
    /**
     * Minimum value for the pitch in Volts.
     */
    public static final double MIN_PITCH_VOLTS = -1;
    
    /**
     * Maximum value for the pitch in Volts.
     */
    public static final double MAX_PITCH_VOLTS = 1;
    
    /**
     * Synthesizer.
     */
    private Synthesizer synth;
    
    /**
     * Edge Detector.
     */
    private EdgeDetector edgeDetector;
    
    /**
     * Pitch Filter.
     */
    private PitchFilter lf;

    /**
     * Constructor of the Abstraction Sequencer.
     * @param syn
     */
    
    public ASequencer(Synthesizer syn) {
        synth = syn;
        edgeDetector = new EdgeDetector();
        lf = new PitchFilter();
        synth.add(lf);
        synth.add(edgeDetector);
        edgeDetector.output.connect(lf.input);
    }

    @Override
    public UnitInputPort getGatePort() {
        return edgeDetector.input;
    }

    @Override
    public UnitOutputPort getOutputPort() {
        return lf.output;
    }

    
    @Override
    public double getPitch(int index) {
       return lf.getPitch(index);
    }

    @Override
    public void setPitch(int index, double pitch) {
        lf.setPitch(index, pitch);
    }

    @Override
    public void resetStep() {
        lf.setStep(0);
    }
}
