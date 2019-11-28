package fr.istic.synthlab.util;

import java.net.URL;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Utility class to retrieve all icons needed.
 * 
 * @author Laurent Legendre
 * 
 */
public final class Icons {
    /**
     * Log.
     */
    private static final Logger LOGGER = Logger
            .getLogger(Icons.class.getName());

    /**
     * Path for the waveform sine icon.
     */
    public static final Icon WAVEFORM_SINE =
            icon("/pics/waveform_sine.png");

    /**
     * Path for the waveform square icon.
     */
    public static final Icon WAVEFORM_SQUARE =
            icon("/pics/waveform_square.png");

    /**
     * Path for the waveform triangular icon.
     */
    public static final Icon WAVEFORM_TRIANGULAR =
            icon("/pics/waveform_triangular.png");

    /**
     * Path for the waveform sawtooth icon.
     */
    public static final Icon WAVEFORM_SAWTOOTH =
            icon("/pics/waveform_sawtooth.png");

    /**
     * Path for the connector jack female icon.
     */
    public static final Icon CONNECTOR_JACK_FEMALE =
            icon("/pics/connector_jack_female.png");

    /**
     * Path for the connector jack female ok icon.
     */
    public static final Icon CONNECTOR_JACK_FEMALE_OK =
            icon("/pics/connector_jack_female_green.png");

    /**
     * Path for the connector jack female ko icon.
     */
    public static final Icon CONNECTOR_JACK_FEMALE_KO =
            icon("/pics/connector_jack_female_red.png");

    /**
     * Path for the button mute icon.
     */
    public static final Icon BUTTON_MUTE =
            icon("/pics/button_mute.png");

    /**
     * Path for the knob icon.
     */
    public static final Icon KNOB =
            icon("/pics/knob.png");

    /**
     * Path for the black screw north west icon.
     */
    public static final Icon BLACK_SCREW_NW =
            icon("/pics/black_screw_nw.png");

    /**
     * Path for the black screw north east icon.
     */
    public static final Icon BLACK_SCREW_NE =
            icon("/pics/black_screw_ne.png");

    /**
     * Path for the black screw south east icon.
     */
    public static final Icon BLACK_SCREW_SE =
            icon("/pics/black_screw_se.png");

    /**
     * Path for the black screw south west icon.
     */
    public static final Icon BLACK_SCREW_SW =
            icon("/pics/black_screw_sw.png");

    /**
     * Path for the black side north icon.
     */
    public static final Icon BLACK_SIDE_N =
            icon("/pics/black_side_n.png");

    /**
     * Path for the black side south icon.
     */
    public static final Icon BLACK_SIDE_S =
            icon("/pics/black_side_s.png");

    /**
     * Path for the black background icon.
     */
    public static final Icon BLACK_BACKGROUND =
            icon("/pics/black_background.png");

    /**
     * Path for the blue background icon.
     */
    public static final Icon BLUE_BACKGROUND =
            icon("/pics/blue_background.png");

    /**
     * Path for the wood background icon.
     */
    public static final Icon WOOD_BACKGROUND =
            icon("/pics/wood_background.png");
    
    /**
     * Path for the leather background icon.
     */
    public static final Icon LEATHER_BACKGROUND =
            icon("/pics/leather_background.png");
    
    /**
     * Path for the metal background icon.
     */
    public static final Icon METAL_BACKGROUND = icon("/pics/metal_background.png");

    /**
     * Path for the button rec icon.
     */
    public static final Icon BUTTON_REC =
            icon("/pics/button_rec.png");

    /**
     * Path for the button file icon.
     */
    public static final Icon BUTTON_FILE =
            icon("/pics/button_file.png");
    
    /**
     * Path for the active delete file icon.
     */
    public static final Icon BUTTON_DELETE_ACTIVE =
            icon("/pics/button_delete_active.png");

    /**
     * Path for the inactive delete file icon.
     */
    public static final Icon BUTTON_DELETE_INACTIVE =
            icon("/pics/button_delete_inactive.png");
    
    /**
     * Full Synthlab Logo.
     */
    public static final Icon SYNTHLAB_LOGO =
            icon("/pics/synthlab_logo.png");
    
    /**
     * Path for the save icon.
     */
    public static final Icon SAVE =
            icon("/pics/save.png");
    
    /**
     * Path for the main frame icon.
     */
    public static final String MAIN_ICON_PATH =
            "/pics/synthlab_icon.png";
    

    /**
     * Constructor of Icons.
     * @throws UnsupportedOperationException
     */
    protected Icons() {
        // Prevent calls from subclass
        throw new UnsupportedOperationException();
    }

    /**
     * Create an ImageIcon with the pic at the path.
     * 
     * @param path
     *          the path of the image icon.
     * @return an Icon
     */
    public static Icon icon(final String path) {
        URL resource = Icons.class.getResource(path);
        if (resource == null) {
            LOGGER.info("Resource " + path + " does not exist");
            return new ImageIcon();
        }
        return new ImageIcon(resource);
    }
}
