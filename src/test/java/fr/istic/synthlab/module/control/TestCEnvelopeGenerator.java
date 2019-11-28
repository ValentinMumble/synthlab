package fr.istic.synthlab.module.control;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitOscillator;

/**
 * Test class for CEnvelopeGenerator
 * 
 * @author valentinmumble
 * 
 */
public class TestCEnvelopeGenerator {

    private static Synthesizer synth = JSyn.createSynthesizer();
    private UnitOscillator osc;
    private UnitOscillator gatingOsc;
    private ICEnvelopeGenerator eg;

    @Before
    public void setUp() {
        osc = new SineOscillator();
        gatingOsc = new SineOscillator();
        synth.add(osc);
        synth.add(gatingOsc);

        // Low frequency for the gate
        gatingOsc.frequency.setup(0.001, 1, 10.0);

        // Normal frequency for the amplitude modulation
        osc.frequency.setup(50.0, 440.0, 2000.0);

        eg = new CEnvelopeGenerator(synth);

        gatingOsc.output.connect(eg.getGatePort());
        osc.amplitude.connect(eg.getOutputPort());

        synth.start();
    }

    @After
    public void tearDown() {
        synth.stop();
        eg = null;
    }

    @Test
    public void testAttackChanged() {
        eg.attackChanged(3);
        assertEquals(3, eg.getAttack(), 0.001);
    }

    @Test
    public void testDecayChanged() {
        eg.decayChanged(0.5);
        assertEquals(0.5, eg.getDecay(), 0.001);
    }

    @Test
    public void testSustainChanged() {
        eg.sustainChanged(50);
        assertEquals(50, eg.getSustain(), 0.001);
    }

    @Test
    public void testReleaseChanged() {
        eg.releaseChanged(17);
        assertEquals(17, eg.getRelease(), 0.001);
    }

}
