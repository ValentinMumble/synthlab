package fr.istic.synthlab.module.abstraction;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;

/**
 * Specific interface for an VCOA module.
 */
public interface IAVoltageControlledOscillatorA extends IAModule {

    /**
     * TAG Identifier for the VCOA module.
     */
    String TAG = "AVCOA";


    /**
     * Method setOctave.
     *
     * Set the frequency octave value of VCOA's out.
     *
     * @param numOctave
     *            int
     */
    void setOctave(int numOctave);


    /**
     * Method getOctave.
     *
     * Get the frequency octave value of VCOA's out.
     * @return the frequency octave value
     * 
     */
    int getOctave();

    /**
     * Method setOctaveMinusOne.
     *
     * Substract 1 octave of frequency value of VCOA's out
     * 
     */
    void setOctaveMinusOne();

    /**
     * Method setOctaveAddOne.
     *
     * Add 1 octave of frequency value of VCOA's out.
     * 
     */

    void setOctaveAddOne();

    /**
     * Method setFreqFine.
     *
     * Set the fine value of frequency within -1 octave and +1 octave in % (-100
     * to 100).
     * 
     * @param perCentValue
     *            double
     */
    void setFreqFine(double perCentValue);


    /**
     * Method getFreqFine.
     *
     * Get the fine value of frequency within -1 octave and +1 octave in % (-100
     * to 100).
     * @return the fine value of frequency
     */
    double getFreqFine();

    /**
     * Method setFrequency.
     *
     * Set octave and fine value of VCOA's out frequency.
     * 
     * @param numOctave
     *            int
     * @param perCentValue
     *            double
     */
    void setFrequency(int numOctave, double perCentValue);


    /**
     * Method setFrequency.
     *
     * Set frequency value of VCOA's out in Hz.
     * 
     * @param valFreq
     *            double
     */
    void setFrequency(double valFreq);


    /**
     * Method getFrequency. Get the frequency value of VCOA's oscillator in Hz.
     *
     * @return double
     */
    double getFrequency();

    /**
     * Method setAmplitude.
     *
     * Set the amplitude value of VCOA's out in Volts peak to peak.
     *
     * @param amp
     *            double
     */
    void setAmplitude(double amp);


    /**
     * Method getAmplitude.
     *
     * Get the amplitude value of VCOA's out in Volts peak to peak.
     * 
     * @return double
     */
    double getAmplitude();

    /**
     * Method getSortieSin.
     *
     * Get the output port of VCOA's SINUS generator.
     * 
     * @return UnitOutputPort
     */
    UnitOutputPort getSortieSin();


    /**
     * Method getSortieSqu.
     *
     * Get the output port of VCOA's SQUARE generator.
     * 
     * @return UnitOutputPort
     */
    UnitOutputPort getSortieSqu();

    /**
     * Method getSortieTri.
     *
     * Get the output port of VCOA's TRIANGLE generator.
     *
     * @return UnitOutputPort
     */
    UnitOutputPort getSortieTri();

    /**
     * Method getSortieSaw.
     *
     * Get the output port of VCOA's SAWSMOOTHS generator.
     *
     * @return UnitOutputPort
     */
    UnitOutputPort getSortieSaw();

    /**
     * Method getFreqModIN.
     *
     * Get the input port of Frequency modulation input
     *
     * @return UnitInputPort
     */
    UnitInputPort getFreqModIN();

    /**
     * Method setMute.
     *
     * Configure mute on or off.
     *
     * @param val
     *            boolean
     */
    void setMute(boolean val);

    /**
     * Method setMute.
     *
     * true if is mute else false.
     * @return true if is mute else false
     */
    boolean isMute();

}
