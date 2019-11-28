package fr.istic.synthlab.module;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;

import fr.istic.synthlab.module.control.COscilloscope;
import fr.istic.synthlab.module.control.COut;
import fr.istic.synthlab.module.control.CVoltageControlledFilterLowPass;
import fr.istic.synthlab.module.control.CVoltageControlledOscillatorA;
import fr.istic.synthlab.module.control.ICOut;
import fr.istic.synthlab.module.control.ICVoltageControlledFilterLowPass;
import fr.istic.synthlab.module.presentation.IPVoltageControlledFilterLowPass;
import fr.istic.synthlab.module.presentation.POscilloscope;
import fr.istic.synthlab.module.presentation.PVoltageControlledFilterLowPass;
import fr.istic.synthlab.module.presentation.PVoltageControlledOscillatorA;

public class TestVoltageControlledFilterLowPass {

    public static void main(String[] args) {

        JPanel mainContainer = new JPanel(new GridLayout());
        JFrame jframe = new JFrame(IPVoltageControlledFilterLowPass.TITLE);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setContentPane(mainContainer);
        jframe.setSize(800, 400);

        Synthesizer synth = JSyn.createSynthesizer();
        synth.start();

        ICVoltageControlledFilterLowPass vcflp = new CVoltageControlledFilterLowPass(
                synth);

        CVoltageControlledOscillatorA vcoa = new CVoltageControlledOscillatorA(
                synth);
        CVoltageControlledOscillatorA vcoaFM = new CVoltageControlledOscillatorA(
                synth);
        vcoa.getSortieSin().connect(vcflp.getInputPort());
        vcoaFM.getSortieSqu().connect(vcflp.getFreqModInputPort());

        COscilloscope scope = new COscilloscope(synth);
        scope.getInputPort().connect(vcflp.getOutputPort());

        ICOut out = new COut(synth);
        scope.getOutputPort().connect(out.getInputPort());

        mainContainer.add((PVoltageControlledOscillatorA) vcoa
                .getPresentation());
        mainContainer.add((PVoltageControlledOscillatorA) vcoaFM
                .getPresentation());
        mainContainer.add((PVoltageControlledFilterLowPass) vcflp
                .getPresentation());
        mainContainer.add((POscilloscope) scope.getPresentation());
        jframe.setVisible(true);

        vcflp.setCutoffFrequency(440);
    }

}
