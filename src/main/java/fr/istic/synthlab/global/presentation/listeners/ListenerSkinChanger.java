package fr.istic.synthlab.global.presentation.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import fr.istic.synthlab.global.control.CGLOB;
import fr.istic.synthlab.global.control.CSlot;
import fr.istic.synthlab.global.presentation.PGLOB;
import fr.istic.synthlab.util.Template;

public class ListenerSkinChanger implements ActionListener {

    private Template template;

    public ListenerSkinChanger(Template template) {
        this.template = template;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        PGLOB.setTemplate(this.template);
        refreshSkin();
    }

    public void refreshSkin() {
        ArrayList<CSlot[]> racks = CGLOB.getInstance().getCPanelContent()
                .getGrille();
        for (CSlot[] rack : racks) {
            for (CSlot slot : rack) {
                slot.getPresentation().repaint();
            }
        }
    }

}
