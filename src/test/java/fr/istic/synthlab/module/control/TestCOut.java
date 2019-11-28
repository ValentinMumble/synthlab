package fr.istic.synthlab.module.control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
 * Test class for COut
 * 
 * @author valentinmumble
 * 
 */
public class TestCOut {

    private static Synthesizer synth = JSyn.createSynthesizer();
    private UnitOscillator osc;
    private ICOut out;

    @Before
    public void setUp() {
        // Add a tone generator.
        osc = new SineOscillator();
        osc.frequency.set(345.0);
        osc.amplitude.set(0.6);
        synth.add(osc);

        out = new COut(synth);
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
    public void testToggleMute() {
        out.toggleMute();
        assertTrue(out.isMute());
        out.toggleMute();
        assertFalse(out.isMute());
    }

    @Test
    public void testGetAttenuation() {
        assertEquals(0, out.getAttenuation(), 0.0001);
    }

    @Test
    public void testSetAttenuation() {
        out.setAttenuation(6);
        assertEquals(6, out.getAttenuation(), 0.0001);
    }

    @Test
    public void testAttenuationChanged() {
        out.attenuationChanged(3);
        assertEquals(3, out.getAttenuation(), 0.0001);
    }

    @Test
    public void testGetLineIn() {
        assertTrue(out.getLineIn() instanceof ICFemaleJack);
    }

}