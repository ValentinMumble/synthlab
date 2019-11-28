package fr.istic.synthlab.save.abstraction;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

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
import fr.istic.synthlab.save.tools.MyXMLTools;
import fr.istic.synthlab.save.tools.SaveAttenuation;
import fr.istic.synthlab.util.Util.Module;
import fr.istic.synthlab.util.control.ICFemaleJack;

/**
 * Implementation of the ASave.
 *
 * @author Mickael
 * @version 1.0
 */
public class ASave implements IASave {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger
            .getLogger(ASave.class.getName());
    /**
     * if save have success.
     */
    private boolean success = false;

    @Override
    public boolean getSuccess() {
        boolean sol = success;
        success = false;
        return sol;
    }

    /**
     * @param module
     *            the module we are saving.
     * @return the type corresponding to the module.
     */
    private Module getTypeModule(ICModule module) {
        if (module instanceof COut) {
            return Module.OUT;
        }
        if (module instanceof COscilloscope) {
            return Module.SCOP;
        }
        if (module instanceof CMixer) {
            return Module.MIX;
        }
        if (module instanceof CVoltageControlledAmplifier) {
            return Module.VCA;
        }
        if (module instanceof CVoltageControlledOscillatorA) {
            return Module.VCOA;
        }
        if (module instanceof CVoltageControlledFilterLowPass) {
            return Module.VCFLP;
        }
        if (module instanceof CWhiteNoise) {
            return Module.WN;
        }
        if (module instanceof CReplicator) {
            return Module.REP;
        }
        if (module instanceof CEnvelopeGenerator) {
            return Module.EG;
        }
        if (module instanceof CVoltageControlledFilterHighPass) {
            return Module.VCFHP;
        }
        if (module instanceof CSequencer) {
            return Module.SEQ;
        }
        if (module instanceof CRecorderWAV) {
            return Module.REC;
        }
        if (module instanceof CKeyboard) {
            return Module.KEYB;
        }
        return null;
    }

