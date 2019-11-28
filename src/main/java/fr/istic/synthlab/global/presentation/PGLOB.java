package fr.istic.synthlab.global.presentation;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import fr.istic.synthlab.cable.control.ICCable;
import fr.istic.synthlab.cable.presentation.IPCable;
import fr.istic.synthlab.cable.presentation.PCable;
import fr.istic.synthlab.global.control.ICGLOB;
import fr.istic.synthlab.global.presentation.listeners.ListenerAbout;
import fr.istic.synthlab.global.presentation.listeners.ListenerAdd;
import fr.istic.synthlab.global.presentation.listeners.ListenerColor;
import fr.istic.synthlab.global.presentation.listeners.ListenerExit;
import fr.istic.synthlab.global.presentation.listeners.ListenerLoad;
import fr.istic.synthlab.global.presentation.listeners.ListenerNew;
import fr.istic.synthlab.global.presentation.listeners.ListenerRemove;
import fr.istic.synthlab.global.presentation.listeners.ListenerSave;
import fr.istic.synthlab.global.presentation.listeners.ListenerSkinChanger;
import fr.istic.synthlab.util.ColorLabel;
import fr.istic.synthlab.util.Icons;
import fr.istic.synthlab.util.Template;
import fr.istic.synthlab.util.Util;

/**
 * Presentation : global JFrame which contains a left panel (PanelImport) for
 * the importable modules and a right panel to display the current modules
 * already placed. Drag&Drop modules from left to right to place and use them.
 *
 * @author Jonathan
 * @version 1.1
 */
public class PGLOB extends JFrame implements IPGLOB {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -1615067105970215230L;

    /**
     * Control of GLOB.
     */
    private ICGLOB control;

    /**
     * ScrollPane.
     */
    private JScrollPane scrollPane;

    /**
     * MouseListener for Cables.
     */

    private AWTEventListener mouseListener;

    /**
     * LayeredPane.
     */
    private JLayeredPane layeredPane;

    /**
     * topPane.
     */
    private JLayeredPane topPane;

    /**
     * Boolean for management of CloseMenu.
     */
    private boolean cancel;

    /**
     * The current skin (template).
     */
    private static Template template;

    /**
     * Boolean for management of the save.
     */
    private boolean saved;

    /**
     * Color of the future cable.
     */
    private Color color;

    /**
     * Display of the color of the future cable.
     */
    private ColorLabel cableColorLabel;

    /**
     * List of Color Buttons.
     */
    private List<ColorLabel> listColorButton;
    
    /**
     * List of Color.
     */
    private List<Color> listColor;

    /**
     * Color panel.
     */
    private JPanel colorPanel;

    /**
     * Size of the list of color buttons.
     */
    private final static int SIZE_LIST_COLOR_BUTTON = 5;

    /**
     * Constructor of GLOB Presentation.
     *
     * @param cGLOB
     *            Control of GLOB.
     */
    public PGLOB(ICGLOB cGLOB) {
        this.control = cGLOB;
        setTemplate(Template.BLUE);
        new PSplashScreen();
    }

    /**
     * Called when we want to close.
     */
    @Override
    public void closeDialog() {
        if (!saved) {
            control.destroyCurrentCable();
            int choix = JOptionPane.showConfirmDialog(this,
                    "Do you want to save your configuration?", "Exit",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, Icons.SAVE);
            switch (choix) {
            case 0:
                cancel = false;
                if (!control.enregistrer()) {
                    closeDialog();
                }
                if (!cancel) {
                    System.exit(0);
                }
                break;
            case 1:
                System.exit(0);
                break;
            case 2:
                cancel = true;
                break;
            default:
                // Ne rien faire
            }
        } else {
            System.exit(0);
        }
    }

    /**
     * Called whenever a modification is made.
     *
     * @param value
     *            value of saved to set.
     */
    public void setSaved(boolean value) {
        saved = value;
    }

    /**
     * @return saved value.
     */
    public boolean getSaved() {
        return saved;
    }

