package fr.istic.synthlab.module.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jsyn.Synthesizer;

import fr.istic.synthlab.global.control.CGLOB;
import fr.istic.synthlab.global.control.ICGLOB;
import fr.istic.synthlab.module.abstraction.AVoltageControlledFilterHighPass;
import fr.istic.synthlab.module.presentation.IPModule;
import fr.istic.synthlab.module.presentation.IPVoltageControlledFilterHighPass;
import fr.istic.synthlab.module.presentation.PVoltageControlledFilterHighPass;
import fr.istic.synthlab.save.tools.SaveAttenuation;
import fr.istic.synthlab.util.control.CFemaleJack;
import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * The VCFHP controller
 * 
 * @author valentinmumble
 * 
 */
public class CVoltageControlledFilterHighPass extends
        AVoltageControlledFilterHighPass implements
        ICVoltageControlledFilterHighPass {

    private IPVoltageControlledFilterHighPass presentation;

    private ICFemaleJack input;
    private ICFemaleJack freqModInput;
    private ICFemaleJack output;

    public CVoltageControlledFilterHighPass(Synthesizer syn) {
        super(syn);

        input = new CFemaleJack(getInputPort(), true);
        freqModInput = new CFemaleJack(getFreqModInputPort(), true);
        output = new CFemaleJack(getOutputPort(), false);

        presentation = new PVoltageControlledFilterHighPass(this);
    }

    @Override
    public IPModule getPresentation() {
        return presentation;
    }

    @Override
    public void suppressModule() {
        input.removeCable();
        freqModInput.removeCable();
        output.removeCable();
    }

    @Override
    public void cutoffFrequencyChanged(double freq) {
        setCutoffFrequency(freq);
        presentation.updateCutoffFrequency(getCutoffFrequency());
    }

    @Override
    public ICFemaleJack getInput() {
        return input;
    }

    @Override
    public ICFemaleJack getFreqModInput() {
        return freqModInput;
    }

    @Override
    public ICFemaleJack getOutput() {
        return output;
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
        if (this.getInput().isPlugged()) {
            this.getInput().setIdent(ident + "-input");
            listJack.add(this.getInput());
        }
        if (this.getFreqModInput().isPlugged()) {
            this.getFreqModInput().setIdent(ident + "-freqModInput");
            listJack.add(this.getFreqModInput());
        }
        if (this.getOutput().isPlugged()) {
            this.getOutput().setIdent(ident + "-output");
            listJack.add(this.getOutput());
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
            case "input":
                listJacksToPlugged.put(ident, this.getInput());
                break;
            case "freqModInput":
                listJacksToPlugged.put(ident, this.getFreqModInput());
                break;
            case "output":
                listJacksToPlugged.put(ident, this.getOutput());
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
        SaveAttenuation att1 = new SaveAttenuation("freq",
                this.getCutoffFrequency());
        listAtt.add(att1);
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
            if (listAttenuationToInitialize.get(i).getIdent().equals("freq")) {
                this.cutoffFrequencyChanged(listAttenuationToInitialize.get(i)
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
