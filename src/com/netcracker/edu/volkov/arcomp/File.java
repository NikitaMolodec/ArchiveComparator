package com.netcracker.edu.volkov.arcomp;

import com.netcracker.edu.volkov.arcomp.Properties.FileStatus;

/**
 * This class contains information about a file stored in an {@link Archive}.<br/>
 * Such as: file name, uncompressed size of the file data and {@link FileStatus}.
 *
 * @author Nikita Volkov
 */
public class File {
    private String name;
    private long size;
    private FileStatus status;

    public File(String name, long size) {
        this.name = name;
        this.size = size;
        this.status = FileStatus.DEFAULT;
    }

    /**
     * @return file name
     */
    public String getName() {
        return name;
    }

    /**
     * @return uncompressed size
     */
    public long getSize() {
        return size;
    }

    /**
     * @return {@link FileStatus}
     */
    public FileStatus getStatus() {
        return status;
    }

    /**
     * Set a new status for this file
     *
     * @param status {@link FileStatus}
     */
    public void setStatus(FileStatus status) {
        this.status = status;
    }
}
