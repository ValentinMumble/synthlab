package fr.istic.synthlab.module.abstraction;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;

/**
 * Specific interface for the module
 * Voltage Controlled Amplifier
 * 
 * @author Favereau
 * 
 */
public interface IAVoltageControlledAmplifier extends IAModule{

    /**
     * TAG Identifier for the
     * AVoltageControlledAmplifier module
     */
    String TAG = "AVCA";

    /**
     * Method setAmplification in dB.
     * between -30 and 60 dB
     * @param value double
     */
    void setAmplification(double value);
    /**
     * Method getAmplification.
     * @return double
     */
    double getAmplification();

    /**
     * Method getInputPort.
     * @return UnitInputPort
     */
    UnitInputPort getInputPort();
    /**
     * Method getInputAmpModul.
     * @return UnitInputPort
     */
    UnitInputPort getInputAmpModul();

    /**
     * Method getOutputPort.
     * @return UnitOutputPort
     */
    UnitOutputPort getOutputPort();

}
