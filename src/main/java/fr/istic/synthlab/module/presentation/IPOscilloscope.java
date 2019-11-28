package fr.istic.synthlab.module.presentation;

/**
 * Interface for the presentation of the Oscilloscope module
 * 
 * @author valentinmumble
 * 
 */
public interface IPOscilloscope extends IPModule {

    /**
     * Module title.
     */
    String TITLE = "<html><center>OSCILLOSCOPE</center></html>";

    /**
     * Module width.
     */
    int WIDTH_U = 4;

    /**
     * Updates the range of the scope screen
     *
     * @param range
     *            The new range
     */
    void updateRange(double range);
}
