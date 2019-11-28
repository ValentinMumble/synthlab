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
import fr.istic.synthlab.module.control.CVoltageControlledFilterLowPass;
import fr.istic.synthlab.module.control.ICVoltageControlledFilterLowPass;
import fr.istic.synthlab.util.control.CFemaleJack;
import fr.istic.synthlab.util.control.ICFemaleJack;
import fr.istic.synthlab.util.presentation.PFemaleJack;

/**
 * Test class for PVoltageControlledFilterLowPass
 *
 * @author Florent
 *
 */
public class TestPVoltageControlledFilterLowPass {

    private static ICVoltageControlledFilterLowPass mICVCFLP = mock(CVoltageControlledFilterLowPass.class);
    private static ICFemaleJack mICFemaleJackInputFM = mock(CFemaleJack.class);
    private static ICFemaleJack mICFemaleJackInputSignal = mock(CFemaleJack.class);
    private static ICFemaleJack mICFemaleJackOutput = mock(CFemaleJack.class);
    private static ICGLOB mICGlob = PowerMockito.mock(ICGLOB.class);

    public static void main(String[] args) {
        // stubbing
        when(mICVCFLP.getInput()).thenReturn(mICFemaleJackInputSignal);
        when(mICVCFLP.getFreqModInput()).thenReturn(mICFemaleJackInputFM);
        when(mICVCFLP.getOutput()).thenReturn(mICFemaleJackOutput);
        when(mICVCFLP.getCutoffFrequency()).thenReturn(80.0);
        when(mICVCFLP.getResonance()).thenReturn(792.0);

        when(mICVCFLP.getGlobalControl()).thenReturn(mICGlob);
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

        PVoltageControlledFilterLowPass pVCFLP = new PVoltageControlledFilterLowPass(
                mICVCFLP);
        mainContainer.add(pVCFLP);

        jframe.setVisible(true);
    }
}
