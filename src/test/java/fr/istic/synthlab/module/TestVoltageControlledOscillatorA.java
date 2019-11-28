package fr.istic.synthlab.module;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;

import fr.istic.synthlab.module.control.CVoltageControlledOscillatorA;
import fr.istic.synthlab.module.presentation.IPVoltageControlledOscillatorA;
import fr.istic.synthlab.module.presentation.PVoltageControlledOscillatorA;

/**
 * Test class for PVoltageControlOscillatorA
 * 
 * @author Laurent Legendre
 * 
 */
public class TestVoltageControlledOscillatorA {

//	private final static Logger LOGGER = Logger
//			.getLogger(TestVoltageControlOscillatorA.class.getName());
	
	private static Synthesizer synth = JSyn.createSynthesizer();

	
//	@Before
//	public void setUp() {
//		this.cVcoa = new CVoltageControlOscillatorA(synth);
//		TestVoltageControlOscillatorA.synth.start();
//	}
//	
//	@After
//	public void tearDown() {
//		TestVoltageControlOscillatorA.synth.stop();
//		this.cVcoa = null;
//	}
	
	public static void main(String[] args) {

		JPanel mainContainer = new JPanel(new BorderLayout());
		mainContainer.setBackground(Color.WHITE);

		JFrame jframe = new JFrame(IPVoltageControlledOscillatorA.TITLE);
		jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jframe.setContentPane(mainContainer);
		jframe.setSize(300, 500);

		
//		AVoltageControlledOscillatorA moduleVCO = new AVoltageControlledOscillatorA(synth);
//		moduleVCO.setAmplitude(2);
//		moduleVCO.setFrequency(10, 0);
		
		
		CVoltageControlledOscillatorA cVCOA = new CVoltageControlledOscillatorA(
				synth);
		
		mainContainer.add((PVoltageControlledOscillatorA) cVCOA.getPresentation());

		jframe.setVisible(true);
		
		LineOut sortie = new LineOut();
		synth.add(sortie);
		
		
		cVCOA.getSortieSin().connect(0, sortie.input, 0);
		cVCOA.getSortieSin().connect(0, sortie.input, 1);
		
		sortie.start();
		synth.start();
	}


}
