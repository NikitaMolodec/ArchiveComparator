package com.netcracker.edu.volkov.arcomp;

import java.util.HashMap;
import java.util.Objects;

import com.netcracker.edu.volkov.arcomp.Properties.FileStatus;

/**
 * This class compares two objects of the {@link Archive} type
 * and gives {@code HashMap} which the {@link Printer} can use.<br/>
 * {@code oldArchive} it's a previous version of the archive, {@code newArchive}
 * it's an actual version of the archive.
 *
 * @author Nikita Volkov
 */
public class ArchiveComparator {
    private Archive newArchive;
    private Archive oldArchive;

    ArchiveComparator(Archive oldArchive, Archive newArchive) {
        this.oldArchive = oldArchive;
        this.newArchive = newArchive;
    }

    /**
     * After comparing, the status {@link FileStatus} of files, stored
     * in the {@code oldArchive} and {@code newArchive}, will change to
     * {@code NEW}, {@code DELETED}, {@code UPDATED} or {@code RENAMED}.
     *
     * @see FileStatus
     */
    public void compare() {
        this.findDelFiles();
        this.findNewFiles();
        this.findUpFiles();
        this.findReFiles();
    }

    /**
     * Find deleted files and change their status to {@code DELETED}
     *
     * @see FileStatus
     */
    private void findDelFiles() {
        for (int i = 0; i < oldArchive.getSize(); i++) {
            boolean flag = true;
            for (int j = 0; j < newArchive.getSize(); j++) {
                if (Objects.equals(newArchive.getFiles().get(j).getName(), oldArchive.getFiles().get(i).getName())) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                oldArchive.getFiles().get(i).setStatus(FileStatus.DELETED);
            }
        }
    }

    /**
     * Find new files and change their status to {@code NEW}
     *
     * @see FileStatus
     */
    private void findNewFiles() {
        for (int i = 0; i < newArchive.getSize(); i++) {
            boolean flag = true;
            for (int j = 0; j < oldArchive.getSize(); j++) {
                if (Objects.equals(newArchive.getFiles().get(i).getName(), oldArchive.getFiles().get(j).getName())) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                newArchive.getFiles().get(i).setStatus(FileStatus.NEW);
            }
        }
    }

    /**
     * Find updated files and change their status to {@code UPDATED}
     *
     * @see FileStatus
     */
    private void findUpFiles() {
        for (int i = 0; i < oldArchive.getSize(); i++) {
            for (int j = 0; j < newArchive.getSize(); j++) {
                if (Objects.equals(newArchive.getFiles().get(j).getName(), oldArchive.getFiles().get(i).getName())
                        && newArchive.getFiles().get(j).getSize() != oldArchive.getFiles().get(i).getSize()) {
                    newArchive.getFiles().get(j).setStatus(FileStatus.UPDATED);
                    oldArchive.getFiles().get(i).setStatus(FileStatus.UPDATED);
                    break;
                }
            }
        }
    }

    /**
     * Find renamed files and change their status to {@code RENAMED}
     *
     * @see FileStatus
     */
    private void findReFiles() {
        for (int i = 0; i < oldArchive.getSize(); i++) {
            for (int j = 0; j < newArchive.getSize(); j++) {
                if (!Objects.equals(newArchive.getFiles().get(j).getName(), oldArchive.getFiles().get(i).getName())
                        && newArchive.getFiles().get(j).getSize() == oldArchive.getFiles().get(i).getSize()) {
                    newArchive.getFiles().get(j).setStatus(FileStatus.RENAMED);
                    oldArchive.getFiles().get(i).setStatus(FileStatus.RENAMED);
                    break;
                }
            }
        }
    }

    /**
     * Return the {@code HashMap} in which the key is the {@link FileStatus}
     * and the value is the file name. {@link Printer} can use this map
     * to create {@code TXT} file.
     *
     * @return {@code HashMap} for {@code Printer}
     */
    public HashMap<FileStatus, String> getMap() {
        HashMap<FileStatus, String> map = new HashMap<>();
        for (File file : oldArchive.getFiles()) {
            if (file.getStatus() == FileStatus.RENAMED) {
                for (File file1 : newArchive.getFiles()) {
                    if (file.getSize() == file1.getSize()) {
                        map.put(file.getStatus(), file.getName() + '/' + file1.getName());
                        break;
                    }
                }
            } else {
                map.put(file.getStatus(), file.getName());
            }
        }
        for (File file : newArchive.getFiles()) {
            if (Objects.equals(file.getStatus(), FileStatus.NEW)) {
                map.put(file.getStatus(), file.getName());
            }
        }
        return map;
    }

    /**
     * @return {@code newArchive}
     */
    public Archive getNewArchive() {
        return newArchive;
    }

    /**
     * @return {@code oldArchive}
     */
    public Archive getOldArchive() {
        return oldArchive;
    }
}
