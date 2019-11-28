package fr.istic.synthlab.module.presentation;

import fr.istic.synthlab.module.control.ICModule;

/**
 * 
 * Interface that all module's presentation should implement
 * 
 * @author Laurent
 * 
 */
public interface IPModule {

    /**
     * Module title.
     */
    String TITLE = "Default module name";

    /**
     * Module width.
     */
    int WIDTH_U = 1;

    /**
     * Get the controller of this presentation.
     * 
     * @return The controller of the given Presentation
     */
    ICModule getController();

    /**
     * Get the width of this presentation.
     * 
     * @return the width in U of the module presentation
     */
    int getWidthU();

    /**
     * Remove module from its slot
     */
    void removeModule();
}