package fr.istic.synthlab.module.abstraction;

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
 * Test class for AOscilloscope
 * 
 * @author valentinmumble
 * 
 */
public class TestAOscilloscope {

    private static Synthesizer synth = JSyn.createSynthesizer();
    private UnitOscillator osc;
    private IAOscilloscope scope;

    @Before
    public void setUp() {
        // Add a tone generator.
        osc = new SineOscillator();
        osc.frequency.set(345.0);
        osc.amplitude.set(0.6);
        synth.add(osc);

        scope = new AOscilloscope(synth);
        osc.output.connect(scope.getInputPort());

        synth.start();
    }

    @After
    public void tearDown() {
        synth.stop();
        scope = null;
    }

    @Test
    public void testGetInputPort() {
        assertTrue(scope.getInputPort() instanceof UnitInputPort);
    }

    @Test
    public void testGetOutputPort() {
        assertTrue(scope.getOutputPort() instanceof UnitOutputPort);
    }

}
