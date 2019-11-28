package fr.istic.synthlab.filter;

import com.jsyn.unitgen.UnitFilter;

/**
 * Pitch filter.
 * 
 * @author Florent
 * 
 */
public class PitchFilter extends UnitFilter {

    /**
     * Array of pitches.
     */
    private double[] pitches;
    
    /**
     * The current step.
     */
    private int currentStep;

    /**
     * Constructor of the AttenuationFilter.
     */
    public PitchFilter() {
        pitches = new double[8];
        for (int i = 0; i < pitches.length; i++) {
            pitches[i] = 0.0;
        }
        currentStep = 0;
    }

    /**
     * Set the pitch corresponding to the index.
     * @param index
     *          Index ot the pitch
     * @param pitch
     *          Value of the pitch
     */
    public void setPitch(int index, double pitch) {
        pitches[index] = pitch;
    }

    /**
     * Return the pitch corresponding to the index.
     * @return The pitch corresponding to the index
     * @param index
     *          Index of the pitch.
     */
    public double getPitch(int index) {
        return pitches[index];
    }

    /**
     * Step setter.
     * @param step
     *          Value of the current step
     */
    public void setStep(int step) {
        currentStep = step;
    }

    /**
     * Step getter.
     * @return the current step
     */
    public int getStep() {
        return currentStep;
    }

    @Override
    public final void generate(final int offset, final int limit) {
        double[] inputs = input.getValues();
        double[] outputs = output.getValues();

        for (int i = offset; i < limit; i++) {
            outputs[i] = pitches[currentStep];
           
            if (inputs[i] == 1){
                currentStep++;
                if (currentStep == 8) {
                    currentStep = 0;
                }
            }
        }
    }
}
