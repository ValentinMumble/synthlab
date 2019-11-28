package fr.istic.synthlab.module.presentation;

/**
 * The interface for the VCFHP presentation
 * 
 * @author Valentin
 * 
 */
public interface IPVoltageControlledFilterHighPass extends IPModule {
    
    /**
     * Module title.
     */
    String TITLE = "<html><center>VOLTAGE-CONTROLLED<br/>HIGH-PASS FILTER</center></html>";

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
}
