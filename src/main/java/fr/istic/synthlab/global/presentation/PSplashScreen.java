package fr.istic.synthlab.global.presentation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JWindow;

import fr.istic.synthlab.util.Icons;
import fr.istic.synthlab.util.Util;

/**
 * The Splashscreen which is shown on startup.
 * 
 * @author Laurent Legendre
 * 
 */
public class PSplashScreen extends JWindow {

    /**
     * UID to serialize this object.
     */
    private static final long serialVersionUID = -38532367420944046L;
    
    /**
     * Icon to use in the Spalshscreen.
     */
    private static final Image SPLASH_ICON = Util
            .iconToImage(Icons.SYNTHLAB_LOGO);
    
    /**
     * Width of the Icon.
     */
    private int splashIconWidth = SPLASH_ICON.getWidth(this);
    
    /**
     * Height of the Icon.
     */
    private int splashIconHeight = SPLASH_ICON.getHeight(this);
    
    /**
     * Duration of the icon display (in milliseconds). 
     */
    
    private static final int DURATION = 3000;

    /**
     * Default Constructor.
     */
    public PSplashScreen() {
        try {
            // give the icon some space...
            setSize(splashIconWidth + 200, splashIconHeight + 100);
            setLocationRelativeTo(null);
            setBackground(Color.BLACK);
            setVisible(true);
            setAlwaysOnTop(true);
            Thread.sleep(DURATION);
            dispose();

        } catch (Exception exception) {
            javax.swing.JOptionPane.showMessageDialog(
                    (java.awt.Component) null,
                    "Error" + exception.getMessage(), "Error:",
                    javax.swing.JOptionPane.DEFAULT_OPTION);
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(SPLASH_ICON, (getSize().width - splashIconWidth) / 2,
                (getSize().height - splashIconHeight) / 2, this);
    }
}
