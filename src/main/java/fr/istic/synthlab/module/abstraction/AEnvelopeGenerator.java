package fr.istic.synthlab.module.abstraction;

import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.EnvelopeDAHDSR;

/**
 * A class to represent the Envelope Generator module
 * 
 * @author valentinmumble
 * 
 */
public class AEnvelopeGenerator implements IAEnvelopeGenerator {

    public static final double ATTACK_MIN_S = 0.001;
    public static final double ATTACK_MAX_S = 5;
    public static final double ATTACK_DEF_S = 0.3;
    public static final double DECAY_MIN_S = 0.001;
    public static final double DECAY_MAX_S = 5;
    public static final double DECAY_DEF_S = 0.3;
    public static final double SUSTAIN_MIN_LEVEL = 0;
    public static final double SUSTAIN_MAX_LEVEL = 1;
    public static final double SUSTAIN_DEF_LEVEL = 0.7;
    public static final double RELEASE_MIN_S = 0.01;
    public static final double RELEASE_MAX_S = 20;
    public static final double RELEASE_DEF_S = 0.3;

    private EnvelopeDAHDSR adsr;
    private Synthesizer synth;

    public AEnvelopeGenerator(Synthesizer syn) {
        synth = syn;
        adsr = new EnvelopeDAHDSR();
        synth.add(adsr);
        
        adsr.sustain.set(SUSTAIN_DEF_LEVEL);
        
        setAttack(ATTACK_DEF_S);
        setDecay(DECAY_DEF_S);
        setRelease(RELEASE_DEF_S);
                
    }

    @Override
    public UnitInputPort getGatePort() {
        return adsr.input;
    }

    @Override
    public UnitOutputPort getOutputPort() {
        return adsr.output;
    }

    @Override
    public double getAttack() {
        return adsr.attack.get();
    }

    @Override
    public double getDecay() {
        double decay;
        double decayAttenuation;
        
        if(adsr.sustain.get() != 0){
            decayAttenuation = 20 * Math.log10(adsr.sustain.get());
            decay = adsr.decay.get() * decayAttenuation / -90;
        }else{
            System.err.println("ERROR in 'getDecay', sustain is not defined !!!");
            decay = adsr.decay.get();
        } 

        return decay;
    }

    @Override
    public double getSustain() {
        return adsr.sustain.get();
    }

    @Override
    public double getRelease() {
        return adsr.release.get() / 1.5;
    }

    @Override
    public void setAttack(double a) {
        a = Math.min(ATTACK_MAX_S, Math.max(ATTACK_MIN_S, a));
        adsr.attack.set(a);
    }

    @Override
    public void setDecay(double d) {
        double decay;
        double decayAttenuation;
        
        decay = Math.min(DECAY_MAX_S, Math.max(DECAY_MIN_S, d));
        if(adsr.sustain.get() != 0){
            decayAttenuation = 20 * Math.log10(adsr.sustain.get());
            adsr.decay.set(-90 * decay / decayAttenuation);
        }else{
            System.err.println("ERROR in 'setDecay', sustain is not defined !!!");
            adsr.decay.set(d);
        } 
    }

    @Override
    public void setSustain(double s) {
        s = Math.min(SUSTAIN_MAX_LEVEL, Math.max(SUSTAIN_MIN_LEVEL, s));
        adsr.sustain.set(s);
    }

    @Override
    public void setRelease(double r) {
        
        double release;
        //double releaseAttenuation;
        
        release = Math.min(RELEASE_MAX_S, Math.max(RELEASE_MIN_S, r));
        
//        if(adsr.sustain.get() != 0){
//            releaseAttenuation = 20 * Math.log10(adsr.sustain.get());
//            release = (-60-releaseAttenuation) / 90;
//        }else{
//            System.err.println("ERROR in 'getDecay', sustain is not defined !!!");
//        } 
        
        adsr.release.set(release * 1.5);
    }

}
