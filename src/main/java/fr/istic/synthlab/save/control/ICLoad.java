package fr.istic.synthlab.save.control;

import fr.istic.synthlab.global.presentation.IPGLOB;
import fr.istic.synthlab.save.abstraction.IALoad;

/**
 * Interface of the CLoad.
 *
 * @author Mickael
 * @version 1.0
 */
public interface ICLoad extends IALoad {

    /**
     * Show the dialogWindow for the Load.
     */
    void pop();

    /**
     * @return the Presentation of the GLOB.
     */
    IPGLOB getPGLOB();
}
