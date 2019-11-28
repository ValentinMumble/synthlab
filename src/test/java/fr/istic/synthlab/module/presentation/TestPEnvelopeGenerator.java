package fr.istic.synthlab.module.presentation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.powermock.api.mockito.PowerMockito;

import fr.istic.synthlab.global.control.ICGLOB;
import fr.istic.synthlab.global.presentation.PGLOB;
import fr.istic.synthlab.module.control.CEnvelopeGenerator;
import fr.istic.synthlab.module.control.ICEnvelopeGenerator;
import fr.istic.synthlab.util.control.CFemaleJack;
import fr.istic.synthlab.util.control.ICFemaleJack;
import fr.istic.synthlab.util.presentation.PFemaleJack;

/**
 * Test class for PEnvelopeGenerator
 * 
 * @author valentinmumble
 * 
 */
public class TestPEnvelopeGenerator {

    private static ICEnvelopeGenerator mICEnvelopeGenerator = mock(CEnvelopeGenerator.class);
    private static ICFemaleJack mICJackGate = mock(CFemaleJack.class);
    private static ICFemaleJack mICJackOutput = mock(CFemaleJack.class);
    private static ICGLOB mICGlob = PowerMockito.mock(ICGLOB.class);

    public static void main(String[] args) {

        when(mICEnvelopeGenerator.getGate()).thenReturn(mICJackGate);
        when(mICEnvelopeGenerator.getOutput()).thenReturn(mICJackOutput);
        
        when(mICEnvelopeGenerator.getGlobalControl()).thenReturn(mICGlob);
        when(mICGlob.getPresentation()).thenReturn(new PGLOB(mICGlob));

        when(mICJackGate.getPresentation()).thenReturn(
                new PFemaleJack(mICJackGate));
        when(mICJackOutput.getPresentation()).thenReturn(
                new PFemaleJack(mICJackOutput));

        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBackground(Color.WHITE);

        JFrame jframe = new JFrame(IPEnvelopeGenerator.TITLE);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setContentPane(mainContainer);
        jframe.setSize(400, 300);

        IPEnvelopeGenerator pscope = new PEnvelopeGenerator(
                mICEnvelopeGenerator);

        mainContainer.add((PEnvelopeGenerator) pscope);
        jframe.setVisible(true);
    }
}