    /**
     * Initialize the global JFrame.
     */
    public void init() {
        saved = false;
        PPanelContent panelContent = this.control.getCPanelContent()
                .getPresentation();
        PPanelImport panelImport = this.control.getCPanelImport()
                .getPresentation();

        cancel = false;

        color = Color.BLACK;

        // 80% de la taille de l'écran
        this.setSize(1180, 744);
        this.setPreferredSize(this.getSize());

        JPanel panelLeft = new JPanel();
        panelLeft.setOpaque(false);
        panelLeft.setLayout(new BorderLayout());
        
        // Panel qui contient les composants importable
        panelLeft.add(panelImport, BorderLayout.CENTER);

        // Cable colors {
        colorPanel = new JPanel(new BorderLayout());
        colorPanel.setOpaque(false);
        colorPanel.setBorder(BorderFactory.createMatteBorder(3, 0, 0, 1,
                Color.BLACK));
        JPanel currentColor = new JPanel();
        currentColor.setOpaque(false);
        JPanel recentColors = new JPanel();
        recentColors.setOpaque(false);
        JLabel text = new JLabel("CABLE COLOR");
        text.setHorizontalAlignment(JLabel.CENTER);
        text.setForeground(Color.WHITE);
        text.setFont(new Font("SansSerif", Font.BOLD, 11));
        currentColor.add(text);
        JPanel currentCablePanel = new JPanel(null);
        currentCablePanel.setOpaque(false);
        JLabel jack1 = new JLabel(Icons.CONNECTOR_JACK_FEMALE);
        JLabel jack2 = new JLabel(Icons.CONNECTOR_JACK_FEMALE);
        int jackSide = Icons.CONNECTOR_JACK_FEMALE.getIconWidth();
        cableColorLabel = new ColorLabel(color, new Dimension(82, jackSide));
        cableColorLabel.setIncurved(true);
        jack1.setSize(jackSide, jackSide);
        jack2.setSize(jackSide, jackSide);
        jack2.setLocation(cableColorLabel.getWidth() - jackSide, 0);
        cableColorLabel.setA(new Point(jack1.getX() + jackSide / 2, jack1
                .getY() + jackSide / 2));
        cableColorLabel.setB(new Point(jack2.getX() + jackSide / 2, jack2
                .getY() + jackSide / 2));
        currentCablePanel.setSize(cableColorLabel.getWidth() + jackSide,
                jackSide);
        currentCablePanel.setPreferredSize(currentCablePanel.getSize());
        currentCablePanel.add(cableColorLabel);
        currentCablePanel.add(jack1);
        currentCablePanel.add(jack2);
        currentColor.add(currentCablePanel);
        currentColor.addMouseListener(new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {
                chooseCableColor();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });
        colorPanel.add(currentColor, BorderLayout.NORTH);
        colorPanel.add(recentColors, BorderLayout.SOUTH);
        panelLeft.add(colorPanel, BorderLayout.SOUTH);

        listColor = new ArrayList<Color>(SIZE_LIST_COLOR_BUTTON);
        listColor.add(new Color(51, 51, 51));
        listColor.add(new Color(0, 51, 102));
        listColor.add(new Color(153, 0, 0));
        listColor.add(new Color(0, 102, 0));
        listColor.add(new Color(255, 204, 51));

        listColorButton = new ArrayList<ColorLabel>(SIZE_LIST_COLOR_BUTTON);
        for (int i = 0; i < SIZE_LIST_COLOR_BUTTON; i++) {
            Color color = listColor.get(i);
            ColorLabel colorButton = new ColorLabel(Color.BLACK);
            if (color != null) {
                colorButton  = new ColorLabel(color);
            }
            colorButton.addMouseListener(new ListenerColor(this, colorButton));
            listColorButton.add(colorButton);
            recentColors.add(colorButton);
        }

        layeredPane = new JLayeredPane();
        JPanel main = new JPanel(new GridLayout());
        main.add(layeredPane);

        scrollPane = new JScrollPane(main,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(50);
        scrollPane.getVerticalScrollBar().setBlockIncrement(20);

        scrollPane.setBorder(null);

        // Panel qui contient les composants mis en place
        panelContent.setOpaque(true);

        layeredPane.add(panelContent, 0, new Integer(0));

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                closeDialog();
            }
        });

        this.setResizable(false);

        this.setJMenuBar(createMenuBar());
        this.setLayout(new GridLayout());

        topPane = new JLayeredPane();

        topPane.setSize(this.getWidth() - 5, this.getHeight() - 60);
        // => C'est moche mais si quelqu'un trouve comment faire autrement...
        // c'est du pifomètre

        topPane.setPreferredSize(topPane.getSize());

        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(new Color(60, 60, 65));

        container.setSize(topPane.getWidth(), topPane.getHeight());
        container.setPreferredSize(container.getSize());
        container.add(panelLeft, BorderLayout.WEST);
        container.add(scrollPane, BorderLayout.CENTER);
        topPane.add(container);
        this.add(topPane);

        control.initPanelContent();

        this.setLocationRelativeTo(null);

        // Panel de gauche à 20% de la JFrame GLOB
        panelImport.setSize(248, this.getHeight());
        panelImport.setPreferredSize(panelImport.getSize());

        // Panel de droite à 80% de la JFrame GLOB
        layeredPane.setSize(927, control.getCPanelContent().getGrille().size()
                * Util.SLOT_HEIGHT
                // Pour adapter le layeredPane en fonction du nombre de ligne
                + control.getCPanelContent().getGrille().size() + 2);
        layeredPane.setPreferredSize(layeredPane.getSize());

        panelContent.setSize(layeredPane.getSize());
        panelContent.setPreferredSize(panelContent.getSize());

        revalidate();

        this.setVisible(true);
    }

    /**
     * Resize the different component to fit in the JFrame.
     */
    @Override
    public void myResize() {

        PPanelContent panelContent = control.getCPanelContent()
                .getPresentation();

        layeredPane.setSize(layeredPane.getWidth(), control.getCPanelContent()
                .getGrille().size()
                * Util.SLOT_HEIGHT
                // Pour adapter le layeredPane en fonction du nombre de ligne
                + control.getCPanelContent().getGrille().size() + 2);
        layeredPane.setPreferredSize(layeredPane.getSize());

        panelContent.setSize(layeredPane.getWidth(), layeredPane.getHeight());
        panelContent.setPreferredSize(panelContent.getSize());

        control.getCPanelContent().myResize();

        revalidate();
    }

    /**
     * Create the menu bar.
     *
     * @return the JMenuBar created.
     */
    private JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu mainMenu;
        JMenu gridMenu;
        JMenu windowMenu;
        JMenu aboutMenu;
        JMenuItem menuItem;

        // Create the menu bar.
        menuBar = new JMenuBar();
        menuBar.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Build the main menu.
        mainMenu = new JMenu("File");
        mainMenu.setMnemonic(KeyEvent.VK_M);
        mainMenu.getAccessibleContext().setAccessibleDescription("Main menu");
        menuBar.add(mainMenu);

        // Build the grid menu.
        gridMenu = new JMenu("Rack");
        gridMenu.setMnemonic(KeyEvent.VK_G);
        gridMenu.getAccessibleContext().setAccessibleDescription("Rack menu");
        menuBar.add(gridMenu);

        // Button Add a rack.
        menuItem = new JMenuItem("Reset racks", KeyEvent.VK_N);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_5,
                ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Make a new empty grid");
        menuItem.addActionListener(new ListenerNew());
        gridMenu.add(menuItem);

        // Button Add a rack.
        menuItem = new JMenuItem("Add a rack", KeyEvent.VK_A);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_8,
                ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Add a rack");
        menuItem.addActionListener(new ListenerAdd(control.getCPanelContent()
                .getPresentation(), this));
        gridMenu.add(menuItem);

        // Button remove a rack.
        menuItem = new JMenuItem("Remove a rack", KeyEvent.VK_R);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4,
                ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Remove a rack");
        menuItem.addActionListener(new ListenerRemove());
        gridMenu.add(menuItem);

        // Button Save.
        menuItem = new JMenuItem("Save", KeyEvent.VK_S);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_7,
                ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Save the grid");
        menuItem.addActionListener(new ListenerSave());
        mainMenu.add(menuItem);

        // Button Load.
        menuItem = new JMenuItem("Load", KeyEvent.VK_L);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_6,
                ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Load a rack");
        menuItem.addActionListener(new ListenerLoad());
        mainMenu.add(menuItem);
        // Button exit.
        menuItem = new JMenuItem("Exit", KeyEvent.VK_Q);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_9,
                ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Exit the application");
        menuItem.addActionListener(new ListenerExit(this));
        mainMenu.add(menuItem);
        
        // Build the window menu.
        windowMenu = new JMenu("Window");
        windowMenu.setMnemonic(KeyEvent.VK_W);
        windowMenu.getAccessibleContext().setAccessibleDescription("Window menu");
        menuBar.add(windowMenu);
        
        // Button load black skin.
        menuItem = new JMenuItem("Load black skin");
        menuItem.getAccessibleContext().setAccessibleDescription("Load black skin");
        menuItem.addActionListener(new ListenerSkinChanger(Template.BLACK));
        windowMenu.add(menuItem);
        
        // Button load blue skin.
        menuItem = new JMenuItem("Load blue skin");
        menuItem.getAccessibleContext().setAccessibleDescription("Load blue skin");
        menuItem.addActionListener(new ListenerSkinChanger(Template.BLUE));
        windowMenu.add(menuItem);
        
        // Button load wood skin.
        menuItem = new JMenuItem("Load wood skin");
        menuItem.getAccessibleContext().setAccessibleDescription("Load wood skin");
        menuItem.addActionListener(new ListenerSkinChanger(Template.WOOD));
        windowMenu.add(menuItem);
        
        // Button load leather skin.
        menuItem = new JMenuItem("Load leather skin");
        menuItem.getAccessibleContext().setAccessibleDescription("Load leather skin");
        menuItem.addActionListener(new ListenerSkinChanger(Template.LEATHER));
        windowMenu.add(menuItem);
        
        // Button load leather skin.
        menuItem = new JMenuItem("Load metal skin");
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Load metal skin");
        menuItem.addActionListener(new ListenerSkinChanger(Template.METAL));
        windowMenu.add(menuItem);

        // Build the about menu.
        aboutMenu = new JMenu("About");
        aboutMenu.setMnemonic(KeyEvent.VK_H);
        aboutMenu.getAccessibleContext().setAccessibleDescription("Help section");
        menuBar.add(aboutMenu);
        
        // Button Add a rack.
        menuItem = new JMenuItem("Development team", KeyEvent.VK_A);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3,
                ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Development team");
        menuItem.addActionListener(new ListenerAbout());
        aboutMenu.add(menuItem);

        return menuBar;
    }

    /**
     * Open a Color chooser for the user to select a Cable Color.
     */
    private void chooseCableColor() {
        Color newColor = JColorChooser.showDialog((JFrame) getParent(),
                "Choose Cable Color", Color.BLACK);
        if (newColor != null) {
            setCableColor(newColor);
            for (int i = SIZE_LIST_COLOR_BUTTON - 1; i > 0; i--) {
                listColorButton.get(i).setColor(
                        listColorButton.get(i - 1).getColor());
            }
            listColorButton.get(0).setColor(newColor);
            colorPanel.repaint();
        }
    }

    /**
     * Getter for the panel Cable.
     *
     * @return layeredPane.
     */
    @Override
    public JLayeredPane getLayeredPane() {
        return layeredPane;
    }

    /**
     * @return Pane used to draw the dragged modules.
     */
    @Override
    public JLayeredPane getTopPane() {
        return topPane;
    }

    /**
     * Listener for clic to update position of the current cable.
     */
    @Override
    public void addMouseListener() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        mouseListener = new AWTEventListener() {
            @Override
            public void eventDispatched(AWTEvent event) {
                if (event instanceof MouseEvent
                        && SwingUtilities
                                .isDescendingFrom(
                                        ((MouseEvent) event).getComponent(),
                                        scrollPane)) {
                    // Cancel creation of the cable on right clic
                    if (SwingUtilities.isRightMouseButton((MouseEvent) event)) {
                        control.destroyCurrentCable();
                    } else if (layeredPane.getMousePosition() != null) {
                        control.getcurrentCable().getPresentation()
                                .updatePos(layeredPane.getMousePosition());
                    }
                }
            }
        };
        kit.addAWTEventListener(mouseListener, AWTEvent.MOUSE_MOTION_EVENT_MASK);
    }

    /**
     * Remove the listener for clic to update position of the current cable.
     */
    @Override
    public void removeMouseListener() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        kit.removeAWTEventListener(mouseListener);
        mouseListener = null;
    }

    /**
     * add the cable to panelCable.
     *
     * @param pcable
     *            Cable Presentation to add.
     */
    @Override
    public void addCable(PCable pcable) {
        layeredPane.add(pcable, 1, 0);
        pcable.setOpaque(false);
        pcable.setBounds(0, 0, layeredPane.getWidth(), layeredPane.getHeight());
        saved = false;
    }

    /**
     * remove the cable from panelCable.
     *
     * @param pcable
     *            Cable Presentation to remove.
     */
    @Override
    public void removeCablePresentation(IPCable pcable) {
        layeredPane.remove((PCable) pcable);
        revalidate();
        repaint();
        saved = false;
    }

    /**
     * Function to update the Presentation of cables.
     */
    @Override
    public void updatePresentationCables() {
        List<ICCable> listCCables = control.getListCables();
        if (listCCables != null) {
            for (ICCable cable : listCCables) {
                cable.getPresentation().updateAndValidate();
            }
        }
    }

    /**
     * Setter of the color of the cable.
     *
     * @param c
     *            color to set.
     */
    public void setCableColor(Color c) {
        color = c;
        cableColorLabel.setColor(color);
        cableColorLabel.repaint();
    }

    /**
     * get the color for the future cable.
     *
     * @return the color for the future cable
     */
    @Override
    public Color getCableColor() {
        return color;
    }

    /**
     * get the template of the application.
     *
     * @return The current template in use.
     */
    public static Template getTemplate() {
        return PGLOB.template;
    }

    /**
     * Set the template of the application.
     *
     * @param newTemplate
     *            template to set.
     */
    public static void setTemplate(Template newTemplate) {
        PGLOB.template = newTemplate;
    }
}
