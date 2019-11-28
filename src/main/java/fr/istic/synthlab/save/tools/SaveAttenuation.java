package fr.istic.synthlab.save.tools;

/**
 * Class used to save and load Attenuation.
 *
 * @author Mickael
 * @version 1.0
 */
public class SaveAttenuation {
    /**
     * Ident of the attenuation.
     */
    private String ident;
    /**
     * Value of the attenuation.
     */
    private double value;

    /**
     * Constructor of the SaveAttenuation.
     *
     * @param ident
     *            The ident of the attenuation.
     * @param value
     *            The value of the attenuation.
     */
    public SaveAttenuation(String ident, double value) {
        this.ident = ident;
        this.value = value;
    }

    /**
     * @return the ident of the attenuation saved.
     */
    public String getIdent() {
        return ident;
    }

    /**
     * @return the value of the attenuation saved.
     */
    public double getValue() {
        return value;
    }
}
