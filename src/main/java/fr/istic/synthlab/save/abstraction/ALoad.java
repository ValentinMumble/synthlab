package fr.istic.synthlab.save.abstraction;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import com.jsyn.Synthesizer;

import fr.istic.synthlab.cable.control.CCable;
import fr.istic.synthlab.cable.control.ICCable;
import fr.istic.synthlab.global.control.CGLOB;
import fr.istic.synthlab.global.control.CSlot;
import fr.istic.synthlab.global.presentation.PGLOB;
import fr.istic.synthlab.module.control.CEnvelopeGenerator;
import fr.istic.synthlab.module.control.CKeyboard;
import fr.istic.synthlab.module.control.CMixer;
import fr.istic.synthlab.module.control.COscilloscope;
import fr.istic.synthlab.module.control.COut;
import fr.istic.synthlab.module.control.CRecorderWAV;
import fr.istic.synthlab.module.control.CReplicator;
import fr.istic.synthlab.module.control.CSequencer;
import fr.istic.synthlab.module.control.CVoltageControlledAmplifier;
import fr.istic.synthlab.module.control.CVoltageControlledFilterHighPass;
import fr.istic.synthlab.module.control.CVoltageControlledFilterLowPass;
import fr.istic.synthlab.module.control.CVoltageControlledOscillatorA;
import fr.istic.synthlab.module.control.CWhiteNoise;
import fr.istic.synthlab.module.control.ICModule;
import fr.istic.synthlab.module.control.ICOut;
import fr.istic.synthlab.module.control.ICWhiteNoise;
import fr.istic.synthlab.save.tools.MyXMLTools;
import fr.istic.synthlab.save.tools.SaveAttenuation;
import fr.istic.synthlab.util.Template;
import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * Implementation of the ALoad.
 * 
 * @author Mickael
 * @version 1.0
 */
public class ALoad implements IALoad {

    /**
     * Log.
     */
    private static final Logger LOGGER = Logger
            .getLogger(ALoad.class.getName());

    /**
     * The list of ports to plugged.
     */
    private HashMap<String, ICFemaleJack> listPortToPlug;

    /**
     * initialize the list of ports to plugged.
     */
    protected void initPortList() {
        this.listPortToPlug = new HashMap<String, ICFemaleJack>();
    }

    /**
     * initialize the grid with the number of line of the save.
     * 
     * @param size
     *            number of line saved.
     */
    private void loadGridSize(int size) {
        CGLOB.getInstance().getCPanelContent().removeLine();
        CGLOB.getInstance().getCPanelContent().removeLine();
        while (CGLOB.getInstance().getCPanelContent().getGrille().size() < size) {
            CGLOB.getInstance().getCPanelContent().addLine();
        }
    }

    /**
     * Function that load a module with parameters saved at its place in the
     * grid.
     * 
     * @param line
     *            line inthe grid.
     * @param position
     *            position in the line.
     * @param moduleLoad
     *            icmodule to load
     * @param listAtt
     *            list of SaveAttenuation to load.
     * @param listIdentJackPlugged
     *            list of jack to ident for plug cables.
     */
    private void loadModule(int line, int position, ICModule moduleLoad,
            List<SaveAttenuation> listAtt, List<String> listIdentJackPlugged) {
        // chargement des valeurs des attenuateurs
        moduleLoad.loadAttenuation(listAtt);

        // chargement des ident des ports a brancher
        for (String ident : listIdentJackPlugged) {
            moduleLoad.loadJack(ident, listPortToPlug);
        }

        // chargement du module à sa place dans la grille
        CSlot place = CGLOB.getInstance().getCPanelContent().getGrille()
                .get(line)[position];
        place.loadModule(moduleLoad);

        CGLOB.getInstance().myResize();
    }

    /**
     * Function that load the cable plugged ine the two jacks and with the color
     * saved.
     * 
     * @param jack1
     *            First Jack to plugged.
     * @param jack2
     *            Second Jack to plugged.
     * @param color
     *            color of the cable.
     */
    private void loadCable(ICFemaleJack jack1, ICFemaleJack jack2, Color color) {
        ICCable cable = new CCable(jack1);
        cable.plug(jack2);
        cable.setColor(color);

        CGLOB.getInstance().loadCable(cable);
    }

    /**
     * Generate a module of the type give in parameter.
     * 
     * @param type
     *            Type of the module wanted.
     * @return The module of the good type.
     */
    private ICModule generateModule(String type) {
        ICModule module;
        Synthesizer synth = CGLOB.getInstance().getSynthesizer();
        switch (type) {
        case "OUT":
            module = new COut(synth);
            break;
        case "VCOA":
            module = new CVoltageControlledOscillatorA(synth);
            break;
        case "SCOP":
            module = new COscilloscope(synth);
            break;
        case "REP":
            module = new CReplicator(synth);
            break;
        case "MIX":
            module = new CMixer(synth);
            break;
        case "EG":
            module = new CEnvelopeGenerator(synth);
            break;
        case "VCA":
            module = new CVoltageControlledAmplifier(synth);
            break;
        case "WN":
            module = new CWhiteNoise(synth);
            break;
        case "VCFLP":
            module = new CVoltageControlledFilterLowPass(synth);
            break;
        case "VCFHP":
            module = new CVoltageControlledFilterHighPass(synth);
            break;
        case "SEQ":
            module = new CSequencer(synth);
            break;
        case "REC":
            module = new CRecorderWAV(synth);
            break;
        case "KEYB":
            module = new CKeyboard(synth);
            break;
        default:
            module = null;
            break;
        }
        return module;
    }

