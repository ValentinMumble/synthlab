package fr.istic.synthlab.module.abstraction;

import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.unitgen.LineOut;

import fr.istic.synthlab.filter.AttenuationFilter;
import fr.istic.synthlab.util.Util;

/**
 * A class to represent the output module.
 * 
 * @author valentinmumble
 * 
 */
public class AOut implements IAOut {

    public static final double ATTENUATION_MAX_DB = 12;

    public static final double ATTENUATION_MIN_DB = -60;

    /**
     * Synthetizer.
     */
    private Synthesizer synth;

    /**
     * physical output.
     */
    private LineOut lineOut;

    /**
     * Attenuation filter.
     */
    private AttenuationFilter filter;

    /**
     * attenuation in Decibels.
     */
    private double attenuationDB;

    /**
     * if the physical output is mute, true, else false.
     */
    private boolean active;
    

    /**
     * Constructor of the Out Abstraction.
     * 
     * @param s
     *            Synthetizer
     */
    public AOut(final Synthesizer s) {
        synth = s;
        
        lineOut = new LineOut();
        filter = new AttenuationFilter();
        attenuationDB = 0;
        synth.add(filter);
        synth.add(lineOut);

        lineOut.input.connect(0, filter.output, 0);
        lineOut.input.connect(1, filter.output, 0);      
        
        lineOut.start();
        active = true;
    }

    @Override
    public boolean isMute() {
        return !active;
    }

    @Override
    public UnitInputPort getInputPort() {
        return filter.input;
    }

    /**
     * The value in decibels is converted to volts and the nominal tension (1 in
     * JSyn) is subtracted to acquire attenuation.
     * 
     * @param dB
     *            the attenuation in decibels
     */
    @Override
    public void setAttenuation(final double dB) {
        attenuationDB = Math.min(ATTENUATION_MAX_DB,
                Math.max(ATTENUATION_MIN_DB, dB));
        filter.setAttenuation(Util.decibelsToVoltage(attenuationDB));
    }

    @Override
    public double getAttenuation() {
        return attenuationDB;
    }

    @Override
    public void mute() {
        lineOut.stop();
        active = false;
    }

    @Override
    public void unmute() {
        lineOut.start();
        active = true;
    }
}
