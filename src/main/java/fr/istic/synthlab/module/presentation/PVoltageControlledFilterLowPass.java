package fr.istic.synthlab.module.presentation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jsyn.swing.DoubleBoundedRangeModel;

import fr.istic.synthlab.module.abstraction.AVoltageControlledFilterLowPass;
import fr.istic.synthlab.module.control.ICModule;
import fr.istic.synthlab.module.control.ICVoltageControlledFilterLowPass;
import fr.istic.synthlab.module.presentation.component.ConnectorBloc;
import fr.istic.synthlab.module.presentation.component.KnobBloc;
import fr.istic.synthlab.module.presentation.component.TitledBlocGroup;
import fr.istic.synthlab.util.presentation.IPFemaleJack;

/**
 * Presentation for Voltage Controlled Filter Low Pass (#VCFA) module.
 * 
 * @author Florent
 */
public class PVoltageControlledFilterLowPass extends PModule implements
        IPVoltageControlledFilterLowPass {

    /**
     * UID to serialize this object.
     */
    private static final long serialVersionUID = 6818662410993730003L;

    /**
     * model for the cutoff frequency knob
     */
    private DoubleBoundedRangeModel modelCutoff;

    /**
     * model for resonance knob
     */
    private DoubleBoundedRangeModel modelResonance;
    
    /**
     * VCFLP control.
     */
    private ICVoltageControlledFilterLowPass control;

    private IPFemaleJack input;
    private IPFemaleJack inputFM;
    private IPFemaleJack output;

    public PVoltageControlledFilterLowPass(ICVoltageControlledFilterLowPass control) {
        this.control = control;

        input = control.getInput().getPresentation();
        inputFM = control.getFreqModInput().getPresentation();
        output = control.getOutput().getPresentation();

        this.setLayout(new GridBagLayout());

        GridBagConstraints gbcKnobs = new GridBagConstraints(0, 1,
                GridBagConstraints.REMAINDER, 1, 0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, gbcInsets,
                0, 0);
        GridBagConstraints gbcInputs = new GridBagConstraints(0, 2,
                GridBagConstraints.RELATIVE, GridBagConstraints.RELATIVE, 2,
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                gbcInsets, 0, 0);
        GridBagConstraints gbcOutput = new GridBagConstraints(1, 2,
                GridBagConstraints.REMAINDER, GridBagConstraints.RELATIVE, 1.66,
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                gbcInsets, 0, 0);

        this.add(this.createModuleTitle(IPVoltageControlledFilterLowPass.TITLE),
                gbcModuleTitle);
        this.add(this.processKnobs(), gbcKnobs);
        this.add(this.processInputs(), gbcInputs);
        this.add(buildSimpleJackBloc("OUTPUT", output), gbcOutput);
        this.add(this.createModuleBottom(3), gbcBottom);


    }

    /**
     * Wrapper method to build the cutoff frequency & resonance knobs
     * 
     * @return the knobs bloc
     */
    private TitledBlocGroup processKnobs() {
        TitledBlocGroup knobsBlocGroup = new TitledBlocGroup("FREQUENCY");

        modelCutoff = new DoubleBoundedRangeModel("Cutoff", 20000, 
                AVoltageControlledFilterLowPass.MIN_CUTOFF_FREQUENCY_HZ, 
                AVoltageControlledFilterLowPass.MAX_CUTOFF_FREQUENCY_HZ,
                this.control.getCutoffFrequency());

        modelCutoff.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                control.cutoffFrequencyChanged(modelCutoff.getDoubleValue());
            }
        });

        modelResonance = new DoubleBoundedRangeModel("Resonance", 20000, 
                AVoltageControlledFilterLowPass.MIN_RESONANCE, 
                AVoltageControlledFilterLowPass.MAX_RESONANCE,
                this.control.getResonance());

        modelResonance.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                control.resonanceChanged(modelResonance.getDoubleValue());
            }
        });

        KnobBloc cutoffKnob = new KnobBloc(modelCutoff, "Cutoff");
        KnobBloc resonanceKnob = new KnobBloc(modelResonance, "Resonance");

        cutoffKnob.setDecimalFormat(5, 5, 0, 0);
        resonanceKnob.setDecimalFormat(2, 2, 2, 2);

        knobsBlocGroup.add(cutoffKnob);
        knobsBlocGroup.add(resonanceKnob);
        return knobsBlocGroup;
    }


    /**
     * Create a TitledBlocGroup for Inputs populated with theFM input Bloc.
     * 
     * @return The Inputs TitledBlocGroup populated with the FM input Bloc.
     */
    private TitledBlocGroup processInputs() {
        TitledBlocGroup intputsBlocGroup = new TitledBlocGroup("INPUTS");

        ConnectorBloc connectorBlocIn = new ConnectorBloc(input,
                "Signal");
        ConnectorBloc connectorBlocFm = new ConnectorBloc(inputFM,
                "FM");

        intputsBlocGroup.add(connectorBlocIn);
        intputsBlocGroup.add(connectorBlocFm);

        intputsBlocGroup
                .setSize(70, intputsBlocGroup.getPreferredSize().height);
        intputsBlocGroup.setPreferredSize(intputsBlocGroup.getSize());
        intputsBlocGroup.setMinimumSize(intputsBlocGroup.getSize());

        return intputsBlocGroup;
    }
    
    @Override
    public ICModule getController() {
        return this.control;
    }

    /**
     * get size of the module for the glob.
     * 
     * @return size of the module for the glob.
     * 
     */
    @Override
    public int getWidthU() {
        return IPVoltageControlledFilterLowPass.WIDTH_U;
    }

    @Override
    public void updateCutoffFrequency(double freq) {
        modelCutoff.setDoubleValue(freq);
    }

    @Override
    public void updateResonance(double res) {
        modelResonance.setDoubleValue(res);
    }
}
