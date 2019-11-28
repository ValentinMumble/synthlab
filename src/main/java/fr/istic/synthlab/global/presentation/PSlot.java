package fr.istic.synthlab.global.presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DragSourceMotionListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import fr.istic.synthlab.global.control.CSlot;
import fr.istic.synthlab.module.control.ICModule;
import fr.istic.synthlab.module.presentation.PModule;

/**
 * Location in the grid.
 * 
 * @author Jonathan
 */
public class PSlot extends JPanel {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -2663333760675116472L;

    // Position x/y dans la grille (premiere case : 0/0)
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(CSlot.class
            .getName());
    /**
     * Control of the Slot.
     * 
     * @category PAC
     */
    private CSlot control;

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
     * Remember the module presentation we are moving.
     * 
     * @category DnD
     */
    private PModule selectedModule;

    /**
     * DropTargetDropEvent that remember the final event for DnD.
     * 
     * @category DnD
     */
    private DropTargetDropEvent theFinalEvent;

    /**
     * Remember gbc, for cases when we have modules that need more than one
     * Slot.
     * 
     * @category MultiSlots
     */
    private GridBagConstraints gbc;

    /**
     * Constructor of Slot presentation.
     * 
     * @category Constructor
     * @param control
     *            Control of the Slot.
     */
    public PSlot(final CSlot control) {
        this.setBackground(new Color(60, 60, 65));
        this.setLayout(new BorderLayout());
        this.control = control;

        // Initialisation du Drag and Drop
        myDSL = new MyDragSourceListener();
        ds = new DragSource();
        ds.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_MOVE,
                new MyDragGestureListener());
        myDSML = new MyDragSourceMotionListener();
        ds.addDragSourceMotionListener(myDSML);

        new DropTarget(this, new MyDropTargetListener());
    }

    /**
     * @return the controller of Slot.
     */
    public final CSlot getController() {
        return control;
    }

    /**
     * Add a module in the panel.
     * 
     * @category DnD
     */
    public final void ajouterModule() {
        this.add((JPanel) control.getICModule().getPresentation(),
                BorderLayout.CENTER);
    }

    /**
     * Display the border of an Slot in green.
     * 
     * @category DnD
     */
    public final void c2p_showAjoutable() {
        this.setBorder(BorderFactory.createLineBorder(new Color(51, 102, 0), 1));
        repaint();
    }

    /**
     * Display the border of an Slot in red.
     * 
     * @category DnD
     */
    public final void c2p_showNonAjoutable() {
        this.setBorder(BorderFactory.createLineBorder(new Color(102, 0, 0), 1));
        repaint();
    }

    /**
     * Display the border of an Slot in it's original color.
     * 
     * @category DnD
     */
    public final void c2p_showNeutre() {
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        repaint();
    }

    /**
     * Called when the DnD begins.
     * 
     * @category DnD
     * @param cModule
     *            module we are moving.
     */
    public final void c2p_debutDnDOK(final ICModule cModule) {
        selectedModule = (PModule) cModule.getPresentation();
        ds.startDrag(theInitialEvent, DragSource.DefaultMoveDrop,
                (Transferable) selectedModule, myDSL);

        Point p = control.getPGLOB().getLayeredPane()
                .getLocationOnScreen();
        int x = MouseInfo.getPointerInfo().getLocation().x - p.x
                - selectedModule.getWidth() / 2;
        int y = MouseInfo.getPointerInfo().getLocation().y - p.y
                - selectedModule.getHeight() / 8;
        selectedModule.setLocation(x, y);

        control.getPGLOB().getLayeredPane()
                .add(selectedModule, 0, 0);
    }

    /**
     * Called when the drop success.
     * 
     * @category DnD
     */
    public final void c2p_dropOK() {
        theFinalEvent.acceptDrop(DnDConstants.ACTION_MOVE);
        theFinalEvent.getDropTargetContext().dropComplete(true);
    }

    /**
     * Called when the drop success on the other panel corresponding.
     * 
     * @category DnD
     */
    public final void c2p_dropOK_from_other() {
        ajouterModule();
        control.myResize();
    }

    /**
     * Called when the drop failed.
     * 
     * @category DnD
     */
    public final void c2p_dropKO() {
        theFinalEvent.rejectDrop();
    }

    /**
     * Listener for the source of the drag (delete the drag at the end) .
     * 
     * @category DnD
     * @author Jonathan
     */
    class MyDragSourceListener implements DragSourceListener {
        @Override
        public void dragDropEnd(DragSourceDropEvent e) {
            control.p2c_dragDropEnd(e.getDropSuccess(),
                    selectedModule.getController());
            control.myResize();
            control.getPGLOB().getLayeredPane().validate();
            control.getPGLOB().getLayeredPane().repaint();
            control.getPGLOB().updatePresentationCables();
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
            if (control.dragable()) {
                theInitialEvent = dge;
                control.p2c_debutDnD(control.getICModule());
                control.myResize();
            }
        }
    }

    /**
     * Listener for the drag target (drop).
     * 
     * @category DnD
     * @author Jonathan
     */
    class MyDropTargetListener implements DropTargetListener {
        @Override
        public void dragEnter(DropTargetDragEvent e) {
            try {
                selectedModule = (PModule) e.getTransferable().getTransferData(
                        e.getCurrentDataFlavors()[0]);
                control.p2c_dragEnter(selectedModule.getController());

            } catch (UnsupportedFlavorException e1) {
                LOGGER.info("UnsupportedFlavorException");
            } catch (IOException e1) {
                LOGGER.info("IOException");
            }
        }

        @Override
        public void dragExit(DropTargetEvent dte) {
            control.p2c_dragExit();
        }

        @Override
        public void dragOver(DropTargetDragEvent dtde) {
        }

        @Override
        public void drop(DropTargetDropEvent e) {
            theFinalEvent = e;
            if (selectedModule != null) {
                control.p2c_drop(selectedModule.getController());
            }
        }

        @Override
        public void dropActionChanged(DropTargetDragEvent dtde) {
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
            Point p = control.getPGLOB().getLayeredPane()
                    .getLocationOnScreen();
            int x = dsde.getX() - p.x - selectedModule.getWidth() / 2;
            int y = dsde.getY() - p.y - selectedModule.getHeight() / 8;
            selectedModule.setLocation(x, y);
            control.myResize();
            control.getPGLOB().updatePresentationCables();
        }
    }

    /**
     * Setter of gridBagConstraint.
     * 
     * @category MultiSlots
     * @param ngbc
     *            The new gbc to remember
     */
    public final void setGbc(final GridBagConstraints ngbc) {
        this.gbc = ngbc;
    }

    /**
     * Getter of gridBagConstraint we have in memory.
     * 
     * @category DnD
     * @return the gbc in memory.
     */
    public final GridBagConstraints getGbc() {
        return gbc;
    }
}
