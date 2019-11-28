package fr.istic.synthlab.cable.control;

import java.awt.Color;

import fr.istic.synthlab.cable.abstraction.IACable;
import fr.istic.synthlab.cable.presentation.IPCable;
import fr.istic.synthlab.global.presentation.IPGLOB;
import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * Control Cable Interface.
 *
 * @author Mickael
 * @version 1.1
 */
public interface ICCable extends IACable {

    /**
     * Presentation Getter.
     *
     * @return Cable Presentation.
     */
    IPCable getPresentation();

    /**
     * @return the Presentation of the GLOB.
     */
    IPGLOB getPGLOB();

    /**
     * Function calls when we plug one end of the cable. It adds the jack if one
     * end of the cable is not plugged. The connection is created only if the
     * two jacks are instantiated and are of different types.
     *
     * @param jack
     *            jack where we plug.
     * @return have been plugged
     */
    boolean plug(ICFemaleJack jack);

    /**
     * Function calls when we unplug one end of the cable. It deletes the jack
     * if we had it in memory. It deletes the connection if need.
     *
     * @param jack
     *            jack to be unplugged.
     */
    void unplug(ICFemaleJack jack);

    /**
     * Function calls to destroy the cable. It Destroys the connection if it
     * exists.
     */
    void destroyCable();

    /**
     * Function calls when we are destroying a jack. If one of the two Jacks of
     * the cable is the same than the jack in parameter, the cable is destroy
     * too.
     *
     * @param jackDestroyed
     *            jack is being destroyed
     */
    void destroyCableIfJack(ICFemaleJack jackDestroyed);

    /**
     * Load the color of the cable.
     *
     * @param color
     *            The color to load.
     */
    void loadColor(Color color);
}
