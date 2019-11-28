package fr.istic.synthlab.util.presentation;

import java.awt.Point;

/**
 * The interface for the female jack presentation.
 * 
 * @author valentinmumble
 * 
 */
public interface IPFemaleJack {
    Point getLocation();

    /**
     * Function calls by PCable. Give the point where the PCable must be
     * connected.
     * 
     * @return The middle point of the Jack.
     */
    Point getPointForCable();

    /**
     * Called by the control to show that the port is pluggable
     */
    void showPluggable();

    /**
     * Called by the control to show that the port is not pluggable
     */
    void showNotPluggable();

    /**
     * Called by the control to show that the port is neutral
     */
    void showNeutral();
}
