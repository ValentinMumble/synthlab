package fr.istic.synthlab.module;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitOscillator;

import fr.istic.synthlab.module.control.CRecorderWAV;
import fr.istic.synthlab.module.presentation.PRecorderWAV;

/**
 * Test class for the RecorderWAV module
 * 
 * @author Favereau
 * 
 */
public class TestRecorderWAV {

    public static void main(String[] args) {
        JFrame jframe = new JFrame("TestRecorderWAV");
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        Synthesizer synth = JSyn.createSynthesizer();

        // Add a tone generator.
        UnitOscillator osc = new SineOscillator();
        osc.frequency.set(345.0);
        osc.amplitude.set(0.6);

        synth.add(osc);
        synth.start();

        CRecorderWAV enreg = new CRecorderWAV(synth);
        
        osc.output.connect(enreg.getInputPort(1));

        PRecorderWAV present = (PRecorderWAV) enreg.getPresentation();
        jframe.setContentPane(present);
        jframe.setSize(present.getSize());

        jframe.setVisible(true);

    }

}
