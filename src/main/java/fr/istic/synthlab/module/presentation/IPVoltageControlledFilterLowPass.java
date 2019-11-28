package fr.istic.synthlab.module.presentation;

/**
 * The interface for the VCFLP presentation
 * 
 * @author valentinmumble
 * 
 */
public interface IPVoltageControlledFilterLowPass extends IPModule {

    /**
     * Module title.
     */
    String TITLE = "<html><center>VOLTAGE-CONTROLLED<br/>LOW-PASS FILTER</center></html>";

    /**
     * Module width.
     */
    int WIDTH_U = 2;

    /**
     * Called by the control to update the cutoff frequency
     * 
     * @param freq
     */
    void updateCutoffFrequency(double freq);

    /**
     * Called by the control to update the resonance
     * 
     * @param res
     */
    void updateResonance(double res);
}
