package fr.istic.synthlab.module.presentation.component;

import java.util.Collection;

import javax.swing.BoxLayout;

/**
 * GUI component to be used in a PModule.
 * 
 * Represents a simple group of Blocs
 * 
 * @author Laurent Legendre
 * 
 */
public class SimpleBlocGroup extends Bloc {

    /**
     * UID to serialize this object.
     */
    private static final long serialVersionUID = -3081334810818669338L;

    /**
     * Collection of blocs which are members of this SimpleBlocGroup.
     * 
     */
    private Collection<Bloc> blocs;

    /**
     * Constructor.
     * 
     * Default layout is BoxLayout.LINE_AXIS
     */
    public SimpleBlocGroup() {
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
    }

    /**
     * Constructor with as many Blocs as you want.
     * 
     * @param name
     * @param blocs
     */
    public SimpleBlocGroup(Bloc... blocs) {
        for (Bloc bloc : blocs) {
            this.addBloc(bloc);
        }
    }

    /**
     * Constructor with an existing collection of Blocs
     * 
     * @param name
     * @param blocs
     */
    public SimpleBlocGroup(Collection<Bloc> blocs) {
        this();
        this.setBlocs(blocs);
    }

    /**
     * Get the Blocs which are members of this SimpleBlocGroup.
     * 
     * @return A Collection of Bloc
     */
    protected Collection<Bloc> getBlocs() {
        return blocs;
    }

    /**
     * Set all the members from an existing Collection of Blocs
     * 
     * @param blocs
     */
    protected void setBlocs(Collection<Bloc> blocs) {
        this.blocs = blocs;
    }

    /**
     * Add a bloc as a member of this SimpleBlocGroup. If the bloc already
     * exists, nothing is done.
     * 
     * @param bloc
     */
    protected void addBloc(Bloc bloc) {
        if (!this.getBlocs().contains(bloc)) {
            this.blocs.add(bloc);
        }
    }

}