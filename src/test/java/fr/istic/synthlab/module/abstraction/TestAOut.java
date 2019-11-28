package fr.istic.synthlab.module.abstraction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitOscillator;

/**
 * Test class for AOut
 * 
 * @author valentinmumble
 * 
 */
public class TestAOut {

    private static Synthesizer synth = JSyn.createSynthesizer();
    private UnitOscillator osc;
    private IAOut out;

    @Before
    public void setUp() {
        // Add a tone generator.
        osc = new SineOscillator();
        osc.frequency.set(345.0);
        osc.amplitude.set(0.6);
        synth.add(osc);

        out = new AOut(synth);
        osc.output.connect(out.getInputPort());

        synth.start();
    }

    @After
    public void tearDown() {
        synth.stop();
        out = null;
    }

    @Test
    public void testIsMute() {
        assertFalse(out.isMute());
    }

    @Test
    public void testMute() {
        out.mute();
        assertTrue(out.isMute());
    }

    @Test
    public void testUnmute() {
        out.unmute();
        assertFalse(out.isMute());
    }

    @Test
    public void testGetAttenuation() {
        assertEquals(0, out.getAttenuation(), 0.0001);
    }

    @Test
    public void testSetAttenuation() {
        out.setAttenuation(5);
        assertEquals(5, out.getAttenuation(), 0.0001);

        out.setAttenuation(-70);
        assertEquals(AOut.ATTENUATION_MIN_DB, out.getAttenuation(), 0.0001);

        out.setAttenuation(42);
        assertEquals(AOut.ATTENUATION_MAX_DB, out.getAttenuation(), 0.0001);
    }

    @Test
    public void testGetInputPort() {
        assertTrue(out.getInputPort() instanceof UnitInputPort);
    }

}