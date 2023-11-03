import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.security.*;

public class Security {
    public static void cipherAES() {
        try {
            Cipher encrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
            Cipher decrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");

            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = new SecureRandom();
            keyGenerator.init(256, secureRandom);
            Key secretKey = keyGenerator.generateKey();

            SecureRandom rnd = new SecureRandom();
            IvParameterSpec iv = new IvParameterSpec(rnd.generateSeed(16));

            encrypt.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            decrypt.init(Cipher.DECRYPT_MODE, secretKey, iv);

            byte[] plainText = "abcdefghijklmnopqrstuvwxyz".getBytes("UTF-8");
            System.out.println(new String(plainText, "UTF-8"));

            byte[] cipherText = encrypt.doFinal(plainText);
            System.out.println(new String(cipherText, "UTF-8"));

            byte[] decryptText = decrypt.doFinal(cipherText);
            System.out.println(new String(decryptText, "UTF-8"));


//            new String(bytes, UTF8_CHARSET)

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException |
                 InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }


    }

    public static void cipherRSA() {

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(4096);

            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            Cipher cipher = Cipher.getInstance("RSA");


            byte[] plainText = ("abcdefghijklmnopqrstuvwxyk").getBytes("UTF-8");
            System.out.println(new String(plainText, "UTF-8"));

            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] cipherText = cipher.doFinal(plainText);
            System.out.println(new String(cipherText, "UTF-8"));

            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] encryptText = cipher.doFinal(cipherText);
            System.out.println(new String(encryptText, "UTF-8"));


        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }


    }

    public static byte[] generateRandomBytes(int numBytes){
        byte[] salt;
        try {// Генерация соли
            salt = new byte[numBytes];
            SecureRandom randByte = new SecureRandom(SecureRandom.getSeed(numBytes));
            randByte.getInstanceStrong().nextBytes(salt);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Исключение, соль не сгенерирована");
            salt = null;
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
            saltHash = null;
            throw new RuntimeException(e);
        }

        return saltHash;
    }

    public static void setGenerateSign(User user)//todo Сделать нормальную генерацию
    {
        user.setSign(generateRandomBytes(32));//******************

    }
    public static void setGenerateKeys(User user)//todo Сделать нормальную генерацию
    {
        user.setKeyPrivate(generateRandomBytes(512));//******************
        user.setKeyPublic(generateRandomBytes(512));//******************

    }
    public static void setGeneratedSignPerformer(Task task)//todo Сделать нормальную генерацию
    {
        task.setSignPerformer(generateRandomBytes(32));

    }
    public static void setGeneratedSignLead(Task task)//todo Сделать нормальную генерацию
    {
        task.setSignLead(generateRandomBytes(32));

    }
    public static Task updateSignTask(Task task){

        task.setSignLead(Security.generateRandomBytes(32));
        task.setSignPerformer(Security.generateRandomBytes(32));
        return null;//*********************

    }

}
