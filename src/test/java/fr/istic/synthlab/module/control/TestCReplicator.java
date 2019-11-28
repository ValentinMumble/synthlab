package fr.istic.synthlab.module.control;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitOscillator;

import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * Test class for CReplicator
 * 
 * @author valentinmumble
 * 
 */
public class TestCReplicator {

    private static Synthesizer synth = JSyn.createSynthesizer();
    private UnitOscillator osc;
    private ICReplicator replicator;

    @Before
    public void setUp() {
        // Add a tone generator.
        osc = new SineOscillator();
        osc.frequency.set(345.0);
        osc.amplitude.set(0.6);
        synth.add(osc);

        replicator = new CReplicator(synth);
        osc.output.connect(replicator.getInputPort());

        synth.start();
    }

    @After
    public void tearDown() {
        synth.stop();
        replicator = null;
    }

    @Test
    public void testGetInput() {
        assertTrue(replicator.getInput() instanceof ICFemaleJack);
    }

    @Test
    public void testGetOutputs() {
        assertTrue(replicator.getOutput1() instanceof ICFemaleJack);
        assertTrue(replicator.getOutput2() instanceof ICFemaleJack);
        assertTrue(replicator.getOutput3() instanceof ICFemaleJack);
    }

}
