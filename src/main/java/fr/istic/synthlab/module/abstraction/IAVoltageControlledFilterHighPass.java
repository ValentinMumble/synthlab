package fr.istic.synthlab.module.abstraction;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;

/**
 * The interface for the Voltage-controlled Filter (High Pass) abstraction
 * 
 * @author valentinmumble
 * 
 */
public interface IAVoltageControlledFilterHighPass {

    /**
     * TAG identifier for the Voltage-controlled Filter (High Pass)
     */
    String TAG = "AVCFHP";

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
     * Set the cutoff frequency of the filter
     * 
     * @param freq
     *            Frequency in Hz
     */
    void setCutoffFrequency(double freq);
}
