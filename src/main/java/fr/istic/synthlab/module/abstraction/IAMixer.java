package fr.istic.synthlab.module.abstraction;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;

/**
 * The interface for the mixer module abstraction.
 * 
 * @author florent
 * @version 1.0
 */
public interface IAMixer extends IAModule {

    /**
     * TAG Identifier for the MIX module.
     */
    String TAG = "AMIX";

    /**
     * 
     * @return the input port whose number is given as parameter. 1 is the first
     *         one.
     */
    UnitInputPort getInputPort(int inputPortNumber);

    /**
     * 
     * @return the output port.
     */
    UnitOutputPort getOutputPort();

    /**
     * Set the attenuation value (between -inf and +12dB) for input port whose
     * number is given as parameter.
     * 
     * @param inputPortNumber
     *            number of the wanted input port. 1 is the first one.
     * @param value
     *            attenuation value in dB.
     * @throws Exception
     */
    void setAttenuation(int inputPortNumber, double value) throws Exception;

    /**
     * Get the attenuation value for the input port whose number is given as
     * parameter.
     * 
     * @param inputNumber
     *            number of the wanted input port. 1 is the first one.
     * @return the attenuation value for the given input number in dB
     * @throws Exception
     */
    double getAttenuation(int inputNumber) throws Exception;
}
