package fr.istic.synthlab.global.presentation.listeners;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import fr.istic.synthlab.global.control.CGLOB;

/**
 * Listener on the Button to Create a new grid.
 *
 * @author Mickael
 * @version 1.0
 */
public class ListenerNew implements ActionListener {

    /**
     * boolean cancel.
     */
    private boolean cancel = false;

    /**
     * if the state wasn't saved open a dialog window, else call function to
     * reinitialize the grid.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        CGLOB.getInstance().destroyCurrentCable();
        if (CGLOB.getInstance().getSaved()) {
            CGLOB.getInstance().getCPanelContent().reinit();
            CGLOB.getInstance().getPresentation().myResize();
            CGLOB.getInstance().setSaved(false);
        } else {
            demand();
        }
    }

    /**
     * Manage the dialog for create a new grid.
     */
    private void demand() {
        int choix = JOptionPane.showConfirmDialog((Component) CGLOB
                .getInstance().getPresentation(),
                "Do you want to save your configuration?", "New",
                JOptionPane.YES_NO_CANCEL_OPTION);
        switch (choix) {
        case 0:
            cancel = false;
            if (!CGLOB.getInstance().enregistrer()) {
                demand();
            }
            if (!cancel) {
                CGLOB.getInstance().getCPanelContent().reinit();
                CGLOB.getInstance().getPresentation().myResize();
                CGLOB.getInstance().setSaved(false);
            }
            break;
        case 1:
            CGLOB.getInstance().getCPanelContent().reinit();
            CGLOB.getInstance().getPresentation().myResize();
            CGLOB.getInstance().setSaved(false);
            break;
        case 2:
            cancel = true;
            break;
        default:
            // Ne rien faire
        }
    }
}
