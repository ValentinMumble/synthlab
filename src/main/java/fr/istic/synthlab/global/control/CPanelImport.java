package fr.istic.synthlab.global.control;

import com.jsyn.Synthesizer;

import fr.istic.synthlab.global.abstraction.APanelImport;
import fr.istic.synthlab.global.presentation.PPanelImport;

/**
 * Controller for PanelImport.
 *
 * @author Jonathan
 */
public class CPanelImport extends APanelImport {

    /**
     * Presentation of the PanelImport.
     * @category PAC
     */
    private PPanelImport pPanelImport;

    /**
     * Contructor of the PanelImport.
     *
     * @category Contructor
     * @param synthesizer
     *            The global Synthesizer.
     */
    public CPanelImport(final Synthesizer synthesizer) {
        this.pPanelImport = new PPanelImport(this, synthesizer);
    }

    /**
     * getter for the presentation of PanelImport.
     *
     * @return the presentation
     */
    public PPanelImport getPresentation() {
        return pPanelImport;
    }
}
