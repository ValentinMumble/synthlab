package fr.istic.synthlab.module.presentation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jsyn.swing.DoubleBoundedRangeModel;

import fr.istic.synthlab.module.abstraction.AVoltageControlledFilterHighPass;
import fr.istic.synthlab.module.control.ICModule;
import fr.istic.synthlab.module.control.ICVoltageControlledFilterHighPass;
import fr.istic.synthlab.module.presentation.component.ConnectorBloc;
import fr.istic.synthlab.module.presentation.component.KnobBloc;
import fr.istic.synthlab.module.presentation.component.TitledBlocGroup;
import fr.istic.synthlab.util.presentation.IPFemaleJack;


/**
 * Presentation for Voltage Controlled Filter High Pass (#HPF) module.
 * 
 * @author Valentin
 * 
 */
public class PVoltageControlledFilterHighPass extends PModule implements
        IPVoltageControlledFilterHighPass {

    /**
     * UID to serialize this object.
     */
    private static final long serialVersionUID = -4101272317352889890L;

    /**
     * model for the cutoff frequency knob
     */
    private DoubleBoundedRangeModel modelCutoff;

    private ICVoltageControlledFilterHighPass control;
    
    private IPFemaleJack input;
    private IPFemaleJack inputFM;
    private IPFemaleJack output;
    

    /**
     * Constructor.
     * 
     * @param ctrl
     *            ICVoltageControlledFilterHighPass
     */
    public PVoltageControlledFilterHighPass(
            ICVoltageControlledFilterHighPass ctrl) {
        this.control = ctrl;
        this.setLayout(new GridBagLayout());
        
        input = control.getInput().getPresentation();
        inputFM = control.getFreqModInput().getPresentation();
        output = control.getOutput().getPresentation();

        GridBagConstraints gbcKnobs = new GridBagConstraints(0, 1,
                GridBagConstraints.REMAINDER, 1, 0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, gbcInsets,
                0, 0);
        
        GridBagConstraints gbcInputs = new GridBagConstraints(0, 2,
                GridBagConstraints.RELATIVE, GridBagConstraints.RELATIVE, 1,
                1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                gbcInsets, 0, 0);
        
        GridBagConstraints gbcOutput = new GridBagConstraints(1, 2,
                GridBagConstraints.REMAINDER, GridBagConstraints.RELATIVE, 1.66,
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                gbcInsets, 0, 0);

        this.add(
                this.createModuleTitle(IPVoltageControlledFilterHighPass.TITLE),
                gbcModuleTitle);
        this.add(this.processKnobs(), gbcKnobs);
        this.add(this.processInputs(), gbcInputs);
        this.add(buildSimpleJackBloc("OUTPUT", output), gbcOutput);
        this.add(this.createModuleBottom(3), gbcBottom);

    }
    
    
    /**
     * Method to build the cutoff frequency knob.
     * 
     * @return the cutoff frequency knob.
     */
    private TitledBlocGroup processKnobs() {
        TitledBlocGroup knobsBlocGroup = new TitledBlocGroup("FREQUENCY");

        modelCutoff = new DoubleBoundedRangeModel("Cutoff", 20000,
                AVoltageControlledFilterHighPass.MIN_CUTOFF_FREQUENCY_HZ,
                AVoltageControlledFilterHighPass.MAX_CUTOFF_FREQUENCY_HZ,
                this.control.getCutoffFrequency());

        modelCutoff.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                control.cutoffFrequencyChanged(modelCutoff.getDoubleValue());
            }
        });

        KnobBloc gainKnob = new KnobBloc(modelCutoff, "Cutoff");
        gainKnob.setDecimalFormat(5, 5, 0, 0);

        knobsBlocGroup.add(gainKnob);
        
        return knobsBlocGroup;
    }
    
    /**
     * Create a TitledBlocGroup for inputs with signal and FM inputs.
     * 
     * @return The inputs TitledBlocGroup populated with all its inputs Blocs.
     */
    private TitledBlocGroup processInputs() {
        TitledBlocGroup inputsBlocGroup = new TitledBlocGroup("INPUTS");

        ConnectorBloc connectorBlocInSign = new ConnectorBloc(input, "Signal");
        ConnectorBloc connectorBlocInMod = new ConnectorBloc(inputFM, "FM");

        inputsBlocGroup.add(connectorBlocInSign);
        inputsBlocGroup.add(connectorBlocInMod);

        return inputsBlocGroup;
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
        return IPVoltageControlledFilterHighPass.WIDTH_U;
    }

    @Override
    public void updateCutoffFrequency(double freq) {
        modelCutoff.setDoubleValue(freq);
    }
}
