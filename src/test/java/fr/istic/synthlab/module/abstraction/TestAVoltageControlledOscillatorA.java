package fr.istic.synthlab.module.abstraction;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;

/**
 * The class <code>TestAVoltageControlledOscillatorA</code> contains tests for
 * the class {@link <code>AVoltageControlledOscillatorA</code>}.
 *
 * @pattern JUnit Test Case
 *
 * @author Favereau
 *
 * @version $Revision$
 */
public class TestAVoltageControlledOscillatorA extends TestCase {


    private static Synthesizer synth = JSyn.createSynthesizer();
    private static IAVoltageControlledOscillatorA generator;


    /**
     * Construct new test instance.
     *
     * @param name the test name
     */
    public TestAVoltageControlledOscillatorA(String name) {
        super(name);

    }

    /**
     * Perform pre-test initialization.
     *
     * @throws Exception
     *
     * @see TestCase#setUp()
     */
    @Before
    protected void setUp() throws Exception {
        super.setUp();
        generator = new AVoltageControlledOscillatorA(synth);


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
    protected void tearDown() throws Exception {
        super.tearDown();

        synth.stop();

        generator = null;


    }

    /**
     * Run the double getFrequency() method test.
     */
    @Test
    public void testGetFrequency() {
        assertEquals(generator.getFrequency(), 440.0);
    }

    /**
     * Run the double getAmplitude() method test.
     */
    @Test
    public void testGetAmplitude() {
        assertEquals(5, generator.getAmplitude(), 0.000001);
    }


    /**
     * Run the void setAmplitude(double) method test.
     */
    @Test
    public void testSetAmplitude() {

        generator.setAmplitude(1);
        assertEquals(generator.getAmplitude(), 1, 0.000001);
        generator.setAmplitude(2);
        assertEquals(generator.getAmplitude(), 2, 0.000001);
        generator.setAmplitude(5);
        assertEquals(generator.getAmplitude(), 5, 0.000001);
        generator.setAmplitude(7);
        assertEquals(generator.getAmplitude(), 7, 0.000001);
        generator.setAmplitude(10);
        assertEquals(generator.getAmplitude(), 10, 0.000001);
        generator.setAmplitude(15);
        assertEquals(generator.getAmplitude(), 15, 0.000001);
        generator.setAmplitude(20);
        assertEquals(generator.getAmplitude(), 20, 0.000001);
    }



    /**
     * Run the UnitOutputPort getSortieSin() method test.
     */
    @Test
    public void testGetSortieSin() {

        assertTrue(generator.getSortieSin() instanceof UnitOutputPort);

    }

    /**
     * Run the UnitOutputPort getSortieSqu() method test.
     */
    @Test
    public void testGetSortieSqu() {

        assertTrue(generator.getSortieSqu() instanceof UnitOutputPort);

    }

    /**
     * Run the UnitOutputPort getSortieTri() method test.
     */
    @Test
    public void testGetSortieTri() {

        assertTrue(generator.getSortieTri() instanceof UnitOutputPort);

    }

    /**
     * Run the UnitOutputPort getSortieSaw() method test.
     */
    @Test
    public void testGetSortieSaw() {

        assertTrue(generator.getSortieSaw() instanceof UnitOutputPort);

    }

    /**
     * Run the UnitInputPort getFreqModIN() method test.
     */
    @Test
    public void testGetFreqModIN() {

        assertTrue(generator.getFreqModIN() instanceof UnitInputPort);

    }

    /**
     * Run the void setOctave(int) method test.
     */
    @Test
    public void testSetOctave() {

        generator.setOctave(1);
        assertEquals(generator.getFrequency(), 0.859375);
        generator.setOctave(2);
        assertEquals(generator.getFrequency(), 1.71875);
        generator.setOctave(4);
        assertEquals(generator.getFrequency(), 6.875);
        generator.setOctave(8);
        assertEquals(generator.getFrequency(), 110.0);
        generator.setOctave(12);
        assertEquals(generator.getFrequency(), 1760.0);
        generator.setOctave(16);
        assertEquals(generator.getFrequency(), 28000.0);
        generator.setOctave(10);
        assertEquals(generator.getFrequency(), 440.0);
    }

    /**
     * Run the int getOctave() method test.
     */
    @Test
    public void testGetOctave() {

        generator.setOctave(1);
        assertEquals(generator.getOctave(), 1);
        generator.setOctave(2);
        assertEquals(generator.getOctave(), 2);
        generator.setOctave(4);
        assertEquals(generator.getOctave(), 4);
        generator.setOctave(8);
        assertEquals(generator.getOctave(), 8);
        generator.setOctave(12);
        assertEquals(generator.getOctave(), 12);
        generator.setOctave(16);
        assertEquals(generator.getOctave(), 16);
        generator.setOctave(10);
        assertEquals(generator.getOctave(), 10);
    }

    /**
     * Run the void setOctaveMinusOne() method test.
     */
    @Test
    public void testSetOctaveMinusOne() {
        int actualOct = 10;
        generator.setOctave(actualOct);

        generator.setOctaveMinusOne();
        actualOct--;
        assertEquals(generator.getOctave(), actualOct);
        generator.setOctaveMinusOne();
        actualOct--;
        assertEquals(generator.getOctave(), actualOct);
        generator.setOctaveMinusOne();
        actualOct--;
        assertEquals(generator.getOctave(), actualOct);
        generator.setOctaveMinusOne();
        actualOct--;
        assertEquals(generator.getOctave(), actualOct);

    }

    /**
     * Run the void setOctaveAddOne() method test.
     */
    @Test
    public void testSetOctaveAddOne() {
        int actualOct = 10;
        generator.setOctave(actualOct);

        generator.setOctaveAddOne();
        actualOct++;
        assertEquals(generator.getOctave(), actualOct);
        generator.setOctaveAddOne();
        actualOct++;
        assertEquals(generator.getOctave(), actualOct);
        generator.setOctaveAddOne();
        actualOct++;
        assertEquals(generator.getOctave(), actualOct);
        generator.setOctaveAddOne();
        actualOct++;
        assertEquals(generator.getOctave(), actualOct);
    }

    /**
     * Run the void setFreqFine(double) method test.
     */
    @Test
    public void testSetFreqFine() {

        assertEquals(generator.getFrequency(), 440.0);

        generator.setFreqFine(20);
        assertEquals(generator.getFrequency(), 528.0);
        generator.setFreqFine(-20);
        assertEquals(generator.getFrequency(), 396.0);

        generator.setFreqFine(50);
        assertEquals(generator.getFrequency(), 660.0);
        generator.setFreqFine(-50);
        assertEquals(generator.getFrequency(), 330.0);

        generator.setFreqFine(80);
        assertEquals(generator.getFrequency(), 792.0);
        generator.setFreqFine(-80);
        assertEquals(generator.getFrequency(), 264.0);
    }

    /**
     * Run the double getFreqFine() method test.
     */
    @Test
    public void testGetFreqFine() {
        assertEquals(generator.getFreqFine(), 0.0);

        generator.setFreqFine(20);
        assertEquals(generator.getFreqFine(), 20.0);
        generator.setFreqFine(-20);
        assertEquals(generator.getFreqFine(), -20.0);

        generator.setFreqFine(50);
        assertEquals(generator.getFreqFine(), 50.0);
        generator.setFreqFine(-50);
        assertEquals(generator.getFreqFine(), -50.0);

        generator.setFreqFine(80);
        assertEquals(generator.getFreqFine(), 80.0);
        generator.setFreqFine(-80);
        assertEquals(generator.getFreqFine(), -80.0);
    }

    /**
     * Run the void setFrequency(int, double) method test.
     */
    @Test
    public void testSetFrequency() {

        generator.setFrequency(10, 0.0);
        assertEquals(generator.getFrequency(), 440.0);
        generator.setFrequency(12, 0.0);
        assertEquals(generator.getFrequency(), 1760.0);
        generator.setFrequency(8, 0.0);
        assertEquals(generator.getFrequency(), 110.0);
        generator.setFrequency(14, 0.0);
        assertEquals(generator.getFrequency(), 7040.0);
        generator.setFrequency(5, 0.0);
        assertEquals(generator.getFrequency(), 13.75);
        generator.setFrequency(16, 0.0);
        assertEquals(generator.getFrequency(), 28000.0);
        generator.setFrequency(1, 0.0);
        assertEquals(generator.getFrequency(), 0.859375);

        generator.setFrequency(2, 20.0);
        assertEquals(generator.getFrequency(), 2.0625);
        generator.setFrequency(8, 40.0);
        assertEquals(generator.getFrequency(), 154.0);
        generator.setFrequency(8, -40.0);
        assertEquals(generator.getFrequency(), 88.0);
        generator.setFrequency(12, 75.0);
        assertEquals(generator.getFrequency(), 3080.0);
        generator.setFrequency(12, -75.0);
        assertEquals(generator.getFrequency(), 1100.0);

        generator.setFrequency(1, -100.0);
        assertEquals(generator.getFrequency(), 0.4296875);
        generator.setFrequency(15, 100.0);
        assertEquals(generator.getFrequency(), 28000.0);
        generator.setFrequency(16, 50.0);
        assertEquals(generator.getFrequency(), 28000.0);
        generator.setFrequency(16, -20.0);
        assertEquals(generator.getFrequency(), 25344.0);
    }
}
