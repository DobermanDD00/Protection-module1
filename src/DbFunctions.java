
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;


public class DbFunctions {

    //    private static final String url = "jdbc:h2:tcp://localhost/~/test";
    private static final String url = "jdbc:h2:D:/Project/JavaProject/Protection-module1//dirDb/db2";
    private static final String user = "sa";
    private static final String password = "";
    private static Connection con;
    private static Statement stat;
    private static ResultSet rs;


    public static void main(String[] args) {

//        boolean bool = addUserDb("login10", "masha2","a", "a", "8798", "13879", "role1 role2 role3", "wporj");
        String str1 = "drop all objects;";
        updateDb(str1);
        String str2 = """
                CREATE TABLE roles
                (
                    id int primary key auto_increment,
                    name varchar(256) not null unique
                );

                CREATE TABLE users
                (
                    id int primary key auto_increment,
                    name varchar(256) not null unique,
                    role varchar(256),
                    foreign key (role) references roles (name) on update cascade on delete set null,
                    lead varchar(256),
                    foreign key (lead) references users (name) on update cascade on delete set null,
                    sign varbinary(32) not null,
                    keyPublic varbinary(512) not null,
                    keyPrivate varbinary(512) not null,
                    salt varbinary(32) not null,
                    password varbinary(32) not null
                );

                CREATE TABLE statuses
                (
                    id int primary key auto_increment,
                    number int not null unique,
                    name varchar(256) not null unique
                );


                CREATE TABLE tasks
                (
                    id int primary key auto_increment,
                    name varchar(256) not null unique,
                    description text,
                    lead varchar(256),
                    foreign key (lead) references users (name) on update cascade on delete set null,
                    performer varchar(256),
                    foreign key (performer) references users (name)  on update cascade on delete set null,
                    signlead varbinary(32),
                    status varchar(256),
                    foreign key (status) references statuses (name) on update cascade on delete set null,
                    report text,
                    signperformer varbinary(32)
                );""";
        updateDb(str2);
        String str3 = """
                INSERT INTO ROLES
                (name)
                values
                    ('Работник'), ('Руководитель младшего звена'), ('Руководитель среднего звена'), ('Руководитель высшего звена'), ('Владелец');

                INSERT INTO STATUSES
                (number, name)
                values
                    (1, 'Отправлена на выполнение'), (2, 'Возвращена для корректировки'), (3, 'В процессе выполнения'), (4, 'Отправлено на проверку'), (5, 'Завершена'), (6, 'Возвращена на доработку');

                INSERT INTO USERS
                (NAME, ROLE, LEAD, SIGN, KEYPUBLIC, KEYPRIVATE, SALT, PASSWORD)
                VALUES ('Виктория', NULL , NULL, '123', '123','123','123','123'),
                       ('Владимир', NULL , NULL, '123', '123','123','123','123'),
                       ('Александр', NULL , NULL, '123', '123','123','123','123'),
                       ('Григорий', NULL , NULL, '123', '123','123','123','123'),
                       ('Роман', NULL , NULL, '123', '123','123','123','123');

                UPDATE USERS
                SET ROLE = 'Работник', LEAD = 'Александр'
                WHERE NAME = 'Виктория' OR NAME = 'Владимир';

                UPDATE USERS
                SET ROLE = 'Руководитель младшего звена', LEAD = 'Григорий'
                WHERE NAME = 'Александр';

                UPDATE USERS
                SET ROLE = 'Руководитель среднего звена', LEAD = 'Роман'
                WHERE NAME = 'Григорий';

                UPDATE USERS
                SET ROLE = 'Руководитель высшего звена'
                WHERE NAME = 'Роман';



                INSERT INTO TASKS
                (NAME, DESCRIPTION, lead, performer, SIGNlead, STATUS, REPORT, SIGNperformer)
                values
                    ('Починить токарный станок', 'Починить токарный станок 6Б46', null, null, '123', 'Отправлена на выполнение','', '234'),
                    ('Починить фрезерный станок', 'Починить фрезерный станок 3ГСЕ51', null, null, '123', 'Отправлена на выполнение','', '234'),
                    ('Наладить работы сборочной линии', 'Починить токарные и фрезерные станки', null, null, '123', 'Отправлена на выполнение','', '234'),
                    ('Заключить договора с заводом БелМаш', 'Встретиться с представителем БелМаш и обсудить условия', null, null, '123', 'Отправлена на выполнение','', '234'),
                    ('Продумать курс развития компании', 'Рассмотреть вопрос масштабирования компании', null, null, '123', 'Отправлена на выполнение','', '234');

                UPDATE TASKS
                SET lead = 'Виктория', performer = 'Александр', STATUS = 'Отправлена на выполнение'
                WHERE NAME = 'Починить токарный станок';

                UPDATE TASKS
                SET lead = 'Владимир', performer = 'Александр', STATUS = 'Отправлена на выполнение'
                WHERE NAME = 'Починить фрезерный станок';

                UPDATE TASKS
                SET lead = 'Александр', performer = 'Григорий', STATUS = 'В процессе выполнения'
                WHERE NAME = 'Наладить работы сборочной линии';

                UPDATE TASKS
                SET lead = 'Григорий', performer = 'Роман', STATUS = 'В процессе выполнения'
                WHERE NAME = 'Заключить договора с заводом БелМаш';

                UPDATE TASKS
                SET lead = 'Роман', performer = 'Роман', STATUS = 'В процессе выполнения'
                WHERE NAME = 'Продумать курс развития компании';""";
        updateDb(str3);

//        createBasicInformation();
//        boolean bool = addReportDb("report9", "text", "", "user1", "user2 user3 user4","role1 role2 role3", "user7 user9");
//        LocalDateTime deadline = LocalDateTime.of(2023,5,21,19,7,7);
//        boolean bool = addTaskDb("Подготовить годовой отчет3", "Быстро!", "pasha", "masha misha", "role1 role3",  "user1 user2", "rsfe", deadline, "do task");

//        boolean bool = authentication("login8", "13879");
//        createRolesUser(1, "role1 role2");
//        System.out.println(bool);


//        cipherAES();
//        cipherRSA();
    }

