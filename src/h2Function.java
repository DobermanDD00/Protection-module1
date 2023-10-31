import javax.crypto.*;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HexFormat;


public class h2Function {

//    private static final String url = "jdbc:h2:tcp://localhost/~/test";
    private static final String url = "jdbc:h2:D:/Project/JavaProject/Protection-module1//dirDb/db2";
    private static final String user = "sa";
    private static final String password = "";
    private static Connection con;
    private static Statement stat;
    private static ResultSet rs;



    public static void main(String[] args) {

//        boolean bool = addUserDb("login10", "masha2","a", "a", "8798", "13879", "role1 role2 role3", "wporj");
        test1();
//        boolean bool = addReportDb("report9", "text", "", "user1", "user2 user3 user4","role1 role2 role3", "user7 user9");
//        LocalDateTime deadline = LocalDateTime.of(2023,5,21,19,7,7);
//        boolean bool = addTaskDb("Подготовить годовой отчет3", "Быстро!", "pasha", "masha misha", "role1 role3",  "user1 user2", "rsfe", deadline, "do task");

//        boolean bool = authentication("login8", "13879");
//        createRolesUser(1, "role1 role2");
//        System.out.println(bool);



//        cipherAES();
//        cipherRSA();
    }

    public static boolean addUserDb(String login, String firstName, String lastName, String middleName,
                                    String textField, String strPassUser, String roles, String status){
        boolean ret = true;

        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(url, user, password);
            stat = con.createStatement();

            rs = stat.executeQuery("SELECT ID FROM USERS ORDER BY ID DESC LIMIT 1;");
            int idNext = 0;
            if (rs.next()){
                idNext = rs.getInt("ID") + 1;
            }
            rs.close();



            byte[] salt;
            String strSaltHex;
            try {// Генерация соли
               salt = new byte[32];
                SecureRandom randByte = new SecureRandom(SecureRandom.getSeed(32));
                randByte.getInstanceStrong().nextBytes(salt);
                strSaltHex = byteToHexStr(salt);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }



            byte[] saltHash;
            String strSaltHexHash;
            try {//Генерация хеша соли и пароля пользователя
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                messageDigest.update(salt);
                messageDigest.update(strPassUser.getBytes(StandardCharsets.UTF_8));
                saltHash = messageDigest.digest();

                strSaltHexHash = byteToHexStr(saltHash);
               //System.out.println(strSaltHexHash);

            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }


            stat.executeUpdate("INSERT INTO USERS (ID, LOGIN, FIRST_NAME, lAST_NAME, MIDDLE_NAME, " +
                    "TEXT_FIELD, SALT, SALT_HASH, ROLES, STATUS)" +
                    " VALUES ("+idNext+", '"+login+"', '"+firstName+"', '"+lastName+"', '"+middleName+"', " +
                    "'"+textField+"', "+strSaltHex+", "+strSaltHexHash+", '"+roles+"', '"+status+"');");

            createRolesUser(idNext, roles);


        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Исключение: Пользователь не добавлен, скорее всего неправильный ввод, скорее всего логин");
            ret = false;
            throw new RuntimeException(e);//todo sql1
        }

        try {
            stat.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("Исключение соединение с БД не закрыто");
            throw new RuntimeException(e);
        }

