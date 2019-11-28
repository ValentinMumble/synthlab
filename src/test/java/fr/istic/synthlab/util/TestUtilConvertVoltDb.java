package fr.istic.synthlab.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestUtilConvertVoltDb {

    private double decibel = 0.0D;
    private double voltage = 1.0D;

    @Test
    public void testDecibelToVoltage() {
        this.decibel = 0.0D;
        assertEquals(1.0D, Util.decibelsToVoltage(decibel), 0.0001);
        this.decibel = 5.0D;
        assertEquals(1.778279D, Util.decibelsToVoltage(decibel), 0.0001);
        this.decibel = -5.0D;
        assertEquals(0.562341D, Util.decibelsToVoltage(decibel), 0.0001);

    }
    
    @Test
    public void testVoltageToDecibel() {
        this.voltage = 1.0D;
        assertEquals(0.0D, Util.voltageToDecibels(voltage), 0.0001);
        this.voltage = 1.778279D;
        assertEquals(5.0D, Util.voltageToDecibels(voltage), 0.0001);
        this.voltage = 0.562341D;
        assertEquals(-5.0D, Util.voltageToDecibels(voltage), 0.0001);
    }

}
