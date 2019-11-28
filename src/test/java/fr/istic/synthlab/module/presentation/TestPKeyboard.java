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
import fr.istic.synthlab.module.control.CKeyboard;
import fr.istic.synthlab.module.control.ICKeyboard;
import fr.istic.synthlab.util.control.CFemaleJack;
import fr.istic.synthlab.util.control.ICFemaleJack;
import fr.istic.synthlab.util.presentation.PFemaleJack;

/**
 * Test class for the Presentation of Keyboard module
 * 
 * @author florent
 * 
 */

public class TestPKeyboard {

    private static ICKeyboard mICKeyboard = mock(CKeyboard.class);
    private static ICFemaleJack mICFemaleJackGate = mock(CFemaleJack.class);
    private static ICFemaleJack mICFemaleJackControlVoltage= mock(CFemaleJack.class);
    private static ICGLOB mICGlob = PowerMockito.mock(ICGLOB.class);
    
    public static void main(String[] args) {
        // stubbing
        when(mICKeyboard.getOctave()).thenReturn(3.0);
        when(mICKeyboard.getNote()).thenReturn(3.0);
        when(mICKeyboard.getGate()).thenReturn(mICFemaleJackGate);
        when(mICKeyboard.getControlVoltage()).thenReturn(mICFemaleJackControlVoltage);
        
        when(mICKeyboard.getGlobalControl()).thenReturn(mICGlob);
        when(mICGlob.getPresentation()).thenReturn(new PGLOB(mICGlob));

        when(mICFemaleJackGate.getPresentation()).thenReturn(
                new PFemaleJack(mICFemaleJackGate));
        when(mICFemaleJackControlVoltage.getPresentation()).thenReturn(
                new PFemaleJack(mICFemaleJackControlVoltage));


        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBackground(Color.WHITE);

        JFrame jframe = new JFrame(IPKeyboard.TITLE);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setContentPane(mainContainer);
        jframe.setSize(300, 500);

        PKeyboard pKeyboard = new PKeyboard(
                mICKeyboard);
        mainContainer.add(pKeyboard);
        jframe.setVisible(true);
    }
}
