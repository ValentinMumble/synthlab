package fr.istic.synthlab.cable.abstraction;

import java.awt.Color;

import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * Interface for the Cable Abstraction.
 *
 * @author Mickael
 * @version 1.1
 */
public interface IACable {

    /**
     * jack1 setter.
     *
     * @param jack
     *            where the cable is plugged.
     */
    void setJack1(ICFemaleJack jack);

    /**
     * jack2 setter.
     *
     * @param jack
     *            where the cable is plugged.
     */
    void setJack2(ICFemaleJack jack);

    /**
     * jack1 Getter.
     *
     * @return jack1 where the cable is plugged.
     */
    ICFemaleJack getJack1();

    /**
     * jack2 Getter.
     *
     * @return jack2 where the cable is plugged.
     */
    ICFemaleJack getJack2();

    /**
     * Color setter.
     *
     * @param cableColor
     *            Color to set.
     */
    void setColor(Color cableColor);

    /**
     * @return The color of the cable.
     */
    Color getColor();
}
