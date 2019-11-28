package fr.istic.synthlab.global.presentation.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.istic.synthlab.global.presentation.IPGLOB;

/**
 * Listener on "Quitter" in the JMenuBar of PGLOB (exit the application).
 *
 * @author Jonathan
 */
public class ListenerExit implements ActionListener {

    /**
     * The IPGLOB.
     */
    private IPGLOB pGLOB;

    /**
     * Constructor of the ListenerExit.
     *
     * @param ipGLOB
     *            The IPGLOB.
     */
    public ListenerExit(IPGLOB ipGLOB) {
        this.pGLOB = ipGLOB;
    }

    /**
     * call {@link IPGLOB#closeDialog()}.
     */
    public void actionPerformed(ActionEvent e) {
        pGLOB.closeDialog();
    }
}
