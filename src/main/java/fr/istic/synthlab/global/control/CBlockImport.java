package fr.istic.synthlab.global.control;

import com.jsyn.Synthesizer;

import fr.istic.synthlab.global.abstraction.ABlockImport;
import fr.istic.synthlab.global.presentation.IPGLOB;
import fr.istic.synthlab.global.presentation.PBlockImport;
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
import fr.istic.synthlab.module.presentation.IPEnvelopeGenerator;
import fr.istic.synthlab.module.presentation.IPKeyboard;
import fr.istic.synthlab.module.presentation.IPMixer;
import fr.istic.synthlab.module.presentation.IPOscilloscope;
import fr.istic.synthlab.module.presentation.IPOut;
import fr.istic.synthlab.module.presentation.IPRecorderWAV;
import fr.istic.synthlab.module.presentation.IPReplicator;
import fr.istic.synthlab.module.presentation.IPSequencer;
import fr.istic.synthlab.module.presentation.IPVoltageControlledAmplifier;
import fr.istic.synthlab.module.presentation.IPVoltageControlledFilterHighPass;
import fr.istic.synthlab.module.presentation.IPVoltageControlledFilterLowPass;
import fr.istic.synthlab.module.presentation.IPVoltageControlledOscillatorA;
import fr.istic.synthlab.module.presentation.IPWhiteNoise;
import fr.istic.synthlab.util.Util.Module;

/**
 * Controller for BlockImport.
 *
 * @author Jonathan
 */
public class CBlockImport extends ABlockImport {

    /**
     * BlockImport Presentation.
     *
     * @category PAC
     */
    private PBlockImport pBlockImport;

    /**
     * Synthesizer.
     */
    private Synthesizer synthesizer;

    /**
     * Module generate by the BlockImport.
     */
    private ICModule module;

    /**
     * Constructor of CBlockImport.
     *
     * @category Constructor
     * @param type
     *            type of module the block import will generate
     * @param synth
     *            synthesizer global
     */
    public CBlockImport(Module type, Synthesizer synth) {
        this.setType(type);
        this.synthesizer = synth;
        pBlockImport = new PBlockImport(this);
    }

    /**
     * @return the Presentation of the GLOB.
     */
    public IPGLOB getPGLOB() {
        return CGLOB.getInstance().getPresentation();
    }

    /**
     * Begin the drag&drop.
     *
     * @category DnD
     */
    public void p2c_debutDnD() {
        switch (getType()) {
        case OUT:
            module = new COut(synthesizer);
            break;
        case VCOA:
            module = new CVoltageControlledOscillatorA(synthesizer);
            break;
        case SCOP:
            module = new COscilloscope(synthesizer);
            break;
        case REP:
            module = new CReplicator(synthesizer);
            break;
        case MIX:
            module = new CMixer(synthesizer);
            break;
        case EG:
            module = new CEnvelopeGenerator(synthesizer);
            break;
        case VCA:
            module = new CVoltageControlledAmplifier(synthesizer);
            break;
        case WN:
            module = new CWhiteNoise(synthesizer);
            break;
        case VCFLP:
            module = new CVoltageControlledFilterLowPass(synthesizer);
            break;
        case VCFHP:
            module = new CVoltageControlledFilterHighPass(synthesizer);
            break;
        case SEQ:
            module = new CSequencer(synthesizer);
            break;
        case KEYB:
            module = new CKeyboard(synthesizer);
            break;
        case REC:
            module = new CRecorderWAV(synthesizer);
            break;
        default:
            module = null;
            break;
        }
        pBlockImport.c2p_debutDnDOK(module);
    }

    /**
     * @return the presentation
     * @category Accessor
     */
    public PBlockImport getPresentation() {
        return pBlockImport;
    }

    /**
     * @return the widthU of the module generate by the BlockImport.
     */
    public int getWidthU() {
        int widthU = 1;
        switch (getType()) {
        case OUT:
            widthU = IPOut.WIDTH_U;
            break;
        case VCOA:
            widthU = IPVoltageControlledOscillatorA.WIDTH_U;
            break;
        case SCOP:
            widthU = IPOscilloscope.WIDTH_U;
            break;
        case REP:
            widthU = IPReplicator.WIDTH_U;
            break;
        case MIX:
            widthU = IPMixer.WIDTH_U;
            break;
        case EG:
            widthU = IPEnvelopeGenerator.WIDTH_U;
            break;
        case VCA:
            widthU = IPVoltageControlledAmplifier.WIDTH_U;
            break;
        case WN:
            widthU = IPWhiteNoise.WIDTH_U;
            break;
        case VCFLP:
            widthU = IPVoltageControlledFilterLowPass.WIDTH_U;
            break;
        case VCFHP:
            widthU = IPVoltageControlledFilterHighPass.WIDTH_U;
            break;
        case SEQ:
            widthU = IPSequencer.WIDTH_U;
            break;
        case KEYB:
            widthU = IPKeyboard.WIDTH_U;
            break;
        case REC:
            widthU = IPRecorderWAV.WIDTH_U;
            break;
        default:
            break;
        }
        return widthU;
    }
}
