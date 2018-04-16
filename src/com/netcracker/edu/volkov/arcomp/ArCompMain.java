package com.netcracker.edu.volkov.arcomp;

import java.io.IOException;

/**
 * Just an entry point that supports setting initial conditions
 * through the terminal and through the dialog box {@link Frame}.
 *
 * @author Nikita Volkov
 */
public class ArCompMain {
    public static void main(String[] args) throws IOException {
        try {
            Printer printer = new Printer(args[0], args[1]);
            printer.printToFile();
        } catch (ArrayIndexOutOfBoundsException e) {
            Frame frame = new Frame();
            Printer printer = new Printer(
                    frame.getFirstFilePath(),
                    frame.getSecondFilePath(),
                    frame.getDirectoryPath()
            );
            printer.printToFile();
        }
    }
}
