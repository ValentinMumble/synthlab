package fr.istic.synthlab.module.presentation;

public interface IPVoltageControlledAmplifier extends IPModule {

    /**
     * Module title.
     */
    String TITLE = "<html><center>VOLTAGE-CONTROLLED<br/>AMPLIFIER</center></html>";

    /**
     * Module width.
     */
    int WIDTH_U = 2;

    /**
     * Called by the Controller when the Frequency changes
     */
    void updateAmplification();
}
