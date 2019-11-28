package fr.istic.synthlab.filter;

import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

/**
 * Level generator.
 * @author Florent
 *
 */
public class LevelGenerator extends UnitGenerator {

    /**
     * Default level.
     */
    public static final double DEFAULT_LEVEL = 0;

    /**
     * Output.
     */
    public UnitOutputPort output;

    /**
     * Level.
     */
    private double level;

    /**
     * Constructor of the Level Generator.
     */
    public LevelGenerator() {
        output = new UnitOutputPort();
        addPort(output);
        level = DEFAULT_LEVEL;
    }

    @Override
    public void generate(int start, int limit) {
        double[] outputs = output.getValues();

        for (int i = start; i < limit; i++) {
            outputs[i] = level;
        }
    }

    /**
     * Level getter.
     * @return level
     */
    public double getLevel() {
        return level;
    }

    /**
     * Level setter
     * @param l
     *      level
     */
    public void setLevel(double l) {
        level = l;
    }
}
