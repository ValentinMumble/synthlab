import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import fr.istic.synthlab.Synthlab;
import fr.istic.synthlab.global.control.CGLOB;
import fr.istic.synthlab.save.control.CLoad;

/**
 * Starts Synthlab, loads a bundled sample montage and saves the window as a PNG.
 *
 * Usage: java -cp target/synthlab-0.0.1-SNAPSHOT.jar:target/tools Screenshot AcidArp.xml out.png [width height]
 */
public class Screenshot {
    public static void main(String[] args) throws Exception {
        Synthlab.main(new String[0]);
        JFrame frame = (JFrame) CGLOB.getInstance().getPresentation();
        SwingUtilities.invokeAndWait(() -> {
            if (args.length >= 4) {
                frame.setSize(Integer.parseInt(args[2]), Integer.parseInt(args[3]));
            }
            new CLoad().loadSample(args[0]);
        });
        // Let the scopes draw a few frames
        Thread.sleep(2500);
        SwingUtilities.invokeAndWait(() -> {
            BufferedImage image = new BufferedImage(frame.getRootPane().getWidth(),
                    frame.getRootPane().getHeight(), BufferedImage.TYPE_INT_RGB);
            frame.getRootPane().paint(image.getGraphics());
            try {
                ImageIO.write(image, "png", new File(args[1]));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        System.exit(0);
    }
}
