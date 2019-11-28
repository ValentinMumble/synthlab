package fr.istic.synthlab.module.abstraction;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;

/**
 * The interface for the VCFLP abstraction
 * 
 * @author valentinmumble
 * 
 */
public interface IAVoltageControlledFilterLowPass {

    /**
     * TAG identifier for the VCFLP
     */
    String TAG = "AVCFLP";

    /**
     * 
     * @return The signal input port
     */
    UnitInputPort getInputPort();

    /**
     * 
     * @return The frequency modulation input port
     */
    UnitInputPort getFreqModInputPort();

    /**
     * 
     * @return The signal output port
     */
    UnitOutputPort getOutputPort();

    /**
     * 
     * @return The cutoff frequency
     */
    double getCutoffFrequency();

    /**
     * 
     * @return The resonance of the filter
     */
    double getResonance();

    /**
     * Set the cutoff frequency of the filter
     * 
     * @param freq
     *            Frequency in Hz
     */
    void setCutoffFrequency(double freq);

    /**
     * Set the resonance of the filter
     * 
     * @param res
     */
    void setResonance(double res);
}
