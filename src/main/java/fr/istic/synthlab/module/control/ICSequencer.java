package fr.istic.synthlab.module.control;

import fr.istic.synthlab.module.abstraction.IASequencer;
import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * The interface for the Sequencer controller.
 * @author valentinmumble
 * 
 */
public interface ICSequencer extends ICModule, IASequencer {

    /**
     * Called by the presentation when the indexed pitch has been changed.
     * @param index
     * @param pitch
     */
    void pitchChanged(int index, double pitch);

    /**
     * 
     * @return The gate input controller
     */
    ICFemaleJack getGate();

    /**
     * 
     * @return The signal output controller
     */
    ICFemaleJack getOutput();

    /**
     * Called by the presentation when the reset step button has been clicked
     */
    void resetStepClicked();
}
