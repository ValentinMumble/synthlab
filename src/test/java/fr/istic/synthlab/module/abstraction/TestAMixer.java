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
 * Test class for AMixer
 * 
 * @author valentinmumble
 * 
 */
public class TestAMixer {

    private static Synthesizer synth = JSyn.createSynthesizer();
    private UnitOscillator osc1;
    private UnitOscillator osc2;
    private UnitOscillator osc3;
    private UnitOscillator osc4;
    private IAMixer mixer;

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

        mixer = new AMixer(synth);
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
    public void testGetAttenuation1() throws Exception {
        assertEquals(0, mixer.getAttenuation(1), 0.0001);
    }

    @Test()
    public void testSetAttenuation1() throws Exception {
        mixer.setAttenuation(1,5);
        assertEquals(5, mixer.getAttenuation(1), 0.0001);

        mixer.setAttenuation(1,-70);
        assertEquals(AMixer.ATTENUATION_MIN_DB, mixer.getAttenuation(1), 0.0001);

        mixer.setAttenuation(1,42);
        assertEquals(AMixer.ATTENUATION_MAX_DB, mixer.getAttenuation(1), 0.0001);
        
    }

    @Test
    public void testGetAttenuation2() throws Exception {
        assertEquals(0, mixer.getAttenuation(1), 0.0001);
    }

    @Test
    public void testSetAttenuation2() throws Exception {
        mixer.setAttenuation(2,10);
        assertEquals(10, mixer.getAttenuation(2), 0.0001);

        mixer.setAttenuation(2,-70);
        assertEquals(AMixer.ATTENUATION_MIN_DB, mixer.getAttenuation(2), 0.0001);

        mixer.setAttenuation(2,42);
        assertEquals(AMixer.ATTENUATION_MAX_DB, mixer.getAttenuation(2), 0.0001);
    }
    
    @Test
    public void testGetAttenuation3() throws Exception {
        assertEquals(0, mixer.getAttenuation(3), 0.0001);
    }

    @Test
    public void testSetAttenuation3() throws Exception {
        mixer.setAttenuation(3,8);
        assertEquals(8, mixer.getAttenuation(3), 0.0001);

        mixer.setAttenuation(3,-70);
        assertEquals(AMixer.ATTENUATION_MIN_DB, mixer.getAttenuation(3), 0.0001);

        mixer.setAttenuation(3,42);
        assertEquals(AMixer.ATTENUATION_MAX_DB, mixer.getAttenuation(3), 0.0001);
    }
    
    @Test
    public void testGetAttenuation4() throws Exception {
        assertEquals(0, mixer.getAttenuation(4), 0.0001);
    }

    @Test
    public void testSetAttenuation4() throws Exception {
        mixer.setAttenuation(4,5);
        assertEquals(5, mixer.getAttenuation(4), 0.0001);

        mixer.setAttenuation(4,-70);
        assertEquals(AMixer.ATTENUATION_MIN_DB, mixer.getAttenuation(4), 0.0001);

        mixer.setAttenuation(4,42);
        assertEquals(AMixer.ATTENUATION_MAX_DB, mixer.getAttenuation(4), 0.0001);
    }
    
    @Test
    public void testGetInputsPort() {
        assertTrue(mixer.getInputPort(1) instanceof UnitInputPort);
        assertTrue(mixer.getInputPort(2) instanceof UnitInputPort);
        assertTrue(mixer.getInputPort(3) instanceof UnitInputPort);
        assertTrue(mixer.getInputPort(4) instanceof UnitInputPort);
    }
    
    @Test
    public void testGetOutputPort() {
        assertTrue(mixer.getOutputPort() instanceof UnitOutputPort);
    }

}