package fr.istic.synthlab.module.presentation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jsyn.swing.DoubleBoundedRangeModel;

import fr.istic.synthlab.module.abstraction.AMixer;
import fr.istic.synthlab.module.control.ICMixer;
import fr.istic.synthlab.module.control.ICModule;
import fr.istic.synthlab.module.presentation.component.ConnectorBloc;
import fr.istic.synthlab.module.presentation.component.KnobBloc;
import fr.istic.synthlab.module.presentation.component.TitledBlocGroup;
import fr.istic.synthlab.util.presentation.IPFemaleJack;

/**
 * The presentation class for the Mixer module.
 * 
 * @author Laurent Legendre
 * @version 1.1
 */

public class PMixer extends PModule implements IPMixer {

    /**
     * UID to serialize this object.
     */
    private static final long serialVersionUID = 2530666111267853948L;

    /**
     * Control of the PMixer.
     */
    private ICMixer control;

    /**
     * model for the Attenuator knob #1.
     */
    private DoubleBoundedRangeModel modelAtt1;

    /**
     * model for the Attenuator knob #2.
     */
    private DoubleBoundedRangeModel modelAtt2;

    /**
     * model for the Attenuator knob #3.
     */
    private DoubleBoundedRangeModel modelAtt3;

    /**
     * model for the Attenuator knob #4.
     */
    private DoubleBoundedRangeModel modelAtt4;

    private DoubleBoundedRangeModel[] models = { modelAtt1, modelAtt2,
            modelAtt3, modelAtt4 };

    /**
     * PMixer Constructor.
     *
     * @param control
     *            Control of the Mixer.
     */
    public PMixer(ICMixer control) {
        this.control = control;

        this.setLayout(new GridBagLayout());

        // Configure GUI constraints for each top-level componenent of this
        // module. Top-level componant are mostly BlocGroups.
        GridBagConstraints gbcKnobs = new GridBagConstraints(0, 1,
                GridBagConstraints.REMAINDER, 1, 0,
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                gbcInsets, 0, 0);
        GridBagConstraints gbcInputs = new GridBagConstraints(0, 2,
                GridBagConstraints.RELATIVE, GridBagConstraints.RELATIVE, 1.0,
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                gbcInsets, 0, 0);
        GridBagConstraints gbcOutputs = new GridBagConstraints(1, 2,
                GridBagConstraints.REMAINDER, GridBagConstraints.RELATIVE, 0,
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                gbcInsets, 0, 0);

        // Build and add main blocs
        this.add(this.createModuleTitle(IPMixer.TITLE), gbcModuleTitle);
        this.add(this.processKnobs(), gbcKnobs);
        this.add(this.processInputs(), gbcInputs);
        this.add(this.processOutputs(), gbcOutputs);
        this.add(this.createModuleBottom(3), gbcBottom);

    }

    /**
     * Wrapper method to build the coarse knob.
     * 
     * @return the coarse knob.
     */
    private TitledBlocGroup processKnobs() {
        // Create the TitledBlocGroup of Knobs
        TitledBlocGroup knobsBlocGroup = new TitledBlocGroup("GAIN");

        for (int i = 0; i < models.length; i++) {
            this.models[i] = new DoubleBoundedRangeModel("ATTENUATION", 10000,
                    AMixer.ATTENUATION_MIN_DB, AMixer.ATTENUATION_MAX_DB,
                    this.control.getAttenuation(i + 1));
            final int j = i;
            this.models[i].addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    control.attenuationChanged(j+1, models[j].getDoubleValue());
                }
            });

            KnobBloc knob = new KnobBloc(this.models[i], "" + (i + 1));
            knob.setDecimalFormat(2, 2, 2, 2);
            knobsBlocGroup.add(knob);
        }

        return knobsBlocGroup;
    }

    /**
     * Create a TitledBlocGroup for Inputs populated with the individual input Blocs.
     * 
     * @return The Inputs TitledBlocGroup for Inputs populated with the individual
     *         input Blocs.
     */
    private TitledBlocGroup processInputs() {
        // Create the TitledBlocGroup of Inputs Blocs
        TitledBlocGroup intputsBlocGroup = new TitledBlocGroup("INPUTS");

        for (int i = 0; i < models.length; i++) {

            IPFemaleJack inputConnector = control.getInput(i + 1)
                    .getPresentation();

            ConnectorBloc connectorBloc = new ConnectorBloc(inputConnector,
                    String.valueOf(i + 1));
            intputsBlocGroup.add(connectorBloc);
        }

        return intputsBlocGroup;
    }

    /**
     * Create a TitledBlocGroup for Outputs populated with the single output Bloc.
     * 
     * @return The Inputs TitledBlocGroup for Outputs populated with the single output
     *         Bloc.
     */
    private TitledBlocGroup processOutputs() {

        IPFemaleJack outputConnector = control.getOutput().getPresentation();
        // Create the TitledBlocGroup of Inputs Blocs
        TitledBlocGroup outputsBlocGroup = buildSimpleJackBloc("OUTPUT",
                outputConnector);

        outputsBlocGroup
                .setSize(85, outputsBlocGroup.getPreferredSize().height);
        outputsBlocGroup.setPreferredSize(outputsBlocGroup.getSize());
        outputsBlocGroup.setMinimumSize(outputsBlocGroup.getSize());
        return outputsBlocGroup;
    }

    /**
     * @return The controller of the given Presentation.
     */
    @Override
    public ICModule getController() {
        return control;
    }

    /**
     * @return the width in U of the module presentation.
     */
    @Override
    public int getWidthU() {
        return IPMixer.WIDTH_U;
    }

    @Override
    public void updateAttenuation(int index, double att) {
        models[index].setDoubleValue(att);
    }
}
