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
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import fr.istic.synthlab.save.control.ICSave;

/**
 * Implementation of the PLoad.
 *
 * @author Mickael
 * @version 1.0
 */
public class PSave extends JDialog implements IPSave {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1118999349721130669L;
    /**
     * Control of Save.
     */
    private ICSave cSave;
    /**
     * JFileChooser.
     */
    private JFileChooser chooser;
    /**
     * JTextArea.
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
     * Constructor of the PSave.
     * 
     * @param control
     *            control of Save.
     */
    public PSave(ICSave control) {
        this.cSave = control;
        this.setTitle("Save");
        this.setLayout(new GridBagLayout());
        this.init();
    }

    /**
     * Initialize the panel.
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

        JButton save = new JButton("Save");
        save.addActionListener(new MySaveActionListener());
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
            if(chooser.showOpenDialog((Component) cSave.getPGLOB()) == JFileChooser.APPROVE_OPTION){
                path.setText(chooser.getSelectedFile().getAbsolutePath());
                path.setToolTipText(chooser.getSelectedFile().getAbsolutePath());
            }
        }
    }

    /**
     * Listener for save files.
     */
    private class MySaveActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            if (!path.getText().equals(initSearch)) {
                File fichier = new File(path.getText());
                try {
                    if (path.getText().endsWith(".xml")) {
                        if (fichier.createNewFile()) {
                            int choix = JOptionPane
                                    .showConfirmDialog(
                                            getParent(),
                                            "File not found, do you want to create it?",
                                            "Not found",
                                            JOptionPane.YES_NO_OPTION);
                            switch (choix) {
                            case 0:
                                cSave.save(path.getText());
                                dispose();
                                break;
                            case 1:
                                error.setText("File or path not found.");
                                fichier.delete();
                                break;
                            default:
                                error.setText("File or path not found.");
                                fichier.delete();
                            }
                        } else {
                            if (fichier.isFile()) {
                                int choix = JOptionPane.showConfirmDialog(
                                        getParent(),
                                        "File already exist, overwrite?",
                                        "Overwrite", JOptionPane.YES_NO_OPTION);
                                switch (choix) {
                                case 0:
                                    cSave.save(path.getText());
                                    dispose();
                                    break;
                                case 1:
                                    error.setText("File already exist, enter a new name.");
                                    break;
                                default:
                                    error.setText("File already exist, enter a new name.");
                                }
                            } else {
                                // The Path is an incorrect repository or file
                                error.setText("FileName incorrect (name.xml).");
                            }
                        }
                    } else {
                        error.setText("Wrong extension (.xml needed).");
                    }
                } catch (HeadlessException | IOException e) {
                    error.setText("Wrong path.");
                }
            } else {
                // Empty path
                error.setText("Enter a file name before saving.");
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
