package com.netcracker.edu.volkov.arcomp;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.Objects;

/**
 * This class is used to display a dialog window in which you can choose
 * two files and a directory to save the output file.
 *
 * @author Nikita Volkov
 */
class Frame extends JFrame {
    private String firstFilePath = "";
    private String secondFilePath = "";
    private String directoryPath = "";

    /**
     * Creates a new dialog window for choosing path to first file, to second file
     * and path to directory for saving output file.
     */
    Frame() {
        JFileChooser fileopen = new JFileChooser();
        fileopen.setFileFilter(new FileNameExtensionFilter("ZIP & JAR Archives", "zip", "jar"));
        int ret = fileopen.showDialog(null, "Choose first file");
        if (ret == JFileChooser.APPROVE_OPTION) {
            firstFilePath = fileopen.getSelectedFile().getPath();
        }
        int ret1 = fileopen.showDialog(null, "Choose second file");
        if (ret1 == JFileChooser.APPROVE_OPTION) {
            secondFilePath = fileopen.getSelectedFile().getPath();
        }
        int ret2 = fileopen.showDialog(null, "Choose directory");
        if (ret2 == JFileChooser.APPROVE_OPTION) {
            directoryPath = fileopen.getSelectedFile().getPath();
        }
    }

    /**
     * @return path to first file
     */
    String getFirstFilePath() {
        return firstFilePath;
    }

    /**
     * @return path to second file
     */
    String getSecondFilePath() {
        return secondFilePath;
    }

    /**
     * If the directory isn't selected, output file will be saved in root of project folder.
     *
     * @return path to directory
     */
    String getDirectoryPath() {
        if (Objects.equals(directoryPath, "")) {
            return directoryPath;
        }
        return directoryPath + '/';
    }
}
