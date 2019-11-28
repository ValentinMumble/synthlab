package fr.istic.synthlab.module.presentation;

/**
 * The interface for the Envelope Generator module presentation
 * 
 * @author valentinmumble
 * 
 */
public interface IPEnvelopeGenerator extends IPModule {

    /**
     * Module title.
     */
    String TITLE = "<html><center>ENVELOPE GENERATOR</center></html>";

    /**
     * Module width.
     */
    int WIDTH_U = 3;

    /**
     * Update the "Attack" value into the presentation.
     *
     * @param a
     *            The new attack value
     */
    void updateAttack(double a);

    /**
     * Update the "Decay" value into the presentation.
     * 
     * @param a
     *            The new decay value
     */
    void updateDecay(double d);

    /**
     * Update the "Sustain" value into the presentation.
     *
     * @param a
     *            The new sustain value
     */
    void updateSustain(double s);

    /**
     * Update the "Release" value into the presentation.
     *
     * @param a
     *            The new release value
     */
    void updateRelease(double r);
}
