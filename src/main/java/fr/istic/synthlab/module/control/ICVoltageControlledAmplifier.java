package fr.istic.synthlab.module.control;

import fr.istic.synthlab.module.abstraction.IAVoltageControlledAmplifier;
import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * Interface for the VCA module controller
 * 
 * @author Favereau
 * 
 */
public interface ICVoltageControlledAmplifier extends ICModule, IAVoltageControlledAmplifier {

    /**
     * Method amplificationChange
     * Called by the Presentation when the nominal amplification adjustment changes.
     * 
     * value is in dB (usually betwenn -30 and 60)
     * @param value double
     *
     */
    void amplificationChange(double value);

    /**
     * Method getValAmplification.
     * Called by the Presentation to read abstraction amplification value
     *
     * @return double
     * 
     */
    double getAmplification();


    /**
     * Method getPortSignIn.
     * @return ICFemaleJack
     */
    ICFemaleJack getPortSignIn();

    /**
     * Method getPortAMIn.
     * @return ICFemaleJack
     */
    ICFemaleJack getPortAMIn();


    /**
     * Method getPortSignOut.
     * @return ICFemaleJack
     */
    ICFemaleJack getPortSignOut();

}
