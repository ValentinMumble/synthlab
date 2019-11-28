package fr.istic.synthlab.util;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * Utility class to filter the file type
 * in saveFileBox for Recorder module.
 * 
 * @author Favereau
 * 
 */
public class FileWavFilter extends FileFilter{

    @Override
    public boolean accept(File f) {
        
        String extension = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            extension = s.substring(i+1).toLowerCase();
        }
            
        if (extension != null && extension.equals("wav") 
                /* || extension.equals("mp3")*/) {
                    return true;
        }
        
        if (f.isDirectory()) {
            return true;
        }

        return false;
    }

    @Override
    public String getDescription() {
        return "Sound Wave File";
    }
}
