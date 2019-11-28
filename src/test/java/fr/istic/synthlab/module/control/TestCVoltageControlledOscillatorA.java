package fr.istic.synthlab.module.control;


import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;

import fr.istic.synthlab.module.presentation.IPVoltageControlledOscillatorA;
import fr.istic.synthlab.module.presentation.PVoltageControlledOscillatorA;

public class TestCVoltageControlledOscillatorA {

    private static Synthesizer synt = JSyn.createSynthesizer();

    private static CVoltageControlledOscillatorA vcoSignal;
    private static CVoltageControlledOscillatorA vcoModul;



    private static LineOut sortieDirecte;




    public static void main(String[] args) {

        // visual test with presentation
        JPanel mainContainer = new JPanel(new FlowLayout());
        mainContainer.setBackground(Color.WHITE);

        JFrame jframe = new JFrame(IPVoltageControlledOscillatorA.TITLE);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setContentPane(mainContainer);
        jframe.setSize(300, 500);

        jframe.setVisible(true);

        sortieDirecte = new LineOut();
        synt.add(sortieDirecte);

        vcoSignal = new CVoltageControlledOscillatorA(synt);
        mainContainer.add((PVoltageControlledOscillatorA) vcoSignal.getPresentation());

        vcoModul = new CVoltageControlledOscillatorA(synt);
        mainContainer.add((PVoltageControlledOscillatorA) vcoModul.getPresentation());

        vcoSignal.getSortieSin().connect(0, sortieDirecte.input, 0);
        vcoSignal.getSortieSin().connect(0, sortieDirecte.input, 1);

        vcoModul.octaveCoarseChanged(1);

        sortieDirecte.start();
        synt.start();

        vcoSignal.getFreqModIN().connect(vcoModul.getSortieSqu());

    }
}
