package fr.istic.synthlab.module.abstraction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import junit.framework.TestCase;

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
 * Test class for AVoltageControlledFilterLowPass.
 * 
 * @author valentinmumble
 * 
 */
public class TestAVoltageControlledFilterLowPass {

    private static Synthesizer synth = JSyn.createSynthesizer();
    private UnitOscillator osc;
    private IAVoltageControlledFilterLowPass vcflp;

    /**
     * Perform pre-test initialization.
     *
     * @throws Exception
     *
     * @see TestCase#setUp()
     */
    @Before
    public void setUp() {
        // Add a tone generator.
        osc = new SineOscillator();
        osc.frequency.set(345.0);
        osc.amplitude.set(0.6);
        synth.add(osc);

        vcflp = new AVoltageControlledFilterLowPass(synth);
        osc.output.connect(vcflp.getInputPort());

        synth.start();
    }

    /**
     * Perform post-test clean up.
     *
     * @throws Exception
     *
     * @see TestCase#tearDown()
     */
    @After
    public void tearDown() {
        synth.stop();
        vcflp = null;
    }

    /**
     * Run the UnitInputPort getInputPort() method test.
     */
    @Test
    public void testGetInputPort() {
        assertTrue(vcflp.getInputPort() instanceof UnitInputPort);
    }
    
    /**
     * Run the UnitInputPort getFreqModInputPort() method test.
     */
    @Test
    public void testGetFreqModInputPort() {
        assertTrue(vcflp.getFreqModInputPort() instanceof UnitInputPort);
    }
    
    /**
     * Run the UnitOutputPort getOutputPort() method test.
     */
    @Test
    public void testGetOutputPort() {

        assertTrue(vcflp.getOutputPort() instanceof UnitOutputPort);
    }
    
    /**
     * Run the double getCutoffFrequency() method test.
     */
    @Test
    public void testGetCutoffFrequency() {
        assertEquals(AVoltageControlledFilterLowPass.DEFAULT_CUTOFF_FREQUENCY_HZ,
                vcflp.getCutoffFrequency(), 0);
    }

    /**
     * Run the double getResonance() method test.
     */
    @Test
    public void testGetResonance() {
        assertEquals(AVoltageControlledFilterLowPass.DEFAULT_RESONANCE,
                vcflp.getResonance(), 0.000001);
    }
    
    /**
     * Run the void setCutoffFrequency(double) method test.
     */
    @Test
    public void testSetCutoffFrequency() {

        vcflp.setCutoffFrequency(1);
        assertEquals(1, vcflp.getCutoffFrequency(), 0.000001);
        vcflp.setCutoffFrequency(2);
        assertEquals(2, vcflp.getCutoffFrequency(), 0.000001);
        vcflp.setCutoffFrequency(5);
        assertEquals(5, vcflp.getCutoffFrequency(), 0.000001);
        vcflp.setCutoffFrequency(7);
        assertEquals(7, vcflp.getCutoffFrequency(), 0.000001);
        vcflp.setCutoffFrequency(10);
        assertEquals(10, vcflp.getCutoffFrequency(), 0.000001);
        vcflp.setCutoffFrequency(15);
        assertEquals(15, vcflp.getCutoffFrequency(), 0.000001);
        vcflp.setCutoffFrequency(AVoltageControlledFilterLowPass.MAX_CUTOFF_FREQUENCY_HZ);
        assertEquals(AVoltageControlledFilterLowPass.INFINITY_FREQUENCY_HZ,
                vcflp.getCutoffFrequency(), 0.000001);
    }
    
    /**
     * Run the void setResonance(double) method test.
     */
    @Test
    public void testSetResonance() {
        vcflp.setResonance(0.1);
        assertEquals(0.1, vcflp.getResonance(), 0.000001);
        vcflp.setResonance(0.2);
        assertEquals(0.2, vcflp.getResonance(), 0.000001);
        vcflp.setResonance(0.5);
        assertEquals(0.5, vcflp.getResonance(), 0.000001);
        vcflp.setResonance(1.1);
        assertEquals(1.1, vcflp.getResonance(), 0.000001);
        vcflp.setResonance(2.0);
        assertEquals(2.0, vcflp.getResonance(), 0.000001);
    }
}
