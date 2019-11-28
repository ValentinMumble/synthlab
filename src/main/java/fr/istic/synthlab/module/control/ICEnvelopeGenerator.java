package fr.istic.synthlab.module.control;

import fr.istic.synthlab.module.abstraction.IAEnvelopeGenerator;
import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * The interface for the Envelope Generator module controller
 * 
 * @author valentinmumble
 * 
 */
public interface ICEnvelopeGenerator extends ICModule, IAEnvelopeGenerator {

    /**
     * Called by the presentation when the attack has been changed
     * 
     * @param a
     *            attack
     */
    void attackChanged(double a);

    /**
     * Called by the presentation when the decay has been changed
     * 
     * @param d
     *            decay
     */
    void decayChanged(double d);

    /**
     * Called by the presentation when the sustain has been changed
     * 
     * @param s
     *            sustain in percentage (1 - 100)
     */
    void sustainChanged(double s);

    /**
     * Called by the presentation when the release has been changed
     * 
     * @param r
     *            release
     */
    void releaseChanged(double r);

    /**
     * 
     * @return the gate input controller
     */
    ICFemaleJack getGate();

    /**
     * 
     * @return the output controller
     */
    ICFemaleJack getOutput();
}
