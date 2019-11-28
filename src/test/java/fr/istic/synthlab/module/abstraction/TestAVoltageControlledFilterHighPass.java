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
 * Test class for AVoltageControlledFilterHighPass.
 * 
 * @author Florent
 */
public class TestAVoltageControlledFilterHighPass {
    
    private static Synthesizer synth = JSyn.createSynthesizer();
    private UnitOscillator osc;
    private IAVoltageControlledFilterHighPass vcfhp;

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

        vcfhp = new AVoltageControlledFilterHighPass(synth);
        osc.output.connect(vcfhp.getInputPort());

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
        vcfhp = null;
    }

    /**
     * Run the UnitInputPort getInputPort() method test.
     */
    @Test
    public void testGetInputPort() {
        assertTrue(vcfhp.getInputPort() instanceof UnitInputPort);
    }
    
    /**
     * Run the UnitInputPort getFreqModInputPort() method test.
     */
    @Test
    public void testGetFreqModInputPort() {
        assertTrue(vcfhp.getFreqModInputPort() instanceof UnitInputPort);
    }
    
    /**
     * Run the UnitOutputPort getOutputPort() method test.
     */
    @Test
    public void testGetOutputPort() {

        assertTrue(vcfhp.getOutputPort() instanceof UnitOutputPort);
    }
    
    /**
     * Run the double getCutoffFrequency() method test.
     */
    @Test
    public void testGetCutoffFrequency() {
        assertEquals(AVoltageControlledFilterHighPass.DEFAULT_CUTOFF_FREQUENCY_HZ,
                vcfhp.getCutoffFrequency(), 0);
    }
    
    /**
     * Run the void setCutoffFrequency(double) method test.
     */
    @Test
    public void testSetCutoffFrequency() {

        vcfhp.setCutoffFrequency(1);
        assertEquals(1, vcfhp.getCutoffFrequency(), 0.000001);
        vcfhp.setCutoffFrequency(2);
        assertEquals(2, vcfhp.getCutoffFrequency(), 0.000001);
        vcfhp.setCutoffFrequency(5);
        assertEquals(5, vcfhp.getCutoffFrequency(), 0.000001);
        vcfhp.setCutoffFrequency(7);
        assertEquals(7, vcfhp.getCutoffFrequency(), 0.000001);
        vcfhp.setCutoffFrequency(10);
        assertEquals(10, vcfhp.getCutoffFrequency(), 0.000001);
        vcfhp.setCutoffFrequency(15);
        assertEquals(15, vcfhp.getCutoffFrequency(), 0.000001);
    }
    
}
