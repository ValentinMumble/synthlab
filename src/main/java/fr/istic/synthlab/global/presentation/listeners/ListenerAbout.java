package fr.istic.synthlab.global.presentation.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import fr.istic.synthlab.util.Icons;

public class ListenerAbout implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent arg0) {
        String[] options = { "OK" };
        JOptionPane optionPane = new JOptionPane(
                new JLabel(
                        "<html><div style=\"text-align: center;\">Team involved in the development of this software:<br><br>"
                                + "Laurent Legendre, Marc Favereau, Jonathan Delétoile,<br>"
                                + "Mickaël Herrouet, Florent Berthelot, Valentin Dijkstra.</html>",
                        JLabel.CENTER), JOptionPane.QUESTION_MESSAGE,
                JOptionPane.NO_OPTION, Icons.icon(Icons.MAIN_ICON_PATH), options, options[0]);
        JDialog dialog = optionPane.createDialog("Development team");
        dialog.setModal(true);
        dialog.setVisible(true);
    }

}
