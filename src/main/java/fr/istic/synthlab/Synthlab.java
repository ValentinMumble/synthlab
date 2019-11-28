package fr.istic.synthlab;

import java.awt.Image;

import javax.swing.JFrame;

import fr.istic.synthlab.global.control.CGLOB;
import fr.istic.synthlab.util.Icons;
import fr.istic.synthlab.util.Util;

/**
 * Main class for the Synthlab application.
 *
 * @author valentinmumble
 *
 */
public abstract class Synthlab {

    public static String OS_NAME = "N/A";

    /**
     * Main of the application Synthlab.
     * @param args
     *          arguments
     */
    public static void main(final String[] args) {

        // Set the synthlab icon
        Image icon = Util.iconToImage(Icons.icon(Icons.MAIN_ICON_PATH));
        

        OS_NAME = System.getProperty("os.name");
        if (OS_NAME.contains("Mac")) {
            com.apple.eawt.Application.getApplication().setDockIconImage(icon);
        }

        JFrame frame = (JFrame) CGLOB.getInstance().getPresentation();
        frame.setIconImage(icon);
    }
}
