package fr.istic.synthlab.module.abstraction;

import java.util.EnumMap;
import java.util.Map;

import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitOutputPort;

import fr.istic.synthlab.filter.LevelGenerator;

/**
 * A class the represent the Keyboard module
 * 
 * @author valentinmumble
 * 
 */
public class AKeyboard implements IAKeyboard {

    public static final int MAX_OCTAVE = 4;
    public static final int MIN_OCTAVE = -4;
    public static final int DEFAULT_OCTAVE = 0;
    public static final double GATE_LEVEL = 5D;
    public static final double VOLTS_PER_OCTAVE = 1.0D;
    public static final double DEFAULT_NOTE = 0D;
    public static final double MAJOR_SECOND_LEVEL = VOLTS_PER_OCTAVE / 6;

    private static final Map<Note, Double> noteLevels;
    static {
        noteLevels = new EnumMap<>(Note.class);
        noteLevels.put(Note.Do, -4.5 * MAJOR_SECOND_LEVEL);
        noteLevels.put(Note.Re, -3.5 * MAJOR_SECOND_LEVEL);
        noteLevels.put(Note.Mi, -2.5 * MAJOR_SECOND_LEVEL);
        noteLevels.put(Note.Fa, -2 * MAJOR_SECOND_LEVEL);
        noteLevels.put(Note.Sol, -MAJOR_SECOND_LEVEL);
        noteLevels.put(Note.La, 0.0D);
        noteLevels.put(Note.Si, MAJOR_SECOND_LEVEL);
        noteLevels.put(Note.Do2, 1.5 * MAJOR_SECOND_LEVEL);
        noteLevels.put(Note.DoD, noteLevels.get(Note.Do) + MAJOR_SECOND_LEVEL
                / 2);
        noteLevels.put(Note.ReD, noteLevels.get(Note.Re) + MAJOR_SECOND_LEVEL
                / 2);
        noteLevels.put(Note.FaD, noteLevels.get(Note.Fa) + MAJOR_SECOND_LEVEL
                / 2);
        noteLevels.put(Note.SolD, noteLevels.get(Note.Sol) + MAJOR_SECOND_LEVEL
                / 2);
        noteLevels.put(Note.LaD, noteLevels.get(Note.La) + MAJOR_SECOND_LEVEL
                / 2);
    }

    private Synthesizer synth;
    private LevelGenerator noteGenerator;
    private LevelGenerator gateGenerator;
    private double octave;
    private double note;

    public AKeyboard(Synthesizer syn) {
        synth = syn;

        noteGenerator = new LevelGenerator();
        gateGenerator = new LevelGenerator();
        gateGenerator.setLevel(-GATE_LEVEL);

        synth.add(noteGenerator);
        synth.add(gateGenerator);

        note = DEFAULT_NOTE;
        octave = DEFAULT_OCTAVE * VOLTS_PER_OCTAVE;
    }

    @Override
    public UnitOutputPort getControlVoltagePort() {
        return noteGenerator.output;
    }

    @Override
    public UnitOutputPort getGatePort() {
        return gateGenerator.output;
    }

    @Override
    public double getOctave() {
        return octave;
    }

    @Override
    public void setOctave(double o) {
        if (o <= MAX_OCTAVE * VOLTS_PER_OCTAVE 
                && o >= MIN_OCTAVE * VOLTS_PER_OCTAVE) {
            octave = o;
        }
    }

    @Override
    public void increaseOctave() {
        if (octave < MAX_OCTAVE * VOLTS_PER_OCTAVE) {
            octave += VOLTS_PER_OCTAVE;
        }
    }

    @Override
    public void decreaseOctave() {
        if (octave > MIN_OCTAVE * VOLTS_PER_OCTAVE) {
            octave -= VOLTS_PER_OCTAVE;
        }
    }

    @Override
    public void playNote() {
        double noteLevel = octave + note;
        noteGenerator.setLevel(noteLevel);
        gateGenerator.setLevel(GATE_LEVEL);
    }

    @Override
    public double getNote() {
        return note;
    }

    @Override
    public void setNote(double n) {
        note = n;
        playNote();
    }

    @Override
    public void setNote(Note note) {
        setNote(noteLevels.get(note));
    }

    @Override
    public void stopNote() {
        gateGenerator.setLevel(-GATE_LEVEL);
    }

}
