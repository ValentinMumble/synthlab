package fr.istic.synthlab.global.control;

import java.util.List;

import com.jsyn.Synthesizer;

import fr.istic.synthlab.cable.control.ICCable;
import fr.istic.synthlab.global.abstraction.IAGLOB;
import fr.istic.synthlab.global.presentation.IPGLOB;
import fr.istic.synthlab.module.control.ICModule;
import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * Interface of CGLOB.
 *
 * @author Mickael
 */
public interface ICGLOB extends IAGLOB {

    /**
     * Getter of Presentation of GLOB.
     *
     * @return The presentation of the GLOB
     */
    IPGLOB getPresentation();

    /**
     * Getter of the cPanelContent.
     *
     * @return The cPanelContent.
     */
    CPanelContent getCPanelContent();

    /**
     * Getter of the cPanelImport.
     *
     * @return The cPanelImport.
     */
    CPanelImport getCPanelImport();

    /**
     * Function to initialize the PanelContent.
     */
    void initPanelContent();

    /**
     * @return the Synthesizer.
     */
    Synthesizer getSynthesizer();

    /**
     * Function for resize elements of the GLOB.
     */
    void myResize();

    /**
     * Called whenever a modification is made.
     *
     * @param value
     *            value to set in saved.
     */
    void setSaved(boolean value);

    /**
     * @return saved value.
     */
    boolean getSaved();

    /**
     * Call by load system to add a cable.
     *
     * @param iccable
     *            cable to load.
     */
    void loadCable(ICCable iccable);

    /**
     * Plug and show currentCable.
     *
     * @param controlJack
     *            Control of the jack that was clicked.
     */
    void portTouched(ICFemaleJack controlJack);

    /**
     * Get the currentCable.
     *
     * @return the currentCable.
     */
    ICCable getcurrentCable();

    /**
     * Getter of the List of cables.
     *
     * @return The list of the cables .
     */
    List<ICCable> getListCables();

    /**
     * Function use to destroy a cable.
     *
     * @param ccable
     *            The cable we want destroy.
     */
    void destroyCable(ICCable ccable);

    /**
     * Called when we abort the creation of a cable.
     */
    void destroyCurrentCable();
    
    /**
     * Called when we delete a module.
     */
    void destroyModule(ICModule module);
}
