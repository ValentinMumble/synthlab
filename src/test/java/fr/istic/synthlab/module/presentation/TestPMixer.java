package fr.istic.synthlab.module.presentation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.powermock.api.mockito.PowerMockito;

import fr.istic.synthlab.global.control.ICGLOB;
import fr.istic.synthlab.global.presentation.PGLOB;
import fr.istic.synthlab.module.control.CMixer;
import fr.istic.synthlab.module.control.ICMixer;
import fr.istic.synthlab.util.control.CFemaleJack;
import fr.istic.synthlab.util.control.ICFemaleJack;
import fr.istic.synthlab.util.presentation.PFemaleJack;

/**
 * Test class for PMixer
 *
 * @author Laurent Legendre
 * @version 1.1
 */
public class TestPMixer {

    private static ICMixer mICMixer = mock(CMixer.class);
    private static ICFemaleJack mICJackInput1 = mock(CFemaleJack.class);
    private static ICFemaleJack mICJackInput2 = mock(CFemaleJack.class);
    private static ICFemaleJack mICJackInput3 = mock(CFemaleJack.class);
    private static ICFemaleJack mICJackInput4 = mock(CFemaleJack.class);
    private static ICFemaleJack mICJackOutput = mock(CFemaleJack.class);
    private static ICGLOB mICGlob = PowerMockito.mock(ICGLOB.class);

    public static void main(String[] args) {
      when(mICMixer.getInput(1)).thenReturn(mICJackInput1);
      when(mICMixer.getInput(2)).thenReturn(mICJackInput2);
      when(mICMixer.getInput(3)).thenReturn(mICJackInput3);
      when(mICMixer.getInput(4)).thenReturn(mICJackInput4);
      when(mICMixer.getOutput()).thenReturn(mICJackOutput);
      
      when(mICMixer.getGlobalControl()).thenReturn(mICGlob);
      when(mICGlob.getPresentation()).thenReturn(new PGLOB(mICGlob));

        when(mICJackInput1.getPresentation()).thenReturn(
                new PFemaleJack(mICJackInput1));
        when(mICJackInput2.getPresentation()).thenReturn(
                new PFemaleJack(mICJackInput2));
        when(mICJackInput3.getPresentation()).thenReturn(
                new PFemaleJack(mICJackInput3));
        when(mICJackInput4.getPresentation()).thenReturn(
                new PFemaleJack(mICJackInput4));
        when(mICJackOutput.getPresentation()).thenReturn(
                new PFemaleJack(mICJackOutput));

        JFrame jframe = new JFrame(IPMixer.TITLE);
        PMixer pMixer = (PMixer) new PMixer(mICMixer);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setContentPane(pMixer);
        jframe.setSize(pMixer.getSize());
        jframe.setVisible(true);
    }
}