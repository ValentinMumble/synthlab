package fr.istic.synthlab.module.presentation.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.UIManager;

public class Bloc extends JPanel {

    /**
     * UID to serialize this object.
     */
    private static final long serialVersionUID = -678055747487215229L;

    /**
     * Default Insets (padding between internal components) to use.
     */
    protected static final Insets DEFAULT_INSETS = new Insets(2, 2, 2, 2);
    
    /**
     * Default Font to use.
     */
    protected static final Font DEFAULT_FONT = UIManager.getDefaults()
            .getFont("TabbedPane.font").deriveFont(11.0f);
    
    /**
     * Default Font color to use.
     */
    protected static final Color DEFAULT_COLOR = Color.WHITE;

    /**
     * Constructor.
     * 
     * Default layout is GridBagLayout
     */
    public Bloc() {
        this.setLayout(new GridBagLayout());
        this.setBackground(null);
        this.setOpaque(false);
    }
}
