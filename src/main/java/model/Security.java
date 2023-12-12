package model;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

public class Security {
    public static void main(String[] args) {

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
    public static KeyPair generatedRsaKeys(){
        KeyPairGenerator keyPairGenerator;
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
    public static RSAPrivateKey decodedKeyPrivateRsa(byte[] bytesOfKey)
    {
        try {
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytesOfKey);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            RSAPrivateKey privKey = (RSAPrivateKey) kf.generatePrivate(spec);
            return  privKey;

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }


    public static boolean isCorrectPairKeys(byte[] bytesKeyPublic, byte[] bytesKeyPrivate){
        if(bytesKeyPublic == null || bytesKeyPrivate == null)
            return false;
        RSAPublicKey keyPub = decodedKeyPublicRsa(bytesKeyPublic);
        RSAPrivateKey keyPri = decodedKeyPrivateRsa(bytesKeyPrivate);
        return isCorrectPairKeys(keyPub, keyPri);

    }

    public static boolean isCorrectPairKeys(PublicKey publicKey, PrivateKey privateKey){
        byte[] plainText = generateRandomBytes(100);
        byte[] encryptedText = cipherRSAEncrypt(plainText, publicKey);
        byte[] decryptedText = cipherRSADecrypt(encryptedText, privateKey);

        return Arrays.equals(plainText, decryptedText);

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
