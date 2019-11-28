package fr.istic.synthlab.module.presentation.component;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

import fr.istic.synthlab.util.Icons;


/**
 * GUI component to be used in a PModule.
 * 
 * Represents a "mute" button under a LED which indicates the mute status
 * 
 * @author Laurent Legendre
 * 
 */
public class MuteBloc extends Bloc {

    /**
     * UID to serialize this object.
     */
    private static final long serialVersionUID = 7059732370025481670L;

    /**
     * Action Command used in the ActionEvent which will be send to the owner of
     * this MuteBloc after a click occurs on the Mute button
     */
    public static final String ACTION_CMD_TOGGLE_MUTE = "TOGGLE_MUTE";

    /**
     * GridBagConstraints for the LED
     */
    private GridBagConstraints gbcMuteLed;

    /**
     * GridBagConstraints for the Mute button
     */
    private GridBagConstraints gbcMuteBtn;

    /**
     * Button to toggle mute on/off.
     */
    private JButton muteBtn;

    /**
     * Representation of the LED indicating if output is muted (led on) or not
     * (led off).
     */
    private JLabel muteLed;

    /**
     * 
     * Constructor.
     * 
     * All Action Events thrown by an action on the mute button will be
     * forwarded to the ActionListen given to this Constructor.
     * 
     * @param listener
     */
    public MuteBloc(final ActionListener listener) {

        // Setup the Btn
        this.muteBtn = new JButton(Icons.BUTTON_MUTE);
        this.muteBtn.setSize(this.muteBtn.getIcon().getIconWidth() + 10,
                this.muteBtn.getIcon().getIconHeight() + 5);
        this.muteBtn.setPreferredSize(this.muteBtn.getSize());
        this.muteBtn.setAlignmentX(CENTER_ALIGNMENT);
        this.muteBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                listener.actionPerformed(new ActionEvent(this,
                        ActionEvent.ACTION_PERFORMED, ACTION_CMD_TOGGLE_MUTE));
            }
        });

        // Setup the LED
        this.muteLed = new JLabel();
        this.muteLed.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        this.muteLed.setSize(15, 8);
        this.muteLed.setPreferredSize(this.muteLed.getSize());
        this.muteLed.setMinimumSize(this.muteLed.getSize());
        this.muteLed.setMaximumSize(this.muteLed.getSize());
        this.muteLed.setAlignmentX(CENTER_ALIGNMENT);
        this.muteLed.setOpaque(true);
        // Setup the GBC
        this.setGbc();
        // Add components
        this.add(this.muteLed, gbcMuteLed);
        this.add(this.muteBtn, gbcMuteBtn);
    }

    /**
     * Initialize GridBagConstraints for both components.
     */
    private void setGbc() {
        this.gbcMuteLed = new GridBagConstraints(0, 0,
                GridBagConstraints.REMAINDER, GridBagConstraints.RELATIVE, 0,
                0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                Bloc.DEFAULT_INSETS, 0, 0);
        this.gbcMuteBtn = new GridBagConstraints(0, 1,
                GridBagConstraints.REMAINDER, GridBagConstraints.REMAINDER, 0,
                0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                Bloc.DEFAULT_INSETS, 0, 0);
    }

    /**
     * Called by the parent component to switch the mute LED on
     */
    public void showMuteOn() {
        if (this.muteLed != null) {
            this.muteLed.setBackground(Color.BLUE);
        }
    }

    /**
     * Called by the parent component to switch the mute LED off
     */
    public void showMuteOff() {
        if (this.muteLed != null) {
            this.muteLed.setBackground(null);
        }
    }

}
