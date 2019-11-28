package fr.istic.synthlab.module.control;

import fr.istic.synthlab.module.abstraction.IARecorderWAV;
import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * Interface for the WAVE RECORDER module controller.
 *
 * @author Favereau
 *
 */
public interface ICRecorderWAV extends ICModule, IARecorderWAV {

    
    /**
    *
    * @return the line in controller
    * 
    * @param index of the line
    */
   ICFemaleJack getLineIn(int lineID);
   
   /**
   *
   * @return the line out controller
   * 
   * @param index of the line
   */
  ICFemaleJack getLineOut(int lineID);
  
  /**
  *
  * Call by the presentation to change
  * the save file of the specified line.
  * 
  * @param index of the line
  */
  void changeSaveFile(int lineID);
  
  /**
  *
  * Call by the presentation to change
  * the record state of the specified line.
  * 
  * @param index of the line
  * @param new state 
  *         true = record
  *         false = stop
  */
  void changeRecordState(int lineID);
  
}
