package fr.istic.synthlab.module;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitOscillator;

import fr.istic.synthlab.module.control.COscilloscope;
import fr.istic.synthlab.module.control.COut;
import fr.istic.synthlab.module.control.CReplicator;
import fr.istic.synthlab.module.presentation.IPReplicator;
import fr.istic.synthlab.module.presentation.POscilloscope;
import fr.istic.synthlab.module.presentation.POut;
import fr.istic.synthlab.module.presentation.PReplicator;

/**
 * Test class for the Replicator module
 * 
 * @author valentinmumble
 * 
 */
public class TestReplicator {

    public static void main(String[] args) {

        JPanel mainContainer = new JPanel(new GridLayout());
        mainContainer.setBackground(Color.WHITE);

        JFrame jframe = new JFrame(IPReplicator.TITLE);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setContentPane(mainContainer);
        jframe.setSize(1000, 400);

        Synthesizer synth = JSyn.createSynthesizer();

        // Add a tone generator.
        UnitOscillator osc = new SineOscillator();
        osc.frequency.set(345.0);
        osc.amplitude.set(0.6);

        synth.add(osc);
        synth.start();

        CReplicator replicator = new CReplicator(synth);
        osc.output.connect(replicator.getInputPort());

        COscilloscope osc1 = new COscilloscope(synth);
        COscilloscope osc2 = new COscilloscope(synth);
        COscilloscope osc3 = new COscilloscope(synth);
        
        COut out = new COut(synth);

        osc1.getInputPort().connect(replicator.getOutput1Port());
        osc2.getInputPort().connect(replicator.getOutput2Port());
        osc3.getInputPort().connect(replicator.getOutput3Port());

        out.getInputPort().connect(osc3.getOutputPort());

        mainContainer.add((PReplicator) replicator.getPresentation());
        mainContainer.add((POscilloscope) osc1.getPresentation());
        mainContainer.add((POscilloscope) osc2.getPresentation());
        mainContainer.add((POscilloscope) osc3.getPresentation());
        mainContainer.add((POut) out.getPresentation());
        
        jframe.setVisible(true);
    }
}
