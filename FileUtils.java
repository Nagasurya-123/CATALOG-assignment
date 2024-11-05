package com.mxdui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {
    /**
     * Writes data to a file.
     * 
     * @param filename The name of the file to write to.
     * @param data     The data to write.
     * @throws IOException If an error occurs during writing.
     */
    public static void writeFile(String filename, byte[] data) throws IOException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(filename));
            fos.write(data);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Reads data from a file.
     * 
     * @param filename The name of the file to read from.
     * @return A byte array containing the data read from the file.
     * @throws IOException If an error occurs during reading.
     */
    public static byte[] readFile(String filename) throws IOException {
        File file = new File(filename);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            return data;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
