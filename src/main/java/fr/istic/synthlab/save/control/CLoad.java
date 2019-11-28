package fr.istic.synthlab.save.control;

import fr.istic.synthlab.global.control.CGLOB;
import fr.istic.synthlab.global.presentation.IPGLOB;
import fr.istic.synthlab.save.abstraction.ALoad;
import fr.istic.synthlab.save.presentation.IPLoad;
import fr.istic.synthlab.save.presentation.PLoad;

/**
 * Implementation of the CLoad.
 *
 * @author Mickael
 * @version 1.0
 */
public class CLoad extends ALoad implements ICLoad {

    /**
     * Presentation of the Load.
     */
    private IPLoad pLoad;

    /**
     * Constructor of the CLoad.
     */
    public CLoad() {
        pLoad = new PLoad(this);
        this.initPortList();
    }

    /**
     * Show the dialogWindow for the Load.
     */
    public void pop() {
        pLoad.setVisible(true);
    }

    /**
     * @return the Presentation of the GLOB.
     */
    public IPGLOB getPGLOB() {
        return CGLOB.getInstance().getPresentation();
    }
}
