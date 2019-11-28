package fr.istic.synthlab.filter;

import com.jsyn.unitgen.UnitFilter;

/**
 * Frequency Modulation Filter.
 * @author Florent
 *
 */
public class FrequencyModulationFilter extends UnitFilter {

    /**
     * Default minimum frequency in Hertz.
     */
    public static final double DEFAULT_MIN_FREQUENCY_HZ = 40;

    /**
     * Default maximum frequency in Hertz.
     */
    public static final double DEFAULT_MAX_FREQUENCY_HZ = 11000;

    /**
     * Default base frequency in Hertz.
     */
    public static final double DEFAULT_BASE_FREQUENCY_HZ = 1000;

    /**
     * Minimum frequency in Hertz.
     */
    private double minFrequency;

    /**
     * Maximum frequency in Hertz.
     */
    private double maxFrequency;

    /**
     * Base frequency in Hertz.
     */
    private double baseFrequency;

    /**
     * Constructor of the Frequency Modulation Filter.
     */
    public FrequencyModulationFilter() {
        minFrequency = DEFAULT_MIN_FREQUENCY_HZ;
        maxFrequency = DEFAULT_MAX_FREQUENCY_HZ;
        baseFrequency = DEFAULT_BASE_FREQUENCY_HZ;
    }

    @Override
    public void generate(int start, int limit) {
        double[] inputs = input.getValues();
        double[] outputs = output.getValues();

        for (int i = start; i < limit; i++) {
            // f = f0 * 2 ˆ (fm)
            double out = baseFrequency * Math.pow(2, inputs[i]);
            out = Math.min(maxFrequency, Math.max(minFrequency, out));
            outputs[i] = out;
        }
    }

    /**
     * Base Frequency getter.
     * @return the base frequency in Hertz.
     */
    public double getBaseFrequency() {
        return this.baseFrequency;
    }

    /**
     * Base Frequency setter.
     * @param f
     *       the base frequency in Hertz.
     */
    public void setBaseFrequency(double f) {
        this.baseFrequency = f;
    }

    /**
     * Maximum Frequency getter.
     * @return the maximum frequency in Hertz.
     */
    public double getMaxFrequency() {
        return this.maxFrequency;
    }

    /**
     * Base Frequency setter.
     * @param f
     *       the maximum frequency in Hertz.
     */
    public void setMaxFrequency(double f) {
        this.maxFrequency = f;
    }

    /**
     * Minimum Frequency getter.
     * @return the minimum frequency in Hertz.
     */
    public double getMinFrequency() {
        return this.minFrequency;
    }

    /**
     * Minimum Frequency setter.
     * @param f
     *       the minimum frequency in Hertz.
     */
    public void setMinFrequency(double f) {
        this.minFrequency = f;
    }
}
