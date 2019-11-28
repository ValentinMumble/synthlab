package fr.istic.synthlab.util.control;

import fr.istic.synthlab.global.presentation.IPGLOB;
import fr.istic.synthlab.util.abstraction.IAFemaleJack;
import fr.istic.synthlab.util.presentation.IPFemaleJack;

/**
 * The interface for the female jack controller.
 * 
 * @author valentinmumble
 * 
 */
public interface ICFemaleJack extends IAFemaleJack {

    /**
     * 
     * @return the presentation of the female jack.
     */
    IPFemaleJack getPresentation();

    /**
     * @return the Presentation of the GLOB.
     */
    IPGLOB getPGLOB();

    /**
     * 
     * @return if the port is connected.
     */
    boolean isConnected();

    /**
     * 
     * @return if the port is plugged.
     */
    boolean isPlugged();

    /**
     * 
     * @param jack
     * @return true if the jack can be plugged here
     */
    boolean isPluggable(ICFemaleJack jack);

    /**
     * isPlugged setter.
     * 
     * @param isPlugged
     *            true if the port is plugged
     */
    void setPlugged(boolean isPlugged);

    /**
     * Calls to remove the cable plugged in the Jack. Do nothing if there is no
     * cable plugged.
     */
    void removeCable();

    /**
     * Called by the presentation when the port has been touched
     */
    void portTouched();

    /**
     * Called by the presentation when the port is being hovered
     */
    void portHovered();

    /**
     * Called by the presentation when the port is being exited
     */
    void portExited();
}
