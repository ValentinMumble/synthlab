package fr.istic.synthlab.module.abstraction;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitOscillator;

/**
* Test class for ARecorderWAV
* 
* @author Favereau
* 
*/
public class TestARecorderWAV {
    private static Synthesizer synth = JSyn.createSynthesizer();
    private UnitOscillator osc1;
    private UnitOscillator osc2;
    private UnitOscillator osc3;
    private LineOut sortie;
    private ARecorderWAV rec;
    
    @Before
    public void setUp() throws FileNotFoundException {
        
        osc1 = new SineOscillator();
        osc1.frequency.set(500.0);
        osc1.amplitude.set(0.6);
        synth.add(osc1);
        
        osc2 = new SineOscillator();
        osc2.frequency.set(200.0);
        osc2.amplitude.set(1);
        synth.add(osc2);
        
        osc3 = new SineOscillator();
        osc3.frequency.set(800.0);
        osc3.amplitude.set(2.5);
        synth.add(osc3);
        
        
        sortie = new LineOut();
        synth.add(sortie);
        
        rec = new ARecorderWAV(synth);
        
        osc1.output.connect(rec.getInputPort(1));
        osc2.output.connect(rec.getInputPort(2));
        osc3.output.connect(rec.getInputPort(3));
        rec.getOutputPort(1).connect(sortie.input);

        sortie.start();
        synth.start();
    }
    
    @After
    public void tearDown() {
        
        synth.stop();
        sortie = null;
        
    }
    
    @Test
    public void testNormalFunction() throws InterruptedException {
        
        rec.startRecord(1);
        Thread.currentThread();
        Thread.sleep(1000);
        rec.startRecord(2);
        Thread.currentThread();
        Thread.sleep(1000);
        rec.startRecord(3);
        Thread.currentThread();
        Thread.sleep(1000);

        rec.stopRecord(1);
        rec.stopRecord(2);
        rec.stopRecord(3);
        
        rec.startRecord(1);
        Thread.currentThread();
        Thread.sleep(1000);
        rec.startRecord(2);
        Thread.currentThread();
        Thread.sleep(1000);
        rec.startRecord(3);
        Thread.currentThread();
        Thread.sleep(1000);
        
        rec.stopRecordAll();
        rec.startRecordAll();
        Thread.currentThread();
        Thread.sleep(1500);
        rec.stopRecordAll();
        
        rec.setFile(1, new File("C:/tmp/Line1/test.wav"));
        rec.setFile(2, new File("C:/tmp/Line2/test.wav"));
        rec.setFile(3, new File("C:/tmp/Line3/test.wav"));
        
        rec.startRecordAll();
        Thread.currentThread();
        Thread.sleep(1000);
        rec.stopRecordAll();
        
        assertTrue(true);
    }
    
}
