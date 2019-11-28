package fr.istic.synthlab.module.presentation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.istic.synthlab.module.control.ICModule;
import fr.istic.synthlab.module.control.ICWhiteNoise;
import fr.istic.synthlab.module.presentation.component.ConnectorBloc;
import fr.istic.synthlab.module.presentation.component.MuteBloc;
import fr.istic.synthlab.module.presentation.component.TitledBlocGroup;
import fr.istic.synthlab.util.presentation.IPFemaleJack;


/**
 * Presentation for White Noise module.
 * 
 * @author Favereau
 * 
 */
public class PWhiteNoise extends PModule implements ActionListener, IPWhiteNoise{

    /**
     * UID to serialize this object.
     */
    private static final long serialVersionUID = -922653086635125880L;

    /**
     * The bloc which contains the Mute system
     */
    private MuteBloc muteBloc;
    
    /**
     * The Control associated to the Presentation.
     */
    private ICWhiteNoise control;
    
    
    /**
     * Constructor.
     * 
     * @param control
     */
    public PWhiteNoise(ICWhiteNoise ctrl){
        
        this.control = ctrl;
        this.setLayout(new GridBagLayout());
        
        GridBagConstraints gbcMute = new GridBagConstraints(0, 1,
                GridBagConstraints.REMAINDER, 1, 0,
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, gbcInsets,
                0, 0);
        
        GridBagConstraints gbcOutputs = new GridBagConstraints(0, 2,
                GridBagConstraints.REMAINDER, GridBagConstraints.RELATIVE, 0,
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                gbcInsets, 0, 0);   
        
        this.add(this.createModuleTitle(IPWhiteNoise.TITLE),
                gbcModuleTitle);
        
        this.add(this.processMute(), gbcMute);
        this.add(this.processOutputBloc(), gbcOutputs);
        this.add(this.createModuleBottom(3), gbcBottom);

    }
    
    
    /**
     * Wrapper method to build the mute bloc of this module
     * 
     * @return the mute bloc as JPanel
     */
    private TitledBlocGroup processMute() {
        // Create the TitledBlocGroup of mute
        TitledBlocGroup muteBlocGroup = new TitledBlocGroup("MUTE");

        this.muteBloc = new MuteBloc(this);
        muteBlocGroup.add(this.muteBloc);

        // Return the builded muteBloc
        return muteBlocGroup;

    }
    
    
    /**
     * Create a TitledBlocGroup for outputs with signal.
     * 
     * @return The outputs TitledBlocGroup populated with all its inputs Blocs.
     */
    private TitledBlocGroup processOutputBloc() {
        // Create the TitledBlocGroup of outputs Blocs
        TitledBlocGroup outputsBlocGroup = new TitledBlocGroup("OUTPUT");

        // Get all connector's Presentation
        IPFemaleJack out = control.getOutput().getPresentation();

        // Create a ConnectorBloc with a legend (Icon) for each connector's
        // Presentation
        ConnectorBloc connectorBlocOutSign = new ConnectorBloc(out, "SIGNAL");

        // Add each ConnectorBloc to this blocGroup
        outputsBlocGroup.add(connectorBlocOutSign);

        // Return this blocGroup with populated ConnectorBloc
        return outputsBlocGroup;
    }
    
    @Override
    public ICModule getController() {
        
        return this.control;
    }

    @Override
    public int getWidthU() {
        
        return IPWhiteNoise.WIDTH_U;
    }

    @Override
    public void showMuteOn() {
        
        this.muteBloc.showMuteOn();
    }

    @Override
    public void showMuteOff() {
        
        this.muteBloc.showMuteOff();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(MuteBloc.ACTION_CMD_TOGGLE_MUTE)) {
            this.control.toggleMute();
        }
        
    } 
}
