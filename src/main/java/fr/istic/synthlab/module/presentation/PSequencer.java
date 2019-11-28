package fr.istic.synthlab.module.presentation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jsyn.swing.DoubleBoundedRangeModel;

import fr.istic.synthlab.module.abstraction.ASequencer;
import fr.istic.synthlab.module.control.ICModule;
import fr.istic.synthlab.module.control.ICSequencer;
import fr.istic.synthlab.module.presentation.component.Bloc;
import fr.istic.synthlab.module.presentation.component.KnobBloc;
import fr.istic.synthlab.module.presentation.component.SimpleBlocGroup;
import fr.istic.synthlab.module.presentation.component.TitledBlocGroup;
import fr.istic.synthlab.util.presentation.IPFemaleJack;

/**
 * Presentation for the Sequencer (#SEQ) module.
 * 
 * @author valentinmumble
 */
public class PSequencer extends PModule implements IPSequencer {

    /**
     * UID to serialize this object.
     */
    private static final long serialVersionUID = -7964609728005654899L;

    /**
     * model for the first pitch knob.
     */
    private DoubleBoundedRangeModel modelPitch1;

    /**
     * model for the second pitch knob.
     */
    private DoubleBoundedRangeModel modelPitch2;

    /**
     * model for the third pitch knob.
     */
    private DoubleBoundedRangeModel modelPitch3;

    /**
     * model for the fourth pitch knob.
     */
    private DoubleBoundedRangeModel modelPitch4;

    /**
     * model for the fifth pitch knob.
     */
    private DoubleBoundedRangeModel modelPitch5;

    /**
     * model for the sixth pitch knob.
     */
    private DoubleBoundedRangeModel modelPitch6;

    /**
     * model for the seventh pitch knob.
     */
    private DoubleBoundedRangeModel modelPitch7;

    /**
     * model for the eighth pitch knob.
     */
    private DoubleBoundedRangeModel modelPitch8;

    /**
     * Sequencer control.
     */
    private ICSequencer control;

    /**
     * The presentation of the gate
     */
    private IPFemaleJack gate;

    /**
     * The presentation of the output.
     */
    private IPFemaleJack output;

    /**
     * The constructor of the Sequencer presentation.
     * 
     * @param control
     */
    public PSequencer(ICSequencer control) {
        this.control = control;
        gate = control.getGate().getPresentation();
        output = control.getOutput().getPresentation();
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbcKnobs = new GridBagConstraints(0, 1,
                GridBagConstraints.REMAINDER, 1, 0,
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                gbcInsets, 0, 0);

        GridBagConstraints gbcControls = new GridBagConstraints(0, 2,
                GridBagConstraints.REMAINDER, GridBagConstraints.RELATIVE, 0,
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                gbcInsets, 0, 0);

        this.add(this.createModuleTitle(IPSequencer.TITLE), gbcModuleTitle);
        this.add(this.processKnobs(), gbcKnobs);
        this.add(this.buildControls(), gbcControls);
        this.add(this.createModuleBottom(3), gbcBottom);

    }

