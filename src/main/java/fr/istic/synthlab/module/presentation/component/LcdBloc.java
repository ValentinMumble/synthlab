package fr.istic.synthlab.module.presentation.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;

import fr.istic.synthlab.util.Fonts;

/**
 * GUI component to be used in a PModule.
 * 
 * Represents an LCD with the unit of measure next to it
 * 
 * @author Laurent Legendre
 * 
 */
public class LcdBloc extends Bloc {

    /**
     * UID to serialize this object.
     */
    private static final long serialVersionUID = 6195786964370171040L;
    private GridBagConstraints gbcLcd;
    private GridBagConstraints gbcUnit;
    private static final int MAX_INTEGER = 100;
    private static final int MAX_FRACTION = 100;
    /**
     * Format the value.
     */
    private DecimalFormat decimalFormat;

    /**
     * The unit of measure String to display next to the LCD.
     */
    private String uom = "N/A";

    /**
     * The LCD label.
     */
    private JLabel lcdLabel;

    /**
     * The Unit of Measure label
     */
    private JLabel uomLabel;

    /**
     * Default Constructor.
     *
     */
    public LcdBloc() {
        this.decimalFormat = new DecimalFormat();
        this.decimalFormat.setGroupingUsed(false);
        this.decimalFormat.setMinimumIntegerDigits(1);
        this.decimalFormat.setMaximumIntegerDigits(1);
        this.decimalFormat.setMinimumFractionDigits(1);
        this.decimalFormat.setMaximumFractionDigits(1);
        this.setGbcWithoutUom();
        this.configureLcd();
        this.add(this.lcdLabel, gbcLcd);
    }

    /**
     * Constructor.
     *
     * @param uom
     */
    public LcdBloc(String uom) {
        this();
        this.uom = uom;
        this.setGbcWithUom();
        this.configureUom();
        this.add(this.lcdLabel, gbcLcd);
        this.add(this.uomLabel, gbcUnit);

    }
    
    /**
     * Initialize GridBagConstraints for a single LCD
     */
    private void setGbcWithoutUom() {
        this.gbcLcd = new GridBagConstraints(0, 0, GridBagConstraints.REMAINDER,
                GridBagConstraints.REMAINDER, 0, 0,
                GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL,
                Bloc.DEFAULT_INSETS, 0, 0);
        this.gbcUnit = null;
    }

    /**
     * Initialize GridBagConstraints for a usage with a LCD next to its UoM
     */
    private void setGbcWithUom() {
        this.gbcLcd = new GridBagConstraints(0, 0, GridBagConstraints.RELATIVE,
                GridBagConstraints.REMAINDER, 0, 0,
                GridBagConstraints.PAGE_START, GridBagConstraints.HORIZONTAL,
                Bloc.DEFAULT_INSETS, 0, 0);
        this.gbcUnit = new GridBagConstraints(1, 0,
                GridBagConstraints.REMAINDER, GridBagConstraints.REMAINDER, 0,
                0, GridBagConstraints.PAGE_END, GridBagConstraints.NONE,
                Bloc.DEFAULT_INSETS, 0, 0);
    }

    private void configureLcd() {
        this.lcdLabel = new JLabel();
        this.lcdLabel.setFont(Fonts.LCD_DIGITAL7_MONO.deriveFont(Font.BOLD));
        this.resize();
        this.lcdLabel.setHorizontalAlignment(JLabel.CENTER);
        this.lcdLabel.setVerticalAlignment(JLabel.CENTER);
        this.lcdLabel.setForeground(DEFAULT_COLOR);
        this.lcdLabel
                .setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        this.lcdLabel.setBackground(Color.BLACK);
        this.lcdLabel.setOpaque(true);

    }

    private void configureUom() {
        this.uomLabel = new JLabel();
        this.uomLabel.setText(this.uom);
        this.uomLabel.setSize(this.uomLabel.getPreferredSize());
        this.uomLabel.setPreferredSize(this.uomLabel.getSize());
        this.uomLabel.setHorizontalAlignment(JTextField.CENTER);
        this.uomLabel.setForeground(DEFAULT_COLOR);
    }

    public void setDecimalFormat(int minIntDigits, int maxIntDigits,
            int minFracDigits, int maxFracDigits) {
        this.decimalFormat.setMinimumIntegerDigits(minIntDigits);
        this.decimalFormat.setMaximumIntegerDigits(maxIntDigits);
        this.decimalFormat.setMinimumFractionDigits(minFracDigits);
        this.decimalFormat.setMaximumFractionDigits(maxFracDigits);
        this.resize();
    }

    public void setLcdValue(double value) {
        String formattedValue = decimalFormat.format(value);
        this.lcdLabel.setText(formattedValue);
    }
    
    private static int calcNbDigits(DecimalFormat decimalFormat) {
        int NbIntDigits = Math.min(Math.max(decimalFormat.getMaximumIntegerDigits(), 0), MAX_INTEGER);
        int NbFracDigits = Math.min(Math.max(decimalFormat.getMaximumFractionDigits(), 0), MAX_FRACTION);

        return NbIntDigits + NbFracDigits;
    }
    
    private void resize() {
        // +10 : Handle the space taken by the "minus" sign
        this.lcdLabel.setSize(calcNbDigits(this.decimalFormat)*10 + 10, 23);
        this.lcdLabel.setPreferredSize(this.lcdLabel.getSize());
    }

}
