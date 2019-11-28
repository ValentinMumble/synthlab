package fr.istic.synthlab.cable.control;

import java.awt.Color;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;

import fr.istic.synthlab.cable.abstraction.ACable;
import fr.istic.synthlab.cable.presentation.IPCable;
import fr.istic.synthlab.cable.presentation.PCable;
import fr.istic.synthlab.global.control.CGLOB;
import fr.istic.synthlab.global.presentation.IPGLOB;
import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * Control Cable Implementation.
 *
 * @author Mickael
 * @version 1.1
 */
public class CCable extends ACable implements ICCable {

    /**
     * Presentation of the cable.
     */
    private IPCable presentation;

    /**
     * CCable constructor.
     *
     * @param jack
     *            jack where the cable is plugged.
     */
    public CCable(final ICFemaleJack jack) {
        if (!jack.isPlugged()) {
            plug(jack);
            presentation = new PCable(this);
        }
    }

    /**
     * Presentation Getter.
     *
     * @return Cable Presentation.
     */
    @Override
    public final IPCable getPresentation() {
        return presentation;
    }

    /**
     * @return the Presentation of the GLOB.
     */
    public IPGLOB getPGLOB() {
        return CGLOB.getInstance().getPresentation();
    }

    /**
     * Load the color of the cable.
     *
     * @param color
     *            The color to load.
     */
    @Override
    public void loadColor(Color color) {
        this.setColor(color);
        this.getPresentation().updateAndValidate();
    }

    /**
     * Function calls when we plug one end of the cable. It adds the jack if one
     * end of the cable is not plugged. The connection is created only if the
     * two jacks are instantiated and are of different types.
     *
     * @param jack
     *            jack where we plug.
     * @return have been plugged
     */
    @Override
    public final boolean plug(final ICFemaleJack jack) {
        boolean isPlugged = false;
        if (jack != null && !jack.isConnected() && !jack.isPlugged()) {
            if (this.getJack1() == null) {
                this.setJack1(jack);
                isPlugged = true;
            } else if (this.getJack2() == null && !this.getJack1().equals(jack)
                    && jackTypeDiff(this.getJack1(), jack)) {
                this.setJack2(jack);
                isPlugged = true;
            }
        }
        if (isPlugged && presentation != null) {
            presentation.updateAndValidate();
        }
        connectJacks();
        return isPlugged;
    }

    /**
     * Function calls when we unplug one end of the cable. It deletes the jack
     * if we had it in memory. It deletes the connection if need.
     *
     * @param jack
     *            jack to be unplugged.
     */
    @Override
    public final void unplug(final ICFemaleJack jack) {
        if (jack.equals(this.getJack1())) {
            disconnectJacks();
            this.getJack1().unplugged();
            this.setJack1(null);
        } else if (jack.equals(this.getJack2())) {
            disconnectJacks();
            this.getJack2().unplugged();
            this.setJack2(null);
        }
        if (this.getJack1() == null && this.getJack2() == null) {
            CGLOB.getInstance().destroyCable(this);
        }
    }

    /**
     * Function calls to destroy the cable. It Destroys the connection if it
     * exists.
     */
    @Override
    public final void destroyCable() {
        disconnectJacks();
        if (getJack1() != null) {
            this.getJack1().unplugged();
        }
        if (getJack2() != null) {
            this.getJack2().unplugged();
        }
        this.setJack1(null);
        this.setJack2(null);
        CGLOB.getInstance().destroyCable(this);
    }

    /**
     * Function calls when we are destroying a jack. If one of the two Jacks of
     * the cable is the same than the jack in parameter, the cable is destroy
     * too.
     *
     * @param jackDestroyed
     *            jack is being destroyed
     */
    @Override
    public final void destroyCableIfJack(final ICFemaleJack jackDestroyed) {
        if (jackDestroyed.equals(this.getJack1())
                || jackDestroyed.equals(this.getJack2())) {
            destroyCable();
        }
    }

    /**
     * Function that connects the two jacks if they are not null.
     */
    private void connectJacks() {
        if (this.getJack1() != null && this.getJack2() != null
                && this.getJack1().isInput() != this.getJack2().isInput()) {
            if (this.getJack1().isInput()) {
                ((UnitInputPort) this.getJack1().getPort())
                        .connect((UnitOutputPort) this.getJack2().getPort());
            } else {
                ((UnitInputPort) this.getJack2().getPort())
                        .connect((UnitOutputPort) this.getJack1().getPort());
            }
        }
        if (this.getJack1() != null && this.getJack2() != null) {
            this.getJack1().plugCable(this);
            this.getJack2().plugCable(this);
            this.getJack1().setPlugged(true);
            this.getJack2().setPlugged(true);
        }
    }

    /**
     * Function that disconnects the two jacks if they are not null.
     */
    private void disconnectJacks() {
        if (this.getJack1() != null && this.getJack2() != null
                && this.getJack1().isInput() != this.getJack2().isInput()) {
            if (this.getJack1().isInput()) {
                ((UnitInputPort) this.getJack1().getPort())
                        .disconnect((UnitOutputPort) this.getJack2().getPort());
            } else {
                ((UnitInputPort) this.getJack2().getPort())
                        .disconnect((UnitOutputPort) this.getJack1().getPort());
            }
        }
        if (this.getJack1() != null) {
            this.getJack1().unplugged();
            this.getJack1().setPlugged(false);
        }
        if (this.getJack2() != null) {
            this.getJack2().unplugged();
            this.getJack2().setPlugged(false);
        }
    }

    /**
     * @param jack1
     *            First jack test.
     * @param jack2
     *            Second jack test.
     * @return true if the two jack have different type.
     */
    private boolean jackTypeDiff(ICFemaleJack jack1, ICFemaleJack jack2) {
        return jack1.isInput() && !jack2.isInput()
                || !jack1.isInput() && jack2.isInput();
    }
}
