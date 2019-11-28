package fr.istic.synthlab.module.presentation;

/**
 * The interface for the presentation of the WhiteNoise module
 * 
 * @author Favereau
 * 
 */
public interface IPWhiteNoise extends IPModule{

    /**
     * Module title.
     */
    String TITLE = "<html><center>WHITE<br/>NOISE</center></html>";

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
}
