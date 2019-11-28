package fr.istic.synthlab.global.abstraction;

import fr.istic.synthlab.util.Util.Module;

/**
 * Abstract for BlockImport.
 *
 * @author Jonathan
 */
public class ABlockImport {

    /**
     * Type of the Module.
     */
    private Module type;

    /**
     * @return the type of the module
     */
    public Module getType() {
        return type;
    }

    /**
     * Set the type.
     *
     * @param newType
     *            The type of the blockImport.
     */
    protected void setType(Module newType) {
        this.type = newType;
    }
}
