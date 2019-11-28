package fr.istic.synthlab.module.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jsyn.Synthesizer;

import fr.istic.synthlab.global.control.CGLOB;
import fr.istic.synthlab.global.control.ICGLOB;
import fr.istic.synthlab.module.abstraction.AMixer;
import fr.istic.synthlab.module.presentation.IPMixer;
import fr.istic.synthlab.module.presentation.IPModule;
import fr.istic.synthlab.module.presentation.PMixer;
import fr.istic.synthlab.save.tools.SaveAttenuation;
import fr.istic.synthlab.util.control.CFemaleJack;
import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * Mixer Control Implementation.
 * 
 * @author Laurent Legendre
 * @version 1.1
 * 
 */
public class CMixer extends AMixer implements ICMixer {

    /**
     * IPMixer of the CMixer.
     */
    private IPMixer presentation;

    /**
     * Jack of the InputPort1.
     */
    private ICFemaleJack inputJack1;

    /**
     * Jack of the InputPort2.
     */
    private ICFemaleJack inputJack2;

    /**
     * Jack of the InputPort3.
     */
    private ICFemaleJack inputJack3;

    /**
     * Jack of the InputPort4.
     */
    private ICFemaleJack inputJack4;

    /**
     * Jack of the OutputPort1.
     */
    private ICFemaleJack outputJack;

    /**
     * Constructor of CMixer.
     * 
     * @param synth
     *            The synthesizer
     */
    public CMixer(Synthesizer synth) {
        super(synth);

        inputJack1 = new CFemaleJack(getInputPort(1), true);
        inputJack2 = new CFemaleJack(getInputPort(2), true);
        inputJack3 = new CFemaleJack(getInputPort(3), true);
        inputJack4 = new CFemaleJack(getInputPort(4), true);

        outputJack = new CFemaleJack(getOutputPort(), false);

        presentation = new PMixer(this);
    }

    /**
     * @return the Presentation of the module
     */
    @Override
    public IPModule getPresentation() {
        return presentation;
    }

    /**
     * @return the first inputJack controller
     */
    @Override
    public ICFemaleJack getInput1() {
        return inputJack1;
    }

    /**
     * @return the second inputJack controller
     */
    @Override
    public ICFemaleJack getInput2() {
        return inputJack2;
    }

    /**
     * @return the third inputJack controller
     */
    @Override
    public ICFemaleJack getInput3() {
        return inputJack3;
    }

    /**
     * @return the forth inputJack controller
     */
    @Override
    public ICFemaleJack getInput4() {
        return inputJack4;
    }

    /**
     * @return the outputJack controller
     */
    @Override
    public ICFemaleJack getOutput() {
        return outputJack;
    }

    /**
     * Call when we destroy the CMixer.
     */
    @Override
    public void suppressModule() {
        inputJack1.removeCable();
        inputJack2.removeCable();
        inputJack3.removeCable();
        inputJack4.removeCable();
        outputJack.removeCable();
    }

    @Override
    public void attenuationChanged(int inputPortNumber, double gain) {
        try {
            this.setAttenuation(inputPortNumber, gain);
            this.presentation.updateAttenuation(inputPortNumber-1, gain);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public double getAttenuation(int inputPortNumber) {
        double gainValue = 0.0;
        try {
            gainValue = super.getAttenuation(inputPortNumber);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return gainValue;
    }

    @Override
    public ICFemaleJack getInput(int inputNumber) {
        ICFemaleJack connector = null;
        if (inputNumber >= 1 && inputNumber <= NB_INPUTS) {

            switch (inputNumber) {
            case 1:
                connector = inputJack1;
                break;

            case 2:
                connector = inputJack2;
                break;

            case 3:
                connector = inputJack3;
                break;

            case 4:
                connector = inputJack4;
                break;

            default:
                break;
            }
        }

        return connector;
    }
    
    @Override
    public ICGLOB getGlobalControl() {
        return CGLOB.getInstance();
    }

    @Override
    public List<ICFemaleJack> getJacksForSave(String ident) {
        List<ICFemaleJack> liste = new ArrayList<ICFemaleJack>();
        for(int i = 1 ; i <= NB_INPUTS ; i++){
            ICFemaleJack input = getInput(i);
            if (input.isPlugged()) {
                input.setIdent(ident + "-input"+i);
                liste.add(input);
            }
        }
        if (this.getOutput().isPlugged()) {
            getOutput().setIdent(ident + "-output");
            liste.add(getOutput());
        }
        return liste;
    }

    @Override
    public void loadJack(String ident,
            HashMap<String, ICFemaleJack> listJacksToPlugged) {
        if (ident != null) {
            String idUnique = ident.split("-")[1];
            switch (idUnique) {
            case "input1":
                listJacksToPlugged.put(ident, this.getInput(1));
                break;
            case "input2":
                listJacksToPlugged.put(ident, this.getInput(2));
                break;
            case "input3":
                listJacksToPlugged.put(ident, this.getInput(3));
                break;
            case "input4":
                listJacksToPlugged.put(ident, this.getInput(4));
                break;
            case "output":
                listJacksToPlugged.put(ident, this.getOutput());
                break;
            }
        }
    }

    @Override
    public List<SaveAttenuation> getAttenuationForSave() {
        List<SaveAttenuation> liste = new ArrayList<SaveAttenuation>();
        liste.add(new SaveAttenuation("1", getAttenuation(1)));
        liste.add(new SaveAttenuation("2", getAttenuation(2)));
        liste.add(new SaveAttenuation("3", getAttenuation(3)));
        liste.add(new SaveAttenuation("4", getAttenuation(4)));
        return liste;
    }

    @Override
    public void loadAttenuation(
            List<SaveAttenuation> listAttenuationToInitialize) {
        for (int i = 1; i <= NB_INPUTS; i++) {
            this.attenuationChanged(i, listAttenuationToInitialize.get(i-1).getValue());
        }
    }
    
    @Override
    public void requestForDelete() {
        this.getGlobalControl().destroyModule(this);
        presentation.removeModule();
    }
}
