package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;

import static model.Security.*;

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
        for (int i = 0; i < 5; i++) {

            byte[] plainText = generateRandomBytes(300);
            byte[] bytesKey = encodedAnyKey(generatedAesKey());
            byte[] encryptedText = cipherAes(plainText, decodedKeyAes(bytesKey), Cipher.ENCRYPT_MODE);
            byte[] decryptedText = cipherAes(encryptedText, decodedKeyAes(bytesKey), Cipher.DECRYPT_MODE);

            Assertions.assertArrayEquals(decryptedText, plainText);
        }
    }

    @Test
    void generatedRsaKeysTest() {
        for (int i = 0; i < 5; i++) {
            byte[] plainText = generateRandomBytes(100);

            KeyPair keyPair = generatedRsaKeys();
            byte[] bytesKeyPublic = encodedAnyKey(keyPair.getPublic());
            byte[] bytesKeyPrivate = encodedAnyKey(keyPair.getPrivate());


            Key keyPub = decodedKeyPublicRsa(bytesKeyPublic);
            byte[] encryptedText = cipherRSAEncrypt(plainText, keyPub);
            RSAPrivateKey keyPri = decodedKeyPrivateRsa(bytesKeyPrivate);
            byte[] decryptedText = cipherRSADecrypt(encryptedText, keyPri);


            Assertions.assertArrayEquals(plainText, decryptedText);
        }
    }

    @Test
    void decodedKeyPublicRsaTest() {
        for (int i = 0; i < 5; i++) {
            byte[] plainText = generateRandomBytes(100);

            KeyPair keyPair = generatedRsaKeys();
            byte[] bytesKeyPublic = encodedAnyKey(keyPair.getPublic());
            byte[] bytesKeyPrivate = encodedAnyKey(keyPair.getPrivate());


            Key keyPub = decodedKeyPublicRsa(bytesKeyPublic);
            byte[] encryptedText = cipherRSAEncrypt(plainText, keyPub);
            RSAPrivateKey keyPri = decodedKeyPrivateRsa(bytesKeyPrivate);
            byte[] decryptedText = cipherRSADecrypt(encryptedText, keyPri);


            Assertions.assertArrayEquals(plainText, decryptedText);
        }
    }

    @Test
    void decodedKeyPrivateRsaTest() {
        for (int i = 0; i < 5; i++) {
            byte[] plainText = generateRandomBytes(100);

            KeyPair keyPair = generatedRsaKeys();
            byte[] bytesKeyPublic = encodedAnyKey(keyPair.getPublic());
            byte[] bytesKeyPrivate = encodedAnyKey(keyPair.getPrivate());


            Key keyPub = decodedKeyPublicRsa(bytesKeyPublic);
            byte[] encryptedText = cipherRSAEncrypt(plainText, keyPub);
            RSAPrivateKey keyPri = decodedKeyPrivateRsa(bytesKeyPrivate);
            byte[] decryptedText = cipherRSADecrypt(encryptedText, keyPri);


            Assertions.assertArrayEquals(plainText, decryptedText);
        }
    }

    @Test
    void cipherRSAEncryptTest() {
        for (int i = 0; i < 5; i++) {
            byte[] plainText = generateRandomBytes(100);

            KeyPair keyPair = generatedRsaKeys();
            byte[] bytesKeyPublic = encodedAnyKey(keyPair.getPublic());
            byte[] bytesKeyPrivate = encodedAnyKey(keyPair.getPrivate());


            Key keyPub = decodedKeyPublicRsa(bytesKeyPublic);
            byte[] encryptedText = cipherRSAEncrypt(plainText, keyPub);
            RSAPrivateKey keyPri = decodedKeyPrivateRsa(bytesKeyPrivate);
            byte[] decryptedText = cipherRSADecrypt(encryptedText, keyPri);


            Assertions.assertArrayEquals(plainText, decryptedText);
        }
    }

    @Test
    void cipherRSADecryptTest() {
        for (int i = 0; i < 5; i++) {
            byte[] plainText = generateRandomBytes(100);

            KeyPair keyPair = generatedRsaKeys();
            byte[] bytesKeyPublic = encodedAnyKey(keyPair.getPublic());
            byte[] bytesKeyPrivate = encodedAnyKey(keyPair.getPrivate());


            Key keyPub = decodedKeyPublicRsa(bytesKeyPublic);
            byte[] encryptedText = cipherRSAEncrypt(plainText, keyPub);
            RSAPrivateKey keyPri = decodedKeyPrivateRsa(bytesKeyPrivate);
            byte[] decryptedText = cipherRSADecrypt(encryptedText, keyPri);


            Assertions.assertArrayEquals(plainText, decryptedText);
        }
    }

    @Test
    void isCorrectPairKeysTest() {
        KeyPair keyPair = generatedRsaKeys();

        byte[] keyPub = encodedAnyKey(keyPair.getPublic());
        byte[] keyPri = encodedAnyKey(keyPair.getPrivate());

        Assertions.assertTrue(isCorrectPairKeys(keyPub, keyPri));

    }
}