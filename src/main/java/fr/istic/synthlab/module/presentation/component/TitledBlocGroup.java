package fr.istic.synthlab.module.presentation.component;

import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 * GUI component to be used in a PModule.
 * 
 * Represents a group of Blocs surrounded by a border which have an optional
 * Title
 * 
 * @author Laurent Legendre
 * 
 */
public class TitledBlocGroup extends SimpleBlocGroup {

    /**
     * UID for serialize this object.
     */
    private static final long serialVersionUID = 3582983309763484833L;

    /**
     * Border for the TitledBlocGroup with the name of the TitledBlocGroup as
     * title.
     */
    private Border border;

    public TitledBlocGroup(String name) {
        super();
        if (name.isEmpty()) {
            this.border = BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(7, 0, 0, 0),
                    BorderFactory.createLineBorder(DEFAULT_COLOR));
        } else {
            this.border = BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(DEFAULT_COLOR), name,
                    TitledBorder.CENTER, TitledBorder.TOP, DEFAULT_FONT,
                    DEFAULT_COLOR);
        }
        this.setBorder(border);
    }

    /**
     * Constructor with as many Blocs as you want.
     * 
     * @param name
     * @param blocs
     */
    public TitledBlocGroup(String name, Bloc... blocs) {
        this(name);
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
    public TitledBlocGroup(String name, Collection<Bloc> blocs) {
        this(name);
        this.setBlocs(blocs);
    }

}