package fr.istic.synthlab.module.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jsyn.Synthesizer;

import fr.istic.synthlab.global.control.CGLOB;
import fr.istic.synthlab.global.control.ICGLOB;
import fr.istic.synthlab.module.abstraction.AEnvelopeGenerator;
import fr.istic.synthlab.module.presentation.IPEnvelopeGenerator;
import fr.istic.synthlab.module.presentation.IPModule;
import fr.istic.synthlab.module.presentation.PEnvelopeGenerator;
import fr.istic.synthlab.save.tools.SaveAttenuation;
import fr.istic.synthlab.util.control.CFemaleJack;
import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * The controller for the Envelope Generator module
 * 
 * @author valentinmumble
 * 
 */
public class CEnvelopeGenerator extends AEnvelopeGenerator implements
        ICEnvelopeGenerator {

    private ICFemaleJack gate;
    private ICFemaleJack output;
    private IPEnvelopeGenerator presentation;

    public CEnvelopeGenerator(Synthesizer syn) {
        super(syn);
        gate = new CFemaleJack(getGatePort(), true);
        output = new CFemaleJack(getOutputPort(), false);
        presentation = new PEnvelopeGenerator(this);
    }

    @Override
    public IPModule getPresentation() {
        return presentation;
    }

    @Override
    public void attackChanged(double a) {
        setAttack(a);
        presentation.updateAttack(getAttack());
    }

    @Override
    public void decayChanged(double d) {
        setDecay(d);
        presentation.updateDecay(getDecay());
    }

    @Override
    public void sustainChanged(double s) {
        setSustain(s / 100);
        presentation.updateSustain(getSustain());
    }

    /**
     * Overridden method because the presentation's range goes from 0 to 100 as
     * opposed to 0 to 1 for the abstraction
     */
    @Override
    public double getSustain() {
        return super.getSustain() * 100;
    }

    @Override
    public void releaseChanged(double r) {
        setRelease(r);
        presentation.updateRelease(getRelease());
    }

    @Override
    public ICFemaleJack getGate() {
        return gate;
    }

    @Override
    public ICFemaleJack getOutput() {
        return output;
    }

    @Override
    public void suppressModule() {
        gate.removeCable();        
        output.removeCable();
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
        List<ICFemaleJack> liste = new ArrayList<ICFemaleJack>();
        if (this.getGate().isPlugged()) {
            getGate().setIdent(ident + "-gate");
            liste.add(gate);
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
            case "gate":
                listJacksToPlugged.put(ident, this.getGate());
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
        liste.add(new SaveAttenuation("0", getAttack()));
        liste.add(new SaveAttenuation("1", getDecay()));
        liste.add(new SaveAttenuation("2", getSustain()));
        liste.add(new SaveAttenuation("3", getRelease()));
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
                this.attackChanged(listAttenuationToInitialize.get(i).getValue());
            } else if (listAttenuationToInitialize.get(i).getIdent()
                    .equals("1")) {
                this.decayChanged(listAttenuationToInitialize.get(i).getValue());
            } else if (listAttenuationToInitialize.get(i).getIdent()
                    .equals("2")) {
                this.sustainChanged(listAttenuationToInitialize.get(i).getValue());
            } else if (listAttenuationToInitialize.get(i).getIdent()
                    .equals("3")) {
                this.releaseChanged(listAttenuationToInitialize.get(i).getValue());
            }
        }
    }
    
    @Override
    public void requestForDelete() {
        this.getGlobalControl().destroyModule(this);
        presentation.removeModule();
    }
}
