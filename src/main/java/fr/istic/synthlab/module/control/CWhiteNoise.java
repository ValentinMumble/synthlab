package fr.istic.synthlab.module.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jsyn.Synthesizer;

import fr.istic.synthlab.global.control.CGLOB;
import fr.istic.synthlab.global.control.ICGLOB;
import fr.istic.synthlab.module.abstraction.AWhiteNoise;
import fr.istic.synthlab.module.presentation.IPModule;
import fr.istic.synthlab.module.presentation.IPWhiteNoise;
import fr.istic.synthlab.module.presentation.PWhiteNoise;
import fr.istic.synthlab.save.tools.SaveAttenuation;
import fr.istic.synthlab.util.control.CFemaleJack;
import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * The White Noise module controller
 */
public class CWhiteNoise extends AWhiteNoise implements ICWhiteNoise{

    private IPWhiteNoise presentation;
    private ICFemaleJack lineOut;
    
    /**
     * Constructor for CWhiteNoise.
     * @param s the main synthesizer
     */
    public CWhiteNoise(Synthesizer s) {
        super(s);
        lineOut = new CFemaleJack(getOutputPort(), false);

        this.presentation = new PWhiteNoise(this);
    }

    /**
     * Method getPresentation.
     * @return IPModule
     * @see fr.istic.synthlab.module.control.ICModule#getPresentation()
     */
    @Override
    public IPModule getPresentation() {
        
        return this.presentation;
    }

   

    /**
     * Method toggleMute.
     * @see fr.istic.synthlab.module.control.ICWhiteNoise#toggleMute()
     */
    @Override
    public void toggleMute() {
        setEnable(!getEnable());
        if(getEnable()){
            presentation.showMuteOff();
        }else{
            presentation.showMuteOn();
        }
    }

    /**
     * Method getOutput.
     * @return ICFemaleJack
     * @see fr.istic.synthlab.module.control.ICWhiteNoise#getOutput()
     */
    @Override
    public ICFemaleJack getOutput() {
        return lineOut;
    }

    /**
     * Method suppressModule.
     * @see fr.istic.synthlab.module.control.ICModule#suppressModule()
     */
    @Override
    public void suppressModule() {
        lineOut.removeCable();
    }
    
    @Override
    public ICGLOB getGlobalControl() {
        return CGLOB.getInstance();
    }

    @Override
    public void loadMuteState(boolean isMute) {
        this.setEnable(isMute);
        if (isMute) {
            this.presentation.showMuteOff();
        } else {
            this.presentation.showMuteOn();
        }
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
        if (this.getOutput().isPlugged()){
            this.getOutput().setIdent(ident+"-output");
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
             //String idUnique = ident.split("-")[1];
             listJacksToPlugged.put(ident, this.getOutput());
        }
    }

    /**
     * @return The list of Doublet ident/value of the different attenuations.
     */
    @Override
    public List<SaveAttenuation> getAttenuationForSave() {
        List<SaveAttenuation> listAtt = new ArrayList<>();
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
    }
    
    @Override
    public void requestForDelete() {
        this.getGlobalControl().destroyModule(this);
        presentation.removeModule();
    }

}
