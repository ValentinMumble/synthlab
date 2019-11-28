package fr.istic.synthlab.module.abstraction;

import com.jsyn.ports.UnitInputPort;

/**
 * Specific interface for an OUT module.
 *
 * @author Laurent Legendre
 */
public interface IAOut extends IAModule {

    /**
     * TAG Identifier for the AOUT module.
     */
    String TAG = "AOUT";

    /**
     * Determines whether the Out module is mute.
     *
     * @return true if the module is mute
     */
    boolean isMute();

    /**
     * Toggle mute on.
     */
    void mute();

    /**
     * Toggle mute off.
     */
    void unmute();

    /**
     * Set the attenuation value (between -inf and +12dB).
     *
     * @param value
     *             attenuation value
     */
    void setAttenuation(double value);

    /**
     * Return the attenuation value (between -inf and +12dB).
     * @return the attenuation value
     */
    double getAttenuation();

    /**
     * Get the input port of the OUT module.
     *
     * @return The input port
     */
    UnitInputPort getInputPort();

}
