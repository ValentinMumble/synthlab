"""Print peak, clipping and a loudness timeline for a mono 16-bit WAV."""
import math
import struct
import sys
import wave

with wave.open(sys.argv[1]) as wav:
    rate = wav.getframerate()
    samples = [value / 32768 for (value,) in struct.iter_unpack('<h', wav.readframes(wav.getnframes()))]
peak = max(abs(value) for value in samples)
clipped = sum(1 for value in samples if abs(value) > 0.999)
window = int(rate * float(sys.argv[2]) if len(sys.argv) > 2 else rate * 0.05)
levels = []
for start in range(0, len(samples) - window, window):
    chunk = samples[start:start + window]
    rms = math.sqrt(sum(value * value for value in chunk) / window)
    levels.append(20 * math.log10(rms) if rms > 0 else -120)
print(f"{len(samples) / rate:.1f}s  peak {peak:.3f}  clipped {clipped}  loudest {max(levels):.0f} dB  quietest {min(levels[10:]):.0f} dB")
print(" ".join(f"{level:.0f}" for level in levels))
