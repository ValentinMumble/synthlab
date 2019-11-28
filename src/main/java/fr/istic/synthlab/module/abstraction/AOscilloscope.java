package fr.istic.synthlab.module.abstraction;

import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.scope.AudioScope;
import com.jsyn.unitgen.PassThrough;

/**
 * A class to represent the Oscilloscope module
 * 
 * @author Valentin
 * 
 */
public class AOscilloscope implements IAOscilloscope {

    public static final double MIN_RANGE_VOLTS = 0.1;
    public static final double DEFAULT_RANGE_VOLTS = 5;
    public static final double MAX_RANGE_VOLTS = 100;

    private Synthesizer synth;
    private PassThrough passThrough;
    private PassThrough outThrough;
    private AudioScope scope;
    private double range;

    public AOscilloscope(Synthesizer syn) {
        synth = syn;
        passThrough = new PassThrough();
        outThrough = new PassThrough();
        passThrough.output.connect(outThrough.input);
        synth.add(passThrough);
        scope = new AudioScope(synth);
        scope.addProbe(passThrough.output);
        setRange(DEFAULT_RANGE_VOLTS);
    }

    @Override
    public AudioScope getScope() {
        return scope;
    }

    @Override
    public UnitInputPort getInputPort() {
        return passThrough.input;
    }

    @Override
    public UnitOutputPort getOutputPort() {
        return outThrough.output;
    }

    @Override
    public void start() {
        scope.start();
    }

    @Override
    public void stop() {
        scope.stop();
    }

    @Override
    public double getRange() {
        return range;
    }

    @Override
    public void setRange(double r) {
        range = r;
    }
}
