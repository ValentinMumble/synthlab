package fr.istic.synthlab.module.presentation;

/**
 * Interface of PMixer.
 * 
 * @author Mickael
 * @version 1.0
 */
public interface IPMixer extends IPModule {

    /**
     * Module title.
     */
    String TITLE = "<html><center>MIXER</center></html>";

    /**
     * PMixer width.
     */
    int WIDTH_U = 3;

    /**
     * Called by the control to update the attenuation of the given index.
     * 
     * @param index
     *            The Port index which will be updated
     * @param att
     *            The new attenuation value.
     */
    void updateAttenuation(int index, double att);
}
