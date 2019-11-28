package fr.istic.synthlab.module;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitOscillator;

import fr.istic.synthlab.module.control.COscilloscope;
import fr.istic.synthlab.module.presentation.IPOscilloscope;
import fr.istic.synthlab.module.presentation.POscilloscope;

/**
 * Test class for the Oscilloscope module
 * 
 * @author valentinmumble
 * 
 */
public class TestOscilloscope {

    public static void main(String[] args) {

        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBackground(Color.WHITE);

        JFrame jframe = new JFrame(IPOscilloscope.TITLE);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setContentPane(mainContainer);
        jframe.setSize(300, 500);

        Synthesizer synth = JSyn.createSynthesizer();

        // Add a tone generator.
        UnitOscillator osc = new SineOscillator();
        osc.frequency.set(345.0);
        osc.amplitude.set(0.6);

        synth.add(osc);
        synth.start();

        COscilloscope scope = new COscilloscope(synth);
        osc.output.connect(scope.getInputPort());

        mainContainer.add((POscilloscope) scope.getPresentation());
        jframe.setVisible(true);
    }
}
