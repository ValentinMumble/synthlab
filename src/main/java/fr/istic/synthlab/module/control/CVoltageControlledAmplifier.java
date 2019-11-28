package fr.istic.synthlab.module.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jsyn.Synthesizer;

import fr.istic.synthlab.global.control.CGLOB;
import fr.istic.synthlab.global.control.ICGLOB;
import fr.istic.synthlab.module.abstraction.AVoltageControlledAmplifier;
import fr.istic.synthlab.module.presentation.IPModule;
import fr.istic.synthlab.module.presentation.IPVoltageControlledAmplifier;
import fr.istic.synthlab.module.presentation.PVoltageControlledAmplifier;
import fr.istic.synthlab.save.tools.SaveAttenuation;
import fr.istic.synthlab.util.control.CFemaleJack;
import fr.istic.synthlab.util.control.ICFemaleJack;

public class CVoltageControlledAmplifier extends AVoltageControlledAmplifier
implements ICVoltageControlledAmplifier{

    private IPVoltageControlledAmplifier presentation;
    
    // Inputs and Outputs
    private ICFemaleJack inputAM;
    private ICFemaleJack inputSig;
    private ICFemaleJack outputSig;


    public CVoltageControlledAmplifier(Synthesizer s) {
        super(s);

        this.inputAM = new CFemaleJack(getInputAmpModul(), true);
        this.inputSig = new CFemaleJack(getInputPort(), true);
        this.outputSig = new CFemaleJack(getOutputPort(), false);

        this.presentation = new PVoltageControlledAmplifier(this);
    }
    

    @Override
    public IPModule getPresentation() {
     
        return this.presentation;
    }

    @Override
    public void amplificationChange(double value) {
        setAmplification(value);
        presentation.updateAmplification();
    }

    @Override
    public ICFemaleJack getPortSignIn() {
        return inputSig;
    }

    @Override
    public ICFemaleJack getPortAMIn() {
        return inputAM;
    }

    @Override
    public ICFemaleJack getPortSignOut() {
        return outputSig;
    }


    @Override
    public void suppressModule() {
        inputAM.removeCable();
        inputSig.removeCable();
        outputSig.removeCable();
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
        List<ICFemaleJack> listJack = new ArrayList<ICFemaleJack>();
        if (this.getPortAMIn().isPlugged()) {
            this.getPortAMIn().setIdent(ident + "-amIn");
            listJack.add(this.getPortAMIn());
        }
        if (this.getPortSignIn().isPlugged()) {
            this.getPortSignIn().setIdent(ident + "-signIn");
            listJack.add(this.getPortSignIn());
        }
        if (this.getPortSignOut().isPlugged()) {
            this.getPortSignOut().setIdent(ident + "-signOut");
            listJack.add(this.getPortSignOut());
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
            case "amIn":
                listJacksToPlugged.put(ident, this.getPortAMIn());
                break;
            case "signIn":
                listJacksToPlugged.put(ident, this.getPortSignIn());
                break;
            case "signOut":
                listJacksToPlugged.put(ident, this.getPortSignOut());
                break;
            }
        }
    }

    /**
     * @return The list of Doublet ident/value of the different attenuations.
     */
    @Override
    public List<SaveAttenuation> getAttenuationForSave() {
        List<SaveAttenuation> listAtt = new ArrayList<SaveAttenuation>();
        SaveAttenuation att1 = new SaveAttenuation("amplif",
                this.getAmplification());
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
            if (listAttenuationToInitialize.get(i).getIdent().equals("amplif")) {
                this.amplificationChange(listAttenuationToInitialize.get(i)
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
