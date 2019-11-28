package fr.istic.synthlab.module.control;

import fr.istic.synthlab.module.abstraction.IAVoltageControlledFilterHighPass;
import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * The interface for the VCFHP controller
 * 
 * @author valentinmumble
 * 
 */
public interface ICVoltageControlledFilterHighPass extends ICModule,
        IAVoltageControlledFilterHighPass {

    /**
     * Called by the presentation when the cutoff frequency has been changed
     */
    void cutoffFrequencyChanged(double freq);

    /**
     * 
     * @return The signal input controller
     */
    ICFemaleJack getInput();

    /**
     * 
     * @return The frequency modulation input controller
     */
    ICFemaleJack getFreqModInput();

    /**
     * 
     * @return The signal output controller
     */
    ICFemaleJack getOutput();
}
