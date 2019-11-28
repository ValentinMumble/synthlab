package fr.istic.synthlab.module.abstraction;

import junit.framework.TestCase;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;

/**
 * The class <code>TestAVoltageControlledAmplifier</code> contains tests for
 * the class {@link <code>AVoltageControlledAmplifier</code>}
 *
 * @pattern JUnit Test Case
 *
 * @author Favereau
 *
 * @version $Revision$
 */
public class TestAVoltageControlledAmplifier extends TestCase {

    private static Synthesizer synth = JSyn.createSynthesizer();
    private static IAVoltageControlledAmplifier ampli;



    /**
     * Construct new test instance
     *
     * @param name the test name
     */
    public TestAVoltageControlledAmplifier(String name) {
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

        ampli = new AVoltageControlledAmplifier(synth);

        synth.start();
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

        synth.stop();

        ampli = null;
    }

    /**
     * Run the void setAmplification(double) method test
     */
    public void testSetAmplification() {

        ampli.setAmplification(6.0);
        assertEquals(ampli.getAmplification(), 6.0);

        ampli.setAmplification(-6.0);
        assertEquals(ampli.getAmplification(), -6.0);

        ampli.setAmplification(60.0);
        assertEquals(ampli.getAmplification(), 60.0);

        ampli.setAmplification(-60.0);
        assertEquals(ampli.getAmplification(), -30.0);

        ampli.setAmplification(120.0);
        assertEquals(ampli.getAmplification(), 60.0);

        ampli.setAmplification(-120.0);
        assertEquals(ampli.getAmplification(), -30.0);
    }

    /**
     * Run the double getAmplification() method test
     */
    public void testGetAmplification() {

        assertEquals(ampli.getAmplification(), 0.0);

        ampli.setAmplification(6.0);
        assertEquals(ampli.getAmplification(), 6.0);

        ampli.setAmplification(-6.0);
        assertEquals(ampli.getAmplification(), -6.0);

        ampli.setAmplification(60.0);
        assertEquals(ampli.getAmplification(), 60.0);

        ampli.setAmplification(-30.0);
        assertEquals(ampli.getAmplification(), -30.0);

        ampli.setAmplification(120.0);
        assertEquals(ampli.getAmplification(), 60.0);

        ampli.setAmplification(-120.0);
        assertEquals(ampli.getAmplification(), -30.0);
    }

    /**
     * Run the UnitInputPort getInputPort() method test
     */
    public void testGetInputPort() {
        assertTrue(ampli.getInputPort() instanceof UnitInputPort);
    }

    /**
     * Run the UnitInputPort getInputAmpModul() method test
     */
    public void testGetInputAmpModul() {
        assertTrue(ampli.getInputAmpModul() instanceof UnitInputPort);
    }

    /**
     * Run the UnitOutputPort getOutputPort() method test
     */
    public void testGetOutputPort() {
        assertTrue(ampli.getOutputPort() instanceof UnitOutputPort);
    }
}
