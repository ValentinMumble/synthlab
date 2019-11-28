package fr.istic.synthlab.global.control;

import fr.istic.synthlab.global.abstraction.APanelContent;
import fr.istic.synthlab.global.presentation.PPanelContent;
import fr.istic.synthlab.module.control.COut;

/**
 * Controller for PanelContent.
 *
 * @author Jonathan
 */
public class CPanelContent extends APanelContent {

    /**
     * Presentation of the PanelContent.
     *
     * @category PAC
     */
    private PPanelContent presentation;

    /**
     * Constructor for the Controller of PanelContent.
     *
     * @category Constructor
     */
    public CPanelContent() {
        this.presentation = new PPanelContent(this);
    }

    /**
     * Getter for the presentation of PanelContent.
     *
     * @return the presentation
     */
    public PPanelContent getPresentation() {
        return presentation;
    }

    /**
     * Can we add a module with the unit size at the position specified.
     * 
     * @param ligne
     *            Line in the grid.
     * @param position
     *            Position in the line.
     * @param unite
     *            Number of Slot the module need.
     * @return True if we can add the module.
     */
    public boolean isAjoutable(int ligne, int position, int unite) {
        if (unite % 2 == 1) { // Si impair
            if ((position + unite / 2) < getGrille().get(ligne).length
                    && (position - unite / 2) >= 0) {
                boolean notOccupe = true;
                for (int i = 0; i < unite; i++) {
                    if (!getGrille().get(ligne)[position - unite / 2 + i]
                            .isNotOccupe()) {
                        notOccupe = false;
                    }
                }
                return notOccupe;
            } else {
                return false;
            }
        } else { // Pair
            if ((position + unite / 2) < getGrille().get(ligne).length
                    && (position - unite / 2 + 1) >= 0) {
                boolean notOccupe = true;
                for (int i = 0; i < unite; i++) {
                    if (!getGrille().get(ligne)[position - unite / 2 + 1
                            + i].isNotOccupe()) {
                        notOccupe = false;
                    }
                }
                return notOccupe;
            } else {
                return false;
            }
        }
    }

    /**
     * Initialize the PanelContent an call {@link PPanelContent#init()} .
     */
    public void initPanel() {
        init();
        presentation.init();
    }

    /**
     * Add a line to the grid.
     */
    public void addLine() {
        CSlot[] ligne = new CSlot[NB_SLOTS_BY_RACK];
        for (int i = 0; i < ligne.length; i++) {
            ligne[i] = new CSlot(i, this.getGrille().size(), this);
        }
        this.getGrille().add(ligne);

        presentation.addLine(this.getGrille().size() - 1);
    }

    /**
     * Set the boolean occupe for each corresponding CSlot when a module is
     * added.
     * 
     * @param line
     *            line in the grid.
     * @param position
     *            position in the line where start the reservation/liberation.
     * @param unite
     *            number of CSlot to reserve/liberate.
     * @param value
     *            the value, true to reserve, false to liberate.
     */
    public void setOccupe(int line, int position, int unite, boolean value) {
        for (int i = 0; i < unite; i++) {
            this.getGrille().get(line)[position + i].setOccupe(value);
        }
    }

    /**
     * Remove the last line in the grid.
     */
    public void removeLine() {
        if (getGrille().size() > 2) {
            boolean vide = true;
            for (CSlot slot : getGrille().get(
                    getGrille().size() - 1)) {
                if (slot.isOccupe()) {
                    vide = false;
                }
            }

            if (vide) {
                presentation.removeLine();
                this.getGrille().remove(getGrille().size() - 1);
                CGLOB.getInstance().myResize();
            }
        }
    }

    /**
     * Reinitialize the grid with two lines and an Out Module.
     */
    public void reinit() {
        super.reinit();
        this.addOutInitial();
        CGLOB.getInstance().myResize();
    }
    /**
     * Reinitialize the grid with two lines and an empty grid.
     */
    public void reinitForLoad() {
        super.reinit();
    }
    /**
     * Call {@link PPanelContent#myResize()} .
     */
    public void myResize() {
        presentation.myResize();
    }

    /**
     * Add a Out Module in place [0,0] of the grid.
     */
    private void addOutInitial(){
        CSlot place = this.getGrille().get(0)[0];
        place.loadModule(new COut(CGLOB.getInstance().getSynthesizer()));
    }
}
