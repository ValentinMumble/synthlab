package fr.istic.synthlab.global.control;

import java.util.logging.Logger;

import javax.swing.JPanel;

import fr.istic.synthlab.Synthlab;
import fr.istic.synthlab.global.abstraction.ASlot;
import fr.istic.synthlab.global.presentation.IPGLOB;
import fr.istic.synthlab.global.presentation.PSlot;
import fr.istic.synthlab.module.control.ICModule;

/**
 * Controller for Slot.
 * 
 * @author Jonathan
 */
public class CSlot extends ASlot {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger
            .getLogger(CSlot.class.getName());
    /**
     * Presentation of the Slot.
     * 
     * @category PAC
     */
    private PSlot presentation;
    /**
     * Controller of the panelContent associate.
     */
    private CPanelContent cPanelContent;

    /**
     * Constructor of CSlot.
     * 
     * @category Constructor
     * @param position
     *            The position of the CSlot.
     * @param ligne
     *            Line in the cPanelContent where is the Slot.
     * @param controlPanelContent
     *            cPanelContent that contain the Slot.
     */
    public CSlot(int position, int ligne, CPanelContent controlPanelContent) {
        this.cPanelContent = controlPanelContent;
        this.setLigne(ligne);
        this.setPosition(position);
        this.setOccupe(false);
        presentation = new PSlot(this);
    }

    /**
     * @return the Presentation of the GLOB.
     */
    public IPGLOB getPGLOB() {
        return CGLOB.getInstance().getPresentation();
    }

    /**
     * @return if the module exist => true
     */
    public boolean dragable() {
        return this.isOccupe();
    }

    /**
     * Begin the Drag&Drop.
     * 
     * @category DnD
     * @param icm
     *            : Interface of Module controller
     */
    public void p2c_debutDnD(ICModule icm) {
        try {
            this.removeIModule();
            cPanelContent.setOccupe(getLigne(), getPosition(), icm
                    .getPresentation().getWidthU(), false);
            presentation.c2p_debutDnDOK(icm);
        } catch (Exception e) {
            LOGGER.info("c2p_debutDnDOK failed");
        }
    }

    /**
     * @category Accessor
     * @return the presentation
     */
    public PSlot getPresentation() {
        return presentation;
    }

    /**
     * @category DnD
     * @param success
     *            if the Drop success
     * @param icm
     *            Interface of Module controller that must be add
     */
    public void p2c_dragDropEnd(boolean success, ICModule icm) {
        if (!success) {
            this.setIModule(icm);
            this.getPresentation().ajouterModule();
            cPanelContent.setOccupe(getLigne(), getPosition(), icm
                    .getPresentation().getWidthU(), true);
            this.myResize();
            CGLOB.getInstance().setSaved(false);
        }
    }

    /**
     * Called when we enter in the panel with a D&D.
     * 
     * @category DnD
     * @param icm
     *            The Module we are moving.
     */
    public void p2c_dragEnter(ICModule icm) {
        if (cPanelContent.isAjoutable(this.getLigne(), this.getPosition(), icm
                .getPresentation().getWidthU())) {
            presentation.c2p_showAjoutable();
        } else {
            presentation.c2p_showNonAjoutable();
        }
    }

    /**
     * Called when we exit the panel with a D&D.
     * 
     * @category DnD
     */
    public void p2c_dragExit() {
        presentation.c2p_showNeutre();
        // If we are on a Mac, we must reset all slots border
        // to prevent the color feedback to stay even if the dragged module is
        // not over the slot anymore
        if (Synthlab.OS_NAME.contains("Mac")) {
            CGLOB.getInstance().getCPanelContent().getPresentation()
                    .resetBorders();
        }
    }

    /**
     * Called when we drop the D&D.
     * 
     * @category DnD
     * @param icm
     *            The Module to drop.
     */
    public void p2c_drop(ICModule icm) {
        int unite = icm.getPresentation().getWidthU();
        CSlot cSlot;
        if (cPanelContent.isAjoutable(this.getLigne(), this.getPosition(),
                unite)) {
            if (unite % 2 == 1) { // Si impair
                cSlot = cPanelContent.getGrille().get(getLigne())[getPosition()
                        - unite / 2];
                cPanelContent.setOccupe(getLigne(), getPosition() - unite / 2,
                        unite, true);
            } else { // Pair
                cSlot = cPanelContent.getGrille().get(getLigne())[getPosition()
                        - unite / 2 + 1];
                cPanelContent.setOccupe(getLigne(), getPosition() - unite / 2
                        + 1, unite, true);
            }
            cSlot.setIModule(icm);
            cSlot.getPresentation().c2p_dropOK_from_other();
            this.getPresentation().c2p_dropOK();
        } else {
            presentation.c2p_dropKO();
        }
        presentation.c2p_showNeutre();
        // If we are on a Mac, we must reset all slots border
        // to prevent the color feedback to stay even if the dragged module is
        // not over the slot anymore
        if (Synthlab.OS_NAME.contains("Mac")) {
            CGLOB.getInstance().getCPanelContent().getPresentation()
                    .resetBorders();
        }
    }

    /**
     * Call to remove the module in the Slot and liberate all slots
     * used by the module.
     */
    public void remove() {
        if (isOccupe() && this.getICModule() != null) {
            int unite = getICModule().getPresentation().getWidthU();
            this.getICModule().suppressModule();
            this.getPresentation().remove(
                    (JPanel) this.getICModule().getPresentation());

            cPanelContent.setOccupe(getLigne(), getPosition(), unite, false);
            this.removeIModule();
        }
    }

    /**
     * Load the module in the Slot.
     * 
     * @param icm
     *            the module to load.
     */
    public void loadModule(ICModule icm) {
        int unite = icm.getPresentation().getWidthU();
        CSlot slot = cPanelContent.getGrille().get(getLigne())[getPosition()];
        cPanelContent.setOccupe(getLigne(), getPosition(), unite, true);
        slot.setIModule(icm);
        slot.getPresentation().ajouterModule();
        this.myResize();
    }

    /**
     * Function to resize.
     */
    public void myResize() {
        cPanelContent.myResize();
    }
}
