package fr.istic.synthlab.module;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;

import fr.istic.synthlab.module.control.CKeyboard;
import fr.istic.synthlab.module.control.COut;
import fr.istic.synthlab.module.control.CVoltageControlledOscillatorA;
import fr.istic.synthlab.module.control.ICKeyboard;
import fr.istic.synthlab.module.control.ICOut;
import fr.istic.synthlab.module.control.ICVoltageControlledOscillatorA;
import fr.istic.synthlab.module.presentation.IPKeyboard;
import fr.istic.synthlab.module.presentation.PKeyboard;
import fr.istic.synthlab.util.Util;

/**
 * Test class for the Keyboard module
 * 
 * @author valentinmumble
 * 
 */
public class TestKeyboard {

    public static void main(String[] args) {
        
        JPanel mainContainer = new JPanel(new GridLayout());
        mainContainer.setBackground(Color.WHITE);

        JFrame jframe = new JFrame(IPKeyboard.TITLE);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setContentPane(mainContainer);
        jframe.setSize(IPKeyboard.WIDTH_U * Util.SLOT_WIDTH, Util.SLOT_HEIGHT);

        Synthesizer synth = JSyn.createSynthesizer();
        synth.start();

        ICKeyboard keyboard = new CKeyboard(synth);

        ICOut out = new COut(synth);

        ICVoltageControlledOscillatorA vco = new CVoltageControlledOscillatorA(
                synth);

        vco.getFreqModIN().connect(keyboard.getControlVoltagePort());

        out.getInputPort().connect(vco.getSortieSin());

        // mainContainer.add((PVoltageControlledOscillatorA)
        // vco.getPresentation());
        PKeyboard pkeyboard = (PKeyboard) keyboard.getPresentation();
        mainContainer.add(pkeyboard);
        // mainContainer.add((POut) out.getPresentation());

        jframe.setVisible(true);
        pkeyboard.requestFocusInWindow();

    }
}
