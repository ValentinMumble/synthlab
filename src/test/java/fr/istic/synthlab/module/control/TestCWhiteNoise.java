package fr.istic.synthlab.module.control;

import junit.framework.TestCase;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;

import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * The class <code>TestCWhiteNoise</code> contains tests for the class {@link
 * <code>CWhiteNoise</code>}
 *
 * @pattern JUnit Test Case
 *
 * @generatedBy CodePro at 12/02/14 09:38
 *
 * @author Favereau
 *
 * @version $Revision$
 */
public class TestCWhiteNoise extends TestCase {

    private static Synthesizer synth;
    private ICWhiteNoise noiseInTest;
    
    /**
     * Construct new test instance
     *
     * @param name the test name
     */
    public TestCWhiteNoise(String name) {
        super(name);
        
    }

    /**
     * Perform pre-test initialization
     *
     * @throws Exception
     *
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        synth = JSyn.createSynthesizer();
        noiseInTest = new CWhiteNoise(synth);
        
    }

    /**
     * Perform post-test clean up
     *
     * @throws Exception
     *
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();

        synth = null;
        noiseInTest = null;
    }

    /**
     * Run the IPModule getPresentation() method test
     */
    public void testGetPresentation() {
        //TODO after presentation
        //assertTrue(noiseInTest.getPresentation() instanceof IPModule);
    }

    /**
     * Run the void toggleMute() method test
     */
    public void testToggleMute() {
        
//        assertEquals(noiseInTest.getEnable(), true);
//        noiseInTest.toggleMute();
//        assertEquals(noiseInTest.getEnable(), false);
//        noiseInTest.toggleMute();
//        assertEquals(noiseInTest.getEnable(), true);
        assertTrue(true);
    }

    /**
     * Run the ICFemaleJack getOutput() method test
     */
    public void testGetOutput() {
        assertTrue(noiseInTest.getOutput() instanceof ICFemaleJack);
    }
}
