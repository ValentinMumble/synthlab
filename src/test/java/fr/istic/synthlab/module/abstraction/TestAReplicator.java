package fr.istic.synthlab.module.abstraction;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitOscillator;

/**
 * Test class for AReplicator
 * 
 * @author valentinmumble
 * 
 */
public class TestAReplicator {

    private static Synthesizer synth = JSyn.createSynthesizer();
    private UnitOscillator osc;
    private IAReplicator replicator;

    @Before
    public void setUp() {
        // Add a tone generator.
        osc = new SineOscillator();
        osc.frequency.set(345.0);
        osc.amplitude.set(0.6);
        synth.add(osc);

        replicator = new AReplicator(synth);
        osc.output.connect(replicator.getInputPort());

        synth.start();
    }

    @After
    public void tearDown() {
        synth.stop();
        replicator = null;
    }

    @Test
    public void testGetInputPort() {
        assertTrue(replicator.getInputPort() instanceof UnitInputPort);
    }

    @Test
    public void testGetOutputsPort() {
        assertTrue(replicator.getOutput1Port() instanceof UnitOutputPort);
        assertTrue(replicator.getOutput2Port() instanceof UnitOutputPort);
        assertTrue(replicator.getOutput3Port() instanceof UnitOutputPort);
    }

}
