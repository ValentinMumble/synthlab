package fr.istic.synthlab.cable.control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;

import fr.istic.synthlab.util.control.CFemaleJack;
import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * Test class for CCable
 *
 * @author Mickael
 * @version 1.1
 */
public class TestCCable {

    protected ICCable controlCable;

    protected ICFemaleJack inputPortTest1;
    protected ICFemaleJack inputPortTest2;

    protected ICFemaleJack outputPortTest1;
    protected ICFemaleJack outputPortTest2;


    @Before
    public void setUp(){

        inputPortTest1 = new CFemaleJack(new UnitInputPort("input1"), true);
        inputPortTest2 = new CFemaleJack(new UnitInputPort("input2"), true);
        outputPortTest1 = new CFemaleJack(new UnitOutputPort("output1"), false);
        outputPortTest2 = new CFemaleJack(new UnitOutputPort("output2"), false);

        controlCable = new CCable(inputPortTest1);
    }

    @After
    public void tearDown() {
    }

    @Test
    public final void testGetPresentation() {
        assertNotEquals("Presentation of CCAble is NULL", null, controlCable.getPresentation());
    }

    @Test
    public final void testPlug1() {
        controlCable.plug(inputPortTest1);

        assertEquals("The Port1 has been modified", inputPortTest1, controlCable.getJack1());
        assertEquals("The Port2 has been modified", null, controlCable.getJack2());
    }
    @Test
    public final void testPlug2() {
        controlCable.plug(outputPortTest2);

        assertEquals("The Port1 has been modified", inputPortTest1, controlCable.getJack1());
        assertEquals("The Port2 hasn't the good value", outputPortTest2, controlCable.getJack2());
        assertEquals("test p1", true, controlCable.getJack1().isInput());
        assertEquals("test p2", false, controlCable.getJack2().isInput());
        assertEquals("The two ports are not connected",
                true,
                ((UnitInputPort)(controlCable.getJack1().getPort())).isConnected()
                && ((UnitOutputPort)(controlCable.getJack2().getPort())).isConnected());

    }
    @Test
    public final void testPlug3() {
        controlCable.plug(inputPortTest2);

        assertEquals("The Port1 has been modified", inputPortTest1, controlCable.getJack1());
        assertEquals("The Port2 must be null", null, controlCable.getJack2());
        assertEquals("The two ports mustn't be connected",
                false,
                ((UnitInputPort)controlCable.getJack1().getPort()).isConnected()
                && ((UnitInputPort)controlCable.getJack2().getPort()).isConnected());
    }
    @Test
    public final void testPlug4() {
        controlCable.plug(outputPortTest1);
        boolean opt2Connexion = ((UnitOutputPort)outputPortTest2.getPort()).isConnected();
        controlCable.plug(outputPortTest2);

        assertEquals("The Port1 has been modified", inputPortTest1, controlCable.getJack1());
        assertEquals("The Port2 hasn't the good value", outputPortTest1, controlCable.getJack2());
        assertNotEquals("The Port2 has taken the new value", outputPortTest2, controlCable.getJack2());
        assertEquals("The connexion has been lost",
                true,
                ((UnitInputPort)controlCable.getJack1().getPort()).isConnected()
                && ((UnitOutputPort)controlCable.getJack2().getPort()).isConnected());
        assertEquals("Connexion state of outputPortTest2 has been modified",
                opt2Connexion, ((UnitOutputPort)outputPortTest2.getPort()).isConnected());
    }
    @Test
    public final void testPlug5() {
        ((UnitInputPort)inputPortTest2.getPort()).connect((UnitOutputPort)outputPortTest1.getPort());
        controlCable.plug(outputPortTest1);

        assertEquals("The Port1 has been modified", inputPortTest1, controlCable.getJack1());
        assertEquals("The Port2 has been modified", null, controlCable.getJack2());
        assertEquals("The old connexion has been lost",
                true,
                (inputPortTest2.isConnected() && outputPortTest1.isConnected()));
        assertEquals("The Port1 musn't be connected",
                false, (inputPortTest1.isConnected()));
    }
    @Test
    public final void testPlug6() {
        controlCable.plug(inputPortTest2);

        assertEquals("The Port1 mustn't be mark as plugged", false, controlCable.getJack1().isPlugged());
        assertEquals("The Port2 mustbe null", null, controlCable.getJack2());

        assertEquals("The Port1 mustn't be connected", false, controlCable.getJack1().isConnected());
    }
    @Test
    public final void testPlug7() {
        controlCable.plug(inputPortTest2);

        ICCable controlCable2 = new CCable(inputPortTest2);

        assertEquals("The Port1 hasn't the good value", inputPortTest2, controlCable2.getJack1());
        assertEquals("The Port2 must be null", null, controlCable2.getJack2());
    }
    @Test
    public final void testPlug8() {
        controlCable.plug(inputPortTest2);

        ICCable controlCable2 = new CCable(outputPortTest1);

        assertEquals("The Port1 mustn't be null", outputPortTest1, controlCable2.getJack1());
        assertEquals("The Port2 must be null", null, controlCable2.getJack2());

        controlCable2.plug(inputPortTest1);

        assertEquals("The Port1 mustn't changed", outputPortTest1, controlCable2.getJack1());
        assertEquals("The Port2 didn't changed", inputPortTest1, controlCable2.getJack2());

        assertEquals("The Port1 mustn't be connected", true, controlCable2.getJack1().isConnected());
    }

