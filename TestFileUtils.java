package com.mxdui;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;


import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

class FileUtilsTest {

    @Test
    void testWriteAndReadFile(@TempDir Path tempDir) throws IOException {
        File tempFile = tempDir.resolve("testFile.txt").toFile();

        String testData = "Hello, World!";
        byte[] testDataBytes = testData.getBytes();

        FileUtils.writeFile(tempFile.getAbsolutePath(), testDataBytes);

        byte[] readData = FileUtils.readFile(tempFile.getAbsolutePath());

        assertArrayEquals(testDataBytes, readData, "Read data should match written data.");
    }
}
