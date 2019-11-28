package fr.istic.synthlab.save.abstraction;

/**
 * Interface of the ALoad.
 *
 * @author Mickael
 * @version 1.0
 */
public interface IALoad {

    /**
     * Function that load the model contained in the file give.
     *
     * @param chooserPath
     *            Path where to found the file.
     */
    void load(String chooserPath);
}
