package fr.istic.synthlab.module.abstraction;

import static org.junit.Assert.assertEquals;
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
 * Test class for ASequencer.
 * 
 * @author Florent
 * 
 */
public class TestASequencer {

    private static Synthesizer synth = JSyn.createSynthesizer();
    private UnitOscillator osc1;
    private IASequencer sequencer;

    @Before
    public void setUp() {
     // Add a tone generator.
        osc1 = new SineOscillator();
        osc1.frequency.set(345.0);
        osc1.amplitude.set(0.6);
        synth.add(osc1);

        sequencer = new ASequencer(synth);
        osc1.output.connect(sequencer.getGatePort());
        synth.start();
    }

    @After
    public void tearDown() {
        synth.stop();
        sequencer = null;
    }

    @Test
    public void testGetPitch() {
        for (int i = 0; i<7; i++){
        assertEquals(0, sequencer.getPitch(i), 0);
        } 
    }

    @Test
    public void testSetPitch() {
        for (int i = 0; i<7; i++){
            sequencer.setPitch(i, i);
            assertEquals(i, sequencer.getPitch(i), 0);
            } 
    }
    
    @Test
    public void testGetInputPort() {
        assertTrue(sequencer.getGatePort() instanceof UnitInputPort);
    }
    
    @Test
    public void testGetOutputPort() {
        assertTrue(sequencer.getOutputPort() instanceof UnitOutputPort);
    }

}