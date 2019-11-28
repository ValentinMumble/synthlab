package fr.istic.synthlab.filter;

import com.jsyn.unitgen.UnitFilter;

/**
 * Attenuation filter, attenuate the input by the attenuation set in volts.
 *
 * @author valentinmumble
 *
 */
public class AttenuationFilter extends UnitFilter {

    /**
     * attenuation in Volts.
     */
    private double attenuationInVolts;

    /**
     * Constructor of the AttenuationFilter.
     */
    public AttenuationFilter() {
        this.setAttenuation(1.0D);
    }

    /**
     * Set the attenuation.
     *
     * @param attV
     *            attenuation to set (in Volt)
     */
    public final void setAttenuation(final double attV) {
        attenuationInVolts = attV - 1;
    }

    /**
     * Get the attenuation.
     *
     * @return The attenuation value (in Volt).
     */
    public final double getAttenuation() {
        return this.attenuationInVolts + 1 ;
    }

    @Override
    public final void generate(final int offset, final int limit) {
        double[] inputs = input.getValues();
        double[] outputs = output.getValues();

        for (int i = offset; i < limit; i++) {
            double x = inputs[i];
            double tmpAttenuation = attenuationInVolts * x;
            x += tmpAttenuation;
            outputs[i] = x;
        }
    }

}
