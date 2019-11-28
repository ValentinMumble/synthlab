package fr.istic.synthlab.module.presentation.component;

import java.awt.GridBagConstraints;
import java.text.DecimalFormat;

import javax.swing.Icon;
import javax.swing.JLabel;

import com.jsyn.swing.DoubleBoundedRangeModel;

/**
 * GUI component to be used in a PModule.
 * 
 * Represents a knob with an above legend and a current value below.
 * 
 * @author Laurent Legendre
 * 
 */
public class KnobBloc extends Bloc {

    /**
     * UID to serialize this object.
     */
    private static final long serialVersionUID = -6487824442448317459L;

    /**
     * Height in pixel of the legend (Icon or text).
     */
    private static final int LEGEND_HEIGHT = 12;

    private GridBagConstraints gbcLegend;
    private GridBagConstraints gbcKnob;
    private GridBagConstraints gbcValueField;

    /**
     * Format the value.
     */
    private DecimalFormat decimalFormat;

    /**
     * The String that may be used as a legend
     */
    private String legendStr;

    /**
     * The Icon that may be used as a legend
     */
    private Icon legendIcon;

    /**
     * The Knob.
     */
    private CustomRotaryController knob;

    /**
     * The Model for the Knob
     */
    private DoubleBoundedRangeModel model;

    /**
     * The field to display the knob value and set it manually.
     */

    private CustomDoubleBoundedTextField valueField;

    /**
     * Constructor without any legend, but with a value field.
     * 
     * @param model
     */
    public KnobBloc(DoubleBoundedRangeModel model) {
        this(model, true);
    }

    /**
     * Constructor without any legend and with a boolean to enable (or NOT) a
     * value field under the knob
     * 
     * @param model
     * @param b
     */
    public KnobBloc(DoubleBoundedRangeModel model, boolean b) {
        if (b) {
            this.model = model;
            this.knob = new CustomRotaryController(this.model);
            this.decimalFormat = new DecimalFormat();
            this.decimalFormat.setGroupingUsed(false);
            this.decimalFormat.setMinimumFractionDigits(2);
            this.decimalFormat.setMaximumFractionDigits(2);
            this.valueField = new CustomDoubleBoundedTextField(this.model, this.decimalFormat);
            this.valueField.setForeground(DEFAULT_COLOR);
            this.valueField.setFont(DEFAULT_FONT);
            this.setGbcWithoutLegend();
            this.add(this.knob, gbcKnob);
            this.add(this.valueField, gbcValueField);
        } else {
            this.model = model;
            this.knob = new CustomRotaryController(this.model);
            this.setGbcKnobOnly();
            this.add(this.knob, gbcKnob);
        }
    }

    /**
     * Constructor with an Icon as legend and a value field.
     * 
     * @param label
     */
    public KnobBloc(DoubleBoundedRangeModel model, Icon legend) {
        this.model = model;
        this.knob = new CustomRotaryController(this.model);
        this.knob.setOpaque(false);
        this.knob.setBackground(null);
        this.decimalFormat = new DecimalFormat();
        this.decimalFormat.setGroupingUsed(false);
        this.decimalFormat.setMinimumFractionDigits(2);
        this.decimalFormat.setMaximumFractionDigits(2);
        this.valueField = new CustomDoubleBoundedTextField(this.model, this.decimalFormat);
        this.valueField.setForeground(DEFAULT_COLOR);
        this.valueField.setFont(DEFAULT_FONT);
        this.legendIcon = legend;
        this.setGbcWithLegend();
        this.add(new JLabel(this.legendIcon), gbcLegend);
        this.add(this.knob, gbcKnob);
        this.add(this.valueField, gbcValueField);
    }

    /**
     * Constructor with an String as legend and a value field.
     * 
     * @param legend
     */
    public KnobBloc(DoubleBoundedRangeModel model, String legend) {
        this.model = model;
        this.knob = new CustomRotaryController(this.model);
        this.decimalFormat = new DecimalFormat();
        this.decimalFormat.setGroupingUsed(false);
        this.decimalFormat.setMinimumFractionDigits(2);
        this.decimalFormat.setMaximumFractionDigits(2);
        this.valueField = new CustomDoubleBoundedTextField(this.model, this.decimalFormat);
        this.valueField.setForeground(DEFAULT_COLOR);
        this.valueField.setFont(DEFAULT_FONT);
        this.legendStr = legend;
        this.setGbcWithLegend();
        JLabel legendLbl = new JLabel(this.legendStr);
        legendLbl.setFont(Bloc.DEFAULT_FONT);
        legendLbl.setSize(legendLbl.getPreferredSize().width,
                KnobBloc.LEGEND_HEIGHT);
        legendLbl.setPreferredSize(legendLbl.getSize());
        legendLbl.setForeground(DEFAULT_COLOR);
        this.add(legendLbl, gbcLegend);
        this.add(this.knob, gbcKnob);
        this.add(this.valueField, gbcValueField);
    }

    /**
     * Initialize GridBagConstraints for a usage with a knob only.
     */
    private void setGbcKnobOnly() {
        this.gbcKnob = new GridBagConstraints(0, 0,
                GridBagConstraints.REMAINDER, GridBagConstraints.REMAINDER, 0,
                0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                Bloc.DEFAULT_INSETS, 0, 0);
        this.gbcValueField = null;
        this.gbcLegend = null;
    }

    /**
     * Initialize GridBagConstraints for a usage without any legend.
     */
    private void setGbcWithoutLegend() {
        this.gbcKnob = new GridBagConstraints(0, 0,
                GridBagConstraints.REMAINDER, GridBagConstraints.RELATIVE, 0,
                0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                Bloc.DEFAULT_INSETS, 0, 0);
        this.gbcValueField = new GridBagConstraints(0, 1,
                GridBagConstraints.REMAINDER, GridBagConstraints.REMAINDER, 0,
                0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                Bloc.DEFAULT_INSETS, 0, 0);
        this.gbcLegend = null;
    }

    /**
     * Initialize GridBagConstraints for a usage with a String or an Icon as
     * legend.
     */
    private void setGbcWithLegend() {
        this.gbcLegend = new GridBagConstraints(0, 0,
                GridBagConstraints.REMAINDER, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                Bloc.DEFAULT_INSETS, 0, 0);
        this.gbcKnob = new GridBagConstraints(0, 1,
                GridBagConstraints.REMAINDER, GridBagConstraints.RELATIVE, 0,
                0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                Bloc.DEFAULT_INSETS, 0, 0);
        this.gbcValueField = new GridBagConstraints(0, 2,
                GridBagConstraints.REMAINDER, GridBagConstraints.REMAINDER, 0,
                0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                Bloc.DEFAULT_INSETS, 0, 0);
    }




    public void setDecimalFormat(int minIntDigits, int maxIntDigits,
            int minFracDigits, int maxFracDigits) {
        this.decimalFormat.setMinimumIntegerDigits(minIntDigits);
        this.decimalFormat.setMaximumIntegerDigits(maxIntDigits);
        this.decimalFormat.setMinimumFractionDigits(minFracDigits);
        this.decimalFormat.setMaximumFractionDigits(maxFracDigits);
        this.valueField.setDecimalFormat(this.decimalFormat);
    }
}
