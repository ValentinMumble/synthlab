package fr.istic.synthlab.module.control;

import fr.istic.synthlab.module.abstraction.IAVoltageControlledOscillatorA;
import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * Interface for the VCOA module controller
 * 
 * @author Laurent Legendre
 * 
 */
public interface ICVoltageControlledOscillatorA extends ICModule,

IAVoltageControlledOscillatorA {

    /**
     * Called by the Presentation when the octave coarse adjustment changes.
     * 
     * UoM is in octave (between 1 and 16)
     * 
     * @param octave
     */
    void octaveCoarseChanged(int octave);

    /**
     * Called by the Presentation when the octave fine adjustment changes.
     * 
     * UoM is in percentage of the remaining frequency of (OctaveCoarse(N) -
     * OctaveCoarse(N +/- 1))
     * 
     * @param percent
     */
    void octaveFineChanged(double percent);

    /**
     * Called by the Presentation to get the current Octave Coarse value.
     * 
     * @return The value of the Coarse setting in octave (between 1 and 16)
     **/
    int getOctaveCoarse();

    /**
     * Called by the Presentation to get the current Octave Fine value.
     * 
     * @return The value of the Fine setting in percentage of the remaining
     *         frequency of (OctaveCoarse(N) - OctaveCoarse(N +/- 1))
     **/
    double getOctaveFine();

    /**
     * Called by the Presentation to get the current Frequency
     * 
     * @return The Frequency in Hz
     **/
    double getFrequency();

    ICFemaleJack getLineInFM();

    ICFemaleJack getLineOutSawtooth();

    ICFemaleJack getLineOutSine();

    ICFemaleJack getLineOutSquare();

    ICFemaleJack getLineOutTriangular();

}
