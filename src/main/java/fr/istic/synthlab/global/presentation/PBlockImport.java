package fr.istic.synthlab.global.presentation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DragSourceMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.istic.synthlab.global.control.CBlockImport;
import fr.istic.synthlab.module.control.ICModule;
import fr.istic.synthlab.module.presentation.PModule;
import fr.istic.synthlab.util.Template;
/**
 * Presentation for BlockImport : it contains a module drag and dropable.
 *
 * @author Jonathan
 */
public class PBlockImport extends JPanel {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -5996747583532874860L;

    /**
     * Control of the BlockImport.
     *
     * @category PAC
     */
    private CBlockImport control;

    /**
     * DragSource for DnD.
     *
     * @category DnD
     */
    private DragSource ds;
    
    /**
     * DragSourceListener for DnD.
     *
     * @category DnD
     */
    private DragSourceListener myDSL;
    
    /**
     * DragSourceMotionListener for DnD.
     *
     * @category DnD
     */
    private DragSourceMotionListener myDSML;

    /**
     * DragGesureEvent that remember the initial event for DnD.
     *
     * @category DnD
     */
    private DragGestureEvent theInitialEvent;

    /**
     * Selected Module.
     */
    private PModule selectedModule;

    /**
     * Constructor of the presentation of BlockImport.
     *
     * @category Constructor
     * @param cBlockImport
     *            Control of the BlockImport.
     */

    public PBlockImport(final CBlockImport cBlockImport) {
        this.control = cBlockImport;
        JLabel lblTexte = new JLabel(control.getType().toString());
        lblTexte.setFont(new Font("SansSerif", Font.BOLD, 11));
        lblTexte.setForeground(Color.WHITE);
        add(lblTexte);

        setBorder(BorderFactory.createLineBorder(Color.black, 1));

        this.setToolTipText(getMsgToolTip());

        // Initialisation du Drag and Drop
        myDSL = new MyDragSourceListener();
        ds = new DragSource();
        ds.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_MOVE,
                new MyDragGestureListener());
        myDSML = new MyDragSourceMotionListener();
        ds.addDragSourceMotionListener(myDSML);
    }

    /**
     * Called when the D&D begin.
     *
     * @category DnD
     * @param cModule
     *            The ICModule we are moving.
     */
    public final void c2p_debutDnDOK(final ICModule cModule) {
        selectedModule = (PModule) cModule.getPresentation();
        ds.startDrag(theInitialEvent, DragSource.DefaultCopyDrop,
                selectedModule, myDSL);

        Point p = control.getPGLOB().getTopPane().getLocationOnScreen();
        int x = MouseInfo.getPointerInfo().getLocation().x - p.x
                - selectedModule.getWidth() / 2;
        int y = MouseInfo.getPointerInfo().getLocation().y - p.y
                - selectedModule.getHeight() / 8;
        selectedModule.setLocation(x, y);

        control.getPGLOB().getTopPane().add(selectedModule, 0, 0);
    }

    /**
     * Listener for the source of the drag (only delete the drag at the end) .
     *
     * @category DnD
     * @author Jonathan
     */
    class MyDragSourceListener implements DragSourceListener {
        @Override
        public void dragDropEnd(DragSourceDropEvent e) {
            control.getPGLOB().getTopPane().remove(selectedModule);
            selectedModule = null;
            control.getPGLOB().getTopPane().repaint();
        }

        @Override
        public void dragEnter(DragSourceDragEvent dsde) {
        }

        @Override
        public void dragExit(DragSourceEvent dse) {
        }

        @Override
        public void dragOver(DragSourceDragEvent dsde) {
        }

        @Override
        public void dropActionChanged(DragSourceDragEvent dsde) {
        }
    }

    /**
     * Listener for when the drag is detected.
     *
     * @category DnD
     * @author Jonathan
     */
    class MyDragGestureListener implements DragGestureListener {
        @Override
        public void dragGestureRecognized(DragGestureEvent dge) {
            theInitialEvent = dge;
            control.p2c_debutDnD();
        }
    }

    /**
     * Listener for the drag movements.
     *
     * @category DnD
     * @author Jonathan
     */
    class MyDragSourceMotionListener implements DragSourceMotionListener {
        @Override
        public void dragMouseMoved(DragSourceDragEvent dsde) {
            Point p = control.getPGLOB().getTopPane().getLocationOnScreen();
            int x = dsde.getX() - p.x - selectedModule.getWidth() / 2;
            int y = dsde.getY() - p.y - selectedModule.getHeight() / 8;
            selectedModule.setLocation(x, y);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Template template = PGLOB.getTemplate();
        
        Image screwNW = template.getScrewNW();
        Image screwNE = template.getScrewNE();
        Image sideN = template.getSideN();
 
        // Bounds of the zone to repaint
        Rectangle clip = new Rectangle(this.getSize());

        // Coordinates
        int x, y;

        int sideN_width = sideN.getWidth(this);
        int sideN_height = sideN.getHeight(this);
        int screwNE_width = screwNE.getWidth(this);

        // START Paint
        if (sideN_width > 0 && sideN_height > 0) {
            y = clip.y;
            for (x = clip.x; x < (clip.x + clip.width); x += sideN_width) {
                g.drawImage(sideN, x, y, this);
            }
        }
        
        g.drawImage(screwNW, clip.x, clip.y, this);
        g.drawImage(screwNE, clip.width - screwNE_width, clip.y, this);
        
    }

    /**
     * @return The message for the corresponding type of module.
     */
    private String getMsgToolTip(){
        String retour = null;
        
        switch (control.getType().toString()) {
        case "OUT":
            retour = "<html>Output module to play sound<br />";
            break;
            
        case "VCOA":
            retour = "<html>Voltage controlled oscillator<br />";
            retour += "to generate a waveform.<br />";
            retour += "We can modulate the frequency by an external signal.<br />";
            break;
            
        case "SCOP":
            retour = "<html>Waveform visualization<br />";
            retour += "like an oscilloscope<br />";
            break;
            
        case "REP":
            retour = "<html>Replicator module<br />";
            retour += "with 1 input to 3 outputs<br />";
            break;
            
        case "MIX":
            retour = "<html>Mixer module<br />";
            retour += "with 4 inputs to 1 output<br />";
            break;
            
        case "EG":
            retour = "<html>Envelope generator module<br />";
            break;
            
        case "VCA":
            retour = "<html>Voltage controlled amplifier<br />";
            retour += "to apply amplification.<br />";
            retour += "We can modulate the amplification by an external signal.<br />";
            break;
            
        case "WN":
            retour = "<html>White noise generator module<br />";
            break;
            
        case "VCFLP":
            retour = "<html>Low-pass voltage controlled filter<br />";
            retour += "We can modulate the cut-off frequency by an external signal.<br />";
            break;
            
        case "VCFHP":
            retour = "<html>High-pass voltage controlled filter<br />";
            retour += "We can modulate the cut-off frequency by an external signal.<br />";
            break;
            
        case "SEQ":
            retour = "<html>Sequencer automatic<br />";
            retour += "To play 8 differents level on output<br />";
            break;
            
        case "KEYB":
            retour = "<html>Piano-style keypad<br />";
            break;
            
        case "REC":
            retour = "<html>Music (or waveform) recorder module<br />";
            retour += "on WAV format<br />";
            break;

        default:
            break;
        }
        retour += "<h4>Size = " + control.getWidthU() + " slots</h4></html>";
        return retour;
    }
}
