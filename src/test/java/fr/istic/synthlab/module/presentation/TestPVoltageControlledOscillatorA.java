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
import fr.istic.synthlab.module.control.CVoltageControlledOscillatorA;
import fr.istic.synthlab.module.control.ICVoltageControlledOscillatorA;
import fr.istic.synthlab.util.control.CFemaleJack;
import fr.istic.synthlab.util.control.ICFemaleJack;
import fr.istic.synthlab.util.presentation.PFemaleJack;

/**
 * Test class for PVoltageControlOscillatorA
 * 
 * @author Laurent Legendre
 * 
 */
public class TestPVoltageControlledOscillatorA {

    private static ICVoltageControlledOscillatorA mICVCOA = mock(CVoltageControlledOscillatorA.class);
    private static ICFemaleJack mICFemaleJackInputFM = mock(CFemaleJack.class);
    private static ICFemaleJack mICFemaleJackOutputSine = mock(CFemaleJack.class);
    private static ICFemaleJack mICFemaleJackOutputSquare = mock(CFemaleJack.class);
    private static ICFemaleJack mICFemaleJackOutputTriangular = mock(CFemaleJack.class);
    private static ICFemaleJack mICFemaleJackOutputSawtooth = mock(CFemaleJack.class);
    private static ICGLOB mICGlob = PowerMockito.mock(ICGLOB.class);

    public static void main(String[] args) {
        // stubbing
        when(mICVCOA.getOctaveCoarse()).thenReturn(10);
        when(mICVCOA.getOctaveFine()).thenReturn(80.0);
        when(mICVCOA.getFrequency()).thenReturn(792.0);
        when(mICVCOA.getLineInFM()).thenReturn(mICFemaleJackInputFM);
        when(mICVCOA.getLineOutSine()).thenReturn(mICFemaleJackOutputSine);
        when(mICVCOA.getLineOutSquare()).thenReturn(mICFemaleJackOutputSquare);
        when(mICVCOA.getLineOutTriangular()).thenReturn(
                mICFemaleJackOutputTriangular);
        when(mICVCOA.getLineOutSawtooth()).thenReturn(
                mICFemaleJackOutputSawtooth);
        
        when(mICVCOA.getGlobalControl()).thenReturn(mICGlob);
        when(mICGlob.getPresentation()).thenReturn(new PGLOB(mICGlob));

        when(mICFemaleJackInputFM.getPresentation()).thenReturn(
                new PFemaleJack(mICFemaleJackInputFM));
        when(mICFemaleJackOutputSine.getPresentation()).thenReturn(
                new PFemaleJack(mICFemaleJackOutputSine));
        when(mICFemaleJackOutputSquare.getPresentation()).thenReturn(
                new PFemaleJack(mICFemaleJackOutputSquare));
        when(mICFemaleJackOutputTriangular.getPresentation()).thenReturn(
                new PFemaleJack(mICFemaleJackOutputTriangular));
        when(mICFemaleJackOutputSawtooth.getPresentation()).thenReturn(
                new PFemaleJack(mICFemaleJackOutputSawtooth));

        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBackground(Color.WHITE);

        JFrame jframe = new JFrame(IPVoltageControlledOscillatorA.TITLE);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setContentPane(mainContainer);
        jframe.setSize(300, 500);

        PVoltageControlledOscillatorA pVCOA = new PVoltageControlledOscillatorA(
                mICVCOA);
        mainContainer.add(pVCOA);

        jframe.setVisible(true);
    }
}
