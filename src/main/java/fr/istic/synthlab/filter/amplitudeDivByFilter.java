package fr.istic.synthlab.filter;

import com.jsyn.unitgen.UnitBinaryOperator;

/**
 * 
 * Filter to divide by ten the
 * input level.
 * 
 * @author Favereau
 *
 */
public class amplitudeDivByFilter extends UnitBinaryOperator {

    // Max level input (5v)
    private double divider = 5.0;
    
    @Override
    public void generate(int start, int stop) {
        
        double[] inputsSignal = inputA.getValues();
        double[] outputs = output.getValues();

        for (int i = start; i < stop; i++) {
            outputs[i] = inputsSignal[i] / divider;
        }
        
    }

    public double getdivider() {
        return divider;
    }

    public void setdivider(double div) {
        this.divider = div;
    }
}
