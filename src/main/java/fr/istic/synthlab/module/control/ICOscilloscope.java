package fr.istic.synthlab.module.control;

import fr.istic.synthlab.module.abstraction.IAOscilloscope;
import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * The interface for the Oscilloscope controller
 * 
 * @author Valentin
 * 
 */
public interface ICOscilloscope extends ICModule, IAOscilloscope {

    /**
     * 
     * @return the female jack associated with the input port
     */
    ICFemaleJack getInput();

    /**
     * 
     * @return the female jack associated with the output port
     */
    ICFemaleJack getOutput();

    /**
     * Called by the presentation when the range has been changed
     */
    void rangeChanged(double range);
}
