package fr.istic.synthlab.global.presentation.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.istic.synthlab.global.control.CGLOB;

/**
 * Listener on the Button Load in the menu.
 *
 * @author Mickael
 * @version 1.0
 */
public class ListenerLoad implements ActionListener {

    /**
     * call {@link CGLOB#load()}.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        CGLOB.getInstance().destroyCurrentCable();
        CGLOB.getInstance().load();
    }
}
