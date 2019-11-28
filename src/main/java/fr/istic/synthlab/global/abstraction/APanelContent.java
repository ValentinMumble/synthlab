package fr.istic.synthlab.global.abstraction;

import java.util.ArrayList;

import fr.istic.synthlab.global.control.CGLOB;
import fr.istic.synthlab.global.control.CSlot;

/**
 * Abstract for PanelContent.
 *
 * @author Jonathan
 */
public class APanelContent {

    /**
     * The Number of Slot by line.
     */
    public static final int NB_SLOTS_BY_RACK = 9;

    /**
     * The grid that memorize the slots.
     */
    private ArrayList<CSlot[]> grille;

    /**
     * Initialize the grid.
     */
    protected void init() {
        grille = new ArrayList<CSlot[]>();
    }

    /**
     * Getter of the grille.
     *
     * @category Accessor
     * @return the grid
     */
    public ArrayList<CSlot[]> getGrille() {
        return grille;
    }

    /**
     * Reinitialize the grid.
     */
    public void reinit() {
        for (CSlot[] ligne : grille) {
            for (CSlot slot : ligne) {
                slot.remove();
            }
        }
        int l = grille.size();
        for (int i = 0; i < l; i++) {
            CGLOB.getInstance().getCPanelContent().removeLine();
        }
    }
}