    @Test
    public final void testUnplug1() {
        controlCable.plug(outputPortTest1);
        controlCable.unplug(inputPortTest1);

        assertNotEquals("The Port1 hasn't been modified", inputPortTest1, controlCable.getJack1());
        assertEquals("The Port1 is not empty", null, controlCable.getJack1());
        assertEquals("The Port2 has been modified", outputPortTest1, controlCable.getJack2());

        assertEquals("The port2 mustn't be connected",
                false,
                ((UnitOutputPort)controlCable.getJack2().getPort()).isConnected());
        assertEquals("The inputPortTest1 mustn't be connected",
                false,
                ((UnitInputPort) (inputPortTest1.getPort())).isConnected());
    }
    @Test
    public final void testUnplug2() {
        controlCable.plug(outputPortTest1);
        controlCable.unplug(outputPortTest1);

        assertEquals("The Port1 has been modified", inputPortTest1, controlCable.getJack1());
        assertNotEquals("The Port2 hasn't been modified", outputPortTest1, controlCable.getJack2());
        assertEquals("The Port2 is not empty", null, controlCable.getJack2());

        assertEquals("The port1 mustn't be connected",
                false,
                ((UnitInputPort)controlCable.getJack1().getPort()).isConnected());
        assertEquals("The outputPortTest1 mustn't be connected",
                false,
                ((UnitOutputPort)outputPortTest1.getPort()).isConnected());
    }
    @Test
    public final void testUnplug3() {
        controlCable.plug(outputPortTest1);
        boolean opt2Connexion = ((UnitOutputPort)outputPortTest2.getPort()).isConnected();
        controlCable.unplug(outputPortTest2);
        assertEquals("The Port1 has been modified", inputPortTest1, controlCable.getJack1());
        assertEquals("The Port2 has been modified", outputPortTest1, controlCable.getJack2());

        assertEquals("The connexion has been lost",
                true,
                ((UnitInputPort)controlCable.getJack1().getPort()).isConnected()
                && ((UnitOutputPort)controlCable.getJack2().getPort()).isConnected());
        assertEquals("Connexion state of outputPortTest2 has been modified",
                opt2Connexion, ((UnitOutputPort)outputPortTest2.getPort()).isConnected());
    }
    @Test
    public final void testUnplug4() {
        controlCable.plug(outputPortTest1);
        controlCable.unplug(outputPortTest1);
        controlCable.unplug(inputPortTest1);

        assertNotEquals("The Port1 hasn't been modified", inputPortTest1, controlCable.getJack1());
        assertEquals("The Port1 is not empty", null, controlCable.getJack1());
        assertNotEquals("The Port2 hasn't been modified", outputPortTest1, controlCable.getJack2());
        assertEquals("The Port2 is not empty", null, controlCable.getJack2());

        assertEquals("The inputPortTest1 mustn't be connected",
                false,
                ((UnitInputPort)inputPortTest1.getPort()).isConnected());
        assertEquals("The outputPortTest1 mustn't be connected",
                false,
                ((UnitOutputPort)outputPortTest1.getPort()).isConnected());
    }

