package fr.istic.synthlab.module.presentation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.EnumMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import fr.istic.synthlab.module.abstraction.IAKeyboard.Note;
import fr.istic.synthlab.module.control.ICKeyboard;
import fr.istic.synthlab.module.control.ICModule;
import fr.istic.synthlab.module.presentation.component.BlackKey;
import fr.istic.synthlab.module.presentation.component.Bloc;
import fr.istic.synthlab.module.presentation.component.Key;
import fr.istic.synthlab.module.presentation.component.LcdBloc;
import fr.istic.synthlab.module.presentation.component.TitledBlocGroup;
import fr.istic.synthlab.module.presentation.component.WhiteKey;
import fr.istic.synthlab.util.presentation.IPFemaleJack;

/**
 * The presentation of the Keyboard module
 * 
 * @author valentinmumble
 * 
 */
public class PKeyboard extends PModule implements IPKeyboard {

    /**
     * UID to serialize this object.
     */
    private static final long serialVersionUID = 3042378072066617257L;

    /**
     * The Keyboard controller
     */
    private ICKeyboard control;

    /**
     * The presentation of the gate output port
     */
    private IPFemaleJack gate;

    /**
     * The presentation of the voltage control port
     */
    private IPFemaleJack voltageControl;

    private Map<Note, Key> keyMap;

    private JButton increaseOctaveButton;

    private JButton decreaseOctaveButton;

    /**
     * LCD.
     */
    private LcdBloc octaveDisplay;

    public PKeyboard(ICKeyboard c) {
        control = c;

        gate = control.getGate().getPresentation();
        voltageControl = control.getControlVoltage().getPresentation();

        initKeyMap();

        this.setLayout(new GridBagLayout());

        GridBagConstraints gbcKeys = new GridBagConstraints(0, 1,
                GridBagConstraints.REMAINDER, 1, 0, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, gbcInsets,
                0, 0);
        GridBagConstraints gbcOctaveChanger = new GridBagConstraints(0, 2, 1,
                GridBagConstraints.RELATIVE, 0.5, 0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, gbcInsets, 0, 0);
        GridBagConstraints gbcCv = new GridBagConstraints(1, 2,
                GridBagConstraints.RELATIVE, GridBagConstraints.RELATIVE, 4, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, gbcInsets,
                0, 0);
        GridBagConstraints gbcGate = new GridBagConstraints(2, 2,
                GridBagConstraints.REMAINDER, GridBagConstraints.RELATIVE, 4,
                0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                gbcInsets, 0, 0);

        this.add(createModuleTitle(IPKeyboard.TITLE), gbcModuleTitle);
        this.add(buildKeys(), gbcKeys);
        this.add(buildOctaveChanger(), gbcOctaveChanger);
        this.add(buildSimpleJackBloc("CV", voltageControl), gbcCv);
        this.add(buildSimpleJackBloc("GATE", gate), gbcGate);

        this.add(this.createModuleBottom(3), gbcBottom);

        // Get the key events from the global frame
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(new KeyEventDispatcher() {
                    @Override
                    public boolean dispatchKeyEvent(final KeyEvent e) {
                        if (e.getID() == KeyEvent.KEY_PRESSED) {
                            handleKeyPressed(e.getKeyChar());
                        } else if (e.getID() == KeyEvent.KEY_RELEASED) {
                            handleKeyReleased();
                        }
                        // Pass the KeyEvent to the next KeyEventDispatcher in
                        // the chain
                        return false;
                    }

                });
    }

    private void initKeyMap() {
        keyMap = new EnumMap<>(Note.class);
        int i = 0;
        for (Note note : Note.values()) {
            Key key = null;
            if (i < 8) {
                key = new WhiteKey();
            } else {
                key = new BlackKey();
            }
            key.addMouseListener(new NoteKeyListener(note));
            keyMap.put(note, key);
            i++;
        }
    }

