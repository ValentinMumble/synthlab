package fr.istic.synthlab.module;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;

import fr.istic.synthlab.module.control.CVoltageControlledAmplifier;
import fr.istic.synthlab.module.control.CVoltageControlledOscillatorA;
import fr.istic.synthlab.module.presentation.IPVoltageControlledOscillatorA;
import fr.istic.synthlab.module.presentation.PVoltageControlledAmplifier;
import fr.istic.synthlab.module.presentation.PVoltageControlledOscillatorA;

public class TestVoltageControlledAmplifier {

    private static Synthesizer synth = JSyn.createSynthesizer();
   
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBackground(Color.WHITE);

        JFrame jframe = new JFrame(IPVoltageControlledOscillatorA.TITLE);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setContentPane(mainContainer);
        jframe.setSize(1000, 500);
        
        

        
        
//      AVoltageControlledOscillatorA moduleVCO = new AVoltageControlledOscillatorA(synth);
//      moduleVCO.setAmplitude(2);
//      moduleVCO.setFrequency(10, 0);
        
        
        CVoltageControlledOscillatorA signal = new CVoltageControlledOscillatorA(synth);
        CVoltageControlledOscillatorA modul = new CVoltageControlledOscillatorA(synth);
        CVoltageControlledAmplifier cVCA = new CVoltageControlledAmplifier(synth);
        
        mainContainer.add((PVoltageControlledOscillatorA) signal.getPresentation());
        mainContainer.add((PVoltageControlledOscillatorA) modul.getPresentation());
        mainContainer.add((PVoltageControlledAmplifier) cVCA.getPresentation());

        jframe.setVisible(true);
        
        LineOut sortie = new LineOut();
        synth.add(sortie);
        
        signal.getSortieSin().connect(cVCA.getInputPort());
        
        cVCA.getOutputPort().connect(0, sortie.input, 0);
        cVCA.getOutputPort().connect(0, sortie.input, 1);
        
        sortie.start();
        synth.start();
    }

}