    @Test
    public final void testDestroyCable1(){
        controlCable.plug(outputPortTest1);
        controlCable.destroyCable();

        assertNotEquals("The Port1 hasn't been modified", inputPortTest1, controlCable.getJack1());
        assertEquals("The Port1 is not empty", null, controlCable.getJack1());
        assertNotEquals("The Port2 hasn't been modified", outputPortTest1, controlCable.getJack2());
        assertEquals("The Port2 is not empty", null, controlCable.getJack2());
        assertEquals("The inputPortTest1 mustn't be connected",
                false,
                ((UnitInputPort)inputPortTest1.getPort()).isConnected());
        assertEquals("The outputPortTest1 mustn't be connected",
                false,
                ((UnitOutputPort)outputPortTest1.getPort()).isConnected());
    }
    @Test
    public final void testDestroyCable2(){
        controlCable.plug(outputPortTest1);
        controlCable.unplug(inputPortTest1);
        controlCable.destroyCable();

        assertNotEquals("The Port1 hasn't been modified", inputPortTest1, controlCable.getJack1());
        assertEquals("The Port1 is not empty", null, controlCable.getJack1());
        assertNotEquals("The Port2 hasn't been modified", outputPortTest1, controlCable.getJack2());
        assertEquals("The Port2 is not empty", null, controlCable.getJack2());
        assertEquals("The inputPortTest1 mustn't be connected",
                false,
                ((UnitInputPort)inputPortTest1.getPort()).isConnected());
        assertEquals("The outputPortTest1 mustn't be connected",
                false,
                ((UnitOutputPort)outputPortTest1.getPort()).isConnected());
    }
    @Test
    public final void testDestroyCable3(){
        controlCable.unplug(inputPortTest1);
        controlCable.destroyCable();

        assertNotEquals("The Port1 hasn't been modified", inputPortTest1, controlCable.getJack1());
        assertEquals("The Port1 is not empty", null, controlCable.getJack1());
        assertEquals("The Port2 is not empty", null, controlCable.getJack2());
        assertEquals("The inputPortTest1 mustn't be connected",
                false,
                ((UnitInputPort)inputPortTest1.getPort()).isConnected());
    }

    @Test
    public final void testDestroyCableIfJack1(){
        controlCable.plug(outputPortTest1);
        controlCable.destroyCableIfJack(outputPortTest1);

        assertEquals("The Port1 is not empty", null, controlCable.getJack1());
        assertEquals("The Port2 is not empty", null, controlCable.getJack2());
        assertEquals("Ports musn't be connected",
                false,
                ( ((UnitInputPort)inputPortTest1.getPort()).isConnected() || ((UnitOutputPort)outputPortTest1.getPort()).isConnected()));
    }
    @Test
    public final void testDestroyCableIfJack2(){
        controlCable.plug(outputPortTest1);
        controlCable.destroyCableIfJack(outputPortTest2);

        assertEquals("The Port1 has been modified", inputPortTest1, controlCable.getJack1());
        assertEquals("The Port2 has been modified", outputPortTest1, controlCable.getJack2());
        assertNotEquals("The Port1 is empty", null, controlCable.getJack1());
        assertNotEquals("The Port2 is empty", null, controlCable.getJack2());
        assertEquals("Ports musn't be disconnected",
                true,
                ( ((UnitInputPort)inputPortTest1.getPort()).isConnected() && ((UnitOutputPort)outputPortTest1.getPort()).isConnected()));
    }

}
