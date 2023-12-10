package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import static model.FileFunctions.*;


class FileFunctionsTest {

    @Test
    void writeFileTest() {
        byte[] plainText = Security.generateRandomBytes(100);
        writeFile(plainText, "TestFile1.txt");
        byte[] readText = readFile("TestFile1.txt");
        Assertions.assertArrayEquals(plainText, readText);
    }

    @Test
    void readFileTest() {
        byte[] plainText = Security.generateRandomBytes(100);
        writeFile(plainText, "TestFile1.txt");
        byte[] readText = readFile("TestFile1.txt");
        Assertions.assertArrayEquals(plainText, readText);    }
}