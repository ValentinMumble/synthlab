package fr.istic.synthlab.global.abstraction;

import fr.istic.synthlab.save.control.ICLoad;
import fr.istic.synthlab.save.control.ICSave;

/**
 * Abstract for GLOB.
 *
 * @author jonathan
 */
public class AGLOB implements IAGLOB {

    /**
     * Control of Save.
     */
    private ICSave cSave;
    /**
     * Control of Load.
     */
    private ICLoad cLoad;

    /**
     * Setter of the CSave and the CLoad.
     *
     * @param controlSave
     *            control of the save
     * @param ccontrolLoad
     *            control of the load
     */
    protected void setCSave(ICSave controlSave, ICLoad ccontrolLoad) {
        this.cSave = controlSave;
        this.cLoad = ccontrolLoad;
    }

    /**
     * Function to open save window.
     *
     * @return true if the save have success.
     */
    public boolean enregistrer() {
        cSave.pop();
        return cSave.getSuccess();
    }

    /**
     * Function to open load window.
     */
    public void load() {
        cLoad.pop();
    }
}
