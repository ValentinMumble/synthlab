package fr.istic.synthlab.global.presentation;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import fr.istic.synthlab.global.abstraction.APanelContent;
import fr.istic.synthlab.global.control.CPanelContent;
import fr.istic.synthlab.global.control.CSlot;
import fr.istic.synthlab.util.Util;

/**
 * Contains the imported elements.
 *
 * @author Jonathan
 */
public class PPanelContent extends JPanel {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 3827527739916453156L;

    /**
     * Control of the PanelContent.
     *
     * @category PAC
     */
    private CPanelContent control;

    /**
     * Used to set the grid anchored to the left.
     */
    private ArrayList<JPanel> anchorLeft;

    /**
     * Presentation of the PanelContent.
     *
     * @category Constructor
     * @param control
     *            Control of the PanelCOntent.
     */
    public PPanelContent(CPanelContent control) {
        this.control = control;
    }

    /**
     * @return the PanelContent controller.
     */
    public CPanelContent getControle() {
        return control;
    }

    /**
     * Initialize the JPanel.
     */
    public void init() {
        this.setBackground(Color.BLACK);
        anchorLeft = new ArrayList<JPanel>();
        this.setLayout(new GridBagLayout());
        for (int i = 0; i < APanelContent.NB_SLOTS_BY_RACK; i++) {
            // Ajouter une première ligne caché pour
            // éviter le déformement lors d'ajouts de modules
            PSlot pCache = new CSlot(-1, -1, getControle())
                    .getPresentation();
            pCache.setSize(Util.SLOT_WIDTH, 0);
            pCache.setPreferredSize(pCache.getSize());
            GridBagConstraints gbcCache = new GridBagConstraints();
            gbcCache.insets = new Insets(0, 1, 1, 0);
            gbcCache.gridx = i;
            gbcCache.gridy = 0;
            this.add(pCache, gbcCache);
        }
        control.addLine();
        control.addLine();
    }

    /**
     * Complete an empty line with empty Slot.
     * 
     * @param numLigne
     *            the number of the line to complete.
     */
    public void addLine(int numLigne) {
        ArrayList<CSlot[]> grille = control.getGrille();
        for (int j = 0; j < grille.get(numLigne).length; j++) {
            PSlot slot = grille.get(numLigne)[j]
                    .getPresentation();
            slot.setSize(Util.SLOT_WIDTH, Util.SLOT_HEIGHT);
            slot.setPreferredSize(slot.getSize());
            slot.setBorder(BorderFactory
                    .createLineBorder(Color.black, 1));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(0, 1, 1, 0);
            gbc.gridx = j;
            gbc.gridy = numLigne + 1;
            gbc.anchor = GridBagConstraints.WEST;
            slot.setGbc(gbc);
            this.add(slot, gbc);

        }

        anchorLeft.add(new JPanel());
        GridBagConstraints gbcCache = new GridBagConstraints();
        gbcCache.gridx = grille.get(numLigne).length + 1;
        gbcCache.gridy = numLigne + 1;
        gbcCache.weightx = 1.0;
        this.add(anchorLeft.get(numLigne), gbcCache);

        myResize();
    }

    /**
     * Remove the last line.
     */
    public void removeLine() {
        CSlot[] line = control.getGrille().get(
                control.getGrille().size() - 1);
        for (int j = 0; j < line.length; j++) {
            this.remove(line[j].getPresentation());
        }
        this.remove(anchorLeft.get(control.getGrille().size() - 1));
        anchorLeft.remove(control.getGrille().size() - 1);
    }

    /**
     * Resize the Slot in the grid.
     */
    public void myResize() {
        ArrayList<CSlot[]> grille = control.getGrille();
        for (int i = 0; i < grille.size(); i++) {
            for (int j = 0; j < grille.get(i).length; j++) {
                PSlot slot = grille.get(i)[j].getPresentation();
                if (grille.get(i)[j].getICModule() != null) {
                    // Si le Slot contient un module
                    // On parcours la grille : i = ligne / j = colonne
                    int unite = grille.get(i)[j].getICModule()
                            .getPresentation().getWidthU();
                    // On récup le nombre de case que prend le composant
                    slot.setSize(unite * Util.SLOT_WIDTH + unite - 1,
                            Util.SLOT_HEIGHT);
                    slot.setPreferredSize(grille.get(i)[j]
                            .getPresentation().getSize());
                    slot.getGbc().gridwidth = unite;
                    this.remove(slot);
                    this.add(slot, slot.getGbc());
                    for (int cache = 1; cache < unite; cache++) {
                        // On parcours les cases qui suivent le composant et qui
                        // font partie de ce module
                        PSlot nextSlot = grille.get(i)[j
                                + cache].getPresentation();
                        nextSlot.getGbc().insets = new Insets(0, 1,
                                1, 0);
                        this.remove(nextSlot);
                        this.add(nextSlot,
                                nextSlot.getGbc());
                        nextSlot.setSize(0, 0);
                        nextSlot
                                .setPreferredSize(nextSlot
                                        .getSize());
                    }
                    j += unite - 1;
                } else {
                    slot.getGbc().gridwidth = 1;
                    slot.getGbc().insets = new Insets(0, 1, 1, 0);
                    this.remove(slot);
                    this.add(slot, slot.getGbc());
                    grille.get(i)[j].getPresentation().setSize(Util.SLOT_WIDTH,
                            Util.SLOT_HEIGHT);
                    grille.get(i)[j].getPresentation().setPreferredSize(
                            grille.get(i)[j].getPresentation().getSize());
                }
            }
        }
        revalidate();
    }

    public void resetBorders() {
        ArrayList<CSlot[]> racks = control.getGrille();
        for (CSlot[] rack : racks) {
            for (CSlot slot : rack) {
                slot.getPresentation().c2p_showNeutre();
            }
        }
    }
}
