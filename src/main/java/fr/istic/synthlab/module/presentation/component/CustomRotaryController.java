package fr.istic.synthlab.module.presentation.component;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.BoundedRangeModel;

import com.jsyn.swing.RotaryController;

import fr.istic.synthlab.util.Icons;
import fr.istic.synthlab.util.Util;

public class CustomRotaryController extends RotaryController {

    private static final long serialVersionUID = 3445719604913501115L;

    /**
     * Antialiasing setting for gd2
     */
    private final static RenderingHints AALIAS = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    /**
     * Image to use for the Knob.
     */
    private final static Image KNOB = Util.iconToImage(Icons.KNOB);

    /**
     * Constructor with a model.
     * 
     * @param arg0
     */
    public CustomRotaryController(BoundedRangeModel arg0) {
        super(arg0);
        this.setOpaque(false);
        this.setSize(60, 60);
        this.setPreferredSize(this.getSize());
        this.setMinimumSize(this.getSize());
        this.setStyle(Style.LINE);

    }

    @Override
    public void drawKnob(Graphics g, int x, int y, int radius, double angle) {
        if (g instanceof Graphics2D) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setBackground(getParent().getBackground());
            g2d.addRenderingHints(AALIAS);
        }
        g.drawImage(KNOB, 0, 0, this);

        drawIndicator(g, x, y, radius, angle);
    }

    @Override
    public void drawIndicator(Graphics g, int x, int y, int radius, double angle) {
        if (g instanceof Graphics2D) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(2));
        }
        int iMax = (int) (radius * Math.sin(angle));
        int jMax = (int) (radius * Math.cos(angle));
        int iMin = (int) ((radius * Math.sin(angle)) / 1.1);
        int jMin = (int) ((radius * Math.cos(angle)) / 1.1);
        g.setColor(Color.RED);
        g.drawLine(y + iMin, y - jMin, y + iMax, y - jMax);
    }
}
