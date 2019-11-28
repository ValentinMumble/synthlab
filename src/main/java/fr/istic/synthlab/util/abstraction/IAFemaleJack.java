package fr.istic.synthlab.util.abstraction;

import com.jsyn.ports.UnitPort;

import fr.istic.synthlab.cable.control.ICCable;

/**
 * Interface for the female jack model.
 *
 * @author valentinmumble
 *
 */
public interface IAFemaleJack {

    /**
     * @return the port associated with the female jack.
     */
    UnitPort getPort();

    /**
     * @return true if the port is an inputPort, false if it's an outputPort.
     */
    boolean isInput();
    
    /**
     * @return if the port is plugged.
     */
    boolean isPlugged();
    
    /**
     * @return the cable plugged in the Jack, null if no cable plugged.
     */
    ICCable getCcable();

    /**
     * isPlugged setter.
     *
     * @param isPlugged 
     *            is the port is plugged ?
     */
    void setPlugged(boolean isPlugged);
    
    /**
     * Plugged a cable.
     * Not done if there is a cable plugged.
     * @param ccable
     *          the cable we are plugging.
     * @return true if the cable was correctly plugged.
     */
    boolean plugCable(ICCable ccable);
    
    /**
     * If a cable is plugged, we unplugged the cable.
     */
    void unplugged();

    /**
     * Set the ident of the FemalJack for the save.
     *
     * @param name
     *            Unique identifier of the FemalJack.
     */
    void setIdent(String name);

    /**
     * @return The ident of the FemalJack.
     */
    String getIdent();
}
