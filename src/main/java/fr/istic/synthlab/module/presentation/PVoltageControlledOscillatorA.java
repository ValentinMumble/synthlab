package fr.istic.synthlab.module.presentation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jsyn.swing.DoubleBoundedRangeModel;

import fr.istic.synthlab.module.control.ICVoltageControlledOscillatorA;
import fr.istic.synthlab.module.presentation.component.Bloc;
import fr.istic.synthlab.module.presentation.component.ConnectorBloc;
import fr.istic.synthlab.module.presentation.component.KnobBloc;
import fr.istic.synthlab.module.presentation.component.LcdBloc;
import fr.istic.synthlab.module.presentation.component.SimpleBlocGroup;
import fr.istic.synthlab.module.presentation.component.TitledBlocGroup;
import fr.istic.synthlab.util.Icons;
import fr.istic.synthlab.util.presentation.IPFemaleJack;

/**
 * Presentation for VCOA module.
 *
 * @author Laurent Legendre
 */
public class PVoltageControlledOscillatorA extends PModule implements
        IPVoltageControlledOscillatorA {

    /**
     * UID to serialize this object.
     */
    private static final long serialVersionUID = 5454079595647854706L;

    /**
     * model for Fine knob to set frequency.
     */
    private DoubleBoundedRangeModel modelFine;

    /**
     * model for Coarse knob to set frequency.
     */
    private DoubleBoundedRangeModel modelCoarse;

    /**
     * LCD.
     */
    private LcdBloc lcd;

    /**
     * VCOA control.
     */
    private ICVoltageControlledOscillatorA control;

    /**
     * Constructor.
     *
     * @param control
     *            ICVoltageControlledOscillatorA.
     */
    public PVoltageControlledOscillatorA(ICVoltageControlledOscillatorA control) {
        this.control = control;
        this.setLayout(new GridBagLayout());
        // this.setBorder(null);

        GridBagConstraints gbcFreq = new GridBagConstraints(0, 1,
                GridBagConstraints.REMAINDER, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, gbcInsets,
                0, 0);
        GridBagConstraints gbcInputs = new GridBagConstraints(0, 2,
                GridBagConstraints.RELATIVE, GridBagConstraints.RELATIVE, 0,
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                gbcInsets, 0, 0);
        GridBagConstraints gbcOutputs = new GridBagConstraints(1, 2,
                GridBagConstraints.REMAINDER, GridBagConstraints.RELATIVE, 0,
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                gbcInsets, 0, 0);

        // Build and add main blocs
        this.add(this.createModuleTitle(IPVoltageControlledOscillatorA.TITLE),
                gbcModuleTitle);

        this.add(this.processFreq(), gbcFreq);
        this.add(this.processInputs(), gbcInputs);
        this.add(this.processOutputs(), gbcOutputs);

        this.add(this.createModuleBottom(3), gbcBottom);

    }

    /**
     * Wrapper method to build the coarse knob.
     *
     * @return the coarse knob.
     */
    private Bloc processFreq() {

        // Master bloc with border and title.
        Bloc freqTitledBlocGroup = new TitledBlocGroup("FREQUENCY");
        // Change layout to stack children blocs verticaly
        freqTitledBlocGroup.setLayout(new BoxLayout(freqTitledBlocGroup,
                BoxLayout.PAGE_AXIS));

        // child top bloc with LCD
        Bloc lcdSimpleBlocGroup = new SimpleBlocGroup();

        // child bottom bloc with knobs
        Bloc knobsSimpleBlocGroup = new SimpleBlocGroup();

        // Create LCD and add it to is child group
        this.lcd = new LcdBloc("Hz");
        this.lcd.setDecimalFormat(5, 5, 2, 2);
        this.lcd.setLcdValue(this.control.getFrequency());
        lcdSimpleBlocGroup.add(lcd);

        // Create model and knobs and add them to their child group
        modelCoarse = new DoubleBoundedRangeModel("Octave", 15, 1, 16,
                this.control.getOctaveCoarse());

        modelCoarse.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                control.octaveCoarseChanged((int) modelCoarse.getDoubleValue());
            }
        });

        modelFine = new DoubleBoundedRangeModel("Tone", 20000, -100, +100,
                this.control.getOctaveFine());

        modelFine.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                control.octaveFineChanged(modelFine.getDoubleValue());
            }
        });

        KnobBloc coarseKnob = new KnobBloc(modelCoarse, "Octave");
        coarseKnob.setDecimalFormat(2, 2, 0, 0);
        KnobBloc fineKnob = new KnobBloc(modelFine, "Tone");
        fineKnob.setDecimalFormat(3, 3, 2, 2);
        knobsSimpleBlocGroup.add(coarseKnob);
        knobsSimpleBlocGroup.add(fineKnob);

        // Add children groups to parent group
        freqTitledBlocGroup.add(lcdSimpleBlocGroup);
        freqTitledBlocGroup.add(knobsSimpleBlocGroup);

        return freqTitledBlocGroup;
    }

    /**
     * Create a BlocGroup for Inputs populated with theFM input Bloc.
     *
     * @return The Inputs BlocGroup populated with the FM input Bloc.
     */
    private Bloc processInputs() {
        // Create the BlocGroup of Inputs Blocs
        Bloc intputsBlocGroup = new TitledBlocGroup("INPUT");

        // Get all connector's Presentation
        IPFemaleJack inputConnectorFm = control.getLineInFM().getPresentation();

        // Create a ConnectorBloc with a legend (Text) for the FM connector
        ConnectorBloc connectorBlocFm = new ConnectorBloc(inputConnectorFm,
                "FM");

        // Add FM ConnectorBloc to this blocGroup
        intputsBlocGroup.add(connectorBlocFm);

        intputsBlocGroup
                .setSize(70, intputsBlocGroup.getPreferredSize().height);
        intputsBlocGroup.setPreferredSize(intputsBlocGroup.getSize());
        intputsBlocGroup.setMinimumSize(intputsBlocGroup.getSize());

        // Return the builded muteBloc
        return intputsBlocGroup;
    }

    /**
     * Create a BlocGroup for Outputs populated with the four different Outputs
     * Bloc based on their waveforms (Sine, Square, Triangular, Sawtooth).
     *
     * @return The outputs BlocGroup populated with all its outputs Blocs.
     */
    private Bloc processOutputs() {
        // Create the BlocGroup of Outputs Blocs
        Bloc outputsBlocGroup = new TitledBlocGroup("OUTPUTS");

        // Get all connector's Presentation
        IPFemaleJack outputConnectorSine = control.getLineOutSine()
                .getPresentation();
        IPFemaleJack outputConnectorSquare = control.getLineOutSquare()
                .getPresentation();
        IPFemaleJack outputConnectorTriangular = control.getLineOutTriangular()
                .getPresentation();
        IPFemaleJack outputConnectorSawtooth = control.getLineOutSawtooth()
                .getPresentation();

        // Create a ConnectorBloc with a legend (Icon) for each connector's
        // Presentation
        ConnectorBloc connectorBlocSine = new ConnectorBloc(
                outputConnectorSine, Icons.WAVEFORM_SINE);
        ConnectorBloc connecorBlocSquare = new ConnectorBloc(
                outputConnectorSquare, Icons.WAVEFORM_SQUARE);
        ConnectorBloc connectorBlocTriangular = new ConnectorBloc(
                outputConnectorTriangular, Icons.WAVEFORM_TRIANGULAR);
        ConnectorBloc connectorBlocSawtooth = new ConnectorBloc(
                outputConnectorSawtooth, Icons.WAVEFORM_SAWTOOTH);

        // Add each ConnectorBloc to this blocGroup
        outputsBlocGroup.add(connectorBlocSine);
        outputsBlocGroup.add(connecorBlocSquare);
        outputsBlocGroup.add(connectorBlocTriangular);
        outputsBlocGroup.add(connectorBlocSawtooth);

        // Return this blocGroup with populated ConnectorBloc
        return outputsBlocGroup;
    }

    @Override
    public ICVoltageControlledOscillatorA getController() {
        return this.control;
    }

    @Override
    public void updateFreqDisplay() {
        this.lcd.setLcdValue(control.getFrequency());
        this.modelCoarse.setDoubleValue(control.getOctaveCoarse());
        this.modelFine.setDoubleValue(control.getOctaveFine());
    }

    /**
     * get size of the module for the glob.
     *
     * @return size of the module for the glob.
     *
     */
    @Override
    public int getWidthU() {
        return IPVoltageControlledOscillatorA.WIDTH_U;
    }

}
