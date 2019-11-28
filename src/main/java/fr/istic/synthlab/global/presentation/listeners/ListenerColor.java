package fr.istic.synthlab.global.presentation.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import fr.istic.synthlab.global.presentation.IPGLOB;
import fr.istic.synthlab.util.ColorLabel;

/**
 * Listener for the chooser of the color of the future cable.
 *
 * @author Florent
 */
public class ListenerColor implements MouseListener {

    /**
     * The IPGLOB.
     */
    private IPGLOB pGLOB;
    /**
     * The colorButton.
     */
    private ColorLabel colorButton;

    /**
     * Constructor of the ListenerColor.
     *
     * @param ipGLOB
     *            the Presentation of the global.
     * @param cb
     *            the ColorLabel that will be modified by the listener.
     */
    public ListenerColor(IPGLOB ipGLOB, ColorLabel cb) {
        this.pGLOB = ipGLOB;
        this.colorButton = cb;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * Set in the PGLOB the color of the ColorButton to all the new cables.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        pGLOB.setCableColor(colorButton.getColor());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