        return ret;
    }
    public static boolean createRolesUser(int idUser, String roles){
        // roles - каждая роль - одно слово, роли разделены пробелом, внутри одного слова допускаются символы разделения (,./-_+ и тд)

        boolean ret = true;

        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(url, user, password);
            stat = con.createStatement();

            String[] role = roles.split(" ");

            StringBuilder sqlRequestBuilder = new StringBuilder();
            sqlRequestBuilder.append("CREATE TABLE USER_"+ Integer.toString(idUser)+" (ROLE VARCHAR(128));");
            for (String currentRole : role){
                sqlRequestBuilder.append("INSERT INTO USER_"+ Integer.toString(idUser)+" (ROLE) VALUES ('"+ currentRole +"');");
            }

            stat.executeUpdate(sqlRequestBuilder.toString());

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Исключение: Роли не добавлены");
            ret = false;
            throw new RuntimeException(e);//todo sql1
        }

        try {
            stat.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("Исключение соединение с БД не закрыто");
            throw new RuntimeException(e);
        }

        return ret;
    }

    public static boolean addReportDb(String name, String textField, String tegs, String sender, String receivers, String roles, String accessOthers){
        boolean ret = true;

        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(url, user, password);
            stat = con.createStatement();

            rs = stat.executeQuery("SELECT ID FROM REPORTS ORDER BY ID DESC LIMIT 1;");
            int idNext = 0;
            if (rs.next()){
                idNext = rs.getInt("ID") + 1;
            }
            rs.close();

            Date dateNow = new Date();
            SimpleDateFormat formatDateNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


            stat.executeUpdate("INSERT INTO REPORTS (ID, NAME, TEXT_FIELD, TEGS, SENDER, RECEIVERS, DATETIMECREATION)" +
                    " VALUES ('"+idNext+"', '"+name+"', '"+textField+"', '"+tegs+"', '"+sender+"', '"+receivers+"', '"+formatDateNow.format(dateNow)+"');");

            createReportAccess(idNext, roles, receivers, accessOthers);



        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Исключение: Отчет не добавлен");

            ret = false;
            throw new RuntimeException(e);//todo sql1
        }

        try {
            stat.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("Исключение соединение с БД не закрыто");
            throw new RuntimeException(e);
        }

        return ret;
    }

    public static boolean createReportAccess(int idReport, String roles, String receivers, String accessOthers){
        // roles, receivers, accessOthers - каждая роль - одно слово, роли разделены пробелом, внутри одного слова допускаются символы разделения (,./-_+ и тд)

        boolean ret = true;

        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(url, user, password);
            stat = con.createStatement();



            String[] receiver = receivers.split(" ");
            String[] role = roles.split(" ");
            String[] accessOther = accessOthers.split(" ");

            StringBuilder sqlRequestBuilder = new StringBuilder();
            sqlRequestBuilder.append("CREATE TABLE REPORT_"+ Integer.toString(idReport)+" (ROLE VARCHAR(128), RECEIVER VARCHAR(128), ACCESS_OTHER VARCHAR(128));");


            for (String currentRole : role ){//todo optimize 1 code
               sqlRequestBuilder.append("INSERT INTO REPORT_"+ Integer.toString(idReport)+" (ROLE) VALUES ('"+ currentRole +"');");
            }
            for (String currentReceivers : receiver ){
                sqlRequestBuilder.append("INSERT INTO REPORT_"+ Integer.toString(idReport)+" (RECEIVER) VALUES ('"+ currentReceivers +"');");
            }
            for (String currentAccessOthers : accessOther ){
                sqlRequestBuilder.append("INSERT INTO REPORT_"+ Integer.toString(idReport)+" (ACCESS_OTHER) VALUES ('"+ currentAccessOthers +"');");
            }

            stat.executeUpdate(sqlRequestBuilder.toString());

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Исключение: Роли не добавлены");
            ret = false;
            throw new RuntimeException(e);//todo sql1
        }

        try {
            stat.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("Исключение соединение с БД не закрыто");
            throw new RuntimeException(e);
        }

        return ret;
    }


    public static boolean addTaskDb(String name, String textField, String creator, String workers, String roles, String accessOthers, String tegs, LocalDateTime datetimeDeadline,  String status){
        boolean ret = true;

        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(url, user, password);
            stat = con.createStatement();

            rs = stat.executeQuery("SELECT ID FROM TASKS ORDER BY ID DESC LIMIT 1;");
            int idNext = 0;
            if (rs.next()){
                idNext = rs.getInt("ID") + 1;
            }
            rs.close();


            Date dateNow = new Date();
            SimpleDateFormat formatDateNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            stat.executeUpdate("INSERT INTO TASKS (ID , NAME, TEXT_FIELD  ," +
                    "CREATOR , WORKERS , TEGS ," +
                    "DATETIMEOFCREATION, DATETIMEDEADLINE, STATUS)" +
                    " VALUES ('"+idNext+"', '"+name+"', '"+textField+"', '"+
                    creator+"', '"+workers+"', '"+tegs+"', '"+
                    formatDateNow.format(dateNow)+"', '"+datetimeDeadline+"', '"+status+"');");

            createTaskAccess(idNext, roles, workers, accessOthers);

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Исключение: Задача не добавлена");
            ret = false;
            throw new RuntimeException(e);//todo sql1
        }

        try {
            stat.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("Исключение соединение с БД не закрыто");
            throw new RuntimeException(e);
        }

        return ret;//****
    }

    public static boolean createTaskAccess(int idTask, String roles, String workers, String accessOthers){
        // roles, workers, accessOthers - каждая роль - одно слово, роли разделены пробелом, внутри одного слова допускаются символы разделения (,./-_+ и тд)

        boolean ret = true;

        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(url, user, password);
            stat = con.createStatement();



            String[] worker = workers.split(" ");
            String[] role = roles.split(" ");
            String[] accessOther = accessOthers.split(" ");

            StringBuilder sqlRequestBuilder = new StringBuilder();
            sqlRequestBuilder.append("CREATE TABLE TASK_"+ Integer.toString(idTask)+" (ROLE VARCHAR(128), WORKER VARCHAR(128), ACCESS_OTHER VARCHAR(128));");


            for (String currentRole : role ){//todo optimize 1 code
                sqlRequestBuilder.append("INSERT INTO TASK_"+ Integer.toString(idTask)+" (ROLE) VALUES ('"+ currentRole +"');");
            }
            for (String currentReceivers : worker ){
                sqlRequestBuilder.append("INSERT INTO TASK_"+ Integer.toString(idTask)+" (WORKER) VALUES ('"+ currentReceivers +"');");
            }
            for (String currentAccessOthers : accessOther ){
                sqlRequestBuilder.append("INSERT INTO TASK_"+ Integer.toString(idTask)+" (ACCESS_OTHER) VALUES ('"+ currentAccessOthers +"');");
            }

            stat.executeUpdate(sqlRequestBuilder.toString());

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Исключение: Роли не добавлены");
            ret = false;
            throw new RuntimeException(e);//todo sql1
        }

        try {
            stat.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("Исключение соединение с БД не закрыто");
            throw new RuntimeException(e);
        }

        return ret;
    }
    public static boolean authentication (String login, String pass){
        boolean ret = false;


        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(url, user, password);
            stat = con.createStatement();

            rs = stat.executeQuery("SELECT SALT, SALT_HASH FROM USERS WHERE LOGIN = '"+login+"' LIMIT 1;");

            if (rs.next()){
                byte[] salt = rs.getBytes("SALT");
                byte[] saltHash = rs.getBytes("SALT_HASH");

                try {

                    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                    messageDigest.update(salt);
                    messageDigest.update(pass.getBytes("UTF-8"));
                    byte[] saltHashCurrent = messageDigest.digest();

                    ret = Arrays.equals(saltHashCurrent, saltHash);


                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }

            }
            rs.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        try {
            stat.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("Исключение соединение с БД не закрыто");
            throw new RuntimeException(e);
        }

        return ret; //todo ret
    }
    public static void test1(){
        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(url, user, password);
            stat = con.createStatement();
            String str = "masha";
            System.out.println("BBBBBBBB");


            stat.executeUpdate("CREATE TABLE TABLE4 (ID INT, NAME VARCHAR(128));");

            stat.executeUpdate("INSERT INTO TABLE4 (ID, NAME) VALUES (1, 'MASHA');");

//            rs = stat.executeQuery("SELECT ID FROM USERS ORDER BY ID DESC LIMIT 1;");
//
//            if (rs.next()){
//                System.out.println(rs.getInt("ID"));
//            }
//            rs.close();

//            while (rs.next()){
//                System.out.println(rs.getString("FIRST_NAME"));
//            }


        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("aaaaaaaaaa");
            e.printStackTrace();

            //throw new RuntimeException(e);
        }




        try {
            stat.close();
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void test2(){


        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(url, user, password);
            stat = con.createStatement();

            byte[] salt = new byte[32];
            SecureRandom randByte = new SecureRandom(SecureRandom.getSeed(32));
            randByte.getInstanceStrong().nextBytes(salt);

            String strHex = byteToHexStr(salt);

            //strHex = "X'01 bc 2a'";
            System.out.println(strHex);


            try {
                byte[] data1 = "0123456789".getBytes("UTF-8");
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                byte[] digest = messageDigest.digest(data1);
                String strSaltHexHash = byteToHexStr(digest);
                System.out.println(strSaltHexHash);

            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }





            stat.executeUpdate("INSERT INTO TABLE2 (SALT) VALUES ("+strHex+")");


        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Исключение: не добавлен");
            throw new RuntimeException(e);//todo sql1
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        try {
            stat.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("Исключение соединение с БД не закрыто");
            throw new RuntimeException(e);
        }



    }

    public static void cipherAES(){
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

            byte[] plainText  = "abcdefghijklmnopqrstuvwxyz".getBytes("UTF-8");
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

    public static void cipherRSA(){

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





    public static String byteToHexStr(byte[] bytes){
        HexFormat hex = HexFormat.ofDelimiter(" ").withUpperCase();
        return  "X'" + hex.formatHex(bytes) + "\'";

    }


}
