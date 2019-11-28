package fr.istic.synthlab.module.control;

import junit.framework.TestCase;

import org.junit.Test;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitOscillator;

import fr.istic.synthlab.module.presentation.IPModule;
import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * The class <code>TestCVoltageControlledAmplifier</code> contains tests for
 * the class {@link <code>CVoltageControlledAmplifier</code>}
 *
 * @pattern JUnit Test Case
 *
 * @author Favereau
 *
 * @version $Revision$
 */
public class TestCVoltageControlledAmplifier extends TestCase {

    private static Synthesizer synth = JSyn.createSynthesizer();
    private UnitOscillator oscSign;
    private UnitOscillator oscModul;
    private ICVoltageControlledAmplifier VCA;
    private LineOut sortie;

    /**
     * Construct new test instance
     *
     * @param name the test name
     */
    public TestCVoltageControlledAmplifier(String name) {
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

        oscSign = new SineOscillator();
        oscSign.frequency.set(440.0);
        oscSign.amplitude.set(2);
        synth.add(oscSign);


        oscModul = new SineOscillator();
        oscModul.frequency.set(1.0);
        oscModul.amplitude.set(0.001);
        synth.add(oscModul);

        try {
            sortie = new LineOut();
            synth.add(sortie);            
        } catch (Exception e) {
            System.out.println("Surement un probleme de LineOut inexistant !!!");
        }

        VCA = new CVoltageControlledAmplifier(synth);

        oscSign.getOutput().connect(VCA.getInputPort());
        oscModul.getOutput().connect(VCA.getInputAmpModul());

        VCA.getOutputPort().connect(sortie.input);

        sortie.start();
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

        oscSign = null;
        oscModul = null;
        VCA = null;
        sortie = null;
    }

    /**
     * Run the void amplificationChange(double) method test
     */
    @Test
    public void testAmplificationChange() {

        try {
            Thread.currentThread();
            Thread.sleep(500);
            VCA.amplificationChange(6.0);
            assertEquals(VCA.getAmplification(), 6.0);

            Thread.currentThread();
            Thread.sleep(500);
            VCA.amplificationChange(12.0);
            assertEquals(VCA.getAmplification(), 12.0);

            Thread.currentThread();
            Thread.sleep(500);
            VCA.amplificationChange(18.0);
            assertEquals(VCA.getAmplification(), 18.0);

            Thread.currentThread();
            Thread.sleep(500);
            oscModul.amplitude.set(2);
            VCA.amplificationChange(6.0);
            assertEquals(VCA.getAmplification(), 6.0);

            Thread.currentThread();
            Thread.sleep(500);
            VCA.amplificationChange(12.0);
            assertEquals(VCA.getAmplification(), 12.0);

            Thread.currentThread();
            Thread.sleep(500);
            VCA.amplificationChange(18.0);
            assertEquals(VCA.getAmplification(), 18.0);

            Thread.currentThread();
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Run the ICFemaleJack getPortAMIn() method test
     */
    @Test
    public void testGetPortAMIn() {
        assertTrue(VCA.getPortAMIn() instanceof ICFemaleJack);
    }

    /**
     * Run the ICFemaleJack getPortSignIn() method test
     */
    @Test
    public void testGetPortSignIn() {
        assertTrue(VCA.getPortSignIn() instanceof ICFemaleJack);
    }

    /**
     * Run the ICFemaleJack getPortSignOut() method test
     */
    @Test
    public void testGetPortSignOut() {
        assertTrue(VCA.getPortSignOut() instanceof ICFemaleJack);
    }

    /**
     * Run the IPModule getPresentation() method test
     */
    @Test
    public void testGetPresentation() {
        assertTrue(VCA.getPresentation() instanceof IPModule);
    }
}
