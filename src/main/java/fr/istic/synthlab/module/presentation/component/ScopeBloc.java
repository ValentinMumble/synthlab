package fr.istic.synthlab.module.presentation.component;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

import com.jsyn.scope.swing.AudioScopeView;
import com.jsyn.swing.ExponentialRangeModel;

/**
 * GUI component to be used in a PModule.
 * 
 * Represents an oscilloscope.
 * 
 * @author Laurent Legendre
 * 
 */
public class ScopeBloc extends Bloc {

    /**
     * UID to serialize this object.
     */
    private static final long serialVersionUID = 2680955389378668426L;

    /**
     * Constructor.
     */
    public ScopeBloc(AudioScopeView scopeView, ExponentialRangeModel rangeModel) {
        this.setLayout(new OverlayLayout(this));
        this.add(scopeView);
        JPanel axisPanel = new Axis(rangeModel);
        // Set the background to black
        scopeView.getComponent(0).setBackground(Color.BLACK);
        // Set the wave color to white
        scopeView.getProbeViews()[0].getWaveTraceView().setColor(Color.WHITE);
        // Disable the autoscaling
        scopeView.getProbeViews()[0].getWaveTraceView().getAutoButtonModel()
                .setSelected(false);
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(7, 2, 0, 1),
                BorderFactory.createLineBorder(Color.WHITE)));
        this.add(axisPanel, 0);
    }

}
