package fr.istic.synthlab.util;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.jsyn.Synthesizer;

/**
 * Useful utility class.
 * 
 * @author valentinmumble
 * 
 */
public class Util {

    /**
     * Constructor of Util.
     * @throws UnsupportedOperationException
     */
    protected Util() {
        // Prevent calls from subclass
        throw new UnsupportedOperationException();
    }

    /**
     * 
     * @param dB
     *            The gain in decibels
     * @return the voltage gain corresponding to the decibel gain
     */
    public static double decibelsToVoltage(double dB) {
        return Math.pow(10.0, dB / 20.0);
    }

    /**
     * 
     * @param voltage
     *            The voltage in volts
     * @return the power gain in decibels
     */
    public static double voltageToDecibels(double voltage) {
        return 20.0 * Math.log10(voltage);
    }

    /**
     * Util method useful for waiting in test cases.
     * 
     * @param synth
     *            the synthesizer thread that will wait
     * @param seconds
     *            number of seconds to wait
     */
    public static void wait(Synthesizer synth, double seconds) {
        try {
            seconds += synth.getCurrentTime();
            synth.sleepUntil(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Width of a 1U slot
     */
    public static final int SLOT_WIDTH = 100;

    /**
     * Height of all slot.
     */
    public static final int SLOT_HEIGHT = 340;

    /**
     * Type of modules.
     */
    public static enum Module {

        /**
         * EG Module.
         */
        EG,

        /**
         * KEYB Module.
         */
        KEYB,

        /**
         * MIX Module.
         */
        MIX,

        /**
         * OUT Module.
         */
        OUT,

        /**
         * REC Module.
         */
        REC,

        /**
         * REP Module.
         */
        REP,

        /**
         * SCOP Module.
         */
        SCOP,

        /**
         * SEQ Module.
         */
        SEQ,

        /**
         * VCA Module.
         */
        VCA,

        /**
         * VCFHP Module.
         */
        VCFHP,

        /**
         * VCFLP Module.
         */
        VCFLP,

        /**
         * VCOA Module.
         */
        VCOA,

        /**
         * WN Module.
         */
        WN
    };
    
    /**
     * Attenuation Units of measure.
     */
    public static enum AttenuationUnit {
        /**
         * VOLT Unit.
         */
        VOLT,

        /**
         * DB Unit.
         */
        DB
    };

    /**
     * Convert an Icon to Image.
     * @author Laurent Legendre
     * @param icon
     *          the icon to convert
     * @return the image converted from the icon
     */
    public static Image iconToImage(Icon icon) {
        if (icon instanceof ImageIcon) {
            return ((ImageIcon) icon).getImage();
        } else {
            int w = icon.getIconWidth();
            int h = icon.getIconHeight();
            GraphicsEnvironment ge = GraphicsEnvironment
                    .getLocalGraphicsEnvironment();
            GraphicsDevice gd = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gd.getDefaultConfiguration();
            BufferedImage image = gc.createCompatibleImage(w, h);
            Graphics2D g = image.createGraphics();
            icon.paintIcon(null, g, 0, 0);
            g.dispose();
            return image;
        }
    }
}
