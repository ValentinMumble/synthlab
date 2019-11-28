package fr.istic.synthlab.module.abstraction;

import java.io.File;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;

/**
 * Specific interface for the files recorder module.
 *
 * @author Favereau
 */

public interface IARecorderWAV extends IAModule{

    /**
     * TAG Identifier for the ARecorderWAV module.
     */
    String TAG = "REC";

    /**
     * Start to record for the specified line.
     *
     * @param line in index
     * 
     * @return true if there is no problems
     */
    boolean startRecord(int index);

    
    /**
     * Stop to record for the specified line.
     *
     * @param line in index
     * 
     * @return true if there is no problems
     */
    boolean stopRecord(int index);
    
    /**
     * Start to record all lines.
     * 
     * @return true if there is no problems
     */
    boolean startRecordAll();

    
    /**
     * Stop to record all lines.
     * 
     * @return true if there is no problems
     */
    boolean stopRecordAll();
    
    /**
     * Ask if specified line is recording.
     * 
     * @return true if the line is
     *          in record mode
     */
    boolean isInRecordMode(int index);
    
    /**
     * Configure the output record file
     * for the specified line
     *
     * @param line in index
     * @param file
     * 
     * @return true if there is no problems
     */
    boolean setFile(int index, File outputFile);
    
    /**
     * Configure the output record file
     * to default file (null) for the specified line
     *
     * @param line in index
     * 
     * @return true if there is no problems
     */
    boolean unsetFile(int index);
    
    /**
     * Configure the outputs record files
     * to default files (null) for all lines
     *
     * 
     * @return true if there is no problems
     */
    boolean unsetAllFiles();
    
    /**
     * Get the output record file
     * for the specified line
     *
     * @param line in index
     * 
     * @return record file.
     */
    File getFile(int index);

    
    /**
     * Get the input port of the ARecorderWAV module
     * for the specified line.
     *
     * @param line in index
     * 
     * @return The input port
     */
    UnitInputPort getInputPort(int lineIndex);
    
    /**
     * Get the output port of the ARecorderWAV module
     * for the specified line.
     *
     * @param line in index
     * 
     * @return The output port
     */
    UnitOutputPort getOutputPort(int lineIndex);
}
