package fr.istic.synthlab.module.presentation;

import fr.istic.synthlab.module.abstraction.IAKeyboard.Note;

/**
 * The interface for the Keyboard module presentation
 * 
 * @author valentinmumble
 * 
 */
public interface IPKeyboard extends IPModule {

    /**
     * Module title.
     */
    String TITLE = "<html><center>KEYBOARD</center></html>";
    
    /**
     * Module width.
     */
    int WIDTH_U = 3;

    /**
     * Called by the controller to update the octave display
     */
    void updateOctave();

    /**
     * Called by the controller to show that the given note key has been pressed
     * 
     * @param note
     */
    void showKeyPressed(Note note);

    /**
     * Called by the controller to show that the given note key has been
     * released
     */
    void showKeyReleased();
}
