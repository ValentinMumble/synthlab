package fr.istic.synthlab.module.abstraction;

import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.FilterHighPass;

import fr.istic.synthlab.filter.FrequencyModulationFilter;

/**
 * A class to represent the Voltage-controlled Filter (High Pass)
 * 
 * @author valentinmumble
 * 
 */
public class AVoltageControlledFilterHighPass implements
        IAVoltageControlledFilterHighPass {

    public static final double DEFAULT_CUTOFF_FREQUENCY_HZ = 1000;
    public static final double MIN_CUTOFF_FREQUENCY_HZ = 40;
    public static final double MAX_CUTOFF_FREQUENCY_HZ = 11000;

    private Synthesizer synth;

    private FilterHighPass filterHP;
    private double cutoffFrequency;
    private FrequencyModulationFilter fmFilter;

    public AVoltageControlledFilterHighPass(Synthesizer syn) {
        synth = syn;
        filterHP = new FilterHighPass();
        fmFilter = new FrequencyModulationFilter();
        synth.add(filterHP);
        synth.add(fmFilter);

        fmFilter.output.connect(filterHP.frequency);

        setCutoffFrequency(DEFAULT_CUTOFF_FREQUENCY_HZ);
    }

    @Override
    public UnitInputPort getInputPort() {
        return filterHP.input;
    }

    @Override
    public UnitInputPort getFreqModInputPort() {
        return fmFilter.input;
    }

    @Override
    public UnitOutputPort getOutputPort() {
        return filterHP.output;
    }

    @Override
    public void setCutoffFrequency(double freq) {
        cutoffFrequency = freq;
        fmFilter.setBaseFrequency(getCutoffFrequency());
    }

    @Override
    public double getCutoffFrequency() {
        return cutoffFrequency;
    }
}
