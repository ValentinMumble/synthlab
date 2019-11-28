package fr.istic.synthlab.cable.abstraction;

import java.awt.Color;

import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * Cable Abstraction.
 *
 * @author Mickael
 * @version 1.1
 */
public class ACable implements IACable {
    /**
     * ICFemaleJack jack1.
     */
    private ICFemaleJack jack1;

    /**
     * ICFemaleJack jack2.
     */
    private ICFemaleJack jack2;

    /**
     * Color of the cable.
     */
    private Color color;

    /**
     * jack1 setter.
     *
     * @param jack
     *            where the cable is plugged.
     */
    @Override
    public final void setJack1(final ICFemaleJack jack) {
        jack1 = jack;
    }

    /**
     * jack2 setter.
     *
     * @param jack
     *            where the cable is plugged.
     */
    @Override
    public final void setJack2(final ICFemaleJack jack) {
        jack2 = jack;
    }

    /**
     * jack1 Getter.
     *
     * @return jack1 where the cable is plugged.
     */
    @Override
    public final ICFemaleJack getJack1() {
        return jack1;
    }

    /**
     * jack2 Getter.
     *
     * @return jack2 where the cable is plugged.
     */
    @Override
    public final ICFemaleJack getJack2() {
        return jack2;
    }

    /**
     * Color setter.
     *
     * @param cableColor
     *            Color to set.
     */
    @Override
    public void setColor(Color cableColor) {
        this.color = cableColor;
    }

    /**
     * @return The color of the cable.
     */
    @Override
    public Color getColor() {
        return color;
    }
}
