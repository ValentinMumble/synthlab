package fr.istic.synthlab.module.presentation;

public interface IPVoltageControlledOscillatorA extends IPModule {

    /**
     * Module title.
     */
    String TITLE = "<html><center>VOLTAGE-CONTROLLED<br/>OSCILLATOR</center></html>";

    /**
     * Module width.
     */
    int WIDTH_U = 3;

    /**
     * Called by the Controller when the Frequency changes
     */
    void updateFreqDisplay();
}
