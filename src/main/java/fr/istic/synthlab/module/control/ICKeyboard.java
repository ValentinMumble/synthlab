package fr.istic.synthlab.module.control;

import fr.istic.synthlab.module.abstraction.IAKeyboard;
import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * The interface for the Keyboard module control
 * 
 * @author valentinmumble
 * 
 */
public interface ICKeyboard extends ICModule, IAKeyboard {

    /**
     * 
     * @return The gate output controller
     */
    ICFemaleJack getGate();

    /**
     * 
     * @return The control voltage output controller
     */
    ICFemaleJack getControlVoltage();

    /**
     * Called by the presentation when the given key note has been pressed
     * 
     * @param note
     */
    void keyPressed(Note note);

    /**
     * Called by the presentation when any key has been released
     * 
     * @param note
     */
    void keyReleased();

    /**
     * Called by the loader to set the octave in both the abstraction & the
     * presentation
     * 
     * @param o
     */
    void octaveChanged(double o);

    /**
     * Called by the loader to set the note in both the abstraction & the
     * presentation
     * 
     * @param n
     */
    void noteChanged(double n);

    /**
     * Called by the presentation when the increase octave key has been pressed
     */
    void octaveIncreased();

    /**
     * Called by the presentation when the decrease octave key has been pressed
     */
    void octaveDecreased();
}
