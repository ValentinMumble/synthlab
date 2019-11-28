package fr.istic.synthlab.cable.presentation;

import java.awt.Point;

import fr.istic.synthlab.cable.control.ICCable;

/**
 * Interface for the Cable Presentation.
 *
 * @author Florent
 */
public interface IPCable {

    /**
     * Width of the cable.
     */
    int CABLE_WIDTH = 10;

    /**
     * Get the controller.
     *
     * @return {@link ICCable}
     */
    ICCable getController();

    /**
     * Update the position of the point b and call repaint().
     *
     * @param b
     *         Point b
     */
    void updatePos(Point b);

    /**
     * Update the position of the two points of the cable and call repaint().
     */
    void updateAndValidate();
}