    public static boolean updateDb(String sqlQuery)//todo Спросить Олега как правильно обрабатывать исключения и возвращать информацию о результате работы ф-ии
    {
        boolean ret = true;
        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(url, user, password);
            stat = con.createStatement();

            stat.executeUpdate(sqlQuery);


        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Исключение, БД не была обновлена");
            ret = false;
            throw new RuntimeException(e);//todo sql1
        }
        try {
            stat.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("Исключение, соединение с БД не закрыто");
            ret = false;
            throw new RuntimeException(e);
        }

        return ret;
    }

    public static ResultSet requestToDb(String sqlQuery)// todo Спросить Олега как правильно тут возвращать значение и закрывать соединение
    {
        ResultSet ret;
        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(url, user, password);
            stat = con.createStatement();

            rs = stat.executeQuery(sqlQuery);
            ret = rs;
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
            System.out.println("Исключение, соединение с БД не закрыто");
            throw new RuntimeException(e);
        }

        return ret;


    }
    public static void getUserNameById(int id)// Пример для запросов к БД
    {
        String sqlQuery = "SELECT * from USERS\n" +
                "where ID = " + id + " LIMIT 1;";

        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(url, user, password);
            stat = con.createStatement();

            rs = stat.executeQuery(sqlQuery);

            if (rs.next()) {
                String name = rs.getString("NAME");
                System.out.println(name);
            }


        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Исключение, данные из БД не получены");
            throw new RuntimeException(e);
        }

        try {
            rs.close();
            stat.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("Исключение, соединение с БД не закрыто");
            throw new RuntimeException(e);
        }
    }

