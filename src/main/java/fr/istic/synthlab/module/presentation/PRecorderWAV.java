package fr.istic.synthlab.module.presentation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.istic.synthlab.module.control.ICModule;
import fr.istic.synthlab.module.control.ICRecorderWAV;
import fr.istic.synthlab.module.presentation.component.Bloc;
import fr.istic.synthlab.module.presentation.component.ConnectorBloc;
import fr.istic.synthlab.module.presentation.component.RecorderBloc;
import fr.istic.synthlab.module.presentation.component.TitledBlocGroup;
import fr.istic.synthlab.util.presentation.IPFemaleJack;

/**
 * Presentation for RecorderWAV module.
 * 
 * @author Favereau
 */
public class PRecorderWAV extends PModule implements IPRecorderWAV, ActionListener{

    /**
     * UID to serialize this object.
     */
    private static final long serialVersionUID = -137787358533429606L;

    /**
     * The blocs which contains the rec control system
     * for the 3 lines
     */
    private RecorderBloc recBloc1, recBloc2, recBloc3;
    
    /**
     * The Control associated to the Presentation.
     */
    private ICRecorderWAV control;
    
    /**
     * Constructor.
     * 
     * @param control
     */
    public PRecorderWAV(ICRecorderWAV ctrl){
        this.control = ctrl;
        this.setLayout(new GridBagLayout());
        

        GridBagConstraints gbcLine1 = new GridBagConstraints(0, 1,
                GridBagConstraints.REMAINDER, 1, 0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, gbcInsets,
                0, 0);

        GridBagConstraints gbcLine2 = new GridBagConstraints(0, 2,
                GridBagConstraints.REMAINDER, 1, 0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, gbcInsets,
                0, 0);

        GridBagConstraints gbcLine3 = new GridBagConstraints(0, 3,
                GridBagConstraints.REMAINDER, GridBagConstraints.RELATIVE, 0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, gbcInsets,
                0, 0);
        
     // Build and add main blocs
        this.add(this.createModuleTitle(IPRecorderWAV.TITLE),
                gbcModuleTitle);

        recBloc1 = new RecorderBloc(this, 1);
        recBloc2 = new RecorderBloc(this, 2);
        recBloc3 = new RecorderBloc(this, 3);
  
        this.add(this.processLineInOut(1), gbcLine1);
        this.add(this.processLineInOut(2), gbcLine2);
        this.add(this.processLineInOut(3), gbcLine3);
        this.add(this.createModuleBottom(4), gbcBottom);

    }
    
    /**
     * Create a BlocGroup for Inputs and output for 1 line
     * 
     * @return The Inputs BlocGroup populated with input and output Bloc.
     */
    private Bloc processLineInOut(int lineID) {
        
        Bloc intputsBlocGroup = new TitledBlocGroup("LINE" + lineID);

        IPFemaleJack inputConnector = control.getLineIn(lineID).getPresentation();
        IPFemaleJack outputConnector = control.getLineOut(lineID).getPresentation();

        // Create a ConnectorBloc with a legend (Text) for the FM connector
        ConnectorBloc connectorBlocIn = new ConnectorBloc(inputConnector, "IN");
        ConnectorBloc connectorBlocOut = new ConnectorBloc(outputConnector, "OUT");

        // Add  ConnectorBloc to this blocGroup
        intputsBlocGroup.add(connectorBlocIn);

        switch (lineID) {
        case 1:
            intputsBlocGroup.add(recBloc1);
            break;
        case 2:
            intputsBlocGroup.add(recBloc2);
            break;
        case 3:
            intputsBlocGroup.add(recBloc3);
    break;
        }
        
        intputsBlocGroup.add(connectorBlocOut);
        
        intputsBlocGroup
                .setSize(70, intputsBlocGroup.getPreferredSize().height);
        intputsBlocGroup.setPreferredSize(intputsBlocGroup.getSize());
        intputsBlocGroup.setMinimumSize(intputsBlocGroup.getSize());

        // Return the builded muteBloc
        return intputsBlocGroup;
    }
    
    @Override
    public ICModule getController() {
        
        return control;
    }

    @Override
    public int getWidthU() {
        
        return IPRecorderWAV.WIDTH_U;
    }

    @Override
    public void updateLed(int line) {

        switch (line) {
        case 1:
            if(control.isInRecordMode(line)){
                recBloc1.showMuteOn();
            }else{
                recBloc1.showMuteOff();
            }
            break;

        case 2:
            if(control.isInRecordMode(line)){
                recBloc2.showMuteOn();
            }else{
                recBloc2.showMuteOff();
            }
            break;
            
        case 3:
            if(control.isInRecordMode(line)){
                recBloc3.showMuteOn();
            }else{
                recBloc3.showMuteOff();
            }
            break;
            
        }
    }


    /**
     * Action performed when the recorderBloc forward an actionEvent after a click
     * occurs on its rec button
     */
    @Override
    public void actionPerformed(ActionEvent e) {


        if (e.getActionCommand().equals(RecorderBloc.ACTION_CMD_TOGGLE_REC + "_1")) {
            this.control.changeRecordState(1);
        }
        if (e.getActionCommand().equals(RecorderBloc.ACTION_CMD_FILE + "_1")) {
            this.control.changeSaveFile(1);
        }



        if (e.getActionCommand().equals(RecorderBloc.ACTION_CMD_TOGGLE_REC + "_2")) {
            this.control.changeRecordState(2);
        }
        if (e.getActionCommand().equals(RecorderBloc.ACTION_CMD_FILE + "_2")) {
            this.control.changeSaveFile(2);
        }



        if (e.getActionCommand().equals(RecorderBloc.ACTION_CMD_TOGGLE_REC + "_3")) {
            this.control.changeRecordState(3);
        }
        if (e.getActionCommand().equals(RecorderBloc.ACTION_CMD_FILE + "_3")) {
            this.control.changeSaveFile(3);
        }

    }
    
}
