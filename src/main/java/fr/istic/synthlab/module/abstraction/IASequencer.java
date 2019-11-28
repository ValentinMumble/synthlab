package fr.istic.synthlab.module.abstraction;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;

/**
 * The interface for the Sequencer abstraction.
 * 
 * @author valentinmumble
 * 
 */
public interface IASequencer {

    /**
     * TAG identifier for the Sequencer.
     */
    String TAG = "ASEQ";

    /**
     * Return the gate input port.
     * @return The gate input port
     */
    UnitInputPort getGatePort();

    /**
     * Return the signal output port.
     * @return The signal output port
     */
    UnitOutputPort getOutputPort();

    /**
     * Return the pitch corresponding to the index.
     * @return The pitch corresponding to the index
     * @param index
     */
    double getPitch(int index);

    /**
     * Set the pitch corresponding to the index.
     * @param index
     * @param pitch
     */
    void setPitch(int index, double pitch);

    /**
     * Reset the current pitch to the first pitch
     */
    void resetStep();
}
