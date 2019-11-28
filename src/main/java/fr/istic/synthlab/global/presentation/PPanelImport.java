package fr.istic.synthlab.global.presentation;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jsyn.Synthesizer;

import fr.istic.synthlab.global.control.CBlockImport;
import fr.istic.synthlab.global.control.CPanelImport;
import fr.istic.synthlab.util.Icons;
import fr.istic.synthlab.util.Util.Module;

/**
 * Contains the importable modules (Drag&Drop) .
 *
 * @author Jonathan
 */
public class PPanelImport extends JPanel {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -2588097793392478393L;
    
    /**
     * Control of the PanelImport.
     *
     * @category PAC
     */
    private CPanelImport control;

    /**
     * Contructor of the PanelImport Presentation.
     *
     * @category Constructor
     * @param control
     *            Control of the PanelImport.
     * @param synthesizer
     *            global synthesizer.
     */
    public PPanelImport(CPanelImport c, Synthesizer synthesizer) {
        this.control = c;
        init(synthesizer);
    }

    /**
     * @return the PanelImport controller.
     */
    public CPanelImport getControle() {
        return control;
    }

    /**
     * Initialize the Panel.
     * 
     * @param synthesizer
     *            global synthesizer.
     */
    private void init(Synthesizer synthesizer) {
        this.setOpaque(false);
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbcLogo = new GridBagConstraints(0, 0,
                GridBagConstraints.REMAINDER, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
                        0, 0, 0, 0), 0, 0);

        GridBagConstraints gbcMiniModules = new GridBagConstraints(0, 1,
                GridBagConstraints.REMAINDER, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
                        0, 10, 8, 10), 0, 0);

        JLabel logo = new JLabel(Icons.SYNTHLAB_LOGO);
        this.add(logo, gbcLogo);
        for (int i = 0; i < Module.values().length; i++) {
            // transgression PAC
            PBlockImport pBlockImport = new CBlockImport(Module.values()[i],
                    synthesizer).getPresentation();

            pBlockImport.setSize(this.getWidth() - 20, 120);
            pBlockImport.setPreferredSize(pBlockImport.getSize());

            this.add(pBlockImport, gbcMiniModules);
            gbcMiniModules.gridy++;
        }
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));
    }
}
