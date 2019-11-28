package fr.istic.synthlab.module.control;

import fr.istic.synthlab.module.abstraction.IAVoltageControlledFilterLowPass;
import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * The interface for the VCFLP controller
 * 
 * @author valentinmumble
 * 
 */
public interface ICVoltageControlledFilterLowPass extends ICModule,
        IAVoltageControlledFilterLowPass {

    /**
     * Called by the presentation when the cutoff frequency has been changed
     */
    void cutoffFrequencyChanged(double freq);

    /**
     * Called by the presentation when the resonance has been changed
     */
    void resonanceChanged(double res);

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
