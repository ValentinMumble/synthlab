package fr.istic.synthlab.save.control;

import fr.istic.synthlab.global.presentation.IPGLOB;
import fr.istic.synthlab.save.abstraction.IASave;

/**
 * Interface of the CSave.
 *
 * @author Mickael
 * @version 1.0
 */
public interface ICSave extends IASave {

    /**
     * Show the dialogWindow for the Load.
     */
    void pop();

    /**
     * @return the Presentation of the GLOB.
     */
    IPGLOB getPGLOB();
}
