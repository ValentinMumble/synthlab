package fr.istic.synthlab.module.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jsyn.Synthesizer;

import fr.istic.synthlab.global.control.CGLOB;
import fr.istic.synthlab.global.control.ICGLOB;
import fr.istic.synthlab.module.abstraction.AOut;
import fr.istic.synthlab.module.presentation.IPModule;
import fr.istic.synthlab.module.presentation.IPOut;
import fr.istic.synthlab.module.presentation.POut;
import fr.istic.synthlab.save.tools.SaveAttenuation;
import fr.istic.synthlab.util.control.CFemaleJack;
import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * The OUT module controller
 *
 * @author valentinmumble
 *
 */
public class COut extends AOut implements ICOut {

    private IPOut presentation;
    private ICFemaleJack lineIn;

    /**
     * 
     * @param synth
     *            the main synthesizer
     */
    public COut(Synthesizer synth) {
        super(synth);
        lineIn = new CFemaleJack(getInputPort(), true);
        presentation = new POut(this);
    }

    /**
     * If the module is active, mutes it and tells the presentation to show it
     * If the module is not active, unmutes it and tells the presentation to
     * show it
     */
    @Override
    public void toggleMute() {
        if (isMute()) {
            unmute();
            presentation.showMuteOff();
        } else {
            mute();
            presentation.showMuteOn();
        }
    }

    @Override
    public IPModule getPresentation() {
        return presentation;
    }

    /**
     * Called by the presentation to tell that the attenuator knob has been
     * changed
     */
    @Override
    public void attenuationChanged(double gain) {
        this.setAttenuation(gain);
        presentation.updateAttenuation(getAttenuation());
    }

    @Override
    public ICFemaleJack getLineIn() {
        return lineIn;
    }

    @Override
    public void suppressModule() {
        lineIn.removeCable();
    }
    
    @Override
    public ICGLOB getGlobalControl() {
        return CGLOB.getInstance();
    }

    @Override
    public void loadMuteState(boolean isMute) {
        if (isMute) {
            this.mute();
            this.presentation.showMuteOn();
        } else {
            this.unmute();
            this.presentation.showMuteOff();
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
        List<ICFemaleJack> liste = new ArrayList<ICFemaleJack>();
        if (this.getLineIn().isPlugged()) {
            getLineIn().setIdent(ident + "-lineIn");
            liste.add(lineIn);
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
            case "lineIn":
                listJacksToPlugged.put(ident, this.getLineIn());
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
        liste.add(new SaveAttenuation("0", getAttenuation()));
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
                this.attenuationChanged(listAttenuationToInitialize.get(i).getValue());
            }
        }
    }
    
    @Override
    public void requestForDelete() {
        this.getGlobalControl().destroyModule(this);
        presentation.removeModule();
    }
}
