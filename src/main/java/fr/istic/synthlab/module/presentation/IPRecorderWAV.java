package fr.istic.synthlab.module.presentation;

public interface IPRecorderWAV extends IPModule {

    /**
     * Module title.
     */
    String TITLE = "<html><center>WAVE RECORDER</center></html>";

    /**
     * Module width.
     */
    int WIDTH_U = 2;

    /**
     * Called by the Controller when the record state change for one line
     *
     * @param line
     *            The line which must have its LED updated.
     */
    void updateLed(int line);
}
