package fr.istic.synthlab.module.abstraction;

import junit.framework.TestCase;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.LineOut;

/**
 * The class <code>TestAWhiteNoise</code> contains tests for the class {@link
 * <code>AWhiteNoise</code>}
 *
 * @pattern JUnit Test Case
 *
 * @generatedBy CodePro at 12/02/14 08:56
 *
 * @author Favereau
 *
 * @version $Revision$
 */
public class TestAWhiteNoise extends TestCase {

    Synthesizer s;
    AWhiteNoise noise;
    
    LineOut sortie;
    /**
     * Construct new test instance
     *
     * @param name the test name
     */
    public TestAWhiteNoise(String name) {
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

        s = JSyn.createSynthesizer();
        noise = new AWhiteNoise(s);
        sortie = new LineOut();
        
        s.add(sortie);
        
        noise.getOutputPort().connect(sortie.input);
        sortie.start();
        s.start();
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
        s.stop();
        s = null;
        noise = null;
        sortie = null;
    }

    /**
     * Run the boolean getEnable() method test
     */
    public void testGetEnable() {
        
       assertEquals(noise.getEnable(), true);
       noise.setEnable(false);
       assertEquals(noise.getEnable(), false);
    }

    /**
     * Run the UnitOutputPort getOutputPort() method test
     */
    public void testGetOutputPort() {
        assertTrue(noise.getOutputPort() instanceof UnitOutputPort);
    }

    /**
     * Run the void setEnable(boolean) method test
     */
    public void testSetEnable() {
        
        
        noise.setEnable(false);
        assertEquals(noise.getEnable(), false);
        noise.setEnable(true);
        assertEquals(noise.getEnable(), true);
        noise.setEnable(true);
        assertEquals(noise.getEnable(), true);
        noise.setEnable(true);
        assertEquals(noise.getEnable(), true);
        noise.setEnable(false);
        assertEquals(noise.getEnable(), false);
        noise.setEnable(false);
        assertEquals(noise.getEnable(), false);
        noise.setEnable(false);
        assertEquals(noise.getEnable(), false);
        noise.setEnable(true);
        assertEquals(noise.getEnable(), true);
    }
}
