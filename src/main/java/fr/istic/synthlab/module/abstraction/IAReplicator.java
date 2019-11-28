package fr.istic.synthlab.module.abstraction;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;

/**
 * The interface for the replicator module abstraction.
 * @author valentinmumble
 *
 */
public interface IAReplicator {

    /**
     * TAG Identifier for the REP module.
     */
    String TAG = "AREP";
    
    /**
     * @return the module input port.
     */
    UnitInputPort getInputPort();

    /**
     * @return the first output
     */
    UnitOutputPort getOutput1Port();
    
    /**
     * @return the second output
     */
    UnitOutputPort getOutput2Port();
    
    /**
     * @return the third output
     */
    UnitOutputPort getOutput3Port();
}
