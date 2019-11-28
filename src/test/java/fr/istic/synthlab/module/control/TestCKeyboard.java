package fr.istic.synthlab.module.control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitOscillator;

import fr.istic.synthlab.module.abstraction.AKeyboard;
import fr.istic.synthlab.module.abstraction.IAKeyboard.Note;
import fr.istic.synthlab.module.presentation.IPModule;
import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * Test class for CKeyboard
 * 
 * @author Florent
 * 
 */
public class TestCKeyboard {

    private static Synthesizer synth = JSyn.createSynthesizer();
    private UnitOscillator osc1;
    private UnitOscillator osc2;
    private ICKeyboard keyboard;

    @Before
    public void setUp() {
        osc1 = new SineOscillator();
        osc2 = new SineOscillator();
        synth.add(osc1);
        synth.add(osc2);

        osc2.frequency.setup(0.001, 1, 10.0);

        osc1.frequency.setup(50.0, 440.0, 2000.0);

        keyboard = new CKeyboard(synth);

        osc2.frequency.connect(keyboard.getGatePort());
        osc1.frequency.connect(keyboard.getControlVoltagePort());

        synth.start();
    }

    @After
    public void tearDown() {
        synth.stop();
        keyboard = null;
    }

    /**
     * Run the ICFemaleJack getControlVoltagePort() method test
     */
    @Test
    public void testGetControlVoltage() {
        assertTrue(keyboard.getControlVoltage() instanceof ICFemaleJack);
    }
    
    
    /**
     * Run the IPModule getPresentation() method test
     */
    @Test
    public void testGetPresentation() {
        assertTrue(keyboard.getPresentation() instanceof IPModule);
    }
    
    /**
     * Run the keyPressed(Note note) method test
     */
    @Test
    public void testKeyPressed() {
        keyboard.keyPressed(Note.Sol);
        assertEquals(-AKeyboard.MAJOR_SECOND_LEVEL, keyboard.getNote(), 0);
    }
    
    /**
     * Run the octaveIncreased() method test
     */
    @Test
    public void testOctaveIncreased() {
        keyboard.octaveIncreased();
        assertEquals(1, keyboard.getOctave(), 0);
    }
    
    /**
     * Run the octaveDecreased() method test
     */
    @Test
    public void testOctaveDecreased() {
        keyboard.octaveDecreased();
        assertEquals(-1, keyboard.getOctave(), 0);
    }
    
    /**
     * Run the octaveChanged(double o) method test
     */
    @Test
    public void testOctaveChanged() {
        keyboard.octaveChanged(3);
        assertEquals(3, keyboard.getOctave(), 0);
    }

    /**
     * Run the noteChanged(double n) method test
     */
    @Test
    public void testNoteChanged() {
        keyboard.noteChanged(2);
        assertEquals(2, keyboard.getNote(), 0);
    }
}
