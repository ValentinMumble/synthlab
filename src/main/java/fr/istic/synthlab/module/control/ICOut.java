package fr.istic.synthlab.module.control;

import fr.istic.synthlab.module.abstraction.IAOut;
import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * Interface for the OUT module controller.
 *
 * @author valentinmumble
 *
 */
public interface ICOut extends ICModule, IAOut {

    /**
     * Called by the Presentation when the mute button is clicked.
     */
    void toggleMute();

    /**
     * Called by the Presentation when the attenuation changed.
     *
     * UoM is in Db.
     *
     * @param gain
     *          gain
     */
    void attenuationChanged(double gain);

    /**
     * @return the line in controller
     */
    ICFemaleJack getLineIn();

    /**
     * Function used by the Load system to initialize the state of mute.
     *
     * @param isMute
     *            if the Out must be mute .
     */
    void loadMuteState(boolean isMute);
}
