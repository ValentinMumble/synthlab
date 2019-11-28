package fr.istic.synthlab.util.presentation;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import fr.istic.synthlab.util.Icons;
import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * The female jack presentation, which is a JLabel.
 * 
 * @author Laurent Legendre
 * 
 */
public class PFemaleJack extends JLabel implements IPFemaleJack {
    private static final long serialVersionUID = 1L;

    /**
     * Icon used to show the FemaleJack.
     */
    private Icon icon;
    /**
     * Controller of the PFemaleJack.
     */
    private ICFemaleJack control;

    /**
     * Constructor of PFemaleJack.
     * 
     * @param control
     *            Control CFemaleJack.
     */
    public PFemaleJack(ICFemaleJack control) {
        this.control = control;
        this.icon = Icons.CONNECTOR_JACK_FEMALE;
        if (this.icon != null) {
            this.setIcon(icon);
            this.setSize(this.icon.getIconWidth(), this.icon.getIconHeight());
            setPreferredSize(getSize());
        }
        this.setAlignmentX(CENTER_ALIGNMENT);

        this.addMouseListener(new PortListener());
    }

    /**
     * Function calls by PCable. Give the point where the PCable must be
     * connected.
     * 
     * @return The middle point of the Jack.
     */
    @Override
    public Point getPointForCable() {
        return SwingUtilities.convertPoint(getParent(), new Point(this.getX()
                + this.getWidth() / 2, this.getY() + this.getHeight() / 2),
                control.getPGLOB().getLayeredPane());
    }

    /**
     * The listener that will tell the GLOB that this jack has been clicked.
     * 
     * @author valentinmumble
     */
    private class PortListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e)) {
                control.removeCable();
            } else {
                control.portTouched();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            control.portHovered();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            control.portExited();
        }
    }

    @Override
    public void showPluggable() {
        setIcon(Icons.CONNECTOR_JACK_FEMALE_OK);
    }

    @Override
    public void showNotPluggable() {
        setIcon(Icons.CONNECTOR_JACK_FEMALE_KO);
    }

    @Override
    public void showNeutral() {
        setIcon(Icons.CONNECTOR_JACK_FEMALE);
    }
}
