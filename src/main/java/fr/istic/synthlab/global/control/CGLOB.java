package fr.istic.synthlab.global.control;

import java.util.ArrayList;
import java.util.List;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;

import fr.istic.synthlab.cable.control.CCable;
import fr.istic.synthlab.cable.control.ICCable;
import fr.istic.synthlab.cable.presentation.PCable;
import fr.istic.synthlab.global.abstraction.AGLOB;
import fr.istic.synthlab.global.presentation.IPGLOB;
import fr.istic.synthlab.global.presentation.PGLOB;
import fr.istic.synthlab.module.control.ICModule;
import fr.istic.synthlab.save.control.CLoad;
import fr.istic.synthlab.save.control.CSave;
import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * Controller for GLOB, contains the D&D.
 *
 * @author Jonathan
 */
public final class CGLOB extends AGLOB implements ICGLOB {

    /**
     * The instance of the CGLOB.
     */
    private static ICGLOB instance;
    /**
     * The Presentation of the GLOB.
     */
    private IPGLOB pGLOB;
    /**
     * The Synthesizer.
     */
    private Synthesizer synthesizer;
    /**
     * The control of the PanelContent.
     */
    private CPanelContent cPanelContent;
    /**
     * The control of the PanelImport.
     */
    private CPanelImport cPanelImport;

    /**
     * The current cable in creation.
     */
    private ICCable currentCable;
    /**
     * The list of the cables.
     */
    private List<ICCable> cables;

    /**
     * Getter of instance of the CGLOB.
     *
     * @return The same instance of the CGLOB for every calls of the getter.
     */
    public static ICGLOB getInstance() {
        if (instance == null) {
            instance = new CGLOB();
            instance.getCPanelContent().reinit();
        }
        return instance;
    }

    /**
     * Constructor of the CGLOB.
     */
    private CGLOB() {
        initSynthesizer();
        cables = new ArrayList<>();
        setCSave(new CSave(), new CLoad());

        synthesizer.start();

        pGLOB = new PGLOB(this);

        this.cPanelContent = new CPanelContent();
        this.cPanelImport = new CPanelImport(synthesizer);
        pGLOB.init();
    }

    /**
     * Call {@link CPanelContent#initPanel()} .
     */
    @Override
    public void initPanelContent() {
        cPanelContent.initPanel();
    }

    /**
     * Getter for the presentation of GLOB.
     *
     * @return the presentation.
     */
    @Override
    public IPGLOB getPresentation() {
        return pGLOB;
    }

    /**
     * Getter of the cPanelContent.
     *
     * @return The cPanelContent.
     */
    @Override
    public CPanelContent getCPanelContent() {
        return cPanelContent;
    }

    /**
     * Getter of the cPanelImport.
     *
     * @return The cPanelImport.
     */
    @Override
    public CPanelImport getCPanelImport() {
        return cPanelImport;
    }

    /**
     * @return the Synthesizer.
     */
    public Synthesizer getSynthesizer() {
        return this.synthesizer;
    }

    /**
     * Initialize the synthesizer.
     */
    private void initSynthesizer() {
        synthesizer = JSyn.createSynthesizer();
    }

    /**
     * Call {@link PGLOB#myResize()} .
     */
    @Override
    public void myResize() {
        pGLOB.myResize();
    }

    /**
     * Plug and show currentCable.
     *
     * @param controlJack
     *            Control of the jack that was clicked.
     */
    @Override
    public void portTouched(ICFemaleJack controlJack) {
        if (currentCable == null && !controlJack.isPlugged()) {
            CCable cCable = new CCable(controlJack);
            currentCable = cCable;
            getPresentation().addCable((PCable) currentCable.getPresentation());
            getPresentation().addMouseListener();
        } else if (currentCable != null && currentCable.plug(controlJack)) {
            getPresentation().removeMouseListener();
            cables.add(currentCable);
            currentCable = null;
        }
    }

    /**
     * @return saved value.
     */
    @Override
    public boolean getSaved() {
        return pGLOB.getSaved();
    }

    /**
     * Called whenever a modification is made.
     *
     * @param value
     *            value to set in saved.
     */
    @Override
    public void setSaved(boolean value) {
        pGLOB.setSaved(value);
    }

    /**
     * Call by load system to add a cable.
     *
     * @param iccable
     *            cable to load.
     */
    @Override
    public void loadCable(ICCable iccable) {
        getPresentation().addCable((PCable) iccable.getPresentation());
        cables.add(iccable);
    }

    /**
     * Get the currentCable.
     *
     * @return the currentCable.
     */
    @Override
    public ICCable getcurrentCable() {
        return currentCable;
    }

    /**
     * Function calls when we destroy a cable.
     *
     * @param cCable
     *            the cable to destroy.
     */
    @Override
    public void destroyCable(ICCable cCable) {
        getPresentation().removeCablePresentation(cCable.getPresentation());
        cables.remove(cCable);
    }

    /**
     * Getter of the List of cables.
     *
     * @return The list of the cables .
     */
    @Override
    public List<ICCable> getListCables() {
        List<ICCable> listCables = new ArrayList<>(this.cables);
        return listCables;
    }

    /**
     * Call when we abort the creation of a cable.
     */
    @Override
    public void destroyCurrentCable() {
        if (currentCable != null) {
            getPresentation().removeMouseListener();
            destroyCable(currentCable);
            currentCable = null;
        }
    }

    @Override
    public void destroyModule(ICModule module) {
        if(module != null) {
            module.suppressModule();
            CGLOB.getInstance().setSaved(false);
        }
    }
}
