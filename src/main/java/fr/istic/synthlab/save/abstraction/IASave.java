package fr.istic.synthlab.save.abstraction;

/**
 * Interface of the ALoad.
 *
 * @author Mickael
 * @version 1.0
 */
public interface IASave {

    /**
     * Function that save the model contained in the file give.
     *
     * @param chooserPath
     *            Path where to create/replace the file.
     */
    void save(String chooserPath);

    /**
     * @return if the save succes.
     */
    boolean getSuccess();

}
