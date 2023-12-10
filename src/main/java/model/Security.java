package model;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;

public class Security {
    public static void main(String[] args) {
        cipherRSA();
    }




    public static byte[] cipherAes(byte[] data, SecretKey key, int mode) {
        //mod = Cipher.DECRYPT_MODE
        //mod = Cipher.ENCRYPT_MODE
        if (data == null) return null;
        try {
            IvParameterSpec iv = new IvParameterSpec(new byte[16]);
            Cipher encrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
            encrypt.init(mode, key, iv);
            return encrypt.doFinal(data);
        } catch (NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException |
                 InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
    }
    public static SecretKey generatedAesKey() {
        try {
            return KeyGenerator.getInstance("AES").generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }
    public static byte[] encodedAnyKey(Key key) {
        return key.getEncoded();
    }

    public static SecretKey decodedKeyAes(byte[] bytesOfKey) {
        return new SecretKeySpec(bytesOfKey, 0, bytesOfKey.length, "AES");
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
    public static void cipherRSA() {

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(4096);

            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            Cipher cipher = Cipher.getInstance("RSA");


            byte[] plainText = ("abcdefghijklmnopqrstuvwxyk").getBytes(StandardCharsets.UTF_8);
            System.out.println(new String(plainText, StandardCharsets.UTF_8));

            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] cipherText = cipher.doFinal(plainText);
            System.out.println(new String(cipherText, StandardCharsets.UTF_8));

            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] encryptText = cipher.doFinal(cipherText);
            System.out.println(new String(encryptText, StandardCharsets.UTF_8));


        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            throw new RuntimeException(e);
        }


    }









    public static byte[] generateRandomBytes(int numBytes){
        byte[] salt;
        try {// Генерация соли
            salt = new byte[numBytes];
            SecureRandom randByte = new SecureRandom(SecureRandom.getSeed(numBytes));
            SecureRandom.getInstanceStrong().nextBytes(salt);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Исключение, соль не сгенерирована");
            throw new RuntimeException(e);
        }
        return salt;
    }

    public static byte[] generateHashSha256(byte[] first, byte[] second) {

        byte[] saltHash;
        try {//Генерация хеша соли и пароля пользователя
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(first);
            messageDigest.update(second);
            saltHash = messageDigest.digest();


        } catch (NoSuchAlgorithmException e) {
            System.out.println("Исключение, хеш не сгенерирован");
            throw new RuntimeException(e);
        }

        return saltHash;
    }






}