    private Bloc buildControls() {
        Bloc controlsBloc = new SimpleBlocGroup();
        controlsBloc.add(buildSimpleJackBloc("GATE", gate));

        Bloc resetBloc = new SimpleBlocGroup();
        JButton resetButton = new JButton("Reset");
        resetBloc.add(resetButton);
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.resetStepClicked();
            }
        });

        controlsBloc.add(resetBloc);
        controlsBloc.add(buildSimpleJackBloc("OUTPUT", output));
        return controlsBloc;
    }

    /**
     * Wrapper method to build the cutoff frequency & resonance knobs.
     * 
     * @return the knobs bloc
     */
    private Bloc processKnobs() {
        Bloc knobsBloc = new TitledBlocGroup("");
        knobsBloc.setLayout(new BoxLayout(knobsBloc,BoxLayout.PAGE_AXIS));
        
        Bloc knobsLine1 = new SimpleBlocGroup();
        Bloc knobsLine2 = new SimpleBlocGroup();

        modelPitch1 = new DoubleBoundedRangeModel("Pitch1", 100,
                ASequencer.MIN_PITCH_VOLTS, ASequencer.MAX_PITCH_VOLTS,
                control.getPitch(1));
        modelPitch2 = new DoubleBoundedRangeModel("Pitch2", 100,
                ASequencer.MIN_PITCH_VOLTS, ASequencer.MAX_PITCH_VOLTS,
                control.getPitch(2));
        modelPitch3 = new DoubleBoundedRangeModel("Pitch3", 100,
                ASequencer.MIN_PITCH_VOLTS, ASequencer.MAX_PITCH_VOLTS,
                control.getPitch(3));
        modelPitch4 = new DoubleBoundedRangeModel("Pitch4", 100,
                ASequencer.MIN_PITCH_VOLTS, ASequencer.MAX_PITCH_VOLTS,
                control.getPitch(4));
        modelPitch5 = new DoubleBoundedRangeModel("Pitch5", 100,
                ASequencer.MIN_PITCH_VOLTS, ASequencer.MAX_PITCH_VOLTS,
                control.getPitch(5));
        modelPitch6 = new DoubleBoundedRangeModel("Pitch6", 100,
                ASequencer.MIN_PITCH_VOLTS, ASequencer.MAX_PITCH_VOLTS,
                control.getPitch(6));
        modelPitch7 = new DoubleBoundedRangeModel("Pitch7", 100,
                ASequencer.MIN_PITCH_VOLTS, ASequencer.MAX_PITCH_VOLTS,
                control.getPitch(7));
        modelPitch8 = new DoubleBoundedRangeModel("Pitch8", 100,
                ASequencer.MIN_PITCH_VOLTS, ASequencer.MAX_PITCH_VOLTS,
                control.getPitch(8));

        modelPitch1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                control.pitchChanged(1, modelPitch1.getDoubleValue());
            }
        });
        modelPitch2.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                control.pitchChanged(2, modelPitch2.getDoubleValue());
            }
        });
        modelPitch3.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                control.pitchChanged(3, modelPitch3.getDoubleValue());
            }
        });
        modelPitch4.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                control.pitchChanged(4, modelPitch4.getDoubleValue());
            }
        });
        modelPitch5.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                control.pitchChanged(5, modelPitch5.getDoubleValue());
            }
        });
        modelPitch6.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                control.pitchChanged(6, modelPitch6.getDoubleValue());
            }
        });
        modelPitch7.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                control.pitchChanged(7, modelPitch7.getDoubleValue());
            }
        });
        modelPitch8.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                control.pitchChanged(8, modelPitch8.getDoubleValue());
            }
        });

        KnobBloc pitch1Knob = new KnobBloc(modelPitch1);
        pitch1Knob.setDecimalFormat(1, 1, 2, 2);
        KnobBloc pitch2Knob = new KnobBloc(modelPitch2);
        pitch2Knob.setDecimalFormat(1, 1, 2, 2);
        KnobBloc pitch3Knob = new KnobBloc(modelPitch3);
        pitch3Knob.setDecimalFormat(1, 1, 2, 2);
        KnobBloc pitch4Knob = new KnobBloc(modelPitch4);
        pitch4Knob.setDecimalFormat(1, 1, 2, 2);
        KnobBloc pitch5Knob = new KnobBloc(modelPitch5);
        pitch5Knob.setDecimalFormat(1, 1, 2, 2);
        KnobBloc pitch6Knob = new KnobBloc(modelPitch6);
        pitch6Knob.setDecimalFormat(1, 1, 2, 2);
        KnobBloc pitch7Knob = new KnobBloc(modelPitch7);
        pitch7Knob.setDecimalFormat(1, 1, 2, 2);
        KnobBloc pitch8Knob = new KnobBloc(modelPitch8);
        pitch8Knob.setDecimalFormat(1, 1, 2, 2);

        knobsLine1.add(pitch1Knob);
        knobsLine1.add(pitch2Knob);
        knobsLine1.add(pitch3Knob);
        knobsLine1.add(pitch4Knob);
        knobsLine2.add(pitch5Knob);
        knobsLine2.add(pitch6Knob);
        knobsLine2.add(pitch7Knob);
        knobsLine2.add(pitch8Knob);

        knobsBloc.add(knobsLine1);
        knobsBloc.add(knobsLine2);

        return knobsBloc;
    }

    @Override
    public ICModule getController() {
        return this.control;
    }

    /**
     * Get size of the module for the glob.
     * 
     * @return size of the module for the glob.
     * 
     */
    @Override
    public int getWidthU() {
        return IPSequencer.WIDTH_U;
    }

    @Override
    public void updatePitch(int index, double pitch) {
        switch (index) {
        case 1:
            modelPitch1.setDoubleValue(pitch);
            break;
        case 2:
            modelPitch2.setDoubleValue(pitch);
            break;
        case 3:
            modelPitch3.setDoubleValue(pitch);
            break;
        case 4:
            modelPitch4.setDoubleValue(pitch);
            break;
        case 5:
            modelPitch5.setDoubleValue(pitch);
            break;
        case 6:
            modelPitch6.setDoubleValue(pitch);
            break;
        case 7:
            modelPitch7.setDoubleValue(pitch);
            break;
        case 8:
            modelPitch8.setDoubleValue(pitch);
            break;
        default:
            break;
        }
    }
}
