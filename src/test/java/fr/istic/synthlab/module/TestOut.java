package fr.istic.synthlab.module;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitOscillator;

import fr.istic.synthlab.module.control.COut;
import fr.istic.synthlab.module.presentation.IPOut;
import fr.istic.synthlab.module.presentation.POut;

/**
 * Test class for the Out module
 * 
 * @author valentinmumble
 * 
 */
public class TestOut {

    public static void main(String[] args) {

        JFrame jframe = new JFrame(IPOut.TITLE);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        Synthesizer synth = JSyn.createSynthesizer();

        // Add a tone generator.
        UnitOscillator osc = new SineOscillator();
        osc.frequency.set(345.0);
        osc.amplitude.set(0.6);

        synth.add(osc);
        synth.start();

        COut cOut = new COut(synth);
        osc.output.connect(cOut.getInputPort());

        POut pOut = (POut) cOut.getPresentation();
        jframe.setContentPane(pOut);
        jframe.setSize(pOut.getSize());

        jframe.setVisible(true);
    }
}
