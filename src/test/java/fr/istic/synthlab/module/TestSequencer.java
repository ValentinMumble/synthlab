package fr.istic.synthlab.module;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;

import fr.istic.synthlab.module.control.COut;
import fr.istic.synthlab.module.control.CSequencer;
import fr.istic.synthlab.module.control.CVoltageControlledOscillatorA;
import fr.istic.synthlab.module.control.ICOut;
import fr.istic.synthlab.module.presentation.IPVoltageControlledFilterLowPass;
import fr.istic.synthlab.module.presentation.POut;
import fr.istic.synthlab.module.presentation.PSequencer;
import fr.istic.synthlab.module.presentation.PVoltageControlledOscillatorA;

public class TestSequencer {

    public static void main(String[] args) {

        JPanel mainContainer = new JPanel(new GridLayout());
        JFrame jframe = new JFrame(IPVoltageControlledFilterLowPass.TITLE);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setContentPane(mainContainer);
        jframe.setSize(800, 400);

        Synthesizer synth = JSyn.createSynthesizer();
        synth.start();


        CVoltageControlledOscillatorA vcoa1 = new CVoltageControlledOscillatorA(
                synth);
        CVoltageControlledOscillatorA vcoa2 = new CVoltageControlledOscillatorA(
                synth);
        CSequencer seq = new CSequencer(synth);

        ICOut out = new COut(synth);
        vcoa2.getSortieSin().connect(seq.getGatePort());
        seq.getOutputPort().connect(vcoa1.getFreqModIN());
        out.getInputPort().connect(vcoa1.getSortieSin());

        mainContainer.add((PVoltageControlledOscillatorA) vcoa1
                .getPresentation());
        mainContainer.add((PVoltageControlledOscillatorA) vcoa2
                .getPresentation());
        mainContainer.add((PSequencer) seq.getPresentation());
        mainContainer.add((POut)out.getPresentation());

        jframe.setVisible(true);
    }

}
