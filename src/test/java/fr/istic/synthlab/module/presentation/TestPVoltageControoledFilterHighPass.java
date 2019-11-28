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
import fr.istic.synthlab.module.control.CVoltageControlledFilterHighPass;
import fr.istic.synthlab.module.control.ICVoltageControlledFilterHighPass;
import fr.istic.synthlab.util.control.CFemaleJack;
import fr.istic.synthlab.util.control.ICFemaleJack;
import fr.istic.synthlab.util.presentation.PFemaleJack;

/**
 * Test class for PVoltageControlledFilterLowPass
 *
 * @author Florent
 *
 */
public class TestPVoltageControoledFilterHighPass {

    private static ICVoltageControlledFilterHighPass mICVCFHP = mock(CVoltageControlledFilterHighPass.class);
    private static ICFemaleJack mICFemaleJackInputFM = mock(CFemaleJack.class);
    private static ICFemaleJack mICFemaleJackInputSignal = mock(CFemaleJack.class);
    private static ICFemaleJack mICFemaleJackOutput = mock(CFemaleJack.class);   
    private static ICGLOB mICGlob = PowerMockito.mock(ICGLOB.class);

    public static void main(String[] args) {
        // stubbing
        when(mICVCFHP.getInput()).thenReturn(mICFemaleJackInputSignal);
        when(mICVCFHP.getFreqModInput()).thenReturn(mICFemaleJackInputFM);
        when(mICVCFHP.getOutput()).thenReturn(mICFemaleJackOutput);
        when(mICVCFHP.getCutoffFrequency()).thenReturn(80.0);
        
        when(mICVCFHP.getGlobalControl()).thenReturn(mICGlob);
        when(mICGlob.getPresentation()).thenReturn(new PGLOB(mICGlob));
        
        when(mICFemaleJackInputFM.getPresentation()).thenReturn(
                new PFemaleJack(mICFemaleJackInputFM));
        when(mICFemaleJackInputSignal.getPresentation()).thenReturn(
                new PFemaleJack(mICFemaleJackInputSignal));
        when(mICFemaleJackOutput.getPresentation()).thenReturn(
                new PFemaleJack(mICFemaleJackOutput));

        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBackground(Color.WHITE);

        JFrame jframe = new JFrame(IPVoltageControlledFilterLowPass.TITLE);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setContentPane(mainContainer);
        jframe.setSize(300, 500);

        PVoltageControlledFilterHighPass pVCFHP = new PVoltageControlledFilterHighPass(
                mICVCFHP);
        mainContainer.add(pVCFHP);

        jframe.setVisible(true);
    }
}
