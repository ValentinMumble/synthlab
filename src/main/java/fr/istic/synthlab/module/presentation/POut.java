package fr.istic.synthlab.module.presentation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jsyn.swing.DoubleBoundedRangeModel;

import fr.istic.synthlab.module.abstraction.AOut;
import fr.istic.synthlab.module.control.ICOut;
import fr.istic.synthlab.module.presentation.component.KnobBloc;
import fr.istic.synthlab.module.presentation.component.MuteBloc;
import fr.istic.synthlab.module.presentation.component.TitledBlocGroup;
import fr.istic.synthlab.util.presentation.IPFemaleJack;

/**
 * Presentation for OUT module.
 * 
 * @author Laurent Legendre
 * 
 */
public class POut extends PModule implements ActionListener, IPOut {
    
    /**
     * UID to serialize this object.
     */
    private static final long serialVersionUID = 8283144724959152604L;

    /**
     * The model for the attenuation.
     * 
     * This model is used by a knob inside the knobBloc. This class also listen
     * for changes made to this model.
     */
    private DoubleBoundedRangeModel modelAtt;

    /**
     * The bloc which contains the Mute system
     */
    private MuteBloc muteBloc;

    /**
     * Representation of the female jack to plug an input cable in.
     */
    private IPFemaleJack lineIn;

    /**
     * The Control associated to the Presentation.
     */
    private ICOut control;

    /**
     * Constructor.
     * 
     * @param control
     */
    public POut(ICOut control) {
        this.control = control;
        this.setLayout(new GridBagLayout());

        lineIn = control.getLineIn().getPresentation();

        GridBagConstraints gbcKnob = new GridBagConstraints(0, 1,
                GridBagConstraints.REMAINDER, 1, 0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, gbcInsets,
                0, 0);
        GridBagConstraints gbcMute = new GridBagConstraints(0, 2,
                GridBagConstraints.REMAINDER, 1, 0,
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                gbcInsets, 0, 0);
        GridBagConstraints gbcOutput = new GridBagConstraints(0, 3,
                GridBagConstraints.REMAINDER, GridBagConstraints.RELATIVE, 0,
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                gbcInsets, 0, 0);

        // Build and add main blocs
        this.add(this.createModuleTitle(IPOut.TITLE), gbcModuleTitle);
        this.add(this.processKnobs(), gbcKnob);
        this.add(this.processMute(), gbcMute);
        this.add(buildSimpleJackBloc("INPUT", lineIn), gbcOutput);
        this.add(this.createModuleBottom(4), gbcBottom);

    }

    /**
     * Wrapper method to build the coarse knob
     * 
     * @return the coarse knob.
     */
    private TitledBlocGroup processKnobs() {
        // Create the TitledBlocGroup of Knobs
        TitledBlocGroup knobsBlocGroup = new TitledBlocGroup("GAIN");

        modelAtt = new DoubleBoundedRangeModel("ATTENUATION", 10000,
                AOut.ATTENUATION_MIN_DB, AOut.ATTENUATION_MAX_DB,
                this.control.getAttenuation());

        modelAtt.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                control.attenuationChanged(modelAtt.getDoubleValue());
            }
        });

        KnobBloc attKnob = new KnobBloc(modelAtt);
        attKnob.setDecimalFormat(2, 2, 2, 2);
        knobsBlocGroup.add(attKnob);

        return knobsBlocGroup;
    }

    /**
     * Wrapper method to build the mute bloc of this module
     * 
     * @return the mute bloc as JPanel
     */
    private TitledBlocGroup processMute() {
        // Create the TitledBlocGroup of Knobs
        TitledBlocGroup muteBlocGroup = new TitledBlocGroup("MUTE");

        this.muteBloc = new MuteBloc(this);
        muteBlocGroup.add(this.muteBloc);

        // Return the builded muteBloc
        return muteBlocGroup;

    }

    /**
     * Action performed when the muteBloc forward an actionEvent after a click
     * occurs on its Mute button
     */
    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getActionCommand().equals(MuteBloc.ACTION_CMD_TOGGLE_MUTE)) {
            this.control.toggleMute();
        }
    }

    @Override
    public ICOut getController() {
        return this.control;
    }

    @Override
    public void showMuteOn() {
        this.muteBloc.showMuteOn();
    }

    @Override
    public void showMuteOff() {
        this.muteBloc.showMuteOff();
    }

    @Override
    public int getWidthU() {
        return IPOut.WIDTH_U;
    }

    @Override
    public void updateAttenuation(double att) {
        modelAtt.setDoubleValue(att);
    }
}
