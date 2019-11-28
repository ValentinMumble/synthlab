package fr.istic.synthlab.module.control;

import java.util.HashMap;
import java.util.List;

import fr.istic.synthlab.global.control.ICGLOB;
import fr.istic.synthlab.module.presentation.IPModule;
import fr.istic.synthlab.save.tools.SaveAttenuation;
import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * Interface that all modules should implement.
 * 
 * @author Laurent
 * 
 */
public interface ICModule {

    /**
     * Get the presentation of the module.
     *
     * @return the Presentation of the module
     */
    IPModule getPresentation();

    /**
     * Called when the module is put in a bin.
     */
    void suppressModule();

    /**
     * Get the control of the global frame
     * 
     * @return
     */
    ICGLOB getGlobalControl();

    /**
     * Called for the save. Initialize names of FemaleJacks and return the list
     * of FemaleJacks that are plugged.
     *
     * @param ident
     *            Used to give a unique ident for each FemaleJack.
     * @return The list of FemalJacks plugged.
     */
    List<ICFemaleJack> getJacksForSave(String ident);

    /**
     * This function adds the corresponding FemaleJack in the HashMap with the
     * ident as key.
     *
     * @param ident
     *            The ident of the FemaleJack to initialize.
     * @param listJacksToPlugged
     *            The HashMap of all Jacks that will be plugged.
     */
    void loadJack(String ident, HashMap<String, ICFemaleJack> listJacksToPlugged);

    /**
     * @return The list of Doublet ident/value of the different attenuations.
     */
    List<SaveAttenuation> getAttenuationForSave();

    /**
     * Called by save system to initialize Attenuations with values saved.
     *
     * @param listAttenuationToInitialize
     *            List of Doublet ident/value of the attenuations of the module.
     */
    void loadAttenuation(List<SaveAttenuation> listAttenuationToInitialize);

    void requestForDelete();
}
