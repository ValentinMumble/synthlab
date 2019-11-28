package fr.istic.synthlab.module.presentation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jsyn.swing.DoubleBoundedRangeModel;

import fr.istic.synthlab.module.abstraction.AEnvelopeGenerator;
import fr.istic.synthlab.module.control.ICEnvelopeGenerator;
import fr.istic.synthlab.module.control.ICModule;
import fr.istic.synthlab.module.presentation.component.KnobBloc;
import fr.istic.synthlab.module.presentation.component.TitledBlocGroup;
import fr.istic.synthlab.util.presentation.IPFemaleJack;

/**
 * The presentation for the Envelope Generator module
 * 
 * @author valentinmumble
 * @version 1.1
 * 
 */
public class PEnvelopeGenerator extends PModule implements IPEnvelopeGenerator {

    /**
     * UID to serialize this object.
     */
    private static final long serialVersionUID = -7266650921821970734L;

    /**
     * Control of the PMixer.
     */
    private ICEnvelopeGenerator control;

    private IPFemaleJack gate;
    private IPFemaleJack output;

    /**
     * model for the attack knob.
     * 
     * Attack time is the time taken for initial run-up of level from nil to
     * peak, beginning when the key is first pressed.
     */

    private DoubleBoundedRangeModel attackModel;

    /**
     * model for the decay knob.
     * 
     * Decay time is the time taken for the subsequent run down from the attack
     * level to the designated sustain level.
     */
    private DoubleBoundedRangeModel decayModel;

    /**
     * model for the sustain knob.
     * 
     * Sustain level is the level during the main sequence of the sound's
     * duration, until the key is released.
     */
    private DoubleBoundedRangeModel sustainModel;

    /**
     * model for the release knob.
     * 
     * Release time is the time taken for the level to decay from the sustain
     * level to zero after the key is released.
     */
    private DoubleBoundedRangeModel releaseModel;

    /**
     * PEnvelopeGenerator Constructor.
     * 
     * @param control
     *            Control of the EnvelopeGenerator.
     */
    public PEnvelopeGenerator(ICEnvelopeGenerator control) {
        this.control = control;
        this.gate = control.getGate().getPresentation();
        this.output = control.getOutput().getPresentation();

        this.setLayout(new GridBagLayout());

        // Configure GUI constraints for each top-level componenent of this
        // module. Top-level componant are mostly BlocGroups.
        GridBagConstraints gbcKnobs = new GridBagConstraints(0, 1,
                GridBagConstraints.REMAINDER, 1, 0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, gbcInsets,
                0, 0);
        GridBagConstraints gbcGate = new GridBagConstraints(0, 2,
                GridBagConstraints.RELATIVE, GridBagConstraints.RELATIVE, 0.20, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, gbcInsets,
                0, 0);
        GridBagConstraints gbcOutput = new GridBagConstraints(1, 2,
                GridBagConstraints.REMAINDER, GridBagConstraints.RELATIVE, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, gbcInsets,
                0, 0);

        // Build and add main blocs
        this.add(createModuleTitle(IPEnvelopeGenerator.TITLE), gbcModuleTitle);
        this.add(processKnobs(), gbcKnobs);
        this.add(buildSimpleJackBloc("GATE", gate), gbcGate);
        this.add(buildSimpleJackBloc("OUTPUT", output), gbcOutput);
        this.add(this.createModuleBottom(3), gbcBottom);

    }

    /**
     * Create a TitledBlocGroup for Knobs
     * 
     * @return The Knobs TitledBlocGroup for ADSR time control
     */
    private TitledBlocGroup processKnobs() {
        // Create the TitledBlocGroup of Knobs
        TitledBlocGroup knobsBlocGroup = new TitledBlocGroup("");

        this.attackModel = new DoubleBoundedRangeModel("ATTACK", 20000,
                AEnvelopeGenerator.ATTACK_MIN_S,
                AEnvelopeGenerator.ATTACK_MAX_S, control.getAttack());
        this.attackModel.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                control.attackChanged(attackModel.getDoubleValue());
            }
        });

        this.decayModel = new DoubleBoundedRangeModel("DECAY", 20000,
                AEnvelopeGenerator.DECAY_MIN_S, AEnvelopeGenerator.DECAY_MAX_S,
                control.getDecay());
        this.decayModel.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                control.decayChanged(decayModel.getDoubleValue());
            }
        });

        this.sustainModel = new DoubleBoundedRangeModel("SUSTAIN", 20000,
                AEnvelopeGenerator.SUSTAIN_MIN_LEVEL,
                AEnvelopeGenerator.SUSTAIN_MAX_LEVEL * 100,
                control.getSustain());
        this.sustainModel.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                control.sustainChanged(sustainModel.getDoubleValue());
            }
        });

        this.releaseModel = new DoubleBoundedRangeModel("RELEASE", 20000,
                AEnvelopeGenerator.RELEASE_MIN_S,
                AEnvelopeGenerator.RELEASE_MAX_S, control.getRelease());
        this.releaseModel.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                control.releaseChanged(releaseModel.getDoubleValue());
            }
        });

        KnobBloc attackKnob = new KnobBloc(attackModel, "Attack");
        attackKnob.setDecimalFormat(1, 1, 3, 3);
        KnobBloc decayKnob = new KnobBloc(decayModel, "Decay");
        decayKnob.setDecimalFormat(1, 1, 3, 3);
        KnobBloc sustainKnob = new KnobBloc(sustainModel, "Sustain");
        sustainKnob.setDecimalFormat(3, 3, 2, 2);
        KnobBloc releaseKnob = new KnobBloc(releaseModel, "Release");
        releaseKnob.setDecimalFormat(2, 2, 2, 2);

        knobsBlocGroup.add(attackKnob);
        knobsBlocGroup.add(decayKnob);
        knobsBlocGroup.add(sustainKnob);
        knobsBlocGroup.add(releaseKnob);

        return knobsBlocGroup;
    }

    @Override
    public ICModule getController() {
        return control;
    }

    @Override
    public int getWidthU() {
        return IPEnvelopeGenerator.WIDTH_U;
    }

    @Override
    public void updateAttack(double a) {
        attackModel.setDoubleValue(a);
    }

    @Override
    public void updateDecay(double d) {
        decayModel.setDoubleValue(d);
    }

    @Override
    public void updateSustain(double s) {
        sustainModel.setDoubleValue(s);
    }

    @Override
    public void updateRelease(double r) {
        releaseModel.setDoubleValue(r);
    }

}
