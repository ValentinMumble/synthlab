package fr.istic.synthlab.module.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jsyn.Synthesizer;

import fr.istic.synthlab.global.control.CGLOB;
import fr.istic.synthlab.global.control.ICGLOB;
import fr.istic.synthlab.module.abstraction.AReplicator;
import fr.istic.synthlab.module.presentation.IPModule;
import fr.istic.synthlab.module.presentation.IPReplicator;
import fr.istic.synthlab.module.presentation.PReplicator;
import fr.istic.synthlab.save.tools.SaveAttenuation;
import fr.istic.synthlab.util.control.CFemaleJack;
import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * The Replicator module controller.
 * 
 * @author valentinmumble
 * 
 */
public class CReplicator extends AReplicator implements ICReplicator {

    /**
     * input
     */
    private ICFemaleJack input;

    /**
     * output 1
     */
    private ICFemaleJack output1;

    /**
     * output 2
     */
    private ICFemaleJack output2;

    /**
     * output 3
     */
    private ICFemaleJack output3;

    /**
     * Presentation of the Replicator
     */
    private IPReplicator presentation;

    /**
     * Constructor of the Control Replicator
     * 
     * @param synth
     *            Synthetizer
     */
    public CReplicator(final Synthesizer synth) {
        super(synth);

        input = new CFemaleJack(getInputPort(), true);
        output1 = new CFemaleJack(getOutput1Port(), false);
        output2 = new CFemaleJack(getOutput2Port(), false);
        output3 = new CFemaleJack(getOutput3Port(), false);
        presentation = new PReplicator(this);
    }

    @Override
    public ICFemaleJack getInput() {
        return input;
    }

    @Override
    public ICFemaleJack getOutput1() {
        return output1;
    }

    @Override
    public ICFemaleJack getOutput2() {
        return output2;
    }

    @Override
    public ICFemaleJack getOutput3() {
        return output3;
    }

    @Override
    public final IPModule getPresentation() {
        return presentation;
    }

    @Override
    public final void suppressModule() {
        input.removeCable();
        output1.removeCable();
        output2.removeCable();
        output3.removeCable();
    }
    
    @Override
    public ICGLOB getGlobalControl() {
        return CGLOB.getInstance();
    }

    /**
     * Called for the save. Initialize names of FemaleJacks and return the list
     * of FemaleJacks that are plugged.
     *
     * @param ident
     *            Used to give a unique ident for each FemaleJack.
     * @return The list of FemalJacks plugged.
     */
    @Override
    public List<ICFemaleJack> getJacksForSave(String ident) {
        List<ICFemaleJack> listJack = new ArrayList<>();
        if (this.getInput().isPlugged()) {
            this.getInput().setIdent(ident + "-input");
            listJack.add(this.getInput());
        }
        if (this.getOutput1().isPlugged()) {
            this.getOutput1().setIdent(ident + "-output1");
            listJack.add(this.getOutput1());
        }
        if (this.getOutput2().isPlugged()) {
            this.getOutput2().setIdent(ident + "-output2");
            listJack.add(this.getOutput2());
        }
        if (this.getOutput3().isPlugged()) {
            this.getOutput3().setIdent(ident + "-output3");
            listJack.add(this.getOutput3());
        }
        return listJack;
    }

    /**
     * This function adds the corresponding FemaleJack in the HashMap with the
     * ident as key.
     *
     * @param ident
     *            The ident of the FemaleJack to initialize.
     * @param listJacksToPlugged
     *            The HashMap of all Jacks that will be plugged.
     */
    @Override
    public void loadJack(String ident,
            HashMap<String, ICFemaleJack> listJacksToPlugged) {
        if (ident != null) {
            String idUnique = ident.split("-")[1];
            switch (idUnique) {
            case "input":
                listJacksToPlugged.put(ident, this.getInput());
                break;
            case "output1":
                listJacksToPlugged.put(ident, this.getOutput1());
                break;
            case "output2":
                listJacksToPlugged.put(ident, this.getOutput2());
                break;
            case "output3":
                listJacksToPlugged.put(ident, this.getOutput3());
                break;
            }
        }
    }

    /**
     * @return The list of Doublet ident/value of the different attenuations.
     */
    @Override
    public List<SaveAttenuation> getAttenuationForSave() {
        List<SaveAttenuation> listAtt = new ArrayList<>();
        return listAtt;
    }

    /**
     * @return The list of Doublet ident/value of the different attenuations.
     */
    @Override
    public void loadAttenuation(
            List<SaveAttenuation> listAttenuationToInitialize) {
    }
    
    @Override
    public void requestForDelete() {
        this.getGlobalControl().destroyModule(this);
        presentation.removeModule();
    }
}