    @Override
    public void save(String chooserPath) {
        Element racine = new Element("content");
        Attribute size = new Attribute(MyXMLTools.gridSize,
                String.valueOf(CGLOB.getInstance().getCPanelContent()
                        .getGrille().size()));
        racine.setAttribute(size);
        
        Attribute skin = new Attribute(MyXMLTools.skin,
                String.valueOf(PGLOB.getTemplate()));
        racine.setAttribute(skin);

        Element listeModules = new Element(MyXMLTools.blocModules);
        racine.addContent(listeModules);

        Element listeCables = new Element(MyXMLTools.blocCables);
        racine.addContent(listeCables);

        ArrayList<CSlot[]> grille = CGLOB.getInstance()
                .getCPanelContent().getGrille();

        for (CSlot[] ligne : grille) {
            for (CSlot slot : ligne) {
                if (slot.isOccupe() && slot.getICModule() != null) {
                    Element racineModule = new Element(MyXMLTools.module);
                    Attribute posY = new Attribute(MyXMLTools.moduleLine,
                            String.valueOf(slot.getLigne()));
                    racineModule.setAttribute(posY);
                    Attribute posX = new Attribute(MyXMLTools.modulePosition,
                            String.valueOf(slot.getPosition()));
                    racineModule.setAttribute(posX);

                    ICModule module = slot.getICModule();

                    // Debut de l'exploration des possibilités de modules
                    Attribute type;
                    Attribute mute;
                    switch (getTypeModule(module)) {
                    case EG:
                        type = new Attribute(MyXMLTools.moduleType, "EG");
                        racineModule.setAttribute(type);
                        break;
                    case MIX:
                        type = new Attribute(MyXMLTools.moduleType, "MIX");
                        racineModule.setAttribute(type);
                        break;
                    case OUT:
                        type = new Attribute(MyXMLTools.moduleType, "OUT");
                        racineModule.setAttribute(type);

                        mute = new Attribute(MyXMLTools.moduleMute,
                                String.valueOf(((COut) module).isMute()));
                        racineModule.setAttribute(mute);
                        break;
                    case REP:
                        type = new Attribute(MyXMLTools.moduleType, "REP");
                        racineModule.setAttribute(type);
                        break;
                    case SCOP:
                        type = new Attribute(MyXMLTools.moduleType, "SCOP");
                        racineModule.setAttribute(type);
                        break;
                    case VCA:
                        type = new Attribute(MyXMLTools.moduleType, "VCA");
                        racineModule.setAttribute(type);
                        break;
                    case VCFLP:
                        type = new Attribute(MyXMLTools.moduleType, "VCFLP");
                        racineModule.setAttribute(type);
                        break;
                    case VCOA:
                        type = new Attribute(MyXMLTools.moduleType, "VCOA");
                        racineModule.setAttribute(type);
                        break;
                    case WN:
                        type = new Attribute(MyXMLTools.moduleType, "WN");
                        racineModule.setAttribute(type);

                        mute = new Attribute(MyXMLTools.moduleMute,
                                String.valueOf(((CWhiteNoise) module)
                                        .getEnable()));
                        racineModule.setAttribute(mute);
                        break;
                    case VCFHP:
                        type = new Attribute(MyXMLTools.moduleType, "VCFHP");
                        racineModule.setAttribute(type);
                        break;
                    case SEQ:
                        type = new Attribute(MyXMLTools.moduleType, "SEQ");
                        racineModule.setAttribute(type);
                        break;
                    case REC:
                        type = new Attribute(MyXMLTools.moduleType, "REC");
                        racineModule.setAttribute(type);
                        break;
                    case KEYB:
                        type = new Attribute(MyXMLTools.moduleType, "KEYB");
                        racineModule.setAttribute(type);
                        break;
                    default:
                        break;
                    }
                    for (SaveAttenuation attenuation : module
                            .getAttenuationForSave()) {
                        Element racineAttenuation = new Element(
                                MyXMLTools.attenuation);
                        Attribute ident = new Attribute(
                                MyXMLTools.attenuationIdent,
                                attenuation.getIdent());
                        racineAttenuation.setAttribute(ident);
                        Attribute value = new Attribute(
                                MyXMLTools.attenuationValue,
                                String.valueOf(attenuation.getValue()));
                        racineAttenuation.setAttribute(value);

                        racineModule.addContent(racineAttenuation);
                    }

                    for (ICFemaleJack jack : module.getJacksForSave(String
                            .valueOf(slot.getLigne())
                            + "_"
                            + String.valueOf(slot.getPosition()))) {
                        Element racineJack = new Element(MyXMLTools.jack);
                        Attribute ident = new Attribute(MyXMLTools.jackIdent,
                                jack.getIdent());
                        racineJack.setAttribute(ident);

                        racineModule.addContent(racineJack);
                    }

                    listeModules.addContent(racineModule);

                }
            }
        }

        // Ajout des cables
        for (ICCable cable : CGLOB.getInstance().getListCables()) {
            Element racineCable = new Element(MyXMLTools.cable);
            Attribute port1 = new Attribute(MyXMLTools.cablePort1, cable
                    .getJack1().getIdent());
            racineCable.setAttribute(port1);
            Attribute port2 = new Attribute(MyXMLTools.cablePort2, cable
                    .getJack2().getIdent());
            racineCable.setAttribute(port2);
            Attribute red = new Attribute(MyXMLTools.cableRed,
                    String.valueOf(cable.getColor().getRed()));
            racineCable.setAttribute(red);
            Attribute green = new Attribute(MyXMLTools.cableGreen,
                    String.valueOf(cable.getColor().getGreen()));
            racineCable.setAttribute(green);
            Attribute blue = new Attribute(MyXMLTools.cableBlue,
                    String.valueOf(cable.getColor().getBlue()));
            racineCable.setAttribute(blue);
            listeCables.addContent(racineCable);
        }

        try {
            File file = new File(chooserPath);
            file.mkdirs();
            Document document = new Document(racine);
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            sortie.output(document, new FileOutputStream(chooserPath));
        } catch (java.io.IOException e) {
            LOGGER.info("Error save file");
            LOGGER.info(e.toString());
        }

        success = true;
    }
}
