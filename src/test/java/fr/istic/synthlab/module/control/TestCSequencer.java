package fr.istic.synthlab.module.control;

import junit.framework.TestCase;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitOscillator;

import fr.istic.synthlab.module.presentation.IPModule;
import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * The class <code>TestCSequencer</code> contains tests for
 * the class {@link <code>CSequencer</code>}
 *
 * @pattern JUnit Test Case
 *
 * @author Favereau
 *
 * @version $Revision$
 */
public class TestCSequencer extends TestCase {

    private static Synthesizer synth = JSyn.createSynthesizer();
    private UnitOscillator osc;
    private CSequencer seq;
    private LineOut out;
    
    /**
     * Construct new test instance
     *
     * @param name the test name
     */
    public TestCSequencer(String name) {
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
        
        osc = new SineOscillator();
        osc.frequency.set(440.0);
        osc.amplitude.set(2);
        synth.add(osc);
        
        try {
            out = new LineOut();
            synth.add(out);            
        } catch (Exception e) {
            System.out.println("LineOut doesn't exist");
        }
        
        seq = new CSequencer(synth);
        
        osc.getOutput().connect(seq.getGatePort());
        seq.getOutputPort().connect(out.input);
        
        out.start();
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
        osc = null;
        seq = null;
        out = null;
    }
 
    /**
     * Run the void pitchChanged(int, double) method test
     */
    public void testPitchChanged() {
            for (int i = 0; i< 8; i++){
                seq.pitchChanged(i+1, (i-1)/10);
                assertEquals(seq.getPitch(i+1), (double)((i-1)/10));
            }
    }


    /**
     * Run the ICFemaleJack getGate() method test
     */
    public void testGetGate() {
        assertTrue(seq.getGate() instanceof ICFemaleJack);
    }

    /**
     * Run the ICFemaleJack getOutput() method test
     */
    public void testGetOutput() {
        assertTrue(seq.getOutput() instanceof ICFemaleJack);
    }

    /**
     * Run the IPModule getPresentation() method test
     */
    public void testGetPresentation() {
        assertTrue(seq.getPresentation() instanceof IPModule);
    }
}
