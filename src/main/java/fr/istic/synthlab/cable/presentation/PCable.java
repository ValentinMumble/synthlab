package fr.istic.synthlab.cable.presentation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.QuadCurve2D;

import javax.swing.JPanel;

import fr.istic.synthlab.cable.control.ICCable;

/**
 * Cable Presentation.
 *
 * @author Florent
 */
public class PCable extends JPanel implements IPCable {

    /**
     * UID of the class.
     */
    private static final long serialVersionUID = -1848894749717019527L;

    /**
     * Cable controller.
     */
    private ICCable control;

    /**
     * Point a.
     */
    private Point a;

    /**
     * Point b.
     */
    private Point b;

    /**
     * Midpoint of the curve.
     */
    private Point midpoint;

    /**
     * Midpoint of the shadow curve.
     */
    private Point shadowMidpoint;

    /**
     * Color of the cable.
     */
    private Color color;

    /**
     * PCable Constructor.
     *
     * @param controleCable
     *            control of the PCable
     */
    public PCable(final ICCable controleCable) {
        control = controleCable;
        Point newA = control.getJack1().getPresentation().getPointForCable();
        a = newA;
        b = newA;
        midpoint = newA;
        shadowMidpoint = newA;
        color = control.getPGLOB().getCableColor();
        this.control.setColor(color);
        setVisible(true);
        setLayout(null);
    }

    /**
     * Get the controller.
     *
     * @return {@link ICCable}
     */
    @Override
    public final ICCable getController() {
        return control;
    }

    /**
     * Update the position of the point b and call repaint().
     *
     * @param newB
     *            new Point b
     */
    @Override
    public final void updatePos(final Point newB) {
        b = newB;
        updateCurvePoints();
        revalidate();
        repaint();
    }

    /**
     * Update the curves midpoints.
     */
    private void updateCurvePoints() {
        int w, h = 0;
        if (a.x < b.x) {
            w = b.x - a.x;
        } else {
            w = a.x - b.x;
        }
        if (a.y < b.y) {
            h = b.y - a.y;
        } else {
            h = a.y - b.y;
        }
        midpoint = new Point(w / 2 + Math.min(a.x, b.x), h + w / 8
                + Math.min(a.y, b.y));
        shadowMidpoint = new Point(w / 2 + Math.min(a.x, b.x), h + w / 4
                + Math.min(a.y, b.y));
    }

    /**
     * Update the position of the two points of the cable and call repaint().
     */
    @Override
    public void updateAndValidate() {
        if (control.getJack1() != null && control.getJack2() != null) {
            a = control.getJack1().getPresentation().getPointForCable();
            b = control.getJack2().getPresentation().getPointForCable();
            setSize(control.getPGLOB().getLayeredPane()
                    .getSize());
            setPreferredSize(getSize());
            updateCurvePoints();
        }
        color = control.getColor();
        revalidate();
        repaint();
    }

    /**
     * Paint the PCable as a curve with the drop proportional to the length of
     * the cable.
     *
     * @param g
     *            Graphics
     */
    @Override
    public final void paint(final Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        QuadCurve2D curve = new QuadCurve2D.Double(a.x, a.y, midpoint.x,
                midpoint.y, b.x, b.y);
        QuadCurve2D shadowCurve = new QuadCurve2D.Double(a.x, a.y,
                shadowMidpoint.x, shadowMidpoint.y, b.x, b.y);

        // Multiple shadow curve strokes with different width & color each time
        g2d.setStroke(new BasicStroke(CABLE_WIDTH, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_BEVEL));
        g2d.setColor(new Color(0, 0, 0, 40));
        g2d.draw(shadowCurve);
        g2d.setStroke(new BasicStroke(CABLE_WIDTH / 2, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_BEVEL));
        g2d.setColor(new Color(0, 0, 0, 20));
        g2d.draw(shadowCurve);
        g2d.setStroke(new BasicStroke(CABLE_WIDTH / 4, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_BEVEL));
        g2d.setColor(new Color(0, 0, 0, 10));
        g2d.draw(shadowCurve);

        // Draw the outline in black
        g2d.setStroke(new BasicStroke(CABLE_WIDTH, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_BEVEL));
        g2d.setColor(Color.BLACK);
        g2d.draw(curve);

        // Draw the cable with its actual color
        g2d.setStroke(new BasicStroke(CABLE_WIDTH - 2, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_BEVEL));
        g2d.setColor(color);
        g2d.draw(curve);

        // Inner reflection over the cable
        g2d.setStroke(new BasicStroke(CABLE_WIDTH / 3, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_BEVEL));
        g2d.setColor(new Color(255, 255, 255, 30));
        g2d.draw(curve);

    }
}
