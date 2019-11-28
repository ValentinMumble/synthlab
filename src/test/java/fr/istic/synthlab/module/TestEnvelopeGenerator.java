package fr.istic.synthlab.module;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.SquareOscillator;
import com.jsyn.unitgen.UnitOscillator;

import fr.istic.synthlab.module.control.CEnvelopeGenerator;
import fr.istic.synthlab.module.control.COscilloscope;
import fr.istic.synthlab.module.control.COut;
import fr.istic.synthlab.module.presentation.IPEnvelopeGenerator;
import fr.istic.synthlab.module.presentation.PEnvelopeGenerator;
import fr.istic.synthlab.module.presentation.POscilloscope;
import fr.istic.synthlab.module.presentation.POut;

/**
 * Test class for the Envelope Generator module
 * 
 * @author valentinmumble
 * 
 */
public class TestEnvelopeGenerator {

    public static void main(String[] args) {

        JPanel mainContainer = new JPanel(new GridLayout());
        JFrame jframe = new JFrame(IPEnvelopeGenerator.TITLE);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setContentPane(mainContainer);
        jframe.setSize(800, 400);

        Synthesizer synth = JSyn.createSynthesizer();

        UnitOscillator gatingOsc = new SquareOscillator();
        UnitOscillator osc = new SineOscillator();

        // Low frequency for the gate
        gatingOsc.frequency.setup(0.001, 1, 10.0);

        // Normal frequency for the amplitude modulation
        osc.frequency.setup(50.0, 440.0, 2000.0);

        synth.add(gatingOsc);
        synth.add(osc);
        synth.start();

        CEnvelopeGenerator eg = new CEnvelopeGenerator(synth);
        gatingOsc.output.connect(eg.getGatePort());

        COscilloscope scope = new COscilloscope(synth);
        eg.getOutputPort().connect(osc.amplitude);

        osc.output.connect(scope.getInputPort());

        COut out = new COut(synth);
        out.getInputPort().connect(scope.getOutputPort());

        mainContainer.add((PEnvelopeGenerator) eg.getPresentation());
        mainContainer.add((POscilloscope) scope.getPresentation());
        mainContainer.add((POut) out.getPresentation());
        jframe.setVisible(true);
    }
}
