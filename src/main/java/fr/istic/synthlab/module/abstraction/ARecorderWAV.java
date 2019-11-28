package fr.istic.synthlab.module.abstraction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;

import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.PassThrough;
import com.jsyn.util.WaveRecorder;

import fr.istic.synthlab.filter.amplitudeDivByFilter;

/**
     * A class to represent the Recorder module.
     * 
     * @author Favereau
     * 
     */
public class ARecorderWAV implements IARecorderWAV{

    public static final int LINE_NUMBER = 3;
    
    public static final String DEF_FILE_EXT = ".wav";
    
    public static String def_file_name = "/Date_";
    public static String def_file_path;
    
    /**
     * Synthetizer.
     */
    private Synthesizer synth;

    /**
     * Level divider.
     */
    private Hashtable<Integer, amplitudeDivByFilter> levelDivider = new Hashtable<>();
    
    /**
     * List of IN passThrough.
     */
    private Hashtable<Integer, PassThrough> passThIn = new Hashtable<>();
    
    /**
     * List of OUT passThrough.
     */
    private Hashtable<Integer, PassThrough> passThOut = new Hashtable<>();
    
    
    /**
     * List of WAV recorder
     */
    private Hashtable<Integer, WaveRecorder> recList = new Hashtable<>();
    
    /**
     * List of Files
     */
    private Hashtable<Integer, File> filesList = new Hashtable<>();
        
    /**
     * REC numbers
     */
    private Hashtable<Integer, Integer> recNumbers = new Hashtable<>();
    
    /**
     * REC states
     */
    private Hashtable<Integer, Boolean> recState = new Hashtable<>();
    
    /**
     * List of default files
     */
    private Hashtable<Integer, Boolean> defaultFilesList = new Hashtable<>();
    
    /**
     * Constructor
     */
    public ARecorderWAV(final Synthesizer s) {
        synth = s;
        String urlCourante = null;
        
        // define default save file prefix
        Date maDate = new Date();
        def_file_name += maDate.getTime() + "_";
        
        // define default save path
        try {
            //String u = this.getClass().getResource('/' + this.getClass().getName().replace('.', '/') + ".class").toString();
            //int x = u.lastIndexOf('/', u.lastIndexOf('!'));
            //urlCourante = u.substring(10, x+1).replace('/', '\\');
            urlCourante = System.getProperty("user.home");
           

            
        } catch (Exception e) {
            urlCourante = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
        }
        
        def_file_path = urlCourante + "/saveFiles";
        File dir = new File(def_file_path);
        if(!dir.exists()){
            dir.mkdir();
        }
        
        
        for(int i = 1; i <= LINE_NUMBER; i++){
            passThIn.put(i, new PassThrough());
            passThOut.put(i, new PassThrough());
            levelDivider.put(i, new amplitudeDivByFilter());
            
            passThIn.get(i).output.connect(passThOut.get(i).input); 
            
            synth.add(passThIn.get(i));
            synth.add(passThOut.get(i));
            
            recNumbers.put(i, 1);
            defaultFilesList.put(i, true);
        }
    }
    
      
    
    @Override
    public boolean startRecord(int index) {
        boolean retour = true;
        String saveFileName = def_file_name;
        File theFile;
        
        if(index <= LINE_NUMBER && index > 0){
            if(defaultFilesList.get(index)){
                saveFileName += "Line_" + index + "_NumEnr_" + recNumbers.get(index);
                String defaultFileName = def_file_path + saveFileName + DEF_FILE_EXT;
                theFile = new File(defaultFileName);
                filesList.put(index, theFile);
            }else{
                if(filesList.get(index).exists()){
                    theFile = filesList.get(index);
                }else{   
                    theFile = new File(filesList.get(index).getAbsolutePath());
                    theFile.getParentFile().mkdirs();
                    try {
                        theFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        
            try {
                recList.put(index, new WaveRecorder(synth, theFile));
               // passThIn.get(index).output.connect(recList.get(index).getInput());
                // Div level by 5 to resize signal
                passThIn.get(index).output.connect(levelDivider.get(index).inputA);
                levelDivider.get(index).output.connect(recList.get(index).getInput());
            } catch (FileNotFoundException e) {
                System.err.println("PB de fichier dans class ARecorderWAV >>> startRecord()");
                retour = false;
            }
        }else{
            retour = false;
        }
        
        recList.get(index).start();
        recState.put(index, true);
        
        return retour;
    }

    @Override
    public boolean stopRecord(int index) {

        recList.get(index).stop();
        filesList.remove(index);
        defaultFilesList.put(index, true);
        
        Integer cmpt = recNumbers.get(index);
        cmpt++;
        recNumbers.put(index, cmpt);
        
        recState.put(index, false);
        
        return true;
    }

    @Override
    public boolean startRecordAll() {
        
        for(int i = 1; i <= recList.size(); i++){
            this.startRecord(i);
        }
        
                
        return true;
    }

    @Override
    public boolean stopRecordAll() {

        for(int i = 1; i <= recList.size(); i++){
            this.stopRecord(i);
        }
        
        return true;
    }

    @Override
    public boolean setFile(int index, File outputFile) {
        File theFile;

        if(outputFile.exists()){
            theFile = outputFile;
        }else{   
            theFile = new File(outputFile.getAbsolutePath());
            theFile.getParentFile().mkdirs();
            try {
                theFile.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        filesList.remove(index);
        filesList.put(index, theFile);
        defaultFilesList.remove(index);
        defaultFilesList.put(index, false);
 
        return true;
    }

    @Override
    public boolean unsetFile(int index) {
        
        filesList.remove(index);
        defaultFilesList.remove(index);
        defaultFilesList.put(index, true);
        
        return true;
    }

    @Override
    public boolean unsetAllFiles() {

        for(int i = 1; i <= filesList.size(); i++){
            filesList.remove(i);
        }
        
        return true;
    }
    
    @Override
    public File getFile(int index) {

        return filesList.get(index);
    }

    @Override
    public UnitInputPort getInputPort(int lineIndex) {
        
        return passThIn.get(lineIndex).input;
    }

    @Override
    public UnitOutputPort getOutputPort(int lineIndex) {
        
        return passThOut.get(lineIndex).output;
    }



    @Override
    public boolean isInRecordMode(int index) {
        
        if(recState.containsKey(index)){
            return recState.get(index);
        }else{
            return false;
        }
        
    }
}
