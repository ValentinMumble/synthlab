package fr.istic.synthlab.module.abstraction;

import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.FilterLowPass;

import fr.istic.synthlab.filter.FrequencyModulationFilter;

/**
 * A class to represent the Voltage-controlled Filter (Low Pass)
 * 
 * @author valentinmumble
 * 
 */
public class AVoltageControlledFilterLowPass implements
        IAVoltageControlledFilterLowPass {

    public static final double DEFAULT_CUTOFF_FREQUENCY_HZ = 1000;
    public static final double DEFAULT_RESONANCE = 1;
    public static final double MIN_CUTOFF_FREQUENCY_HZ = 40;
    public static final double MAX_CUTOFF_FREQUENCY_HZ = 11000;
    public static final double INFINITY_FREQUENCY_HZ = 100000;
    public static final double MIN_RESONANCE = 0.1;
    public static final double MAX_RESONANCE = 10;

    private Synthesizer synth;

    /**
     * Two low pass filters in series to achieve 24 dB/octave
     */
    private FilterLowPass filterLP1;
    private FilterLowPass filterLP2;
    private FrequencyModulationFilter fmFilter;
    private double cutoffFrequency;
    private double resonance;

    public AVoltageControlledFilterLowPass(Synthesizer syn) {
        synth = syn;
        filterLP1 = new FilterLowPass();
        filterLP2 = new FilterLowPass();
        fmFilter = new FrequencyModulationFilter();
        synth.add(filterLP1);
        synth.add(filterLP2);
        synth.add(fmFilter);

        filterLP1.output.connect(filterLP2.input);

        fmFilter.output.connect(filterLP1.frequency);
        fmFilter.output.connect(filterLP2.frequency);

        setCutoffFrequency(DEFAULT_CUTOFF_FREQUENCY_HZ);
        setResonance(DEFAULT_RESONANCE);
    }

    @Override
    public UnitInputPort getInputPort() {
        return filterLP1.input;
    }

    @Override
    public UnitInputPort getFreqModInputPort() {
        return fmFilter.input;
    }

    @Override
    public UnitOutputPort getOutputPort() {
        return filterLP2.output;
    }

    @Override
    public void setCutoffFrequency(double freq) {
        if (freq >= MAX_CUTOFF_FREQUENCY_HZ) {
            freq = INFINITY_FREQUENCY_HZ;
        }
        cutoffFrequency = freq;
        fmFilter.setBaseFrequency(getCutoffFrequency());
    }

    @Override
    public void setResonance(double res) {
        resonance = res;
        filterLP1.Q.set(resonance);
        filterLP2.Q.set(resonance);
    }

    @Override
    public double getCutoffFrequency() {
        return cutoffFrequency;
    }

    @Override
    public double getResonance() {
        return resonance;
    }
}