//    public static boolean addUserDb(String login, String firstName, String lastName, String middleName,
//                                    String textField, String strPassUser, String roles, String status) {
//        boolean ret = true;
//
//        try {
//            Class.forName("org.h2.Driver");
//            con = DriverManager.getConnection(url, user, password);
//            stat = con.createStatement();
//
//            rs = stat.executeQuery("SELECT ID FROM USERS ORDER BY ID DESC LIMIT 1;");
//            int idNext = 0;
//            if (rs.next()) {
//                idNext = rs.getInt("ID") + 1;
//            }
//            rs.close();
//
//
//            byte[] salt;
//            String strSaltHex;
//            try {// Генерация соли
//                salt = new byte[32];
//                SecureRandom randByte = new SecureRandom(SecureRandom.getSeed(32));
//                randByte.getInstanceStrong().nextBytes(salt);
//                strSaltHex = ChangeFormat.byteToHexStr(salt);
//            } catch (NoSuchAlgorithmException e) {
//                throw new RuntimeException(e);
//            }
//
//
//            byte[] saltHash;
//            String strSaltHexHash;
//            try {//Генерация хеша соли и пароля пользователя
//                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
//                messageDigest.update(salt);
//                messageDigest.update(strPassUser.getBytes(StandardCharsets.UTF_8));
//                saltHash = messageDigest.digest();
//
//                strSaltHexHash = ChangeFormat.byteToHexStr(saltHash);
//                //System.out.println(strSaltHexHash);
//
//            } catch (NoSuchAlgorithmException e) {
//                throw new RuntimeException(e);
//            }
//
//
//            stat.executeUpdate("INSERT INTO USERS (ID, LOGIN, FIRST_NAME, lAST_NAME, MIDDLE_NAME, " +
//                    "TEXT_FIELD, SALT, SALT_HASH, ROLES, STATUS)" +
//                    " VALUES (" + idNext + ", '" + login + "', '" + firstName + "', '" + lastName + "', '" + middleName + "', " +
//                    "'" + textField + "', " + strSaltHex + ", " + strSaltHexHash + ", '" + roles + "', '" + status + "');");
//
//            createRolesUser(idNext, roles);
//
//
//        } catch (SQLException | ClassNotFoundException e) {
//            System.out.println("Исключение: Пользователь не добавлен, скорее всего неправильный ввод, скорее всего логин");
//            ret = false;
//            throw new RuntimeException(e);//todo sql1
//        }
//
//        try {
//            stat.close();
//            con.close();
//
//        } catch (SQLException e) {
//            System.out.println("Исключение соединение с БД не закрыто");
//            throw new RuntimeException(e);
//        }
//
//        return ret;
//    }
    public static boolean addNewUser(String name, String role, String lead, String password) {
        boolean ret = true;

        byte[] salt = Security.generateSalt(32);
        byte[] hashSaltPass = Security.generateHashSha256(salt, password.getBytes(StandardCharsets.UTF_8));


        String sqlQuery = "INSERT INTO USERS\n" +
                "(NAME, ROLE, LEAD, SIGN, KEYPUBLIC, KEYPRIVATE, SALT, PASSWORD)\n" +
                "VALUES ('"+name+"', '"+role+"', '"+lead+"', '123', '123','123'," +ChangeFormat.byteToHexStr(salt)+","+ChangeFormat.byteToHexStr(hashSaltPass)+")";

        ret = updateDb(sqlQuery);
        return ret;
    }

    public static boolean addNewTask(String name, String description, String lead, String performer){
        boolean ret = true;


        String sqlQuery = "INSERT INTO TASKS (NAME, DESCRIPTION, LEAD, PERFORMER, SIGNLEAD, STATUS, REPORT, SIGNPERFORMER) \n" +
                "VALUES ( '"+name+"', '"+description+"', '"+lead+"', '"+performer+"', '123', 'Отправлена на выполнение', '', '456'  );";


        ret = updateDb(sqlQuery);
        return ret;

    }
    public static boolean addReportToTask(String name, String report){
        boolean ret = true;

        String oldReport = null;
        String sqlQuery = "SELECT REPORT from TASKS\n" +
                "where name = '" + name + "' LIMIT 1;";

        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(url, user, password);
            stat = con.createStatement();

            rs = stat.executeQuery(sqlQuery);

            if (rs.next()) {
                oldReport = rs.getString("REPORT");
            }


        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Исключение, данные из БД не получены");
            ret = false;
            throw new RuntimeException(e);
        }

        try {
            rs.close();
            stat.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("Исключение, соединение с БД не закрыто");
            ret = false;
            throw new RuntimeException(e);
        }

        String newReport = oldReport + "\n\n" + report;
        sqlQuery = "UPDATE TASKS\n" +
                "SET REPORT = '"+newReport+"'\n" +
                "WHERE NAME = '"+name+"';";


        ret = updateDb(sqlQuery);
        return ret;
    }




    public static boolean createRolesUser(int idUser, String roles) {
        // roles - каждая роль - одно слово, роли разделены пробелом, внутри одного слова допускаются символы разделения (,./-_+ и тд)

        boolean ret = true;

        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(url, user, password);
            stat = con.createStatement();

            String[] role = roles.split(" ");

            StringBuilder sqlRequestBuilder = new StringBuilder();
            sqlRequestBuilder.append("CREATE TABLE USER_" + Integer.toString(idUser) + " (ROLE VARCHAR(128));");
            for (String currentRole : role) {
                sqlRequestBuilder.append("INSERT INTO USER_" + Integer.toString(idUser) + " (ROLE) VALUES ('" + currentRole + "');");
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

    public static boolean addReportDb(String name, String textField, String tegs, String sender, String receivers, String roles, String accessOthers) {
        boolean ret = true;

        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(url, user, password);
            stat = con.createStatement();

            rs = stat.executeQuery("SELECT ID FROM REPORTS ORDER BY ID DESC LIMIT 1;");
            int idNext = 0;
            if (rs.next()) {
                idNext = rs.getInt("ID") + 1;
            }
            rs.close();

            Date dateNow = new Date();
            SimpleDateFormat formatDateNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


            stat.executeUpdate("INSERT INTO REPORTS (ID, NAME, TEXT_FIELD, TEGS, SENDER, RECEIVERS, DATETIMECREATION)" +
                    " VALUES ('" + idNext + "', '" + name + "', '" + textField + "', '" + tegs + "', '" + sender + "', '" + receivers + "', '" + formatDateNow.format(dateNow) + "');");

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

    public static boolean createReportAccess(int idReport, String roles, String receivers, String accessOthers) {
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
            sqlRequestBuilder.append("CREATE TABLE REPORT_" + Integer.toString(idReport) + " (ROLE VARCHAR(128), RECEIVER VARCHAR(128), ACCESS_OTHER VARCHAR(128));");


            for (String currentRole : role) {//todo optimize 1 code
                sqlRequestBuilder.append("INSERT INTO REPORT_" + Integer.toString(idReport) + " (ROLE) VALUES ('" + currentRole + "');");
            }
            for (String currentReceivers : receiver) {
                sqlRequestBuilder.append("INSERT INTO REPORT_" + Integer.toString(idReport) + " (RECEIVER) VALUES ('" + currentReceivers + "');");
            }
            for (String currentAccessOthers : accessOther) {
                sqlRequestBuilder.append("INSERT INTO REPORT_" + Integer.toString(idReport) + " (ACCESS_OTHER) VALUES ('" + currentAccessOthers + "');");
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
            System.out.println("Исключение: соединение с БД не закрыто");
            throw new RuntimeException(e);
        }

        return ret;
    }


    public static boolean addTaskDb(String name, String textField, String lead, String workers, String roles, String accessOthers, String tegs, LocalDateTime datetimeDeadline, String status) {
        boolean ret = true;

        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(url, user, password);
            stat = con.createStatement();

            rs = stat.executeQuery("SELECT ID FROM TASKS ORDER BY ID DESC LIMIT 1;");
            int idNext = 0;
            if (rs.next()) {
                idNext = rs.getInt("ID") + 1;
            }
            rs.close();


            Date dateNow = new Date();
            SimpleDateFormat formatDateNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            stat.executeUpdate("INSERT INTO TASKS (ID , NAME, TEXT_FIELD  ," +
                    "lead , WORKERS , TEGS ," +
                    "DATETIMEOFCREATION, DATETIMEDEADLINE, STATUS)" +
                    " VALUES ('" + idNext + "', '" + name + "', '" + textField + "', '" +
                    lead + "', '" + workers + "', '" + tegs + "', '" +
                    formatDateNow.format(dateNow) + "', '" + datetimeDeadline + "', '" + status + "');");

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

    public static boolean createTaskAccess(int idTask, String roles, String workers, String accessOthers) {
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
            sqlRequestBuilder.append("CREATE TABLE TASK_" + Integer.toString(idTask) + " (ROLE VARCHAR(128), WORKER VARCHAR(128), ACCESS_OTHER VARCHAR(128));");


            for (String currentRole : role) {//todo optimize 1 code
                sqlRequestBuilder.append("INSERT INTO TASK_" + Integer.toString(idTask) + " (ROLE) VALUES ('" + currentRole + "');");
            }
            for (String currentReceivers : worker) {
                sqlRequestBuilder.append("INSERT INTO TASK_" + Integer.toString(idTask) + " (WORKER) VALUES ('" + currentReceivers + "');");
            }
            for (String currentAccessOthers : accessOther) {
                sqlRequestBuilder.append("INSERT INTO TASK_" + Integer.toString(idTask) + " (ACCESS_OTHER) VALUES ('" + currentAccessOthers + "');");
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



    public static boolean authentication(String login, String pass) {
        boolean ret = false;


        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(url, user, password);
            stat = con.createStatement();

            rs = stat.executeQuery("SELECT SALT, SALT_HASH FROM USERS WHERE LOGIN = '" + login + "' LIMIT 1;");

            if (rs.next()) {
                byte[] salt = rs.getBytes("SALT");
                byte[] saltHash = rs.getBytes("SALT_HASH");

                try {

                    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                    messageDigest.update(salt);
                    messageDigest.update(pass.getBytes("UTF-8"));
                    byte[] saltHashCurrent = messageDigest.digest();

                    ret = Arrays.equals(saltHashCurrent, saltHash);


                } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }

            }
            rs.close();
        } catch (ClassNotFoundException | SQLException e) {
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

    public static void test1() {
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

    public static void test2() {


        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(url, user, password);
            stat = con.createStatement();

            byte[] salt = new byte[32];
            SecureRandom randByte = new SecureRandom(SecureRandom.getSeed(32));
            randByte.getInstanceStrong().nextBytes(salt);

            String strHex = ChangeFormat.byteToHexStr(salt);

            //strHex = "X'01 bc 2a'";
            System.out.println(strHex);


            try {
                byte[] data1 = "0123456789".getBytes("UTF-8");
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                byte[] digest = messageDigest.digest(data1);
                String strSaltHexHash = ChangeFormat.byteToHexStr(digest);
                System.out.println(strSaltHexHash);

            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }


            stat.executeUpdate("INSERT INTO TABLE2 (SALT) VALUES (" + strHex + ")");


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





}
