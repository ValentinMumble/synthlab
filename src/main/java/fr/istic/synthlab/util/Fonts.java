package fr.istic.synthlab.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Utility class to retrieve special needed.
 * 
 * @author Laurent Legendre
 * 
 */
public final class Fonts {
    /**
     * Log.
     */
    private static final Logger LOGGER = Logger
            .getLogger(Fonts.class.getName());

    /**
     * LCD_DIGITAL7_MONO Font.
     */
    public static final Font LCD_DIGITAL7_MONO = 
            loadFont("/fonts/digital7_mono.ttf");

    /**
     * Constructor of Fonts.
     * @throws UnsupportedOperationException
     */
    protected Fonts() {
        // Prevent calls from subclass
        throw new UnsupportedOperationException();
    }

    /**
     * Create a Font from the given font file path.
     * 
     * @param path
     *           the font file path
     * @return a Font
     */
    private static Font loadFont(final String path) {
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT,
                    Fonts.class.getResourceAsStream(path)).deriveFont(16.0f);
            GraphicsEnvironment ge = GraphicsEnvironment
                    .getLocalGraphicsEnvironment();
            ge.registerFont(font);
        } catch (IOException | FontFormatException e) {
            LOGGER.info("Font Resource " + path + " does not exist");
        }
        return font;
    }
}
