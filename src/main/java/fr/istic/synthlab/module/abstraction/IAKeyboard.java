package fr.istic.synthlab.module.abstraction;

import com.jsyn.ports.UnitOutputPort;

/**
 * Interface for the abstraction of the Keyboard module
 * 
 * @author valentinmumble
 * 
 */
public interface IAKeyboard extends IAModule {

    public static enum Note {
        Do, Re, Mi, Fa, Sol, La, Si, Do2, DoD, ReD, FaD, SolD, LaD
    };

    String TAG = "AKEYB";

    /**
     * 
     * @return The Control Voltage port
     */
    UnitOutputPort getControlVoltagePort();

    /**
     * 
     * @return The Gate port
     */
    UnitOutputPort getGatePort();

    /**
     * 
     * @return The current octave level
     */
    double getOctave();

    /**
     * Set the current octave level to o
     * 
     * @param o
     */
    void setOctave(double o);

    /**
     * 
     * @return The current note
     */
    double getNote();

    /**
     * Set the current note
     * 
     * @param n
     */
    void setNote(double n);

    /**
     * Increase the current octave to the next one
     */
    void increaseOctave();

    /**
     * Decrease the current octave to the previous one
     */
    void decreaseOctave();

    /**
     * Play the note corresponding to the current note and octave
     */
    void playNote();

    /**
     * Stop the note currently playing
     */
    void stopNote();

    /**
     * Play the note passed as a parameter
     */
    void setNote(Note note);
}
