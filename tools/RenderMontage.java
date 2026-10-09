import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.devices.AudioDeviceManager;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.util.WaveRecorder;

import fr.istic.synthlab.filter.AttenuationFilter;
import fr.istic.synthlab.module.abstraction.AEnvelopeGenerator;
import fr.istic.synthlab.module.abstraction.AMixer;
import fr.istic.synthlab.module.abstraction.AOscilloscope;
import fr.istic.synthlab.module.abstraction.AReplicator;
import fr.istic.synthlab.module.abstraction.ASequencer;
import fr.istic.synthlab.module.abstraction.AVoltageControlledAmplifier;
import fr.istic.synthlab.module.abstraction.AVoltageControlledFilterHighPass;
import fr.istic.synthlab.module.abstraction.AVoltageControlledFilterLowPass;
import fr.istic.synthlab.module.abstraction.AVoltageControlledOscillatorA;
import fr.istic.synthlab.module.abstraction.AWhiteNoise;
import fr.istic.synthlab.util.Util;

/**
 * Renders a montage XML to a mono WAV without the GUI, using the app's own
 * audio classes. Also checks grid placement and that every cable end exists.
 *
 * Usage: java -cp target/synthlab-0.0.1-SNAPSHOT.jar:tools-out RenderMontage montage.xml out.wav seconds
 */
public class RenderMontage {

    private static final int SLOTS_BY_ROW = 9;
    private static final Map<String, Integer> WIDTHS = Map.ofEntries(
            Map.entry("VCOA", 3), Map.entry("SEQ", 3), Map.entry("EG", 3),
            Map.entry("MIX", 3), Map.entry("KEYB", 3), Map.entry("SCOP", 4),
            Map.entry("VCA", 2), Map.entry("VCFLP", 2), Map.entry("VCFHP", 2),
            Map.entry("REC", 2), Map.entry("REP", 1), Map.entry("WN", 1),
            Map.entry("OUT", 1));

