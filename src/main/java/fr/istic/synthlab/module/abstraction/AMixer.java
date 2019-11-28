package fr.istic.synthlab.module.abstraction;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.PassThrough;

import fr.istic.synthlab.filter.AttenuationFilter;
import fr.istic.synthlab.util.Util;
import fr.istic.synthlab.util.Util.AttenuationUnit;

/**
 * A class to represent the mixer module.
 * 
 * @author Laurent Legendre
 * @version 1.1
 * 
 */
public class AMixer implements IAMixer {

    public static final int NB_INPUTS = 4;
    private static final Logger LOGGER = Logger.getLogger(AMixer.class
            .getName());

    private List<MixerLine> mixerLineInputs;

    /**
     * Minimum value for the attenuation filter (in dB).
     * 
     * @category CONSTANT
     */
    public final static double ATTENUATION_MIN_DB = -60;

    /**
     * Minimum value for the attenuation filter (in Volt).
     * 
     * @category CONSTANT
     */
    public final static double ATTENUATION_MIN_VOLT = Util
            .decibelsToVoltage(ATTENUATION_MIN_DB);

    /**
     * Maximum value for the attenuation filter (in dB).
     * 
     * @category CONSTANT
     */
    public final static double ATTENUATION_MAX_DB = 12;

    /**
     * Maximum value for the attenuation filter (in Volt).
     * 
     * @category CONSTANT
     */
    public final static double ATTENUATION_MAX_VOLT = Util
            .decibelsToVoltage(ATTENUATION_MAX_DB);

    /**
     * Synthesizer.
     */
    private Synthesizer synth;

    /**
     * output PassThrough.
     */
    private PassThrough outputPassThrough;

    /**
     * Constructor of the Mixer Abstraction.
     * 
     * @param s
     *            Synthetizer
     */
    public AMixer(final Synthesizer s) {

        this.synth = s;
        this.mixerLineInputs = new ArrayList<MixerLine>();

        // Create Inputs
        for (int i = 1; i <= AMixer.NB_INPUTS; i++) {

            PassThrough passThrough = new PassThrough();
            AttenuationFilter attenuationFilter = new AttenuationFilter();
            mixerLineInputs.add(new MixerLine(passThrough, attenuationFilter));
            this.synth.add(attenuationFilter);
            this.synth.add(passThrough);
        }

        // Create Outputs
        this.outputPassThrough = new PassThrough();
        this.synth.add(outputPassThrough);

        // Connect Inputs to the Output through their respective
        // attenuationFilters
        for (MixerLine mxline : mixerLineInputs) {
            mxline.getInputPassThrough().output.connect(mxline
                    .getAttenuationFilter().input);
            mxline.getAttenuationFilter().output
                    .connect(this.outputPassThrough);
        }
    }

    @Override
    public final UnitInputPort getInputPort(int inputPortNumber) {
        UnitInputPort inputPort = null;
        if (inputPortNumber >= 1 && inputPortNumber <= mixerLineInputs.size()) {
            inputPort = mixerLineInputs.get(inputPortNumber - 1)
                    .getInputPassThrough().input;
        }
        return inputPort;
    }

    @Override
    public UnitOutputPort getOutputPort() {
        return outputPassThrough.output;
    }

    @Override
    public void setAttenuation(int inputPortNumber, double dBvalue)
            throws Exception {

        if (inputPortExists(inputPortNumber)) {
            // get the attenuation filter and set it attenuation value in
            // Volts.
            mixerLineInputs.get(inputPortNumber - 1).setAttenuation(dBvalue,
                    AttenuationUnit.DB);

        } else {
            throw new Exception("Input Port" + inputPortNumber
                    + " does not exists");
        }
    }

    @Override
    public double getAttenuation(int inputPortNumber) throws Exception {
        double dBvalue = 0.0D;
        if (inputPortExists(inputPortNumber)) {
            dBvalue = mixerLineInputs.get(inputPortNumber - 1).getAttenuation(
                    AttenuationUnit.DB);
        } else {
            throw new Exception("Input Port" + inputPortNumber
                    + " does not exists");
        }
        return dBvalue;
    }

    private boolean inputPortExists(int inputPortNumber) {
        return inputPortNumber >= 1 && inputPortNumber <= this.mixerLineInputs
                .size();
    }

    /**
     * A class which represent a Mixer Line.
     * 
     * A mixer line is a couple (PassThrough,AttenuationFilter)
     * 
     * @author Laurent Legendre
     * 
     */
    class MixerLine {
        private PassThrough inputPassThrough;
        private AttenuationFilter attenuationFilter;

        public MixerLine(PassThrough passThrough,
                AttenuationFilter attenuationFilter) {
            this.inputPassThrough = passThrough;
            this.attenuationFilter = attenuationFilter;
        }

        /**
         * Get the PassThrough.
         * 
         * @return the PassThrough
         */
        public PassThrough getInputPassThrough() {
            return inputPassThrough;
        }

        /**
         * Get the AttenuationFilter
         * 
         * @return the AttenuationFilter
         */
        public AttenuationFilter getAttenuationFilter() {
            return attenuationFilter;
        }

        /**
         * Set the attenuation of the AttenuationFilter either in volts or in
         * decibels.
         * 
         * There is no default AttenuationUnit. If the AttenuationUnit is not
         * recognized, the attenuation won't be changed.
         * 
         * @param att
         *            The attenuation.
         * @param unit
         *            The unit of measure to use.
         */
        public void setAttenuation(double att, AttenuationUnit unit) {
            switch (unit) {
            case DB:
                double attV = Util.decibelsToVoltage(Math.min(
                        ATTENUATION_MAX_DB, Math.max(att, ATTENUATION_MIN_DB)));
                this.attenuationFilter.setAttenuation(attV);
                break;
            case VOLT:
                this.attenuationFilter.setAttenuation(Math.min(
                        ATTENUATION_MAX_VOLT,
                        Math.max(att, ATTENUATION_MIN_VOLT)));
                break;

            default:
                LOGGER.info("Unknown AttenuationUnit '" + unit
                        + "'. Won't change attenuation");
                break;
            }
        }

        /**
         * Get the attenuation of the Attenuation Filter.
         * 
         * @param unit
         *            The unit of measure to use.
         * 
         * @return The attenuation value in the right UoM
         */
        public double getAttenuation(AttenuationUnit unit) {
            double att = this.attenuationFilter.getAttenuation();

            switch (unit) {
            case DB:
                att = Util.voltageToDecibels(att);
                break;
            case VOLT:
                // Nothing to do, already in volt
                break;

            default:
                att = Util.voltageToDecibels(att);
                LOGGER.info("Unknown AttenuationUnit '" + unit
                        + "'. Value will be return in Volt");
                break;
            }

            return att;
        }

    }
}
