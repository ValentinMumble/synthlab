package fr.istic.synthlab.module.presentation.component;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.logging.Logger;

import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jsyn.swing.DoubleBoundedRangeModel;

import fr.istic.synthlab.module.abstraction.AMixer;

public class CustomDoubleBoundedTextField extends JTextField {

    private static final long serialVersionUID = 6882779668177620812L;
    private static final int MAX_INTEGER = 100;
    private static final int MAX_FRACTION = 100;
    private static final Logger LOGGER = Logger.getLogger(AMixer.class
            .getName());

    private static final Color COLOR_DIRTY = new Color(224, 0, 0, 50);

    private KeyAdapter keyAdapter;

    boolean modified = false;
    int numCharacters;
    private DoubleBoundedRangeModel model;
    private DecimalFormat decimalFormat;

    public CustomDoubleBoundedTextField(
            DoubleBoundedRangeModel paramDoubleBoundedRangeModel,
            DecimalFormat decimalFormat) {
        super(calcNbDigits(decimalFormat));
        this.decimalFormat = decimalFormat;
        this.numCharacters = CustomDoubleBoundedTextField
                .calcNbDigits(this.decimalFormat);
        this.model = paramDoubleBoundedRangeModel;
        this.setHorizontalAlignment(JTextField.CENTER);
        this.setOpaque(false);
        this.setBorder(null);
        this.setValue(this.model.getDoubleValue());
        this.setEditable(false);

//        addMouseListener(new MouseListener() {
//
//            @Override
//            public void mouseReleased(MouseEvent arg0) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void mousePressed(MouseEvent arg0) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void mouseExited(MouseEvent arg0) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent arg0) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void mouseClicked(MouseEvent arg0) {
//                enableKeyListener();
//            }
//
//            private void enableKeyListener() {
//                boolean exists = false;
//                for (KeyListener k : getKeyListeners()) {
//                    if (k.equals(keyAdapter)) {
//                        exists = true;
//                    }
//                }
//                if (!exists) {
//                    addKeyListener(keyAdapter);
//                }
//            }
//        });

        // addKeyListener(new KeyAdapter() {
        // public void keyTyped(KeyEvent paramAnonymousKeyEvent) {
        // if (paramAnonymousKeyEvent.getKeyChar() == '\n') {
        // try {
        // CustomDoubleBoundedTextField.this.model
        // .setDoubleValue(CustomDoubleBoundedTextField.this
        // .getValue());
        // } catch (NumberFormatException e) {
        // CustomDoubleBoundedTextField.this
        // .setValue(CustomDoubleBoundedTextField.this.model
        // .getDoubleValue());
        // LOGGER.info("The entered value is not a number");
        // }
        // } else {
        // CustomDoubleBoundedTextField.this.markDirty();
        // }
        // }
        // });

        this.keyAdapter = new KeyAdapter() {
            public void keyTyped(KeyEvent paramAnonymousKeyEvent) {
                if (paramAnonymousKeyEvent.getKeyChar() == '\n') {
                    removeKeyListener(keyAdapter);
                    try {
                        CustomDoubleBoundedTextField.this.model
                                .setDoubleValue(CustomDoubleBoundedTextField.this
                                        .getValue());
                        System.out.println("eeee");
                    } catch (NumberFormatException e) {
                        CustomDoubleBoundedTextField.this
                                .setValue(CustomDoubleBoundedTextField.this.model
                                        .getDoubleValue());
                        LOGGER.info("The entered value is not a number");
                    }
                } else {
                    CustomDoubleBoundedTextField.this.markDirty();
                }
            }

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == 102) {
                    removeKeyListener(keyAdapter);
                }
            }

        };
        // this.keyAdapter = new KeyAdapter() {
        // public void keyTyped(KeyEvent paramAnonymousKeyEvent) {
        // if (paramAnonymousKeyEvent.getKeyChar() == '\n') {
        // try {
        // CustomDoubleBoundedTextField.this.model
        // .setDoubleValue(CustomDoubleBoundedTextField.this
        // .getValue());
        //
        // } catch (NumberFormatException e) {
        // CustomDoubleBoundedTextField.this
        // .setValue(CustomDoubleBoundedTextField.this.model
        // .getDoubleValue());
        // LOGGER.info("The entered value is not a number");
        // }
        // } else {
        // CustomDoubleBoundedTextField.this.markDirty();
        // }
        // }
        // }

        this.model.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent paramAnonymousChangeEvent) {
                CustomDoubleBoundedTextField.this
                        .setValue(CustomDoubleBoundedTextField.this.model
                                .getDoubleValue());
            }
        });
    }

    private static int calcNbDigits(DecimalFormat decimalFormat) {
        int NbIntDigits = Math.min(
                Math.max(decimalFormat.getMaximumIntegerDigits(), 0),
                MAX_INTEGER);
        int NbFracDigits = Math.min(
                Math.max(decimalFormat.getMaximumFractionDigits(), 0),
                MAX_FRACTION);

        // if less than 3, create bug beacause of space taken by the "plus sign"
        // return Math.max(NbIntDigits + NbFracDigits, 3);
        return NbIntDigits + NbFracDigits;
    }

    private void markDirty() {
        this.modified = true;
        setBackground(COLOR_DIRTY);
        repaint();
    }

    private void markClean() {
        this.modified = false;
        setBackground(new Color(224, 0, 0));
        setCaretPosition(0);
        repaint();
    }

    public void setText(String paramString) {
        markDirty();
        super.setText(paramString);
    }

    private double getValue() throws NumberFormatException {
        double d = Double.valueOf(getText()).doubleValue();
        markClean();
        return d;
    }

    private void setValue(double paramDouble) {
        String formattedValue = this.decimalFormat.format(paramDouble);
        super.setText(formattedValue);
        markClean();
    }

    public void setDecimalFormat(DecimalFormat decimalFormat) {
        this.decimalFormat = decimalFormat;
        this.setColumns(calcNbDigits(this.decimalFormat));
        this.setValue(this.model.getDoubleValue());
    }

}
