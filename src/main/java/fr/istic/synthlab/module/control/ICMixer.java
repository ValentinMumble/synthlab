package fr.istic.synthlab.module.control;

import fr.istic.synthlab.module.abstraction.IAMixer;
import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * Interface of CMixer.
 * 
 * @author Laurent Legendre
 * @version 1.1
 */
public interface ICMixer extends ICModule, IAMixer {

    /**
     * @return the first inputJack controller
     */
    ICFemaleJack getInput1();

    /**
     * @return the second inputJack controller
     */
    ICFemaleJack getInput2();

    /**
     * @return the third inputJack controller
     */
    ICFemaleJack getInput3();

    /**
     * @return the forth inputJack controller
     */
    ICFemaleJack getInput4();

    /**
     * @return the outputJack controller
     */
    ICFemaleJack getOutput();

    /**
     * Called by the Presentation when the attenuation for an input changed. UoM
     * is in Db
     * 
     * @param gain
     */
    void attenuationChanged(int inputNumber, double gain);

    /**
     * Get the attenuation value for the input port whose number is given as
     * parameter.
     * 
     * @param inputNumber
     *            number of the wanted input port. 1 is the first one.
     * @return the attenuation value for the given input number in dB
     */
    double getAttenuation(int inputPortNumber);

    /**
     * Get the Connector (female jack) controller for the given input number.
     * 
     * 
     * @param inputNumber
     * @return the Connector (female jack) controller for the given input
     *         number.
     */
    ICFemaleJack getInput(int inputNumber);

}
