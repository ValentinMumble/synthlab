package fr.istic.synthlab.module.abstraction;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;

/**
 * The interface for the EG module abstraction
 * 
 * @author valentinmumble
 * 
 */
public interface IAEnvelopeGenerator extends IAModule {

    String TAG = "AEG";

    /**
     * 
     * @return the gate input port
     */
    UnitInputPort getGatePort();

    /**
     * 
     * @return the output port
     */
    UnitOutputPort getOutputPort();

    /**
     * 
     * @return the attack value
     */
    double getAttack();

    /**
     * 
     * @return the decay value
     */
    double getDecay();

    /**
     * 
     * @return the sustain value
     */
    double getSustain();

    /**
     * 
     * @return the release value
     */
    double getRelease();

    /**
     * set the attack
     * 
     * @param a
     *            attack
     */
    void setAttack(double a);

    /**
     * set the decay
     * 
     * @param d
     *            decay
     */
    void setDecay(double d);

    /**
     * set the sustain
     * 
     * @param s
     *            sustain
     */
    void setSustain(double s);

    /**
     * set the release
     * 
     * @param r
     *            release
     */
    void setRelease(double r);
}
