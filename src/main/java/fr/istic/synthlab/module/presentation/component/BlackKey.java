package fr.istic.synthlab.module.presentation.component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import fr.istic.synthlab.util.Icons;
import fr.istic.synthlab.util.Util;

public class BlackKey extends Key {

    /**
     * UID
     */
    private static final long serialVersionUID = 1L;

    public static final int BLACK_KEY_HEIGHT = (int) (Key.KEY_HEIGHT / 1.5);
    public static final int BLACK_KEY_WIDTH = (int) (Key.KEY_WIDTH / 1.3);

    private static final Image reflections = Util.iconToImage(Icons
            .icon("/pics/black_key_reflections.png"));

    public BlackKey() {
        super(Color.BLACK);
        setSize(BLACK_KEY_WIDTH, BLACK_KEY_HEIGHT);
        setPreferredSize(getSize());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(reflections, 0, 0, null);
    }

}
