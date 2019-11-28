package fr.istic.synthlab.module.presentation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;

import fr.istic.synthlab.module.control.ICModule;
import fr.istic.synthlab.module.control.ICReplicator;
import fr.istic.synthlab.module.presentation.component.TitledBlocGroup;
import fr.istic.synthlab.util.presentation.IPFemaleJack;

/**
 * The presentation class for the Replicator module
 * 
 * @author Valentin
 * 
 */
public class PReplicator extends PModule implements IPReplicator {

    /**
     * UID to serialize this object.
     */
    private static final long serialVersionUID = 6235540728719102341L;

    private ICReplicator control;

    private IPFemaleJack input;
    private IPFemaleJack output1;
    private IPFemaleJack output2;
    private IPFemaleJack output3;

    public PReplicator(ICReplicator c) {
        control = c;
        input = control.getInput().getPresentation();
        output1 = control.getOutput1().getPresentation();
        output2 = control.getOutput2().getPresentation();
        output3 = control.getOutput3().getPresentation();

        this.setLayout(new GridBagLayout());

        GridBagConstraints gbcInput = new GridBagConstraints(0, 1,
                GridBagConstraints.REMAINDER, 1, 0,
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                gbcInsets, 0, 0);
        GridBagConstraints gbcOutputs = new GridBagConstraints(0, 2,
                GridBagConstraints.REMAINDER, GridBagConstraints.RELATIVE, 0,
                4.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                gbcInsets, 0, 0);

        this.add(createModuleTitle(IPReplicator.TITLE), gbcModuleTitle);
        this.add(buildSimpleJackBloc("INPUT", input), gbcInput);
        TitledBlocGroup outputsBloc = buildSimpleJackBloc("OUTPUTS", output1,
                output2, output3);
        outputsBloc.setLayout(new BoxLayout(outputsBloc, BoxLayout.PAGE_AXIS));
        this.add(outputsBloc, gbcOutputs);
        this.add(this.createModuleBottom(3), gbcBottom);

    }

    @Override
    public ICModule getController() {
        return control;
    }

    @Override
    public int getWidthU() {
        return IPReplicator.WIDTH_U;
    }

}
