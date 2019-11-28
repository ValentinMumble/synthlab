package fr.istic.synthlab.filter;

import com.jsyn.unitgen.UnitBinaryOperator;

/**
 * Amplitude Modulation Filter.
 * @author Florent
 *
 */
public class AmplModulFilter extends UnitBinaryOperator {

    /**
     *  Max level input (5v)
     */
    private double levelMax = 5.0;

    /**
     * Input sensibility (12dB/Volt)
     */
    private double sensibility = 12.0;

    @Override
    public void generate(int start, int stop) {

        double[] inputsSignal = inputA.getValues();
        double[] inputsModulation = inputB.getValues();
        double[] outputs = output.getValues();

        for (int i = start; i < stop; i++) {
            double a = inputsSignal[i];
            double dB = Math.max(-levelMax, Math.min(levelMax, inputsModulation[i]));
            dB = dB * sensibility;

            outputs[i] = a * Math.pow(10.0, dB / 20.0);
        }
    }

    /**
     * Level maximum of an input getter.
     * @return Level maximum of an input
     */
    public double getLevelMax() {
        return levelMax;
    }

    /**
     * Input level maximum of an  getter.
     * @param lvlMax
     *      Input level maximum
     */
    public void setLevelMax(double lvlMax) {
        this.levelMax = lvlMax;
    }

    /**
     * Input sensibility getter.
     * @return Input sensibility
     */
    public double getSensibility() {
        return sensibility;
    }

    /**
     * Input sensibility setter.
     * @param inputSensibility
     */
    public void setSensibility(double inputSensibility) {
        this.sensibility = inputSensibility;
    }
}
