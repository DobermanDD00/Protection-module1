package model;

import javax.crypto.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import static model.Security.*;

public class Test {
    public static void main(String[] args) {
        System.out.println("Исходное сообщение");
        byte[] plainText = "ksjdhfkjwe".getBytes();
        System.out.println(new String(plainText, Charset.defaultCharset()));


        KeyPair keyPair = generatedRsaKeys();
        byte[] bytesKeyPublic = encodedAnyKey(keyPair.getPublic());
        System.out.println("Публичный ключ");
        System.out.println(new String(bytesKeyPublic, Charset.defaultCharset()));
        byte[] bytesKeyPrivate = encodedAnyKey(keyPair.getPrivate());
        System.out.println("Приватный ключ");
        System.out.println(new String(bytesKeyPrivate, Charset.defaultCharset()));


        Key keyPub = decodedKeyPublicRsa(bytesKeyPublic);
        byte[] encryptedText = cipherRSAEncrypt(plainText, keyPub);
        System.out.println("Зашифрованное сообщение");
        System.out.println(new String(encryptedText, Charset.defaultCharset()));
        RSAPrivateKey keyPri = decodedKeyPrivateRsa(bytesKeyPrivate);


//        System.out.println("Публичный ключ1");
//        byte[] bytesKeyPublic1 = encodedAnyKey(decodedKeyPublicRsa(bytesKeyPublic));
//        System.out.println(new String(bytesKeyPublic1, Charset.defaultCharset()));

//        System.out.println("Приватный ключ");
//        byte[] bytesKeyPrivate1 = encodedAnyKey(decodedKeyPrivateRsa(bytesKeyPrivate));
//        System.out.println(new String(bytesKeyPrivate1, Charset.defaultCharset()));


        byte[] decryptedText = cipherRSADecrypt(plainText, keyPri);
        System.out.println("Расшифрованное сообщение");
        System.out.println(new String(decryptedText, Charset.defaultCharset()));


    }
    public static KeyPair generatedRsaKeys(){
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        keyPairGenerator.initialize(4096);
        return keyPairGenerator.generateKeyPair();
    }



    public static RSAPublicKey decodedKeyPublicRsa(byte[] bytesOfKey) {
        try {
            X509EncodedKeySpec spec1 = new X509EncodedKeySpec(bytesOfKey);
            KeyFactory kf1 = KeyFactory.getInstance("RSA");
            RSAPublicKey pubKey = (RSAPublicKey) kf1.generatePublic(spec1);
            return pubKey;

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static RSAPrivateKey decodedKeyPrivateRsa(byte[] bytesOfKey) {
        try {
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytesOfKey);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            RSAPrivateKey privKey = (RSAPrivateKey) kf.generatePrivate(spec);
            return  privKey;

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }





    public static byte[] cipherRSAEncrypt(byte[] data, Key key) {
        //mod = Cipher.DECRYPT_MODE
        //mod = Cipher.ENCRYPT_MODE
        try {

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(data);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }
    public static byte[] cipherRSADecrypt(byte[] data, PrivateKey key) {
        //mod = Cipher.DECRYPT_MODE
        //mod = Cipher.ENCRYPT_MODE
        try {

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(data);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] encodedAnyKey(Key key) {
        return key.getEncoded();
    }





}
