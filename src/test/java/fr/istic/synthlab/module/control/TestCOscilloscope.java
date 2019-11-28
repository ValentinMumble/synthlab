package fr.istic.synthlab.module.control;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitOscillator;

import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * Test class for COscilloscope
 * 
 * @author valentinmumble
 * 
 */
public class TestCOscilloscope {

    private static Synthesizer synth = JSyn.createSynthesizer();
    private UnitOscillator osc;
    private ICOscilloscope scope;

    @Before
    public void setUp() {
        // Add a tone generator.
        osc = new SineOscillator();
        osc.frequency.set(345.0);
        osc.amplitude.set(0.6);
        synth.add(osc);

        scope = new COscilloscope(synth);
        osc.output.connect(scope.getInputPort());

        synth.start();
    }

    @After
    public void tearDown() {
        synth.stop();
        scope = null;
    }

    @Test
    public void testGetInput() {
        assertTrue(scope.getInput() instanceof ICFemaleJack);
    }

    @Test
    public void testGetOutput() {
        assertTrue(scope.getOutput() instanceof ICFemaleJack);
    }

}
