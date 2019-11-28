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
import fr.istic.synthlab.module.control.CReplicator;
import fr.istic.synthlab.module.control.ICReplicator;
import fr.istic.synthlab.util.control.CFemaleJack;
import fr.istic.synthlab.util.control.ICFemaleJack;
import fr.istic.synthlab.util.presentation.PFemaleJack;

/**
 * Test class for PReplicator
 * 
 * @author valentinmumble
 * 
 */
public class TestPReplicator {

    private static ICReplicator mICReplicator = mock(CReplicator.class);
    private static ICFemaleJack mICJackInput = mock(CFemaleJack.class);
    private static ICFemaleJack mICJackOutput1 = mock(CFemaleJack.class);
    private static ICFemaleJack mICJackOutput2 = mock(CFemaleJack.class);
    private static ICFemaleJack mICJackOutput3 = mock(CFemaleJack.class);
    private static ICGLOB mICGlob = PowerMockito.mock(ICGLOB.class);

    public static void main(String[] args) {

        when(mICReplicator.getInput()).thenReturn(mICJackInput);
        when(mICReplicator.getOutput1()).thenReturn(mICJackOutput1);
        when(mICReplicator.getOutput2()).thenReturn(mICJackOutput2);
        when(mICReplicator.getOutput3()).thenReturn(mICJackOutput3);

        when(mICReplicator.getGlobalControl()).thenReturn(mICGlob);
        when(mICGlob.getPresentation()).thenReturn(new PGLOB(mICGlob));

        when(mICJackInput.getPresentation()).thenReturn(
                new PFemaleJack(mICJackInput));
        when(mICJackOutput1.getPresentation()).thenReturn(
                new PFemaleJack(mICJackOutput1));
        when(mICJackOutput2.getPresentation()).thenReturn(
                new PFemaleJack(mICJackOutput2));
        when(mICJackOutput3.getPresentation()).thenReturn(
                new PFemaleJack(mICJackOutput3));

        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBackground(Color.WHITE);

        JFrame jframe = new JFrame(IPReplicator.TITLE);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setContentPane(mainContainer);
        jframe.setSize(300, 500);

        IPReplicator prep = new PReplicator(mICReplicator);

        mainContainer.add((PReplicator) prep);
        jframe.setVisible(true);
    }
}
