package fr.istic.synthlab.util.control;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;

import fr.istic.synthlab.cable.control.ICCable;
import fr.istic.synthlab.global.control.CGLOB;
import fr.istic.synthlab.global.presentation.IPGLOB;
import fr.istic.synthlab.util.abstraction.AFemaleJack;
import fr.istic.synthlab.util.presentation.IPFemaleJack;
import fr.istic.synthlab.util.presentation.PFemaleJack;

/**
 * The controller for the female jack model.
 * 
 * @author valentinmumble
 * 
 */
public class CFemaleJack extends AFemaleJack implements ICFemaleJack {

    /**
     * Presentation of FemaleJack.
     */
    private IPFemaleJack presentation;

    /**
     * Constructor of CFemaleJack.
     * 
     * @param port
     *      Port associated to the FemaleJack.
     * @param isInput
     *      true if the port is an inputPort, false if it's an outputPort.
     */
    public CFemaleJack(UnitPort port, boolean isInput) {
        super(port, isInput);
        presentation = new PFemaleJack(this);
    }

    @Override
    public IPFemaleJack getPresentation() {
        return presentation;
    }

    /**
     * @return the Presentation of the GLOB.
     */
    public IPGLOB getPGLOB() {
        return CGLOB.getInstance().getPresentation();
    }

    /**
     * @return true if the port is connected
     */
    @Override
    public boolean isConnected() {
        boolean connected = false;
        if (isInput()) {
            connected = ((UnitInputPort) getPort()).isConnected();
        } else {
            connected = ((UnitOutputPort) getPort()).isConnected();
        }

        return connected;
    }

    /**
     * Calls to remove the cable plugged in the Jack. Do nothing if there is no
     * cable plugged.
     */
    @Override
    public void removeCable() {
        if (isPlugged()) {
            getCcable().destroyCableIfJack(this);
        }
    }

    @Override
    public void portTouched() {
        CGLOB.getInstance().portTouched(this);
    }

    @Override
    public void portHovered() {
        ICCable current = CGLOB.getInstance().getcurrentCable();
        if (current != null) {
            if (isPluggable(current.getJack1())) {
                presentation.showPluggable();
            } else {
                presentation.showNotPluggable();
            }
        } else {
            presentation.showPluggable();
        }
    }

    @Override
    public void portExited() {
        presentation.showNeutral();
    }

    @Override
    public boolean isPluggable(ICFemaleJack jack) {
        return isInput() != jack.isInput();
    }
}
