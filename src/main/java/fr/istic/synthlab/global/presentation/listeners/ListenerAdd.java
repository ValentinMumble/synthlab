package fr.istic.synthlab.global.presentation.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.istic.synthlab.global.presentation.IPGLOB;
import fr.istic.synthlab.global.presentation.PPanelContent;

/**
 * Listener for the add a line button in the menu.
 *
 * @author Jonathan
 */
public class ListenerAdd implements ActionListener {

    /**
     * The Presentation of PanelContent.
     */
    private PPanelContent panelContent;
    /**
     * The IPGLOB.
     */
    private IPGLOB pGLOB;

    /**
     * Constructor of the ListenerAdd.
     *
     * @param panelContent
     *            the panel content of the application.
     * @param ipGLOB
     *            the Presenation of the GLOB.
     */
    public ListenerAdd(PPanelContent panelContent, IPGLOB ipGLOB) {
        this.pGLOB = ipGLOB;
        this.panelContent = panelContent;
    }

    /**
     * Ask to the panel content to create a new line and call
     * {@link IPGLOB#myResize()}.
     */
    @Override
    public void actionPerformed(ActionEvent arg0) {
        panelContent.getControle().addLine();
        pGLOB.myResize();
    }
}
