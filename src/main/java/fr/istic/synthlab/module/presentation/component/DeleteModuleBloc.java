package fr.istic.synthlab.module.presentation.component;

import java.awt.GridBagConstraints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import fr.istic.synthlab.module.presentation.PModule;
import fr.istic.synthlab.util.Icons;

/**
 * GUI component to be used in a PModule.
 * 
 * Represents a "delete" button.
 * 
 * @author Laurent Legendre
 * 
 */
public class DeleteModuleBloc extends Bloc {

    /**
     * UID to serialize this object.
     */
    private static final long serialVersionUID = 4308212826012128575L;

    /**
     * Action Command used in the ActionEvent which will be send to the owner of
     * this DeleteBloc after a click occurs on the Delete button
     */
    public static final String ACTION_CMD_DELETE_MODULE = "DELETE_MODULE";

    /**
     * GridBagConstraints Delete Button
     */
    private GridBagConstraints gbcDeleteBtn;

    /**
     * Button to Delete the module.
     */
    private JLabel deleteBtn;

    /**
     * 
     * Constructor.
     * 
     * All Action Events thrown by an action on the mute button will be
     * forwarded to the ActionListen given to this Constructor.
     * 
     * @param listener
     */
    public DeleteModuleBloc(final PModule pModule) {

        // Setup the Btn
        this.deleteBtn = new JLabel(Icons.BUTTON_DELETE_INACTIVE);
        this.deleteBtn.setSize(this.deleteBtn.getIcon().getIconWidth(),
                this.deleteBtn.getIcon().getIconHeight());
        this.deleteBtn.setPreferredSize(this.deleteBtn.getSize());
        this.deleteBtn.setAlignmentX(CENTER_ALIGNMENT);
        this.deleteBtn.addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {
                pModule.requestForDelete();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
                showInactive();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                showActive();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });

        this.gbcDeleteBtn = new GridBagConstraints(0, 0,
                GridBagConstraints.REMAINDER, GridBagConstraints.REMAINDER, 0,
                0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                Bloc.DEFAULT_INSETS, 0, 0);
        
        this.add(this.deleteBtn,this.gbcDeleteBtn);
    }

    private void showActive() {
        this.deleteBtn.setIcon(Icons.BUTTON_DELETE_ACTIVE);

    }

    private void showInactive() {
        this.deleteBtn.setIcon(Icons.BUTTON_DELETE_INACTIVE);

    }

}
