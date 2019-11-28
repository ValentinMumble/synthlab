package fr.istic.synthlab.module.abstraction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitOscillator;

import fr.istic.synthlab.module.abstraction.IAKeyboard.Note;

/**
 * Test class for AKeyboard.
 * 
 * @author Florent
 * 
 */
public class TestAKeyboard {

    private static Synthesizer synth = JSyn.createSynthesizer();
    private UnitOscillator osc1;
    private UnitOscillator osc2;
    private IAKeyboard keyboard;

    @Before
    public void setUp() {
        // Add a tone generator.
        osc1 = new SineOscillator();
        osc1.frequency.set(345.0);
        osc1.amplitude.set(0.6);
        synth.add(osc1);
        
     // Add a tone generator.
        osc2 = new SineOscillator();
        osc2.frequency.set(345.0);
        osc2.amplitude.set(0.6);
        synth.add(osc2);

        keyboard = new AKeyboard(synth);
        osc1.frequency.connect(keyboard.getGatePort());
        osc2.frequency.connect(keyboard.getControlVoltagePort());
        synth.start();
    }

    @After
    public void tearDown() {
        synth.stop();
        keyboard = null;
    }

    @Test
    public void testGetGatePort() {
        assertTrue(keyboard.getGatePort() instanceof UnitOutputPort);
    }

    @Test
    public void testGetControlVoltagePort() {
        assertTrue(keyboard.getControlVoltagePort() instanceof UnitOutputPort);
    }
    
    @Test
    public void testGetOctave() {
        assertEquals(0, keyboard.getOctave(), 0);
    }

    @Test
    public void testSetOctave() {
        keyboard.setOctave(3);
        assertEquals(3, keyboard.getOctave(), 0);
    }

    @Test
    public void testGetNote() {
        assertEquals(0, keyboard.getNote(), 0);
    }

    @Test
    public void testSetNoteDouble() {
        keyboard.setNote(3);
        assertEquals(3, keyboard.getNote(), 0);
    }
    
    @Test
    public void testIncreaseOctave() {
        keyboard.increaseOctave();
        assertEquals(1, keyboard.getOctave(),  0);
    }
    
    @Test
    public void testDecreaseOctave() {
        keyboard.decreaseOctave();
        assertEquals(-1, keyboard.getOctave(), 0);
    }
    
    @Test
    public void testSetNote() {
        keyboard.setNote(Note.Sol);
        assertEquals(-AKeyboard.MAJOR_SECOND_LEVEL, keyboard.getNote(), 0.01);
    }
}