    public static void main(String[] args) throws Exception {
        Element root = new SAXBuilder().build(new File(args[0])).getRootElement();
        double seconds = Double.parseDouble(args[2]);
        int rows = Integer.parseInt(root.getAttributeValue("size"));
        boolean[][] occupied = new boolean[rows][SLOTS_BY_ROW];

        Synthesizer synth = JSyn.createSynthesizer();
        synth.setRealTime(false);
        Map<String, UnitPort> ports = new HashMap<>();
        AttenuationFilter speaker = null;

        for (Element module : root.getChild("liste_modules").getChildren("module")) {
            String type = module.getAttributeValue("type");
            int row = Integer.parseInt(module.getAttributeValue("ligne"));
            int position = Integer.parseInt(module.getAttributeValue("position"));
            String prefix = row + "_" + position + "-";
            int width = WIDTHS.get(type);
            if (row >= rows || position + width > SLOTS_BY_ROW) {
                throw new IllegalStateException(prefix + type + " does not fit in the grid");
            }
            for (int slot = position; slot < position + width; slot++) {
                if (occupied[row][slot]) {
                    throw new IllegalStateException(prefix + type + " overlaps another module");
                }
                occupied[row][slot] = true;
            }
            Map<String, Double> knobs = new HashMap<>();
            for (Element knob : module.getChildren("attenuation")) {
                knobs.put(knob.getAttributeValue("ident"), Double.parseDouble(knob.getAttributeValue("value")));
            }
            Map<String, UnitPort> jacks = new HashMap<>();
            switch (type) {
            case "VCOA": {
                AVoltageControlledOscillatorA vco = new AVoltageControlledOscillatorA(synth);
                vco.setOctave(knobs.getOrDefault("octave", 10.0).intValue());
                vco.setFreqFine(knobs.getOrDefault("freqfine", 0.0));
                jacks.put("lineInFM", vco.getFreqModIN());
                jacks.put("lineOutSine", vco.getSortieSin());
                jacks.put("lineOutSquare", vco.getSortieSqu());
                jacks.put("lineOutTrinagular", vco.getSortieTri());
                jacks.put("lineOutSawtooth", vco.getSortieSaw());
                break;
            }
            case "SEQ": {
                ASequencer sequencer = new ASequencer(synth);
                for (int step = 1; step <= 8; step++) {
                    sequencer.setPitch(step - 1, knobs.getOrDefault(String.valueOf(step), 0.0));
                }
                jacks.put("gate", sequencer.getGatePort());
                jacks.put("output", sequencer.getOutputPort());
                break;
            }
            case "EG": {
                AEnvelopeGenerator envelope = new AEnvelopeGenerator(synth);
                // Same order as the XML, like the app's loader
                for (Element knob : module.getChildren("attenuation")) {
                    double value = Double.parseDouble(knob.getAttributeValue("value"));
                    switch (knob.getAttributeValue("ident")) {
                    case "0": envelope.setAttack(value); break;
                    case "1": envelope.setDecay(value); break;
                    case "2": envelope.setSustain(value / 100); break;
                    case "3": envelope.setRelease(value); break;
                    default: break;
                    }
                }
                jacks.put("gate", envelope.getGatePort());
                jacks.put("output", envelope.getOutputPort());
                break;
            }
            case "VCA": {
                AVoltageControlledAmplifier vca = new AVoltageControlledAmplifier(synth);
                vca.setAmplification(knobs.getOrDefault("amplif", 0.0));
                jacks.put("amIn", vca.getInputAmpModul());
                jacks.put("signIn", vca.getInputPort());
                jacks.put("signOut", vca.getOutputPort());
                break;
            }
            case "VCFLP": {
                AVoltageControlledFilterLowPass filter = new AVoltageControlledFilterLowPass(synth);
                filter.setCutoffFrequency(knobs.getOrDefault("freq", 1000.0));
                filter.setResonance(knobs.getOrDefault("reso", 1.0));
                jacks.put("input", filter.getInputPort());
                jacks.put("freqModInput", filter.getFreqModInputPort());
                jacks.put("output", filter.getOutputPort());
                break;
            }
            case "VCFHP": {
                AVoltageControlledFilterHighPass filter = new AVoltageControlledFilterHighPass(synth);
                filter.setCutoffFrequency(knobs.getOrDefault("freq", 1000.0));
                jacks.put("input", filter.getInputPort());
                jacks.put("freqModInput", filter.getFreqModInputPort());
                jacks.put("output", filter.getOutputPort());
                break;
            }
            case "MIX": {
                AMixer mixer = new AMixer(synth);
                List<Element> mixerKnobs = module.getChildren("attenuation");
                if (mixerKnobs.size() != 4) {
                    throw new IllegalStateException(prefix + "MIX needs exactly 4 attenuations");
                }
                for (int input = 1; input <= 4; input++) {
                    mixer.setAttenuation(input, Double.parseDouble(mixerKnobs.get(input - 1).getAttributeValue("value")));
                    jacks.put("input" + input, mixer.getInputPort(input));
                }
                jacks.put("output", mixer.getOutputPort());
                break;
            }
            case "REP": {
                AReplicator replicator = new AReplicator(synth);
                jacks.put("input", replicator.getInputPort());
                jacks.put("output1", replicator.getOutput1Port());
                jacks.put("output2", replicator.getOutput2Port());
                jacks.put("output3", replicator.getOutput3Port());
                break;
            }
            case "WN": {
                AWhiteNoise noise = new AWhiteNoise(synth);
                noise.setEnable(true);
                jacks.put("output", noise.getOutputPort());
                break;
            }
            case "SCOP": {
                AOscilloscope scope = new AOscilloscope(synth);
                jacks.put("input", scope.getInputPort());
                jacks.put("output", scope.getOutputPort());
                break;
            }
            case "OUT": {
                if (speaker != null) {
                    throw new IllegalStateException("only one OUT is rendered");
                }
                speaker = new AttenuationFilter();
                synth.add(speaker);
                speaker.setAttenuation(Util.decibelsToVoltage(knobs.getOrDefault("0", 0.0)));
                jacks.put("lineIn", speaker.input);
                if ("true".equals(module.getAttributeValue("isMute"))) {
                    System.out.println("WARNING: OUT is muted");
                }
                break;
            }
            default:
                throw new IllegalStateException("module type not handled: " + type);
            }
            for (Element jack : module.getChildren("jack")) {
                String ident = jack.getAttributeValue("ident");
                String name = ident.substring(ident.indexOf('-') + 1);
                if (!ident.startsWith(prefix) || (!type.equals("WN") && !jacks.containsKey(name))) {
                    throw new IllegalStateException("bad jack ident " + ident + " on " + type);
                }
                ports.put(ident, type.equals("WN") ? jacks.get("output") : jacks.get(name));
            }
        }

        int cableCount = 0;
        for (Element cable : root.getChild("liste_cables").getChildren("cable")) {
            UnitPort first = ports.get(cable.getAttributeValue("port1"));
            UnitPort second = ports.get(cable.getAttributeValue("port2"));
            if (first == null || second == null) {
                throw new IllegalStateException("cable end not declared: "
                        + cable.getAttributeValue("port1") + " / " + cable.getAttributeValue("port2"));
            }
            UnitOutputPort output = (UnitOutputPort) (first instanceof UnitOutputPort ? first : second);
            UnitPort input = first instanceof UnitOutputPort ? second : first;
            if (!(input instanceof UnitInputPort)) {
                throw new IllegalStateException("cable joins two outputs: " + cable.getAttributeValue("port1"));
            }
            output.connect((UnitInputPort) input);
            cableCount++;
        }
        if (speaker == null) {
            throw new IllegalStateException("no OUT module");
        }

        WaveRecorder recorder = new WaveRecorder(synth, new File(args[1]), 1);
        speaker.output.connect(recorder.getInput());
        synth.start(44100, AudioDeviceManager.USE_DEFAULT_DEVICE, 0, AudioDeviceManager.USE_DEFAULT_DEVICE, 0);
        recorder.start();
        synth.sleepFor(seconds);
        recorder.stop();
        recorder.close();
        System.out.println("rendered " + args[0] + ": " + cableCount + " cables, " + seconds + "s");
        // The JSyn engine does not always shut down cleanly in non-real-time mode
        System.exit(0);
    }
}
