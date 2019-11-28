package fr.istic.synthlab.module.control;

import fr.istic.synthlab.module.abstraction.IAWhiteNoise;
import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * The specific interface for white noise module controller
 * 
 * @author Favereau
 * 
 */
public interface ICWhiteNoise extends ICModule, IAWhiteNoise{

    /**
     * Called by the Presentation when the mute button is clicked.
     */
    void toggleMute();
    
    /**
     * Method getOutput return the output controller
     * @return ICFemaleJack
     */
    ICFemaleJack getOutput();

    /**
     * Function used by the Load system to initialize the state of mute.
     *
     * @param isMute
     *            if the Out must be mute .
     */
    void loadMuteState(boolean isMute);
}
