package com.netcracker.edu.volkov.arcomp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * This class stores information about {@code ZIP} or {@code JAR} files.<br/>
 * It extends the class {@code JarFile} with support for getting
 * list of stored files {@link File}.<br/>
 *
 * @author Nikita Volkov
 */
public class Archive extends JarFile{

    private List<File> files;
    private int size;

    /**
     * Creates a new {@code Archive} to read from the specified
     * file {@code name}. The {@code Archive} will be verified if
     * it is signed.
     *
     * @param name the name of the zip or jar file to be opened for reading
     * @throws IOException       if an I/O error has occurred
     * @throws SecurityException if access to the file is denied
     *                           by the SecurityManager
     */
    public Archive(String name) throws IOException {
        super(name);
        Enumeration<JarEntry> enumeration = super.entries();
        this.files = new ArrayList<>();

        while(enumeration.hasMoreElements()) {
            JarEntry jarEntry = enumeration.nextElement();
            this.files.add(new File(jarEntry.getName(), jarEntry.getSize()));
        }
        this.size = this.files.size();
    }

    /**
     * @return list of files stored in the archive
     */
    public List<File> getFiles() {
        return files;
    }

    /**
     * @return count of files
     */
    public int getSize() {
        return size;
    }

}
