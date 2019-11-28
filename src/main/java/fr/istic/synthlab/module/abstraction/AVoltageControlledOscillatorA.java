package fr.istic.synthlab.module.abstraction;

import java.util.Hashtable;

import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.Multiply;
import com.jsyn.unitgen.PowerOfTwo;
import com.jsyn.unitgen.SawtoothOscillator;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.SquareOscillator;
import com.jsyn.unitgen.TriangleOscillator;
import com.jsyn.unitgen.UnitOscillator;
import com.softsynth.jsyn.SynthException;

/**
 * AVoltageControlledOscillatorA.
 * 
 * @author Favereau
 * @version $Revision: 1.0 $
 */

public class AVoltageControlledOscillatorA implements
        IAVoltageControlledOscillatorA {

    /**
     * AMPL_MIN.
     * 
     * @category CONSTANT
     */
    private final double AMPL_MIN = 0.001;

    /**
     * AMPL_MAX.
     * 
     * @category CONSTANT
     */
    private final double AMPL_MAX = 20;

    /**
     * FREQ_MAX.
     * 
     * @category CONSTANT
     */
    private final double FREQ_MAX = 28000;

    /**
     * FREQ_OCTAVE_0.
     * 
     * @category CONSTANT
     */
    private final double FREQ_OCTAVE_0 = 0.4296875;

    /**
     * NB_OCTAVE.
     * 
     * @category CONSTANT
     */
    private final int NB_OCTAVE = 16;

    /**
     * Sine Oscillator.
     */
    private UnitOscillator oscillatorSin = new SineOscillator();

    /**
     * Square Oscillator.
     */
    private UnitOscillator oscillatorSqu = new SquareOscillator();

    /**
     * Triangle Oscillator.
     */
    private UnitOscillator oscillatorTri = new TriangleOscillator();

    /**
     * Sawtooth Oscillator.
     */
    private UnitOscillator oscillatorSaw = new SawtoothOscillator();

    /**
     * output of the Sine Oscillator.
     */
    private UnitOutputPort oscOutSin;

    /**
     * outpur of the Square Oscillator.
     */
    private UnitOutputPort oscOutSqu;

    /**
     * output of the Triangle Oscillator.
     */
    private UnitOutputPort oscOutTri;

    /**
     * output of the Sawtooth Oscillator.
     */
    private UnitOutputPort oscOutSaw;

    /**
     * power to Unit.
     */
    private PowerOfTwo powToUnit;

    /**
     * frequency Modulator.
     */
    private Multiply freqModulator;

    /**
     * default start value of the octaveID.
     */
    private int octaveID;

    /**
     * default start value of the frequency per cent.
     */
    private double freqPerCent;

    /**
     * default start value of the frequency per hertz.
     */
    private double frequencyHertz;

    /**
     * default start value of out level.
     */
    private double levelOut;

    /**
     * list of octaves.
     */

    private Hashtable<Integer, Double> octaveList;

    /**
     * Constructor for AVoltageControlOscillatorA.
     * 
     * @param synth
     *            Synthesizer
     */
    public AVoltageControlledOscillatorA(final Synthesizer synth) {
        octaveID = 10;
        freqPerCent = 0;
        levelOut = 5;
        try {
            // Create octaves values tab
            octaveList = new Hashtable<>();
            for (int i = 0; i <= (NB_OCTAVE + 1); i++) {
                octaveList.put(i, FREQ_OCTAVE_0 * Math.pow(2.0, i));
            }

            // create oscillators with different
            // signals type
            oscillatorSin = new SineOscillator();
            oscillatorSqu = new SquareOscillator();
            oscillatorTri = new TriangleOscillator();
            oscillatorSaw = new SawtoothOscillator();

            oscOutSin = oscillatorSin.getOutput();
            oscOutSqu = oscillatorSqu.getOutput();
            oscOutTri = oscillatorTri.getOutput();
            oscOutSaw = oscillatorSaw.getOutput();

            // Frequency modulator
            freqModulator = new Multiply();
            powToUnit = new PowerOfTwo();

            synth.add(freqModulator);
            synth.add(powToUnit);

            freqModulator.inputA.connect(powToUnit.output);

            freqModulator.output.connect(oscillatorSin.frequency);
            freqModulator.output.connect(oscillatorSqu.frequency);
            freqModulator.output.connect(oscillatorTri.frequency);
            freqModulator.output.connect(oscillatorSaw.frequency);

            commandFreqAmp();

            synth.add(oscillatorSin);
            synth.add(oscillatorSqu);
            synth.add(oscillatorTri);
            synth.add(oscillatorSaw);

            freqModulator.start();
            powToUnit.start();

        } catch (SynthException e) {
            System.out.println("ERROR JSYN on " + this.getClass().getName());
            System.out.println(e.getMessage());
        }

    }

    /**
     * Method getFrequency.
     * 
     * @return double
     * @see IAVoltageControlledOscillatorA#getFrequency()
     */
    @Override
    public double getFrequency() {

        return frequencyHertz;
    }

    /**
     * Method setAmplitude.
     * 
     * @param amp
     *            double
     * @see IAVoltageControlledOscillatorA#setAmplitude(double)
     */
    @Override
    public void setAmplitude(final double amp) {

        if (amp >= AMPL_MIN && amp <= AMPL_MAX) {
            this.levelOut = amp;
        }

        commandFreqAmp();
    }

    /**
     * Method getAmplitude.
     * 
     * @return double
     * @see IAVoltageControlledOscillatorA#getAmplitude()
     */
    @Override
    public double getAmplitude() {

        return this.levelOut;

    }

    /**
     * Method getSortieSin.
     * 
     * @return UnitOutputPort
     * @see IAVoltageControlledOscillatorA#getSortieSin()
     */
    @Override
    public UnitOutputPort getSortieSin() {

        return oscOutSin;
    }

    /**
     * Method getSortieSqu.
     * 
     * @return UnitOutputPort
     * @see IAVoltageControlledOscillatorA#getSortieSqu()
     */
    @Override
    public UnitOutputPort getSortieSqu() {

        return oscOutSqu;
    }

    /**
     * Method getSortieTri.
     * 
     * @return UnitOutputPort
     * @see IAVoltageControlledOscillatorA#getSortieTri()
     */
    @Override
    public UnitOutputPort getSortieTri() {

        return oscOutTri;
    }

    /**
     * Method getSortieSaw.
     * 
     * @return UnitOutputPort
     * @see IAVoltageControlledOscillatorA#getSortieSaw()
     */
    @Override
    public UnitOutputPort getSortieSaw() {

        return oscOutSaw;
    }

    /**
     * Method getFreqModIN.
     * 
     * @return UnitInputPort
     * @see IAVoltageControlledOscillatorA#getFreqModIN()
     */
    @Override
    public UnitInputPort getFreqModIN() {

        // /return freqMod_in;
        return powToUnit.input;
    }

    /**
     * Method setOctave.
     * 
     * @param numOctave
     *            int
     * @see IAVoltageControlledOscillatorA#setOctave(int)
     */
    @Override
    public void setOctave(final int numOctave) {

        if (numOctave <= NB_OCTAVE && numOctave > 0) {
            this.octaveID = numOctave;
            commandFreqAmp();
        }
    }

    /**
     * Method getOctave.
     * 
     * @return numOctave int
     * @see IAVoltageControlledOscillatorA#getOctave(int)
     */
    @Override
    public int getOctave() {

        return this.octaveID;
    }

    /**
     * Method setOctaveMinusOne.
     * 
     * @see IAVoltageControlledOscillatorA#setOctaveMinusOne()
     */
    @Override
    public void setOctaveMinusOne() {
        if (this.octaveID > 1) {
            this.octaveID--;
            commandFreqAmp();
        }
    }

    /**
     * Method setOctaveAddOne.
     * 
     * @see IAVoltageControlledOscillatorA#setOctaveAddOne()
     */
    @Override
    public void setOctaveAddOne() {
        if (this.octaveID < NB_OCTAVE) {
            this.octaveID++;
            commandFreqAmp();
        }
    }

    /**
     * Method setFreqFine.
     * 
     * @param perCentValue
     *            double
     * @see IAVoltageControlledOscillatorA#setFreqFine(double)
     */
    @Override
    public void setFreqFine(final double perCentValue) {

        if (perCentValue <= 100 && perCentValue >= -100) {
            freqPerCent = perCentValue;
            commandFreqAmp();
        }
    }

    /**
     * Method getFreqFine.
     * 
     * @return perCentValue double
     * @see IAVoltageControlledOscillatorA#getFreqFine
     */
    @Override
    public double getFreqFine() {

        return this.freqPerCent;
    }

    /**
     * Method setFrequency.
     * 
     * @param numOctave
     *            int
     * @param perCentValue
     *            double
     * @see IAVoltageControlledOscillatorA#setFrequency(int, double)
     */
    @Override
    public void setFrequency(final int numOctave, final double perCentValue) {

        setOctave(numOctave);
        setFreqFine(perCentValue);

    }

    /**
     * Commande Frequency Amplitude.
     */
    private void commandFreqAmp() {
        double freqFine;

        if (freqPerCent <= 0) {
            freqFine = (octaveList.get(octaveID) - octaveList.get(octaveID - 1))
                    * freqPerCent / 100;
        } else {
            freqFine = (octaveList.get(octaveID + 1) - octaveList.get(octaveID))
                    * freqPerCent / 100;
        }

        frequencyHertz = octaveList.get(octaveID) + freqFine;

        if (frequencyHertz > FREQ_MAX) {
            frequencyHertz = FREQ_MAX;
        }

        freqModulator.inputB.set(frequencyHertz);

        oscillatorSin.amplitude.set(levelOut);
        oscillatorSqu.amplitude.set(levelOut);
        oscillatorTri.amplitude.set(levelOut);
        oscillatorSaw.amplitude.set(levelOut);

    }

    /**
     * Method setMute.
     * 
     * @param val
     *            boolean
     * @see IAVoltageControlledOscillatorA#setMute(boolean)
     */
    @Override
    public void setMute(final boolean val) {

        oscillatorSin.setEnabled(val);
        oscillatorSqu.setEnabled(val);
        oscillatorSin.setEnabled(val);
        oscillatorSaw.setEnabled(val);
    }

    /**
     * Method setFrequency.
     * 
     * @param valFreq
     *            double
     * @see IAVoltageControlledOscillatorA#setFrequency(double)
     */
    @Override
    public void setFrequency(final double valFreq) {
        // TODO Auto-generated method stub
        // TODO_Marc not yet implemented

    }

    /**
     * Method isMute.
     * 
     * @return boolean
     * @see IAVoltageControlledOscillatorA#isMute()
     */
    @Override
    public boolean isMute() {
        return oscillatorSin.isEnabled();
    }

}
