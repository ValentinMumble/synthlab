package fr.istic.synthlab.module.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jsyn.Synthesizer;

import fr.istic.synthlab.global.control.CGLOB;
import fr.istic.synthlab.global.control.ICGLOB;
import fr.istic.synthlab.module.abstraction.ASequencer;
import fr.istic.synthlab.module.presentation.IPModule;
import fr.istic.synthlab.module.presentation.IPSequencer;
import fr.istic.synthlab.module.presentation.PSequencer;
import fr.istic.synthlab.save.tools.SaveAttenuation;
import fr.istic.synthlab.util.control.CFemaleJack;
import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * The Sequencer controller.
 * 
 * @author valentinmumble
 * 
 */
public class CSequencer extends ASequencer implements ICSequencer {

    /**
     * The presentation of the Sequencer.
     */
    private IPSequencer presentation;

    /**
     * The control of the gate.
     */
    private ICFemaleJack gate;

    /**
     * The control of the output.
     */
    private ICFemaleJack output;

    /**
     * Constructor of the control Sequencer.
     * 
     * @param syn
     */
    public CSequencer(Synthesizer syn) {
        super(syn);
        gate = new CFemaleJack(getGatePort(), true);
        output = new CFemaleJack(getOutputPort(), false);
        presentation = new PSequencer(this);
    }

    @Override
    public IPModule getPresentation() {
        return presentation;
    }

    @Override
    public void suppressModule() {
        gate.removeCable();
        output.removeCable();
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
    public void setPitch(int index, double pitch) {
        super.setPitch(index - 1, pitch);
    }

    @Override
    public double getPitch(int index) {
        return super.getPitch(index - 1);
    }

    @Override
    public void pitchChanged(int index, double pitch) {
        setPitch(index, pitch);
        presentation.updatePitch(index, getPitch(index));
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
        if (this.getGate().isPlugged()) {
            this.getGate().setIdent(ident + "-gate");
            listJack.add(this.getGate());
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
            case "gate":
                listJacksToPlugged.put(ident, this.getGate());
                break;
            case "output":
                listJacksToPlugged.put(ident, this.getOutput());
                break;
            }
        }
    }

    @Override
    public void resetStepClicked() {
        resetStep();
    }

    /**
     * @return The list of Doublet ident/value of the different attenuations.
     */
    @Override
    public List<SaveAttenuation> getAttenuationForSave() {
        List<SaveAttenuation> listAtt = new ArrayList<>();
        SaveAttenuation att1 = new SaveAttenuation("1", getPitch(1));
        SaveAttenuation att2 = new SaveAttenuation("2", getPitch(2));
        SaveAttenuation att3 = new SaveAttenuation("3", getPitch(3));
        SaveAttenuation att4 = new SaveAttenuation("4", getPitch(4));
        SaveAttenuation att5 = new SaveAttenuation("5", getPitch(5));
        SaveAttenuation att6 = new SaveAttenuation("6", getPitch(6));
        SaveAttenuation att7 = new SaveAttenuation("7", getPitch(7));
        SaveAttenuation att8 = new SaveAttenuation("8", getPitch(8));
        listAtt.add(att1);
        listAtt.add(att2);
        listAtt.add(att3);
        listAtt.add(att4);
        listAtt.add(att5);
        listAtt.add(att6);
        listAtt.add(att7);
        listAtt.add(att8);
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
            this.pitchChanged(Integer.parseInt(listAttenuationToInitialize.get(
                    i).getIdent()), listAttenuationToInitialize.get(i)
                    .getValue());
        }
    }

    @Override
    public ICGLOB getGlobalControl() {
        return CGLOB.getInstance();
    }
    
    @Override
    public void requestForDelete() {
        this.getGlobalControl().destroyModule(this);
        presentation.removeModule();
    }
}
