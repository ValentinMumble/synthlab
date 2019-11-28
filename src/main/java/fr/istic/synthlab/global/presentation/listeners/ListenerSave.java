package fr.istic.synthlab.global.presentation.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.istic.synthlab.global.control.CGLOB;

/**
 * Listener on the save Button.
 *
 * @author Mickael
 * @version 1.0
 */
public class ListenerSave implements ActionListener {

    /**
     * Call the {@link CGLOB#enregistrer()} and set in the PGLOB the saved value
     * return by enregistrer.
     */
    @Override
    public void actionPerformed(ActionEvent arg0) {
        CGLOB.getInstance().destroyCurrentCable();
        CGLOB.getInstance().getPresentation()
                .setSaved(CGLOB.getInstance().enregistrer());
    }
}
