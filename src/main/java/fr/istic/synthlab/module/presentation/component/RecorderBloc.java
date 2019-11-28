package fr.istic.synthlab.module.presentation.component;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

import fr.istic.synthlab.util.Icons;

/**
 * GUI component to be used in a PModule.
 * 
 * Represents :
 *  - Recorder start button
 *  - Recorder stop button
 *  - Recorder save file button
 *  - A LED which indicates recorder status
 * 
 * @author Favereau
 * 
 */
public class RecorderBloc extends Bloc{

    /**
     * UID to serialize this object.
     */
    private static final long serialVersionUID = -4189914978709013635L;

    /**
     * Action Command used in the ActionEvent which will be send to the owner of
     * this RecorderBloc after a click occurs on the REC button
     */
    public static final String ACTION_CMD_TOGGLE_REC = "TOGGLE_REC";
    
    /**
     * Action Command used in the ActionEvent which will be send to the owner of
     * this RecorderBloc after a click occurs on the save file button
     */
    public static final String ACTION_CMD_FILE = "SELECT_FILE";
    
    /**
     * Button to start/stop record.
     */
    private JButton btnRec;

    /**
     * Button to select file.
     */
    private JButton btnSaveFile;
    
    /**
     * Representation of the LED indicating if
     * the recorder is started (led on) or not
     * (led off).
     */
    private JLabel ledRecord;
    
    private int index;
    
    
    
    /**
     * 
     * Constructor.
     * 
     * All Action Events thrown by an action on the buttons will be
     * forwarded to the ActionListen given to this Constructor.
     * 
     * @param listener
     */
    public RecorderBloc(final ActionListener listener, int id){
        
        this.index = id;
        // Setup the REC Btn
        this.btnRec = new JButton(Icons.BUTTON_REC);
        this.btnRec.setSize(this.btnRec.getIcon().getIconWidth() + 10,
                this.btnRec.getIcon().getIconHeight() + 5);
        this.btnRec.setPreferredSize(this.btnRec.getSize());
        //this.btnRec.setAlignmentX(CENTER_ALIGNMENT);
        this.btnRec.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                listener.actionPerformed(new ActionEvent(this,
                        ActionEvent.ACTION_PERFORMED, ACTION_CMD_TOGGLE_REC + "_" + index));
            }
        });
        
        // Setup the FILE Btn
        this.btnSaveFile = new JButton(Icons.BUTTON_FILE);
        this.btnSaveFile.setSize(this.btnSaveFile.getIcon().getIconWidth() + 10,
                this.btnSaveFile.getIcon().getIconHeight() + 5);
        this.btnSaveFile.setPreferredSize(this.btnSaveFile.getSize());
        //this.btnSaveFile.setAlignmentX(CENTER_ALIGNMENT);
        this.btnSaveFile.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                listener.actionPerformed(new ActionEvent(this,        
                        ActionEvent.ACTION_PERFORMED, ACTION_CMD_FILE + "_" + index));
            }
        });

        // Setup the LED
        this.ledRecord = new JLabel();
        this.ledRecord.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        this.ledRecord.setSize(12, 20);
        this.ledRecord.setPreferredSize(this.ledRecord.getSize());
        this.ledRecord.setMinimumSize(this.ledRecord.getSize());
        this.ledRecord.setMaximumSize(this.ledRecord.getSize());
        //this.ledRecord.setAlignmentX(CENTER_ALIGNMENT);
        this.ledRecord.setOpaque(true);
        // Add components
        
        JLabel espace = new JLabel();
        espace.setSize(5, 5);
        espace.setOpaque(false);
        espace.setPreferredSize(espace.getSize());
        JLabel espace1 = new JLabel();
        espace1.setSize(5, 5);
        espace1.setOpaque(false);
        espace1.setPreferredSize(espace1.getSize());
        
        this.add(this.btnRec);
        this.add(espace);
        this.add(this.ledRecord);
        this.add(espace1);
        this.add(this.btnSaveFile);
    }
    
    /**
     * Called by the parent component to switch the mute LED on
     */
    public void showMuteOn() {
        if (this.ledRecord != null) {
            this.ledRecord.setBackground(Color.RED);
            this.repaint();
        }
    }

    /**
     * Called by the parent component to switch the mute LED off
     */
    public void showMuteOff() {
        if (this.ledRecord != null) {
            this.ledRecord.setBackground(null);
        }
    }
    
}