    private Template retieveTemplate(String templateName) {
        Template template = Template.BLUE;
        switch (templateName) {
        case "BLUE":
            break;

        case "BLACK":
            template = Template.BLACK;
            break;

        case "WOOD":
            template = Template.WOOD;
            break;

        case "LEATHER":
            template = Template.LEATHER;
            break;

        case "METAL":
            template = Template.METAL;
            break;

        default:
            break;
        }
        return template;

    }

    /**
     * Function that load the model contained in the file give.
     * 
     * @param chooserPath
     *            Path where to found the file.
     */
    @Override
    public void load(String chooserPath) {
        SAXBuilder sxb = new SAXBuilder();
        Document document;
        Element racine = null;
        try {
            document = sxb.build(new File(chooserPath));
            racine = document.getRootElement();
        } catch (Exception e) {
            LOGGER.info("Error read file");
            LOGGER.info(e.toString());
        }

        if (racine != null) {
            CGLOB.getInstance().getCPanelContent().reinitForLoad();
            // grid initialization
            int size = Integer.parseInt(racine
                    .getAttributeValue(MyXMLTools.gridSize));
            this.loadGridSize(size);
            
            // get the template
            Template template = retieveTemplate(racine.getAttributeValue(MyXMLTools.skin));
            PGLOB.setTemplate(template);
            
            // modules recuperation
            Element blocModules = racine.getChild(MyXMLTools.blocModules);
            List<Element> listModules = blocModules
                    .getChildren(MyXMLTools.module);

            // modules initialization 
            for (Element eModule : listModules) {
                int line = Integer.parseInt(eModule
                        .getAttributeValue(MyXMLTools.moduleLine));
                int position = Integer.parseInt(eModule
                        .getAttributeValue(MyXMLTools.modulePosition));
                String type = eModule.getAttributeValue(MyXMLTools.moduleType);

                // module attenuators recovery
                List<SaveAttenuation> listAtt = new ArrayList<SaveAttenuation>();
                List<Element> listElemAtt = eModule
                        .getChildren(MyXMLTools.attenuation);
                for (Element eAtt : listElemAtt) {
                    String ident = eAtt
                            .getAttributeValue(MyXMLTools.attenuationIdent);
                    Double value = Double.parseDouble(eAtt
                            .getAttributeValue(MyXMLTools.attenuationValue));
                    SaveAttenuation att = new SaveAttenuation(ident, value);
                    listAtt.add(att);
                }

                // ident jack recovery to initialize for this module
                List<String> listIdentJack = new ArrayList<String>();
                List<Element> listElemjack = eModule
                        .getChildren(MyXMLTools.jack);
                for (Element eJack : listElemjack) {
                    String ident = eJack
                            .getAttributeValue(MyXMLTools.jackIdent);
                    listIdentJack.add(ident);
                }
                ICModule moduleLoad = this.generateModule(type);

                this.loadModule(line, position, moduleLoad, listAtt,
                        listIdentJack);

                if (moduleLoad instanceof COut) {
                    boolean isMute = Boolean.parseBoolean(eModule
                            .getAttributeValue(MyXMLTools.moduleMute));
                    ((ICOut) moduleLoad).loadMuteState(isMute);
                } else if (moduleLoad instanceof CWhiteNoise) {
                    boolean isMute = Boolean.parseBoolean(eModule
                            .getAttributeValue(MyXMLTools.moduleMute));
                    ((ICWhiteNoise) moduleLoad).loadMuteState(isMute);
                }
            }

            CGLOB.getInstance().myResize();

            // Cables recovery
            Element blocCables = racine.getChild(MyXMLTools.blocCables);
            List<Element> listCables = blocCables.getChildren(MyXMLTools.cable);

            // Cables drawing
            for (Element eCable : listCables) {
                String identPort1 = eCable
                        .getAttributeValue(MyXMLTools.cablePort1);
                String identPort2 = eCable
                        .getAttributeValue(MyXMLTools.cablePort2);

                int colorRed = Integer.parseInt(eCable
                        .getAttributeValue(MyXMLTools.cableRed));
                int colorGreen = Integer.parseInt(eCable
                        .getAttributeValue(MyXMLTools.cableGreen));
                int colorBlue = Integer.parseInt(eCable
                        .getAttributeValue(MyXMLTools.cableBlue));

                ICFemaleJack jack1 = listPortToPlug.get(identPort1);
                ICFemaleJack jack2 = listPortToPlug.get(identPort2);

                this.loadCable(jack1, jack2, new Color(colorRed, colorGreen,
                        colorBlue));
            }
        }
        CGLOB.getInstance().myResize();
        CGLOB.getInstance().getPresentation().updatePresentationCables();
    }
}
