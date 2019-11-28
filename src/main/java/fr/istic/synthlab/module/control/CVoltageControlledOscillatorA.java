package fr.istic.synthlab.module.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jsyn.Synthesizer;

import fr.istic.synthlab.global.control.CGLOB;
import fr.istic.synthlab.global.control.ICGLOB;
import fr.istic.synthlab.module.abstraction.AVoltageControlledOscillatorA;
import fr.istic.synthlab.module.presentation.IPModule;
import fr.istic.synthlab.module.presentation.IPVoltageControlledOscillatorA;
import fr.istic.synthlab.module.presentation.PVoltageControlledOscillatorA;
import fr.istic.synthlab.save.tools.SaveAttenuation;
import fr.istic.synthlab.util.control.CFemaleJack;
import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * The VCOA module controller
 * 
 * @author Laurent Legendre
 * 
 */
public class CVoltageControlledOscillatorA extends
        AVoltageControlledOscillatorA implements ICVoltageControlledOscillatorA {

    private IPVoltageControlledOscillatorA presentation;

    // Inputs and Outputs
    private ICFemaleJack inputFM;
    private ICFemaleJack outputSine;
    private ICFemaleJack outputSquare;
    private ICFemaleJack outputTriangular;
    private ICFemaleJack outputSawtooth;

    /**
     * 
     * @param synth
     *            the main synthesizer
     */
    public CVoltageControlledOscillatorA(Synthesizer synth) {
        super(synth);
        this.inputFM = new CFemaleJack(getFreqModIN(), true);
        this.outputSine = new CFemaleJack(getSortieSin(), false);
        this.outputSquare = new CFemaleJack(getSortieSqu(), false);
        this.outputTriangular = new CFemaleJack(getSortieTri(), false);
        this.outputSawtooth = new CFemaleJack(getSortieSaw(), false);
        this.presentation = new PVoltageControlledOscillatorA(this);
    }

    @Override
    public IPModule getPresentation() {
        return this.presentation;
    }

    @Override
    public void octaveCoarseChanged(int octave) {
        setOctave(octave);
        presentation.updateFreqDisplay();
    }

    @Override
    public void octaveFineChanged(double percent) {
        setFreqFine(percent);
        presentation.updateFreqDisplay();
    }

    @Override
    public int getOctaveCoarse() {

        return getOctave();
    }

    @Override
    public double getOctaveFine() {

        return getFreqFine();
    }

    @Override
    public ICFemaleJack getLineInFM() {
        return this.inputFM;
    }

    @Override
    public ICFemaleJack getLineOutSawtooth() {
        return this.outputSawtooth;
    }

    @Override
    public ICFemaleJack getLineOutSine() {
        return this.outputSine;

    }

    @Override
    public ICFemaleJack getLineOutSquare() {
        return this.outputSquare;

    }

    @Override
    public ICFemaleJack getLineOutTriangular() {
        return this.outputTriangular;

    }

    @Override
    public void suppressModule() {
        inputFM.removeCable();
        outputSine.removeCable();
        outputSquare.removeCable();
        outputTriangular.removeCable();
        outputSawtooth.removeCable();
    }

    @Override
    public ICGLOB getGlobalControl() {
        return CGLOB.getInstance();
    }

    /**
     * Called for the save. Initialize names of FemaleJacks and return the list
     * of FemaleJacks that are plugged.
     * 
     * @param ident
     *            Used to give a unique ident for each FemaleJack.
     * @return The list of FemalJacks plugged.
     */
    @Override
    public List<ICFemaleJack> getJacksForSave(String ident) {
        List<ICFemaleJack> listJack = new ArrayList<>();
        if (this.getLineInFM().isPlugged()) {
            this.getLineInFM().setIdent(ident + "-lineInFM");
            listJack.add(this.getLineInFM());
        }
        if (this.getLineOutSawtooth().isPlugged()) {
            this.getLineOutSawtooth().setIdent(ident + "-lineOutSawtooth");
            listJack.add(this.getLineOutSawtooth());
        }
        if (this.getLineOutSine().isPlugged()) {
            this.getLineOutSine().setIdent(ident + "-lineOutSine");
            listJack.add(this.getLineOutSine());
        }
        if (this.getLineOutSquare().isPlugged()) {
            this.getLineOutSquare().setIdent(ident + "-lineOutSquare");
            listJack.add(this.getLineOutSquare());
        }
        if (this.getLineOutTriangular().isPlugged()) {
            this.getLineOutTriangular().setIdent(ident + "-lineOutTrinagular");
            listJack.add(this.getLineOutTriangular());
        }
        return listJack;
    }

    /**
     * This function adds the corresponding FemaleJack in the HashMap with the
     * ident as key.
     * 
     * @param ident
     *            The ident of the FemaleJack to initialize.
     * @param listJacksToPlugged
     *            The HashMap of all Jacks that will be plugged.
     */
    @Override
    public void loadJack(String ident,
            HashMap<String, ICFemaleJack> listJacksToPlugged) {
        if (ident != null) {
            String idUnique = ident.split("-")[1];
            switch (idUnique) {
            case "lineInFM":
                listJacksToPlugged.put(ident, this.getLineInFM());
                break;
            case "lineOutSawtooth":
                listJacksToPlugged.put(ident, this.getLineOutSawtooth());
                break;
            case "lineOutSine":
                listJacksToPlugged.put(ident, this.getLineOutSine());
                break;
            case "lineOutSquare":
                listJacksToPlugged.put(ident, this.getLineOutSquare());
                break;
            case "lineOutTrinagular":
                listJacksToPlugged.put(ident, this.getLineOutTriangular());
                break;
            }
        }
    }

    /**
     * @return The list of Doublet ident/value of the different attenuations.
     */
    @Override
    public List<SaveAttenuation> getAttenuationForSave() {
        List<SaveAttenuation> listAtt = new ArrayList<>();
        SaveAttenuation att1 = new SaveAttenuation("octave", this.getOctave());
        SaveAttenuation att2 = new SaveAttenuation("freqfine",
                this.getFreqFine());
        listAtt.add(att1);
        listAtt.add(att2);
        return listAtt;
    }

    /**
     * Called by save system to initialize Attenuations with values saved.
     * 
     * @param listAttenuationToInitialize
     *            List of Doublet ident/value of the attenuations of the module.
     */
    @Override
    public void loadAttenuation(
            List<SaveAttenuation> listAttenuationToInitialize) {
        for (int i = 0; i < listAttenuationToInitialize.size(); i++) {
            if (listAttenuationToInitialize.get(i).getIdent().equals("octave")) {
                this.octaveCoarseChanged((int) listAttenuationToInitialize.get(
                        i).getValue());
            } else if (listAttenuationToInitialize.get(i).getIdent()
                    .equals("freqfine")) {
                this.octaveFineChanged(listAttenuationToInitialize.get(i)
                        .getValue());
            }
        }
    }

    @Override
    public void requestForDelete() {
        this.getGlobalControl().destroyModule(this);
        presentation.removeModule();
    }
}
