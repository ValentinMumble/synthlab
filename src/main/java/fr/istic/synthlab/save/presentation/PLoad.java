package fr.istic.synthlab.save.presentation;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import fr.istic.synthlab.save.control.ICLoad;

/**
 * Implementation of the PLoad.
 *
 * @author Mickael
 * @version 1.0
 */
public class PLoad extends JDialog implements IPLoad {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1118999349721130669L;

    /**
     * Control of the Load.
     */
    private ICLoad cLoad;

    /**
     * The JFileChooser.
     */
    private JFileChooser chooser;
    /**
     * The JTextArea.
     */
    private JTextArea path;
    /**
     * JLabel for error.
     */
    private JLabel error;
    /**
     * Message.
     */
    private final String initSearch = "Select a file";

    /**
     * Constructor of the PLoad.
     *
     * @param control
     *            Control of the Load.
     */
    public PLoad(ICLoad control) {
        this.cLoad = control;
        this.setTitle("Load");
        this.setLayout(new GridBagLayout());
        this.init();
    }

    /**
     * That function initialize the Presentation of the Load.
     */
    private void init() {
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                dispose();
            }
        });
        this.setModal(true);
        this.setMinimumSize(new Dimension(500, 200));
        this.setSize(500, 200);
        this.setPreferredSize(this.getSize());
        setLocationRelativeTo(null);
        chooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "XML files", "xml");
        chooser.setFileFilter(filter);
        chooser.setMultiSelectionEnabled(false);
        chooser.setApproveButtonText("Select");
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        GridBagConstraints gbcError = new GridBagConstraints();
        gbcError.insets = new Insets(0, 0, 5, 0);
        gbcError.gridx = 0;
        gbcError.gridy = 0;
        gbcError.gridwidth = 3;

        error = new JLabel("");
        this.getContentPane().add(error, gbcError);

        GridBagConstraints gbcPath = new GridBagConstraints();
        gbcPath.insets = new Insets(0, 0, 0, 0);
        gbcPath.gridx = 0;
        gbcPath.gridy = 1;
        gbcPath.gridwidth = 3;

        path = new JTextArea(initSearch);
        path.setSize(380, 25);
        path.setPreferredSize(path.getSize());
        path.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        this.getContentPane().add(path, gbcPath);

        GridBagConstraints gbcParcourir = new GridBagConstraints();
        gbcParcourir.insets = new Insets(5, 0, 0, 10);
        gbcParcourir.gridx = 0;
        gbcParcourir.gridy = 2;

        JButton search = new JButton("Search");
        search.addActionListener(new MySearchActionListener());
        search.setSize(120, 25);
        search.setPreferredSize(search.getSize());
        this.getContentPane().add(search, gbcParcourir);

        GridBagConstraints gbcSave = new GridBagConstraints();
        gbcSave.insets = new Insets(5, 0, 1, 10);
        gbcSave.gridx = 1;
        gbcSave.gridy = 2;

        JButton save = new JButton("Load");
        save.addActionListener(new MyLoadActionListener());
        save.setSize(120, 25);
        save.setPreferredSize(search.getSize());
        this.getContentPane().add(save, gbcSave);

        GridBagConstraints gbcCancel = new GridBagConstraints();
        gbcCancel.insets = new Insets(5, 0, 0, 0);
        gbcCancel.gridx = 2;
        gbcCancel.gridy = 2;

        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new MyCancelActionListener());
        cancel.setSize(120, 25);
        cancel.setPreferredSize(search.getSize());
        this.getContentPane().add(cancel, gbcCancel);
    }

    /**
     * Listener for search files.
     */
    private class MySearchActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            error.setText("");
            if (chooser.showOpenDialog((Component) cLoad.getPGLOB()) == JFileChooser.APPROVE_OPTION) {
                path.setText(chooser.getSelectedFile().getAbsolutePath());
                path.setToolTipText(chooser.getSelectedFile().getAbsolutePath());
            }
        }
    }

    /**
     * Listener for load files.
     */
    private class MyLoadActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            if (!path.getText().equals(initSearch)) {
                File fichier = new File(path.getText());
                try {
                    if (path.getText().endsWith(".xml")) {
                        if (fichier.isFile()) {
                            int choix = JOptionPane.showConfirmDialog(
                                    getParent(),
                                    "Do you really want load this file?",
                                    "Validate", JOptionPane.YES_NO_OPTION);
                            switch (choix) {
                            case 0:
                                cLoad.load(path.getText());
                                dispose();
                                break;
                            case 1:
                                error.setText("You refused to load.");
                                break;
                            default:
                                error.setText("You refused to load.");
                            }
                        } else {
                            // The Path is an incorrect repository or file
                            error.setText("FileName incorrect (name.xml).");
                        }
                    } else {
                        error.setText("Wrong extension (.xml needed).");
                    }
                } catch (HeadlessException e) {
                    error.setText("FileName or path incorrect (ex : C:\\name.xml).");
                }

            } else {
                // Empty path
                error.setText("Enter a file name before loading.");
            }
        }
    }

    /**
     * Listener for cancel action.
     */
    private class MyCancelActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            dispose();
        }
    }
}
