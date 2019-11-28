package fr.istic.synthlab.util.abstraction;

import com.jsyn.ports.UnitPort;

import fr.istic.synthlab.cable.control.ICCable;

/**
 * A class to represent a female jack and its associated port.
 *
 * @author valentinmumble
 *
 */
public class AFemaleJack implements IAFemaleJack {

    /**
     * The port associate to the FemaleJack.
     */
    private UnitPort port;
    /**
     * True if the port is an InputPort.
     */
    private boolean isInput;
    /**
     * True if a cable is plug in the Port.
     */
    private boolean isPlugged;
    /**
     * Cable plugged in the Jack, null if no cable plugged
     */
    private ICCable iccable;
    
    /**
     * Ident of the FemalJack.
     */
    private String ident;

    /**
     * Constructor of AFemaleJack.
     * @param port
     *      Port associate to the FemaleJack.
     * @param isInput
     *            true if the port is an inputPort, false if it's an outpuPort.
     */
    public AFemaleJack(UnitPort port, boolean isInput) {
        this.port = port;
        this.isInput = isInput;
        this.isPlugged = false;
    }

    /**
     * 
     * @return the port associated with the female jack
     */
    @Override
    public UnitPort getPort() {
        return port;
    }

    /**
     *
     * @return true if the port is an inputPort, false if it's an outputPort
     */
    @Override
    public boolean isInput() {
        return isInput;
    }

    /**
     *
     * @return if the port is plugged
     */
    @Override
    public boolean isPlugged() {
        return isPlugged;
    }

    /**
     * @return the cable plugged in the Jack, null if no cable plugged
     */
    @Override
    public ICCable getCcable() {
        return iccable;
    }

    /**
     * isPlugged setter.
     *
      * @param isPlugged 
     *            is the port is plugged ?
     */
    @Override
    public void setPlugged(boolean isPlugged) {
        this.isPlugged = isPlugged;
    }

    /**
     * Plugged a cable.
     * Not done if there is a cable plugged.
     * @param ccable
     *          the cable we are plugging.
     * @return true if the cable was correctly plugged.
     */
    @Override
    public boolean plugCable(ICCable ccable) {
        boolean done = false;
        if (!isPlugged) {
            this.iccable = ccable;
            setPlugged(true);
            done = true;
        }
        return done;
    }

    /**
     * If a cable is plugged, we unplugged the cable.
     * @return
     */
    @Override
    public void unplugged() {
        if (isPlugged) {
            this.iccable = null;
            setPlugged(false);
        }
    }

    /**
     * Set the ident of the FemalJack for the save.
     *
     * @param name
     *            Unique identifier of the FemalJack.
     */
    @Override
    public void setIdent(String name) {
        this.ident = new String(name);
    }

    /**
     * @return The ident of the FemalJack.
     */
    @Override
    public String getIdent() {
        return ident;
    }
}
