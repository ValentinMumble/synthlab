package fr.istic.synthlab.module.presentation.component;

import java.awt.GridBagConstraints;

import javax.swing.Icon;
import javax.swing.JLabel;

import fr.istic.synthlab.util.presentation.IPFemaleJack;
import fr.istic.synthlab.util.presentation.PFemaleJack;

/**
 * GUI component to be used in a PModule.
 * 
 * Represents a female jack connector with an optional Icon or Text above.
 * 
 * @author Laurent Legendre
 * 
 */
public class ConnectorBloc extends Bloc {

    /**
     * UID to serialize this object.
     */
    private static final long serialVersionUID = 2680955389378668426L;
    
    /**
     * Height in pixel of the legend (Icon or text).
     */
    private static final int LEGEND_HEIGHT = 12;

    /**
     * UID to serialize this object.
     */
    private GridBagConstraints gbcLegend;
    private GridBagConstraints gbcConnector;

    private String legendStr;
    private Icon legendIcon;
    private IPFemaleJack connector;

    /**
     * Constructor without any legend.
     */
    public ConnectorBloc(IPFemaleJack connector) {
        this.connector = connector;
        this.setGbcWithoutLegend();
        this.add((PFemaleJack) this.connector, gbcConnector);
    }

    /**
     * Constructor with an Icon as legend
     * 
     * @param label
     */
    public ConnectorBloc(IPFemaleJack connector, Icon legend) {
        this.connector = connector;
        this.legendIcon = legend;
        this.setGbcWithLegend();
        this.add(new JLabel(this.legendIcon), gbcLegend);
        this.add((PFemaleJack) this.connector, gbcConnector);
    }

    /**
     * Constructor with an String as legend.
     * 
     * @param legend
     */
    public ConnectorBloc(IPFemaleJack connector, String legend) {
        this.connector = connector;
        this.legendStr = legend;
        this.setGbcWithLegend();
        JLabel legendLbl = new JLabel(this.legendStr);
        legendLbl.setFont(DEFAULT_FONT);
        legendLbl.setForeground(DEFAULT_COLOR);

        legendLbl.setSize(legendLbl.getPreferredSize().width,
                ConnectorBloc.LEGEND_HEIGHT);
        legendLbl.setPreferredSize(legendLbl.getSize());
        this.add(legendLbl, gbcLegend);
        this.add((PFemaleJack) this.connector, gbcConnector);
    }

    /**
     * Initialize GridBagConstraints for a usage without any legend.
     */
    private void setGbcWithoutLegend() {
        this.gbcConnector = new GridBagConstraints(0, 0,
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
                GridBagConstraints.REMAINDER, GridBagConstraints.RELATIVE, 0,
                0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                Bloc.DEFAULT_INSETS, 0, 0);
        this.gbcConnector = new GridBagConstraints(0, 1,
                GridBagConstraints.REMAINDER, GridBagConstraints.REMAINDER, 0,
                0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                Bloc.DEFAULT_INSETS, 0, 0);
    }
}
