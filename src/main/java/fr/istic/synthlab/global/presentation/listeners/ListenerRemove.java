package fr.istic.synthlab.global.presentation.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.istic.synthlab.global.control.CGLOB;

/**
 * Listener on the Button the remove a line.
 *
 * @author Mickael
 * @version 1.0
 */
public class ListenerRemove implements ActionListener {

    /**
     * ask to the PanelContent of the CGLOB to remove a line.
     */
    @Override
    public void actionPerformed(ActionEvent arg0) {
        CGLOB.getInstance().getCPanelContent().removeLine();
    }
}
