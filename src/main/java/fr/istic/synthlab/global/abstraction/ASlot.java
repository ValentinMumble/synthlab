package fr.istic.synthlab.global.abstraction;

import fr.istic.synthlab.module.control.ICModule;

/**
 * Abstract for Slot.
 * 
 * @author Jonathan
 */
public class ASlot {

    /**
     * ICModule stocked in the Slot.
     */
    private ICModule iCModule;
    /**
     * The Slot is occupied.
     */
    private boolean occupe;
    /**
     * Position of the Slot in the line.
     */
    private int position;
    /**
     * Line in the grid.
     */
    private int ligne;

    /**
     * @return The position of the Slot.
     */
    public int getPosition() {
        return position;
    }

    /**
     * Set the position of the Slot.
     * 
     * @param pos
     *            The position to set.
     */
    protected void setPosition(int pos) {
        this.position = pos;
    }

    /**
     * @return The line of the Slot.
     */
    public int getLigne() {
        return ligne;
    }

    /**
     * Set the line of the Slot.
     * 
     * @param line
     *            The line to set.
     */
    protected void setLigne(int line) {
        this.ligne = line;
    }

    /**
     * @return true if the Slot is occupied.
     */
    public boolean isOccupe() {
        return occupe;
    }

    /**
     * Set the parameter occupe.
     * 
     * @param occuped
     *            is the Slot is occupied.
     */
    public void setOccupe(boolean occuped) {
        this.occupe = occuped;
    }

    /**
     * @return if the Slot is empty => true.
     */
    public boolean isNotOccupe() {
        return !occupe;
    }

    /**
     * @return the Module controller.
     */
    public ICModule getICModule() {
        return iCModule;
    }

    /**
     * Set the Module controller and make the location occupied.
     *
     * @param icm
     *            : Interface of Module controller
     */
    protected void setIModule(ICModule icm) {
        iCModule = icm;
    }

    /**
     * Remove the Module controller and make the location not occupied.
     */
    public void removeIModule() {
        iCModule = null;
    }
}
