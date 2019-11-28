package fr.istic.synthlab.module.presentation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jsyn.swing.DoubleBoundedRangeModel;

import fr.istic.synthlab.module.control.ICModule;
import fr.istic.synthlab.module.control.ICVoltageControlledAmplifier;
import fr.istic.synthlab.module.presentation.component.ConnectorBloc;
import fr.istic.synthlab.module.presentation.component.KnobBloc;
import fr.istic.synthlab.module.presentation.component.TitledBlocGroup;
import fr.istic.synthlab.util.presentation.IPFemaleJack;


/**
 * Presentation for VCA module.
 * 
 * @author Favereau
 */
public class PVoltageControlledAmplifier extends PModule implements IPVoltageControlledAmplifier {

    /**
     * UID to serialize this object.
     */
    private static final long serialVersionUID = -8551812805029878128L;

    /**
     * model for knobs to set amplification
     */
    private DoubleBoundedRangeModel modelGain;

    /**
     * VCA control.
     */
    private ICVoltageControlledAmplifier control;
    
    private IPFemaleJack inputSig;
    private IPFemaleJack inputMod;
    private IPFemaleJack output;
    

    /**
     * Constructor.
     * 
     * @param ctrl
     *            ICVoltageControlledAmplifier.
     */
    public PVoltageControlledAmplifier(ICVoltageControlledAmplifier ctrl){
        this.control = ctrl;
        this.setLayout(new GridBagLayout());
        
        // Get all connector's Presentation
        inputSig = control.getPortSignIn().getPresentation();
        inputMod = control.getPortAMIn().getPresentation();
        output = control.getPortSignOut().getPresentation();

        // Order of the parameter :
        // (int gridx, int gridy, int gridwidth, int gridheight, double weightx,
        // double weighty, int anchor, int fill, Insets insets, int ipadx, int
        // ipady)
        
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

        // Build and add main blocs
        this.add(this.createModuleTitle(IPVoltageControlledAmplifier.TITLE),
                gbcModuleTitle);
        
        this.add(this.processKnobs(), gbcKnobs);
        this.add(this.processInputs(), gbcInputs);
        this.add(buildSimpleJackBloc("OUTPUT", output), gbcOutput);
        this.add(this.createModuleBottom(3), gbcBottom);

    }
    
    
    /**
     * Method to build the amplification knob.
     * 
     * @return the amp knob.
     */
    private TitledBlocGroup processKnobs() {
        // Create the TitledBlocGroup of Knobs
        TitledBlocGroup knobsBlocGroup = new TitledBlocGroup("GAIN");

        modelGain = new DoubleBoundedRangeModel("Gain", 900, -30, 60,
                this.control.getAmplification());

        modelGain.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                control.amplificationChange(modelGain.getDoubleValue());
            }
        });

        KnobBloc gainKnob = new KnobBloc(modelGain);
        gainKnob.setDecimalFormat(2, 2, 2, 2);
        knobsBlocGroup.add(gainKnob);
        
        return knobsBlocGroup;
    }
    
    /**
     * Create a TitledBlocGroup for inputs with signal and AM inputs.
     * 
     * @return The inputs TitledBlocGroup populated with all its inputs Blocs.
     */
    private TitledBlocGroup processInputs() {
        // Create the TitledBlocGroup of inputs Blocs
        TitledBlocGroup inputsBlocGroup = new TitledBlocGroup("INPUTS");

        // Create a ConnectorBloc with a legend (Icon) for each connector's
        // Presentation
        ConnectorBloc connectorBlocInSign = new ConnectorBloc(inputSig, "Signal");
        ConnectorBloc connectorBlocInMod = new ConnectorBloc(inputMod, "AM");

        // Add each ConnectorBloc to this blocGroup
        inputsBlocGroup.add(connectorBlocInSign);
        inputsBlocGroup.add(connectorBlocInMod);

        // Return this blocGroup with populated ConnectorBloc
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
        return IPVoltageControlledAmplifier.WIDTH_U;
    }

    @Override
    public void updateAmplification() {
        this.modelGain.setDoubleValue(control.getAmplification());
        
    }
}
