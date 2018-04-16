package com.netcracker.edu.volkov.arcomp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.netcracker.edu.volkov.arcomp.Properties.FileStatus;

/**
 * This class creates {@code TXT} file which contains result of comparing the archives.
 *
 * @author Nikita Volkov
 */
public class Printer {

    private static final String DEFAULT_PATH_OF_OUTPUT_TXT = "/";
    private static final String NAME_OF_OUTPUT_TXT = "output.txt";
    private static final String DEFAULT_ENCODING = "UTF-8";

    private ArchiveComparator archiveComparator;
    private String directoryPath;

    /**
     * This constructor is used when paths to archives are specified via a {@link Frame}.
     *
     * @param oldArchivePath path to old version of archive
     * @param newArchivePath path to new version of archive
     * @param directoryPath  path to directory for save output file
     * @throws IOException if an I/O error has occurred
     */
    Printer(String oldArchivePath, String newArchivePath, String directoryPath) throws IOException {
        this.archiveComparator = new ArchiveComparator(new Archive(oldArchivePath), new Archive(newArchivePath));
        this.directoryPath = directoryPath;
    }

    /**
     * This constructor is used when paths to archives are specified from terminal.
     *
     * @param oldArchivePath path to old version of archive
     * @param newArchivePath path to new version of archive
     * @throws IOException if an I/O error has occurred
     */
    Printer(String oldArchivePath, String newArchivePath) throws IOException {
        this(oldArchivePath, newArchivePath, DEFAULT_PATH_OF_OUTPUT_TXT);
    }

    /**
     * Create output file with result of comparing the archives.
     * Using methods {@link Printer#getDelFile(String)}, {@link Printer#getNewFile(String)},
     * {@link Printer#getReFile(String)}, {@link Printer#getUpFile(String)}.
     *
     * @throws FileNotFoundException        if the selected directory does not exist
     * @throws UnsupportedEncodingException if an unsupported encoding was selected
     */
    void printToFile() throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(directoryPath + NAME_OF_OUTPUT_TXT, DEFAULT_ENCODING);
        archiveComparator.compare();
        HashMap<FileStatus, String> map = archiveComparator.getMap();
        String nameOfOldArch = archiveComparator.getOldArchive().getName();
        String nameOfNewArch = archiveComparator.getNewArchive().getName();
        writer.printf("%-30.30s|%-30.30s\n", nameOfOldArch.substring(nameOfOldArch.lastIndexOf('/') + 1),
                nameOfNewArch.substring(nameOfNewArch.lastIndexOf('/') + 1));
        writer.print(this.getLine());
        for (Map.Entry<FileStatus, String> entry : map.entrySet()) {
            switch (entry.getKey()) {
                case NEW:
                    writer.print(this.getNewFile(entry.getValue()));
                    break;
                case DELETED:
                    writer.print(this.getDelFile(entry.getValue()));
                    break;
                case RENAMED:
                    writer.print(this.getReFile(entry.getValue()));
                    break;
                case UPDATED:
                    writer.print(this.getUpFile(entry.getValue()));
                    break;
            }
        }
        writer.print(this.getLine());
        writer.print(this.getHelp());
        writer.close();
        System.out.println("Create file: " + directoryPath + NAME_OF_OUTPUT_TXT);
    }

    /**
     * Create a string representation of {@code DELETED} file
     *
     * @param name the name of {@code DELETED} file
     * @return a string representation of {@code DELETED} file
     * @see FileStatus
     */
    private String getDelFile(String name) {
        return String.format("- %-28.28s|%-30.30s\n", name, "");
    }

    /**
     * Create a string representation of {@code NEW} file
     *
     * @param name the name of {@code NEW} file
     * @return a string representation of {@code NEW} file
     * @see FileStatus
     */
    private String getNewFile(String name) {
        return String.format("%-30.30s|+ %-28.28s\n", "", name);
    }

    /**
     * Create a string representation of {@code UPDATED} file
     *
     * @param name the name of {@code UPDATED} file
     * @return a string representation of {@code UPDATED} file
     * @see FileStatus
     */
    private String getUpFile(String name) {
        return String.format("* %-28.28s|* %-28.28s\n", name, name);
    }

    /**
     * Create a string representation of {@code RENAMED} file
     *
     * @param name the name of {@code RENAMED} file
     * @return a string representation of {@code RENAMED} file
     * @see FileStatus
     */
    private String getReFile(String name) {
        return String.format("? %-28.28s|? %-28.28s\n",
                name.substring(0, name.indexOf('/')),
                name.substring(name.indexOf('/') + 1));
    }

    /**
     * Create a string representation of line
     *
     * @return a string representation of line
     */
    private String getLine() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 30; i++) {
            stringBuilder.append('-');
        }
        stringBuilder.append('+');
        for (int i = 0; i < 30; i++) {
            stringBuilder.append('-');
        }
        stringBuilder.append('\n');
        return stringBuilder.toString();
    }

    /**
     * Create a string representation of help
     *
     * @return a string representation of help
     */
    private String getHelp() {
        return String.format("\nHELP\n+ %s\n- %s\n* %s\n? %s\n",
                FileStatus.NEW,
                FileStatus.DELETED,
                FileStatus.UPDATED,
                FileStatus.RENAMED);
    }

}
