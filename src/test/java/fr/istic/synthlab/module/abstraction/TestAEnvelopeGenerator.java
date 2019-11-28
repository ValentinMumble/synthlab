package fr.istic.synthlab.module.abstraction;

import static org.junit.Assert.assertEquals;
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
 * Test class for AEnvelopeGenerator
 * 
 * @author valentinmumble
 * 
 */
public class TestAEnvelopeGenerator {

    private static Synthesizer synth = JSyn.createSynthesizer();
    private UnitOscillator osc;
    private UnitOscillator gatingOsc;
    private IAEnvelopeGenerator eg;

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

        eg = new AEnvelopeGenerator(synth);

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
    public void testGetGatePort() {
        assertTrue(eg.getGatePort() instanceof UnitInputPort);
    }

    @Test
    public void testGetOutputPort() {
        assertTrue(eg.getOutputPort() instanceof UnitOutputPort);
    }

    @Test
    public void testSetAttack() {
        eg.setAttack(3);
        assertEquals(3, eg.getAttack(), 0.001);

        eg.setAttack(-1);
        assertEquals(AEnvelopeGenerator.ATTACK_MIN_S, eg.getAttack(), 0.001);

        eg.setAttack(42);
        assertEquals(AEnvelopeGenerator.ATTACK_MAX_S, eg.getAttack(), 0.001);
    }

    @Test
    public void testSetDecay() {
        eg.setDecay(3);
        assertEquals(3, eg.getDecay(), 0.001);

        eg.setDecay(-1);
        assertEquals(AEnvelopeGenerator.DECAY_MIN_S, eg.getDecay(), 0.001);

        eg.setDecay(42);
        assertEquals(AEnvelopeGenerator.DECAY_MAX_S, eg.getDecay(), 0.001);
    }

    @Test
    public void testSetSustain() {
        eg.setSustain(0.5);
        assertEquals(0.5, eg.getSustain(), 0.001);

        eg.setSustain(-1);
        assertEquals(AEnvelopeGenerator.SUSTAIN_MIN_LEVEL, eg.getSustain(), 0.001);

        eg.setSustain(2);
        assertEquals(AEnvelopeGenerator.SUSTAIN_MAX_LEVEL, eg.getSustain(), 0.001);
    }

    @Test
    public void testSetRelease() {
        eg.setRelease(0.5);
        assertEquals(0.5, eg.getRelease(), 0.001);

        eg.setRelease(-1);
        assertEquals(AEnvelopeGenerator.RELEASE_MIN_S, eg.getRelease(), 0.001);

        eg.setRelease(42);
        assertEquals(AEnvelopeGenerator.RELEASE_MAX_S, eg.getRelease(), 0.001);
    }
}
