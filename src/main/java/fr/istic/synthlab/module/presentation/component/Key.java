package fr.istic.synthlab.module.presentation.component;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class Key extends JPanel {

    /**
     * UID
     */
    private static final long serialVersionUID = -3027754738368204317L;

    /**
     * Width in pixels of a key
     */
    public static final int KEY_WIDTH = 35;

    /**
     * Height in pixels of a key
     */
    public static final int KEY_HEIGHT = 190;

    /**
     * Color of a pressed key
     */
    public static final Color KEY_PRESSED_COLOR = new Color(0, 76, 153);

    private Color keyColor;

    public Key(Color c) {
        super();
        keyColor = c;
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        setSize(KEY_WIDTH, KEY_HEIGHT);
        setPreferredSize(getSize());
        setBackground(keyColor);
    }

    public void press() {
        setBackground(KEY_PRESSED_COLOR);
    }

    public void release() {
        setBackground(keyColor);
    }
}
