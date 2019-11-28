package fr.istic.synthlab.module;



import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;

import fr.istic.synthlab.module.control.CWhiteNoise;
import fr.istic.synthlab.module.presentation.IPWhiteNoise;
import fr.istic.synthlab.module.presentation.PWhiteNoise;

/**
 * Test class for the white noise module
 * 
 * @author Favereau
 * 
 */
public class TestWhiteNoise{
    
    public static void main(String[] args) throws Exception {
        JFrame jframe = new JFrame(IPWhiteNoise.TITLE);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        Synthesizer synth = JSyn.createSynthesizer();

        CWhiteNoise cwn = new CWhiteNoise(synth);

        LineOut sortie = new LineOut();
        synth.add(sortie);
        
        
        cwn.getOutputPort().connect(0, sortie.input, 0);
        cwn.getOutputPort().connect(0, sortie.input, 1);
        
        sortie.start();
        
        synth.start();

        PWhiteNoise pwn = (PWhiteNoise) cwn.getPresentation();
        jframe.setContentPane(pwn);
        jframe.setSize(pwn.getSize());
        jframe.setVisible(true);
    }
}
