package com.netcracker.edu.volkov.arcomp;

/**
 * This class contains some properties.
 *
 * @author Nikita Volkov
 */
public class Properties {

    /**
     * This enum stores the status of the files that are stored in the {@link Archive}:<br/>
     * {@code NEW} if the new archive doesn't contain a file that is contained in the old archive,<br/>
     * {@code DELETED} if the old archive doesn't contain a file that is contained in the new archive,<br/>
     * {@code UPDATED} if the archives contain files with the same name but have different size,<br/>
     * {@code DEFAULT} if the archives contain files with the same name and size,<br/>
     * {@code RENAMED} if the archives contain files with the same size but have different names.
     */
    public enum FileStatus {
        NEW, DELETED, UPDATED, RENAMED, DEFAULT
    }

}
