package fr.istic.synthlab.module.presentation;

/**
 * The interface for the presentation of the Out module
 * 
 * @author Valentin
 * 
 */
public interface IPOut extends IPModule {

    /**
     * Module title.
     */
    String TITLE = "<html><center>OUT</center></html>";

    /**
     * Module width.
     */
    int WIDTH_U = 1;

    /**
     * Called by the Controller to Switch the mute LED on
     */
    void showMuteOn();

    /**
     * Called by the Controller to Switch the mute LED off
     */
    void showMuteOff();

    /**
     * Called by the control to update the attenuation value
     *
     * @param att
     *            The new attenuation value.
     */
    void updateAttenuation(double att);
}
