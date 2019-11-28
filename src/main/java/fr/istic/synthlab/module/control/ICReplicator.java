package fr.istic.synthlab.module.control;

import fr.istic.synthlab.module.abstraction.IAReplicator;
import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * The interface for the Replicator module controller.
 * @author valentinmumble
 *
 */
public interface ICReplicator extends ICModule, IAReplicator {

    /**
     * @return the input controller
     */
    ICFemaleJack getInput();

    /**
     * @return the first output controller
     */
    ICFemaleJack getOutput1();

    /**
     * @return the second output controller
     */
    ICFemaleJack getOutput2();

    /**
     * @return the third output controller
     */
    ICFemaleJack getOutput3();
}
