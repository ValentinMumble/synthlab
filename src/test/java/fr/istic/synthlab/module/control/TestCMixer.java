package fr.istic.synthlab.module.control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitOscillator;

import fr.istic.synthlab.util.control.ICFemaleJack;

public class TestCMixer {

    private static Synthesizer synth = JSyn.createSynthesizer();
    private UnitOscillator osc1;
    private UnitOscillator osc2;
    private UnitOscillator osc3;
    private UnitOscillator osc4;
    private ICMixer mixer;

    @Before
    public void setUp() {
        // Add a tone generator.
        osc1 = new SineOscillator();
        osc1.frequency.set(345.0);
        osc1.amplitude.set(0.6);
        synth.add(osc1);
        // Add a tone generator.
        osc2 = new SineOscillator();
        osc2.frequency.set(360.0);
        osc2.amplitude.set(0.7);
        synth.add(osc2);
        // Add a tone generator.
        osc3 = new SineOscillator();
        osc3.frequency.set(200.0);
        osc3.amplitude.set(0.8);
        synth.add(osc3);
        // Add a tone generator.
        osc4 = new SineOscillator();
        osc4.frequency.set(250.0);
        osc4.amplitude.set(0.6);
        synth.add(osc4);

        mixer = new CMixer(synth);
        osc1.output.connect(mixer.getInputPort(1));
        osc2.output.connect(mixer.getInputPort(2));
        osc3.output.connect(mixer.getInputPort(3));
        osc4.output.connect(mixer.getInputPort(4));

        synth.start();
    }

    @After
    public void tearDown() {
        synth.stop();
        mixer = null;
    }

    @Test
    public void testGetAttenuation1() {
        assertEquals(0, mixer.getAttenuation(1), 0.0001);
    }

    @Test
    public void testSetAttenuation1() {
        try {
            mixer.setAttenuation(1, 2);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertEquals(2, mixer.getAttenuation(1), 0.0001);
    }

    @Test
    public void testAttenuation1Changed() {
        mixer.attenuationChanged(1, 3);
        assertEquals(3, mixer.getAttenuation(1), 0.1);
    }

    @Test
    public void testGetAttenuation2() {
        assertEquals(0, mixer.getAttenuation(2), 0.0001);
    }

    @Test
    public void testSetAttenuation2() {
        try {
            mixer.setAttenuation(2, 1);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertEquals(1, mixer.getAttenuation(2), 0.0001);
    }

    @Test
    public void testAttenuation2Changed() {
        mixer.attenuationChanged(2, 4);
        assertEquals(4, mixer.getAttenuation(2), 0.1);
    }

    @Test
    public void testGetAttenuation3() {
        assertEquals(0, mixer.getAttenuation(3), 0.0001);
    }

    @Test
    public void testSetAttenuation3() {
        try {
            mixer.setAttenuation(3, 5);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertEquals(5, mixer.getAttenuation(3), 0.0001);
    }

    @Test
    public void testAttenuation3Changed() {
        mixer.attenuationChanged(3, 9);
        assertEquals(9, mixer.getAttenuation(3), 0.1);
    }

    @Test
    public void testGetAttenuation4() {
        assertEquals(0, mixer.getAttenuation(4), 0.0001);
    }

    @Test
    public void testSetAttenuation4() {
        try {
            mixer.setAttenuation(4, 10);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertEquals(10, mixer.getAttenuation(4), 0.0001);
    }

    @Test
    public void testAttenuation4Changed() {
        mixer.attenuationChanged(4, 11);
        assertEquals(11, mixer.getAttenuation(4), 0.1);
    }

    @Test
    public void testGetInput() {
        assertTrue(mixer.getInput1() instanceof ICFemaleJack);
        assertTrue(mixer.getInput2() instanceof ICFemaleJack);
        assertTrue(mixer.getInput3() instanceof ICFemaleJack);
        assertTrue(mixer.getInput4() instanceof ICFemaleJack);
    }

    @Test
    public void testGetOutput() {
        assertTrue(mixer.getOutput() instanceof ICFemaleJack);
    }
}
