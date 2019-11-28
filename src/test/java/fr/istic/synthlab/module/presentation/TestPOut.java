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
import fr.istic.synthlab.module.control.COut;
import fr.istic.synthlab.module.control.ICOut;
import fr.istic.synthlab.util.control.CFemaleJack;
import fr.istic.synthlab.util.control.ICFemaleJack;
import fr.istic.synthlab.util.presentation.PFemaleJack;

/**
 * Test class for POut
 * 
 * @author valentinmumble
 * 
 */
public class TestPOut {

    private static ICOut mICOut = mock(COut.class);
    private static ICFemaleJack mICFemaleJackLineIn = mock(CFemaleJack.class);
    private static ICGLOB mICGlob = PowerMockito.mock(ICGLOB.class);

    public static void main(String[] args) {

        when(mICOut.getAttenuation()).thenReturn(.0);
        when(mICOut.getLineIn()).thenReturn(mICFemaleJackLineIn);
        
        when(mICOut.getGlobalControl()).thenReturn(mICGlob);
        when(mICGlob.getPresentation()).thenReturn(new PGLOB(mICGlob));

        when(mICFemaleJackLineIn.getPresentation()).thenReturn(
                new PFemaleJack(mICFemaleJackLineIn));

        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBackground(Color.WHITE);

        JFrame jframe = new JFrame(IPOut.TITLE);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setContentPane(mainContainer);
        jframe.setSize(300, 500);

        IPOut pout = new POut(mICOut);

        mainContainer.add((POut) pout);
        jframe.setVisible(true);
    }
}