package fr.istic.synthlab.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;

import javax.swing.JPanel;

import fr.istic.synthlab.cable.presentation.IPCable;

/**
 * Color Label.
 * @author Florent
 */
public class ColorLabel extends JPanel {
    /**
     * id.
     */
    private static final long serialVersionUID = -7451677081660403554L;

    /**
     * Color of the label.
     */
    private Color color;

    /**
     * Point a.
     */
    private Point a;

    /**
     * Point b.
     */
    private Point b;

    /**
     * Middle Point.
     */
    private Point midpoint;

    /**
     * if incurved.
     */
    private boolean incurved;
    /**
     * Constructor of the Color Label with a predefined size.
     * @param c
     *          the color of the label
     */
    public ColorLabel(Color c) {
        this(c, new Dimension(40, 20));
    }

    /**
     * Constructor of the Color Label.
     * @param c
     *         the color of the label
     * @param size
     *         the size of the label
     */
    public ColorLabel(Color c, Dimension size) {
        incurved = false;
        a = new Point();
        b = new Point();
        midpoint = new Point();
        a.x = IPCable.CABLE_WIDTH / 2;
        setOpaque(false);
        setColor(c);
        setSize(size);
        setPreferredSize(getSize());
    }

    /**
     * Incurved setter.
     * @param incurved
     */
    public void setIncurved(boolean incurvedCable) {
        this.incurved = incurvedCable;
    }

    /**
     * Incurved getter.
     * @return true if incurved
     */
    public boolean isIncurved() {
        return incurved;
    }

    /**
     * Color setter.
     * @param c
     *      the new color of the label.
     */
    public void setColor(Color c) {
        this.color = c;
    }

    /**
     * Color getter.
     * @return the color of the label.
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Point A getter.
     * @return Point A
     */
    public Point getA() {
        return a;
    }

    /**
     * Point A setter.
     * @param p
     *      the new Point A.
     */     
    public void setA(Point p) {
        a = p;
    }

    /**
     * Point B getter.
     * @return Point B
     */
    public Point getB() {
        return b;
    }

    /**
     * Point B setter.
     * @param p
     *      the new Point B.
     */
    public void setB(Point p) {
        b = p;
    }

    @Override
    public void setSize(Dimension size) {
        super.setSize(size);
        a.y = getHeight() / 2;
        b.x = getWidth() - IPCable.CABLE_WIDTH / 2;
        b.y = getHeight() / 2;
        midpoint = new Point(getWidth() / 2 + a.x, getHeight() / 2 + getWidth()
                / 6);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        Shape cable;
        if (incurved) {
            cable = new QuadCurve2D.Double(a.x, a.y, midpoint.x, midpoint.y,
                    b.x, b.y);
        } else {
            cable = new Line2D.Double(a, b);
        }
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(IPCable.CABLE_WIDTH,
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
        g2d.draw(cable);
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(IPCable.CABLE_WIDTH - 2,
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
        g2d.draw(cable);
        g2d.setStroke(new BasicStroke(IPCable.CABLE_WIDTH / 3,
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
        g2d.setColor(new Color(255, 255, 255, 30));
        g2d.draw(cable);
    }
}
