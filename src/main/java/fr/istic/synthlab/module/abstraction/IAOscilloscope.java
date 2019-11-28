package fr.istic.synthlab.module.abstraction;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.scope.AudioScope;

/**
 * Specific interface for an Oscilloscope module
 * 
 * @author Valentin
 * 
 */
public interface IAOscilloscope extends IAModule {

    /**
     * TAG Identifier for the Oscilloscope module
     */
    String TAG = "SCOP";

    AudioScope getScope();

    /**
     * Start the oscilloscope
     */
    void start();

    /**
     * Stop the oscilloscope
     */
    void stop();

    /**
     * 
     * @return the input port
     */
    UnitInputPort getInputPort();

    /**
     * 
     * @return the output port
     */
    UnitOutputPort getOutputPort();

    /**
     * 
     * @return The range of the oscilloscope
     */
    double getRange();

    /**
     * Set the range of the oscilloscope
     * 
     * @param r
     */
    void setRange(double r);

}
