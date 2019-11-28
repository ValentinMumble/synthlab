package fr.istic.synthlab.util;

import java.awt.Image;

import javax.swing.Icon;

/**
 * Templates.
 * 
 * @author Laurent Legendre
 * 
 */
public enum Template {
    /**
     * With a Black Background.
     */
    BLACK(Icons.BLACK_SCREW_NW, Icons.BLACK_SCREW_NE, Icons.BLACK_SCREW_SE,
            Icons.BLACK_SCREW_SW, Icons.BLACK_SIDE_N, Icons.BLACK_SIDE_S,
            Icons.BLACK_BACKGROUND),
    /**
     * With a Blue Background.
     */
    BLUE(Icons.BLACK_SCREW_NW, Icons.BLACK_SCREW_NE, Icons.BLACK_SCREW_SE,
            Icons.BLACK_SCREW_SW, Icons.BLACK_SIDE_N, Icons.BLACK_SIDE_S,
            Icons.BLUE_BACKGROUND),
    
    /**
     * With a Wood Background.
     */
    WOOD(Icons.BLACK_SCREW_NW, Icons.BLACK_SCREW_NE, Icons.BLACK_SCREW_SE,
            Icons.BLACK_SCREW_SW, Icons.BLACK_SIDE_N, Icons.BLACK_SIDE_S,
            Icons.WOOD_BACKGROUND),
    
    /**
     * With a Leather Background.
     */
    LEATHER(Icons.BLACK_SCREW_NW, Icons.BLACK_SCREW_NE, Icons.BLACK_SCREW_SE,
            Icons.BLACK_SCREW_SW, Icons.BLACK_SIDE_N, Icons.BLACK_SIDE_S,
            Icons.LEATHER_BACKGROUND),
    
    /**
     * With a Metal Background.
     */
    METAL(Icons.BLACK_SCREW_NW, Icons.BLACK_SCREW_NE, Icons.BLACK_SCREW_SE,
            Icons.BLACK_SCREW_SW, Icons.BLACK_SIDE_N, Icons.BLACK_SIDE_S,
            Icons.METAL_BACKGROUND);

    /**
     * Screw north west image.
     */
    private Image screwNW;

    /**
     * Screw north east image.
     */
    private Image screwNE;

    /**
     * Screw south east image.
     */
    private Image screwSE;

    /**
     * Screw south west image.
     */
    private Image screwSW;

    /**
     * Side north image.
     */
    private Image sideN;

    /**
     * Side south image.
     */
    private Image sideS;

    /**
     * Background image.
     */
    private Image background;

    /**
     * Constructor of the Template.
     * @param screwNorthWest
     *          Screw north west image.
     * @param screwNorthEast
     *          Screw north east image.
     * @param screwSouthEast
     *          Screw south east image.
     * @param screwSouthWest
     *          Screw south west image.
     * @param sideNorth
     *          Side north image.
     * @param sideSouth
     *          Side south image.
     * @param bg
     *          Background image.
     */
    private Template(Icon screwNorthWest, Icon screwNorthEast,
            Icon screwSouthEast, Icon screwSouthWest,
            Icon sideNorth, Icon sideSouth, Icon bg) {
        this.screwNW = Util.iconToImage(screwNorthWest);
        this.screwNE = Util.iconToImage(screwNorthEast);
        this.screwSE = Util.iconToImage(screwSouthEast);
        this.screwSW = Util.iconToImage(screwSouthWest);
        this.sideN = Util.iconToImage(sideNorth);
        this.sideS = Util.iconToImage(sideSouth);
        this.background = Util.iconToImage(bg);
    }

    /**
     * Screw north west getter.
     * @return the screw north west
     */
    public Image getScrewNW() {
        return screwNW;
    }

    /**
     * Screw north east getter.
     * @return the screw north east
     */
    public Image getScrewNE() {
        return screwNE;
    }

    /**
     * Screw south east getter.
     * @return the screw south east
     */
    public Image getScrewSE() {
        return screwSE;
    }

    /**
     * Screw south west getter.
     * @return the screw south west
     */
    public Image getScrewSW() {
        return screwSW;
    }

    /**
     * Side north getter.
     * @return the side north
     */
    public Image getSideN() {
        return this.sideN;
    }

    /**
     * Side south getter.
     * @return the side south
     */
    public Image getSideS() {
        return this.sideS;
    }

    /**
     * Background getter.
     * @return the background
     */
    public Image getBackground() {
        return this.background;
    }
}
