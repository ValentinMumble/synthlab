package fr.istic.synthlab.module.control;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.jsyn.Synthesizer;

import fr.istic.synthlab.global.control.CGLOB;
import fr.istic.synthlab.global.control.ICGLOB;
import fr.istic.synthlab.module.abstraction.ARecorderWAV;
import fr.istic.synthlab.module.presentation.IPModule;
import fr.istic.synthlab.module.presentation.IPRecorderWAV;
import fr.istic.synthlab.module.presentation.PRecorderWAV;
import fr.istic.synthlab.save.tools.SaveAttenuation;
import fr.istic.synthlab.util.FileWavFilter;
import fr.istic.synthlab.util.control.CFemaleJack;
import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * the WAVE RECORDER module controller
 *
 * @author Favereau
 *
 */
public class CRecorderWAV extends ARecorderWAV implements ICRecorderWAV {

    /**
     * Jack of the InputPorts.
     */
    private Hashtable<Integer, ICFemaleJack> linesIn = new Hashtable<>();
    
    /**
     * Jack of the OutputPorts.
     */
    private Hashtable<Integer, ICFemaleJack> linesOut = new Hashtable<>();
    
    /**
     * Presentation of this module.
     */
    
    private IPRecorderWAV presentation;
    
    /**
     * 
     * @param synth
     *            the main synthesizer
     */
    public CRecorderWAV(Synthesizer s) {
        super(s);
        
        for(int i = 1; i <= LINE_NUMBER; i++){
            linesIn.put(i, new CFemaleJack(getInputPort(i), true));
            linesOut.put(i, new CFemaleJack(getOutputPort(i), false));
        }

     
        presentation = new PRecorderWAV(this);

    }

    @Override
    public IPModule getPresentation() {
        
        return presentation;
    }

    @Override
    public void suppressModule() {
        for(int i = 1; i <= LINE_NUMBER; i++){
            linesIn.get(i).removeCable();
            linesOut.get(i).removeCable();
        }
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
        List<ICFemaleJack> liste = new ArrayList<ICFemaleJack>();
        
        for(int i = 1; i <= LINE_NUMBER; i++){
             if (this.getLineIn(i).isPlugged()) {
                 getLineIn(i).setIdent(ident + "-lineIn" + i);
                 liste.add(linesIn.get(i));
             }
             if (this.getLineOut(i).isPlugged()) {
                 getLineOut(i).setIdent(ident + "-lineOut" + i);
                 liste.add(linesOut.get(i));
             }
        }
        
        return liste;
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

        for(int i = 1; i <= LINE_NUMBER; i++){
            if (ident != null) {
                String idUnique = ident.split("-")[1];
                if(idUnique.compareTo("lineIn" + i) == 0){
                    listJacksToPlugged.put(ident, this.getLineIn(i));
                }
                if(idUnique.compareTo("lineOut" + i) == 0){
                    listJacksToPlugged.put(ident, this.getLineOut(i));
                }
            }
        }
    }

    @Override
    public List<SaveAttenuation> getAttenuationForSave() {
        
        return new ArrayList<SaveAttenuation>();
    }

    @Override
    public void loadAttenuation(
            List<SaveAttenuation> listAttenuationToInitialize) {
   
    }

    /**
     * 
     * @param index of desired inputJack
     * 
     * @return the specified inputJack controller
     */
    @Override
    public ICFemaleJack getLineIn(int lineID) {
        ICFemaleJack retour = null;
        
        if(lineID <= LINE_NUMBER && lineID > 0){
            retour = linesIn.get(lineID);
        }
        
        return retour;
    }

    /**
     * 
     * @param index of desired outputJack
     * 
     * @return the specified outputJack controller
     */
    @Override
    public ICFemaleJack getLineOut(int lineID) {
        ICFemaleJack retour = null;
        
        if(lineID <= LINE_NUMBER && lineID > 0){
            retour = linesOut.get(lineID);
        }
        
        return retour;
    }

    @Override
    public void changeSaveFile(int lineID) {

               
        JFileChooser fileBox = new JFileChooser(ARecorderWAV.def_file_path);
        fileBox.addChoosableFileFilter(new FileWavFilter());
        fileBox.setFileFilter(new FileWavFilter());
        fileBox.setAcceptAllFileFilterUsed(false);
        
        int responseBox = fileBox.showSaveDialog(null);
        
        if (responseBox == JFileChooser.APPROVE_OPTION 
                && fileBox.getSelectedFile().getName().length() != 0) {
            File tmpFile = fileBox.getSelectedFile();
            String tmpFilePath = tmpFile.getPath();
            if(!tmpFilePath.toLowerCase().endsWith(".wav"))
            {
                tmpFile = new File(tmpFilePath + ".wav");
            }
            this.setFile(lineID, tmpFile);
              
        } else{
            JOptionPane.showMessageDialog(null,
                    "Le fichier n'a pas été modifié",
                    "Infos - fichier .wav", 
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public void changeRecordState(int lineID) {

        if(this.isInRecordMode(lineID)){
            this.stopRecord(lineID);
        }else{
            this.startRecord(lineID);
        }
        
        presentation.updateLed(lineID);
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
