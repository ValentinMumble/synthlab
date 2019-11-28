package fr.istic.synthlab.module.abstraction;

import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;

import fr.istic.synthlab.filter.AmplModulFilter;
import fr.istic.synthlab.filter.AttenuationFilter;
import fr.istic.synthlab.util.Util;

/**
 * 
 * Abstraction class of the VCA module
 * 
 * @author Favereau
 * 
 */
public class AVoltageControlledAmplifier implements IAVoltageControlledAmplifier{

    /**
     * @category CONSTANT
     * 
     */
    final double AMPL_MIN_DB = -30;
    final double AMPL_MAX_DB = 60;

    private Synthesizer synth;

    private UnitInputPort inputAMMode;

    private AttenuationFilter filterAtt;
    private double ampliDB;

    private AmplModulFilter filterAmpModulation;


    public AVoltageControlledAmplifier(Synthesizer s){

        synth = s;

        filterAtt = new AttenuationFilter();
        synth.add(filterAtt);


        filterAmpModulation = new AmplModulFilter();
        synth.add(filterAmpModulation);

        filterAmpModulation.inputA.connect(filterAtt.output);
        inputAMMode = filterAmpModulation.inputB;

        filterAtt.start();
        filterAmpModulation.start();

        setAmplification(0);

    }


    /**
     * Method setAmplification.
     * @param value double
     * @see fr.istic.synthlab.module.abstraction.IAVoltageControlledAmplifier#setAmplification(double)
     */
    @Override
    public void setAmplification(double value) {


        ampliDB = Math.min(AMPL_MAX_DB, Math.max(value, AMPL_MIN_DB));

        filterAtt.setAttenuation(Util.decibelsToVoltage(ampliDB));

    }

    /**
     * Method getAmplification.
     * @return double
     * @see fr.istic.synthlab.module.abstraction.IAVoltageControlledAmplifier#getAmplification()
     */
    @Override
    public double getAmplification() {

        return ampliDB;
    }

    /**
     * Method getInputPort.
     * @return UnitInputPort
     * @see fr.istic.synthlab.module.abstraction.IAVoltageControlledAmplifier#getInputPort()
     */
    @Override
    public UnitInputPort getInputPort() {

        return filterAtt.input;
    }

    /**
     * Method getInputAmpModul.
     * @return UnitInputPort
     * @see fr.istic.synthlab.module.abstraction.IAVoltageControlledAmplifier#getInputAmpModul()
     */
    @Override
    public UnitInputPort getInputAmpModul() {

        return inputAMMode;
        //return filterAmpModulation.inputB;
    }

    /**
     * Method getOutputPort.
     * @return UnitOutputPort
     * @see fr.istic.synthlab.module.abstraction.IAVoltageControlledAmplifier#getOutputPort()
     */
    @Override
    public UnitOutputPort getOutputPort() {

        //return filterAtt.output;
        return filterAmpModulation.output;
    }

}
