package fr.istic.synthlab.module.presentation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jsyn.scope.swing.AudioScopeView;
import com.jsyn.swing.ExponentialRangeModel;

import fr.istic.synthlab.module.control.ICModule;
import fr.istic.synthlab.module.control.ICOscilloscope;
import fr.istic.synthlab.module.presentation.component.Bloc;
import fr.istic.synthlab.module.presentation.component.KnobBloc;
import fr.istic.synthlab.module.presentation.component.ScopeBloc;
import fr.istic.synthlab.module.presentation.component.TitledBlocGroup;
import fr.istic.synthlab.util.presentation.IPFemaleJack;

/**
 * The presentation class for the Oscilloscope module
 * 
 * @author Valentin
 * 
 */
public class POscilloscope extends PModule implements IPOscilloscope {

    /**
     * UID to serialize this object.
     */
    private static final long serialVersionUID = -3010918641658027334L;

    private ICOscilloscope control;

    private IPFemaleJack input;
    private IPFemaleJack output;
    private AudioScopeView scopeView;
    private ExponentialRangeModel rangeModel;

    public POscilloscope(ICOscilloscope c) {
        control = c;
        input = control.getInput().getPresentation();
        output = control.getOutput().getPresentation();
        scopeView = control.getScope().getView();

        this.setLayout(new GridBagLayout());

        GridBagConstraints gbcScope = new GridBagConstraints(0, 1,
                GridBagConstraints.REMAINDER, 1, 0,
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                gbcInsets, 0, 0);
        GridBagConstraints gbcInput = new GridBagConstraints(0, 2, 1,
                GridBagConstraints.RELATIVE, 1.0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, gbcInsets,
                0, 0);
        GridBagConstraints gbcOutput = new GridBagConstraints(1, 2,
                GridBagConstraints.RELATIVE, GridBagConstraints.RELATIVE, 1.0,
                0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                gbcInsets, 0, 0);
        GridBagConstraints gbcRange = new GridBagConstraints(2, 2,
                GridBagConstraints.REMAINDER, GridBagConstraints.RELATIVE,
                0.66, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                gbcInsets, 0, 0);

        this.add(createModuleTitle(IPOscilloscope.TITLE), gbcModuleTitle);
        this.add(buildScopeBloc(), gbcScope);
        this.add(buildInput(), gbcInput);
        this.add(buildOutput(), gbcOutput);
        this.add(buildRange(), gbcRange);
        this.add(this.createModuleBottom(3), gbcBottom);

    }

    private Bloc buildInput() {
        return buildSimpleJackBloc("INPUT", input);
    }

    private Bloc buildOutput() {
        return buildSimpleJackBloc("OUTPUT", output);
    }

    private Bloc buildRange() {
        TitledBlocGroup knobsBlocGroup = new TitledBlocGroup("RANGE");
        KnobBloc rangeKnob = new KnobBloc(rangeModel, false);
        knobsBlocGroup.add(rangeKnob);
        return knobsBlocGroup;
    }

    /**
     * Wrapper method to build the scope bloc
     * 
     * @return the scope bloc as JPanel
     */
    private JPanel buildScopeBloc() {
        rangeModel = scopeView.getProbeViews()[0].getWaveTraceView()
                .getVerticalRangeModel();
        rangeModel.setDoubleValue(control.getRange());

        rangeModel.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                control.rangeChanged(rangeModel.getDoubleValue());
            }
        });
        return new ScopeBloc(scopeView, rangeModel);
    }

    @Override
    public ICModule getController() {
        return control;
    }

    @Override
    public int getWidthU() {
        return IPOscilloscope.WIDTH_U;
    }

    @Override
    public void updateRange(double range) {
        rangeModel.setDoubleValue(range);
    }

}
