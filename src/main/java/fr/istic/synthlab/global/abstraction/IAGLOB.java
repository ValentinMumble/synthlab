package fr.istic.synthlab.global.abstraction;

/**
 * Interface of AGLOB.
 *
 * @author Mickael
 * @version 1.0
 */
public interface IAGLOB {

    /**
     * Function to open save window.
     *
     * @return true if the save have success.
     */
    boolean enregistrer();

    /**
     * Function to open load window.
     */
    void load();
}
