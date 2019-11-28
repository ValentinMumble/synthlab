package fr.istic.synthlab.module.abstraction;

import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.PassThrough;

/**
 * A class to represent the Replicator module.
 * 
 * @author valentinmumble
 * 
 */
public class AReplicator implements IAReplicator {

    /**
     * Synthesizer.
     */
    private Synthesizer synth;

    /**
     * input port PassThrough.
     */
    private PassThrough inputPassThrough;

    /**
     * output port 1 PassThrough.
     */
    private PassThrough output1PassThrough;

    /**
     * output port 1 PassThrough.
     */
    private PassThrough output2PassThrough;

    /**
     * output port 1 PassThrough.
     */
    private PassThrough output3PassThrough;

    /**
     * Constructor of the Abstraction Replicator.
     * 
     * @param synth
     */
    public AReplicator(final Synthesizer s) {
        this.synth = s;

        inputPassThrough = new PassThrough();
        output1PassThrough = new PassThrough();
        output2PassThrough = new PassThrough();
        output3PassThrough = new PassThrough();
        this.synth.add(inputPassThrough);
        this.synth.add(output1PassThrough);
        this.synth.add(output2PassThrough);
        this.synth.add(output3PassThrough);

        inputPassThrough.output.connect(output1PassThrough.input);
        inputPassThrough.output.connect(output2PassThrough.input);
        inputPassThrough.output.connect(output3PassThrough.input);
    }

    @Override
    public UnitInputPort getInputPort() {
        return inputPassThrough.input;
    }

    @Override
    public UnitOutputPort getOutput1Port() {
        return output1PassThrough.output;
    }

    @Override
    public UnitOutputPort getOutput2Port() {
        return output2PassThrough.output;
    }

    @Override
    public UnitOutputPort getOutput3Port() {
        return output3PassThrough.output;
    }
}
