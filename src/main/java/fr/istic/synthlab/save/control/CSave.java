package fr.istic.synthlab.save.control;

import fr.istic.synthlab.global.control.CGLOB;
import fr.istic.synthlab.global.presentation.IPGLOB;
import fr.istic.synthlab.save.abstraction.ASave;
import fr.istic.synthlab.save.presentation.IPSave;
import fr.istic.synthlab.save.presentation.PSave;

/**
 * Implementation of the CSave.
 *
 * @author Mickael
 * @version 1.0
 */
public class CSave extends ASave implements ICSave {

    /**
     * Presentation of the Save.
     */
    private IPSave pSave;

    /**
     * Constructor of the CSave.
     */
    public CSave() {
        pSave = new PSave(this);
    }

    /**
     * Show the dialogWindow for the Save.
     */
    @Override
    public void pop() {
        pSave.setVisible(true);
    }

    /**
     * @return the Presentation of the GLOB.
     */
    @Override
    public IPGLOB getPGLOB() {
        return CGLOB.getInstance().getPresentation();
    }
}