    private JLayeredPane buildKeys() {
        JLayeredPane main = new JLayeredPane();
        JPanel whitePanel = new JPanel(new GridLayout());
        whitePanel.setSize(Key.KEY_WIDTH * 8, Key.KEY_HEIGHT);
        JPanel blackPanel = new JPanel(null);
        blackPanel.setOpaque(false);
        blackPanel.setSize(whitePanel.getSize());
        for (Key key : keyMap.values()) {
            if (key instanceof WhiteKey) {
                whitePanel.add(key);
            } else {
                blackPanel.add(key);
            }
        }
        keyMap.get(Note.DoD).setLocation(
                Key.KEY_WIDTH - BlackKey.BLACK_KEY_WIDTH / 2, 0);
        keyMap.get(Note.ReD).setLocation(
                2 * Key.KEY_WIDTH - BlackKey.BLACK_KEY_WIDTH / 2, 0);
        keyMap.get(Note.FaD).setLocation(
                4 * Key.KEY_WIDTH - BlackKey.BLACK_KEY_WIDTH / 2, 0);
        keyMap.get(Note.SolD).setLocation(
                5 * Key.KEY_WIDTH - BlackKey.BLACK_KEY_WIDTH / 2, 0);
        keyMap.get(Note.LaD).setLocation(
                6 * Key.KEY_WIDTH - BlackKey.BLACK_KEY_WIDTH / 2, 0);
        main.add(whitePanel, 0, 0);
        main.add(blackPanel, 1, 0);
        return main;
    }

    private Bloc buildOctaveChanger() {
        Bloc octaveBloc = new TitledBlocGroup("OCTAVE");

        decreaseOctaveButton = new JButton("-");
        decreaseOctaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.octaveDecreased();
            }
        });

        this.octaveDisplay = new LcdBloc();
        this.octaveDisplay.setDecimalFormat(1, 1, 0, 0);
        this.octaveDisplay.setLcdValue(control.getOctave());

        increaseOctaveButton = new JButton("+");
        increaseOctaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.octaveIncreased();
            }
        });

        int height = decreaseOctaveButton.getPreferredSize().height;
        decreaseOctaveButton.setSize(height*2, height);
        decreaseOctaveButton.setPreferredSize(decreaseOctaveButton.getSize());
        increaseOctaveButton.setSize(height*2, height);
        increaseOctaveButton.setPreferredSize(increaseOctaveButton.getSize());
        octaveBloc.add(decreaseOctaveButton);
        octaveBloc.add(this.octaveDisplay);
        octaveBloc.add(increaseOctaveButton);

        return octaveBloc;
    }

    @Override
    public ICModule getController() {
        return control;
    }

    @Override
    public int getWidthU() {
        return IPKeyboard.WIDTH_U;
    }

    @Override
    public void updateOctave() {
        octaveDisplay.setLcdValue(control.getOctave());
    }

    @Override
    public void showKeyPressed(Note note) {
        keyMap.get(note).press();
    }

    @Override
    public void showKeyReleased() {
        for (Key key : keyMap.values()) {
            key.release();
        }
    }

    private void handleKeyPressed(char keyChar) {
        Note pressed = null;
        char charPressed = Character.toUpperCase(keyChar);
        switch (charPressed) {
        case 'Q':
            pressed = Note.Do;
            break;
        case 'S':
            pressed = Note.Re;
            break;
        case 'D':
            pressed = Note.Mi;
            break;
        case 'F':
            pressed = Note.Fa;
            break;
        case 'G':
            pressed = Note.Sol;
            break;
        case 'H':
            pressed = Note.La;
            break;
        case 'J':
            pressed = Note.Si;
            break;
        case 'K':
            pressed = Note.Do2;
            break;
        case 'Z':
            pressed = Note.DoD;
            break;
        case 'E':
            pressed = Note.ReD;
            break;
        case 'T':
            pressed = Note.FaD;
            break;
        case 'Y':
            pressed = Note.SolD;
            break;
        case 'U':
            pressed = Note.LaD;
            break;
        case 'X':
            control.octaveIncreased();
            break;
        case 'W':
            control.octaveDecreased();
            break;
        default:
            break;
        }
        if (pressed != null) {
            control.keyPressed(pressed);
        }
    }

    private void handleKeyReleased() {
        control.keyReleased();
    }

    private class NoteKeyListener implements MouseListener {

        private Note note;

        public NoteKeyListener(Note note) {
            this.note = note;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            control.keyPressed(note);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            control.keyReleased();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}
