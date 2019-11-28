package fr.istic.synthlab.module.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jsyn.Synthesizer;

import fr.istic.synthlab.global.control.CGLOB;
import fr.istic.synthlab.global.control.ICGLOB;
import fr.istic.synthlab.module.abstraction.AOscilloscope;
import fr.istic.synthlab.module.presentation.IPModule;
import fr.istic.synthlab.module.presentation.IPOscilloscope;
import fr.istic.synthlab.module.presentation.POscilloscope;
import fr.istic.synthlab.save.tools.SaveAttenuation;
import fr.istic.synthlab.util.control.CFemaleJack;
import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * The Oscilloscope module controller
 * 
 * @author Valentin
 * 
 */
public class COscilloscope extends AOscilloscope implements ICOscilloscope {

    private ICFemaleJack input;
    private ICFemaleJack output;
    private IPOscilloscope presentation;

    public COscilloscope(Synthesizer syn) {
        super(syn);
        input = new CFemaleJack(getInputPort(), true);
        output = new CFemaleJack(getOutputPort(), false);
        presentation = new POscilloscope(this);
        start();
    }

    @Override
    public IPModule getPresentation() {
        return presentation;
    }

    @Override
    public ICFemaleJack getInput() {
        return input;
    }

    @Override
    public ICFemaleJack getOutput() {
        return output;
    }

    @Override
    public void suppressModule() {
        input.removeCable();
        output.removeCable();
    }
    
    @Override
    public ICGLOB getGlobalControl() {
        return CGLOB.getInstance();
    }

    public void rangeChanged(double range) {
        setRange(range);
        presentation.updateRange(getRange());
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
        List<ICFemaleJack> liste = new ArrayList<ICFemaleJack>();
        if (this.getInput().isPlugged()) {
            getInput().setIdent(ident + "-input");
            liste.add(input);
        }
        if (this.getOutput().isPlugged()) {
            getOutput().setIdent(ident + "-output");
            liste.add(output);
        }
        return liste;
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
        List<SaveAttenuation> liste = new ArrayList<SaveAttenuation>();
        liste.add(new SaveAttenuation("0", getRange()));
        return liste;
    }

    /**
     * @return The list of Doublet ident/value of the different attenuations.
     */
    @Override
    public void loadAttenuation(
            List<SaveAttenuation> listAttenuationToInitialize) {
        for (int i = 0; i < listAttenuationToInitialize.size(); i++) {
            if (listAttenuationToInitialize.get(i).getIdent().equals("0")) {
                this.rangeChanged(listAttenuationToInitialize.get(i).getValue());
            }
        }
    }
    
    @Override
    public void requestForDelete() {
        this.getGlobalControl().destroyModule(this);
        presentation.removeModule();
    }

}
