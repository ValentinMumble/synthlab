package fr.istic.synthlab.module.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jsyn.Synthesizer;

import fr.istic.synthlab.global.control.CGLOB;
import fr.istic.synthlab.global.control.ICGLOB;
import fr.istic.synthlab.module.abstraction.AKeyboard;
import fr.istic.synthlab.module.presentation.IPKeyboard;
import fr.istic.synthlab.module.presentation.IPModule;
import fr.istic.synthlab.module.presentation.PKeyboard;
import fr.istic.synthlab.save.tools.SaveAttenuation;
import fr.istic.synthlab.util.control.CFemaleJack;
import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * The Keyboard module controller
 * 
 * @author valentinmumble
 * 
 */
public class CKeyboard extends AKeyboard implements ICKeyboard {

    /**
     * Control of the gate output port
     */
    private ICFemaleJack gate;

    /**
     * Control of the voltage control output port
     */
    private ICFemaleJack controlVoltage;

    /**
     * The Keyboard presentation;
     */
    private IPKeyboard presentation;

    public CKeyboard(Synthesizer syn) {
        super(syn);

        gate = new CFemaleJack(getGatePort(), false);
        controlVoltage = new CFemaleJack(getControlVoltagePort(), false);

        presentation = new PKeyboard(this);
    }

    @Override
    public IPModule getPresentation() {
        return presentation;
    }

    @Override
    public void suppressModule() {
        gate.removeCable();
        controlVoltage.removeCable();
    }

    @Override
    public List<ICFemaleJack> getJacksForSave(String ident) {
        List<ICFemaleJack> liste = new ArrayList<ICFemaleJack>();
        if (gate.isPlugged()) {
            gate.setIdent(ident + "-gate");
            liste.add(gate);
        }
        if (controlVoltage.isPlugged()) {
            controlVoltage.setIdent(ident + "-cv");
            liste.add(controlVoltage);
        }
        return liste;
    }

    @Override
    public void loadJack(String ident,
            HashMap<String, ICFemaleJack> listJacksToPlugged) {
        if (ident != null) {
            String idUnique = ident.split("-")[1];
            switch (idUnique) {
            case "gate":
                listJacksToPlugged.put(ident, gate);
                break;
            case "cv":
                listJacksToPlugged.put(ident, controlVoltage);
                break;
            }
        }
    }

    @Override
    public List<SaveAttenuation> getAttenuationForSave() {
        List<SaveAttenuation> list = new ArrayList<SaveAttenuation>();
        list.add(new SaveAttenuation("0", getOctave()));
        list.add(new SaveAttenuation("1", getNote()));
        return list;
    }

    @Override
    public void loadAttenuation(
            List<SaveAttenuation> listAttenuationToInitialize) {
        for (SaveAttenuation save : listAttenuationToInitialize) {
            if (save.getIdent().equals("0")) {
                octaveChanged(save.getValue());
            } else {
                noteChanged(save.getValue());
            }
        }
    }

    @Override
    public ICFemaleJack getGate() {
        return gate;
    }

    @Override
    public ICFemaleJack getControlVoltage() {
        return controlVoltage;
    }

    @Override
    public void keyPressed(Note note) {
        setNote(note);
        presentation.showKeyPressed(note);
    }

    @Override
    public void octaveIncreased() {
        increaseOctave();
        presentation.updateOctave();
    }

    @Override
    public void octaveDecreased() {
        decreaseOctave();
        presentation.updateOctave();
    }

    @Override
    public void octaveChanged(double o) {
        setOctave(o);
        presentation.updateOctave();
    }

    @Override
    public void noteChanged(double n) {
        setNote(n);
    }

    @Override
    public void keyReleased() {
        stopNote();
        presentation.showKeyReleased();
    }

    @Override
    public ICGLOB getGlobalControl() {
        return CGLOB.getInstance();
    }
    
    @Override
    public void requestForDelete() {
        this.getGlobalControl().destroyModule(this);
        presentation.removeModule();
    }

}
