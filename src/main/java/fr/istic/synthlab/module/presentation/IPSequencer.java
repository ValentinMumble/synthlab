package fr.istic.synthlab.module.presentation;

/**
 * The interface for the presentation of the Sequencer module.
 * 
 * @author valentinmumble
 * 
 */
public interface IPSequencer extends IPModule {

    /**
     * Module title.
     */
    String TITLE = "<html><center>SEQUENCER</center></html>";
    
    /**
     * Module width.
     */
    int WIDTH_U = 3;

    /**
     * Called by the control to update the pitch of the given index.
     * 
     * @param index
     * @param pitch
     */
    void updatePitch(int index, double pitch);
}
