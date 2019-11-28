package fr.istic.synthlab.module.presentation.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jsyn.swing.DoubleBoundedRangeModel;

public class Axis extends JPanel {

    /**
     * UID to serialize this object.
     */
    private static final long serialVersionUID = 3697295913353288890L;
    
    private DecimalFormat df;
    private DoubleBoundedRangeModel model;

    private JLabel top;
    private JLabel midpoint;
    private JLabel bottom;

    public Axis(DoubleBoundedRangeModel model) {
        this.model = model;
        this.setLayout(new BorderLayout());
        this.setOpaque(false);
        df = new DecimalFormat("#.#");
        top = new JLabel();
        top.setForeground(Color.WHITE);
        midpoint = new JLabel("0");
        midpoint.setForeground(Color.WHITE);
        bottom = new JLabel();
        bottom.setForeground(Color.WHITE);
        add(top, BorderLayout.NORTH);
        add(midpoint, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        String value = df.format(model.getDoubleValue());
        top.setText("+" + value + "V");
        bottom.setText("-" + value + "V");
    }

}
