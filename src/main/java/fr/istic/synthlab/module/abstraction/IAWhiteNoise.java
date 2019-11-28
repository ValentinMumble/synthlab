package fr.istic.synthlab.module.abstraction;

import com.jsyn.ports.UnitOutputPort;

/**
 * Specific interface for the module
 * White noise
 * 
 * @author Favereau
 * 
 */
public interface IAWhiteNoise extends IAModule{

    /**
     * TAG Identifier for the
     * White noise module
     */
    String TAG = "BRUI";

   
    /**
     * Method getOutputPort.
     * @return UnitOutputPort
     */
    UnitOutputPort getOutputPort();
    
    /**
     * Method getEnable.
     * @return boolean
     */
    boolean getEnable();
    
    /**
     * Method setEnable.
     * @param boolean
     */
    void setEnable(boolean value);
}
