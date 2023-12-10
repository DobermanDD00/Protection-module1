package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;

import static model.Security.*;
import static org.junit.jupiter.api.Assertions.*;

class SecurityTest {

    @Test
    void generatedAesKeyTest() {
        byte[] plainText = generateRandomBytes(300);
        byte[] bytesKey = encodedAnyKey(generatedAesKey());
        byte[] encryptedText = cipherAes(plainText, decodedKeyAes(bytesKey), Cipher.ENCRYPT_MODE);
        byte[] decryptedText = cipherAes(encryptedText, decodedKeyAes(bytesKey), Cipher.DECRYPT_MODE);

        Assertions.assertArrayEquals(decryptedText, plainText);
    }

    @Test
    void encodedAnyKeyTest() {
        byte[] plainText = generateRandomBytes(300);
        byte[] bytesKey = encodedAnyKey(generatedAesKey());
        byte[] encryptedText = cipherAes(plainText, decodedKeyAes(bytesKey), Cipher.ENCRYPT_MODE);
        byte[] decryptedText = cipherAes(encryptedText, decodedKeyAes(bytesKey), Cipher.DECRYPT_MODE);

        Assertions.assertArrayEquals(decryptedText, plainText);
    }

    @Test
    void decodedKeyAesTest() {
        byte[] plainText = generateRandomBytes(300);
        byte[] bytesKey = encodedAnyKey(generatedAesKey());
        byte[] encryptedText = cipherAes(plainText, decodedKeyAes(bytesKey), Cipher.ENCRYPT_MODE);
        byte[] decryptedText = cipherAes(encryptedText, decodedKeyAes(bytesKey), Cipher.DECRYPT_MODE);

        Assertions.assertArrayEquals(decryptedText, plainText);
    }

    @Test
    void cipherAesTest() {
        byte[] plainText = generateRandomBytes(300);
        byte[] bytesKey = encodedAnyKey(generatedAesKey());
        byte[] encryptedText = cipherAes(plainText, decodedKeyAes(bytesKey), Cipher.ENCRYPT_MODE);
        byte[] decryptedText = cipherAes(encryptedText, decodedKeyAes(bytesKey), Cipher.DECRYPT_MODE);

        Assertions.assertArrayEquals(decryptedText, plainText);
    }
}