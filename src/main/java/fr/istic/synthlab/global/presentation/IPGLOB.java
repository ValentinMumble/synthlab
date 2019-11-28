package fr.istic.synthlab.global.presentation;

import java.awt.Color;

import javax.swing.JLayeredPane;

import fr.istic.synthlab.cable.presentation.IPCable;
import fr.istic.synthlab.cable.presentation.PCable;

/**
 * Interface of PGLOB.
 *
 * @author Mickael
 * @version 1.1
 */
public interface IPGLOB {

    /**
     * Getter for the panel Cable.
     *
     * @return layeredPane.
     */
    JLayeredPane getLayeredPane();

    /**
     * @return Pane used to draw the dragged modules.
     */
    JLayeredPane getTopPane();

    /**
     * Called when we want to close.
     */
    void closeDialog();

    /**
     * Resize the different component to fit in the JFrame.
     */
    void myResize();

    /**
     * Called whenever a modification is made.
     *
     * @param value
     *            value of saved to set.
     */
    void setSaved(boolean value);

    /**
     * @return saved value.
     */
    boolean getSaved();

    /**
     * Initialize the global JFrame.
     */
    void init();

    /**
     * Listener for clic to update position of the current cable.
     */
    void addMouseListener();

    /**
     * Remove the listener for clic to update position of the current cable.
     */
    void removeMouseListener();

    /**
     * add the cable to panelCable.
     *
     * @param pcable
     *            Cable Presentation to add.
     */
    void addCable(PCable pcable);

    /**
     * Function to update the Presentation of cables.
     */
    void updatePresentationCables();

    /**
     * remove the cable from panelCable.
     *
     * @param pcable
     *            Cable Presentation to remove.
     */
    void removeCablePresentation(IPCable pcable);

    /**
     * Setter of the color of the cable.
     *
     * @param c
     *            color to set.
     */
    void setCableColor(Color c);

    /**
     * get the color for the future cable.
     *
     * @return the color for the future cable
     */
    Color getCableColor();
}
