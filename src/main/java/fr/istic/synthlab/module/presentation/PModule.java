package fr.istic.synthlab.module.presentation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.istic.synthlab.global.control.CGLOB;
import fr.istic.synthlab.global.presentation.PGLOB;
import fr.istic.synthlab.global.presentation.PSlot;
import fr.istic.synthlab.module.presentation.component.Bloc;
import fr.istic.synthlab.module.presentation.component.ConnectorBloc;
import fr.istic.synthlab.module.presentation.component.DeleteModuleBloc;
import fr.istic.synthlab.module.presentation.component.TitledBlocGroup;
import fr.istic.synthlab.util.Template;
import fr.istic.synthlab.util.Util;
import fr.istic.synthlab.util.presentation.IPFemaleJack;

/**
 * Common abstract class implementing the Drag & Drop operations for all the
 * modules
 * 
 * @author valentinmumble
 * 
 */
public abstract class PModule extends JPanel implements Transferable, IPModule {

    /**
     * UID to serialize this object.
     */
    private static final long serialVersionUID = 1L;

    protected static Insets gbcInsets = new Insets(5, 5, 5, 5);
    protected static Insets gbcTitleInsets = new Insets(0, 22, 0, 22);
    protected static Insets gbcBottomInsets = new Insets(0, 22, 0, 22);

    protected static GridBagConstraints gbcModuleTitle = new GridBagConstraints(
            0, 0, GridBagConstraints.REMAINDER, 1, 1.0, 0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH, gbcTitleInsets,
            0, 0);

    protected GridBagConstraints gbcBottom;

    public PModule() {
        setModuleSize();

    }

    @Override
    public Object getTransferData(DataFlavor flavor)
            throws UnsupportedFlavorException, IOException {
        return this;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {

        DataFlavor[] data = new DataFlavor[1];
        try {
            data[0] = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType
                    + ";class=" + getClass().getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return false;
    }

    /**
     * Create the module's title bloc from a String
     * 
     * @param moduleTitle
     * @return The prettified module title JPanel
     */
    protected JLabel createModuleTitle(String moduleTitle) {
        JLabel jlabel = new JLabel(moduleTitle, JLabel.CENTER);
        jlabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        jlabel.setForeground(Color.WHITE);
        jlabel.setBackground(Color.BLACK);
        jlabel.setAlignmentY(CENTER_ALIGNMENT);
        jlabel.setSize(jlabel.getPreferredSize().width, 40);
        jlabel.setPreferredSize(jlabel.getSize());
        jlabel.setMinimumSize(jlabel.getSize());
        return jlabel;
    }

    /**
     * Create the module's bottom bloc with the line where it should be put.
     * 
     * 
     * @param line
     *            The line where it should be put. Usually the LAST line.
     * @return The bottom bloc with the delete btn
     */
    protected Bloc createModuleBottom(int line) {
        Bloc deleteBloc = new DeleteModuleBloc(this);
        deleteBloc.setAlignmentY(CENTER_ALIGNMENT);
        deleteBloc.setSize(deleteBloc.getPreferredSize().width, 40);
        deleteBloc.setPreferredSize(deleteBloc.getSize());
        deleteBloc.setMinimumSize(deleteBloc.getSize());
        this.gbcBottom = new GridBagConstraints(0, line,
                GridBagConstraints.REMAINDER, GridBagConstraints.REMAINDER, 0,
                0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                gbcBottomInsets, 0, 0);
        return deleteBloc;
    }

    /**
     * Wrapper method to build a simple Jack bloc with a title
     * 
     * @return the jack bloc as TitledBlocGroup
     */
    protected TitledBlocGroup buildSimpleJackBloc(String title,
            IPFemaleJack... jacks) {
        TitledBlocGroup jackBloc = new TitledBlocGroup(title);
        for (IPFemaleJack jack : jacks) {
            ConnectorBloc connectorBloc = new ConnectorBloc(jack);
            jackBloc.add(connectorBloc);
        }
        return jackBloc;
    }

    /**
     * set the module size based on declared U spread.
     */
    protected void setModuleSize() {
        int width = Util.SLOT_WIDTH * getWidthU();
        int height = Util.SLOT_HEIGHT;
        Dimension moduleDim = new Dimension(width, height);
        setSize(moduleDim);
        setPreferredSize(moduleDim);
        setMinimumSize(moduleDim);
        setMaximumSize(moduleDim);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Template template = PGLOB.getTemplate();
        
        Image screwNW = template.getScrewNW();
        Image screwNE = template.getScrewNE();
        Image screwSW = template.getScrewSW();
        Image screwSE = template.getScrewSE();
        Image sideN = template.getSideN();
        Image sideS = template.getSideS();
        Image background = template.getBackground();

        // Bounds of the zone to repaint
        Rectangle clip = new Rectangle(this.getSize());

        // Coordinates
        int x, y;

        // Stor sizes
        int background_width = background.getWidth(this);
        int background_height = background.getHeight(this);
        int sideN_width = sideN.getWidth(this);
        int sideN_height = sideN.getHeight(this);
        int sideS_width = sideS.getWidth(this);
        int sideS_height = sideS.getHeight(this);
        int screwNE_width = screwNE.getWidth(this);
        int screwSE_width = screwSE.getWidth(this);
        int screwSE_height = screwSE.getHeight(this);
        int screwSW_height = screwSW.getHeight(this);

        // START Paint Background
        if (background_width > 0 && background_height > 0) {
            for (x = clip.x; x < (clip.x + clip.width); x += background_width) {
                for (y = clip.y; y < (clip.y + clip.height); y += background_height) {
                    g.drawImage(background, x, y, this);
                }
            }
        }
        // END Paint Background

        // START Paint North Side
        if (sideN_width > 0 && sideN_height > 0) {
            y = clip.y;
            for (x = clip.x; x < (clip.x + clip.width); x += sideN_width) {
                g.drawImage(sideN, x, y, this);
            }
        }
        // END Paint North Side

        // START Paint South Side
        if (sideS_width > 0 && sideS_height > 0) {
            y = clip.height - sideS_height;
            for (x = clip.x; x < (clip.x + clip.width); x += sideS_width) {
                g.drawImage(sideS, x, y, this);
            }
        }
        // END Paint South Side

        // START Paint Screws
        g.drawImage(screwNW, clip.x, clip.y, this);
        g.drawImage(screwNE, clip.width - screwNE_width, clip.y, this);
        g.drawImage(screwSE, clip.width - screwSE_width, clip.height
                - screwSE_height, this);
        g.drawImage(screwSW, clip.x, clip.height - screwSW_height, this);
        // END Paint Screws
    }

    public void requestForDelete() {
        this.getController().requestForDelete();  
    }

    @Override
    public void removeModule() {
        PSlot slot = (PSlot) getParent();
        slot.getController().remove();
        slot.validate();
        slot.repaint();
        CGLOB.getInstance().getPresentation().myResize();
    }
}
