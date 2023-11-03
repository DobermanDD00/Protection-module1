import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


public class DbFunctions {

    //    private static final String url = "jdbc:h2:tcp://localhost/~/test";
    private static final String url = "jdbc:h2:D:/Project/JavaProject/Protection-module1//dirDb/db2";
    private static final String user = "sa";
    private static final String password = "";
    private static Connection con;
    private static Statement stat;
    private static ResultSet rs;


    public static void main(String[] args) {
        initializeDb();


    }

    public static void initializeDb() {
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
                    hashSaltPassword varbinary(32) not null
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
                    ('Работник'), ('Руководитель младшего звена'), ('Руководитель среднего звена'), ('Руководитель высшего звена'), ('Владелец'), ('Администратор');

                INSERT INTO STATUSES
                (number, name)
                values
                    (1, 'Отправлена на выполнение'), (2, 'Возвращена для корректировки'), (3, 'В процессе выполнения'), (4, 'Отправлено на проверку'), (5, 'Завершена'), (6, 'Возвращена на доработку'), (7, 'Прочее');""";
        updateDb(str3);

        // Добавление пользователей
        byte[] salt;
        addNewUser("Александр", "Администратор", "Александр", "12345");
        addNewUser("Роман", "Руководитель высшего звена", "Роман", "12345");
        addNewUser("Григорий", "Руководитель среднего звена", "Роман", "12345");
        addNewUser("Николай", "Руководитель младшего звена", "Григорий", "12345");
        addNewUser("Виктория", "Работник", "Николай", "12345");
        addNewUser("Владимир", "Работник", "Николай", "12345");

        // Добавление задач
        addNewTask("Починить токарный станок", "Починить токарный станок 6Б46", "Николай", "Виктория");
        addNewTask("Починить фрезерный станок", "Починить фрезерный станок 3ГСЕ51", "Николай", "Владимир");
        addNewTask("Наладить работы сборочной линии", "Починить токарные и фрезерные станки", "Григорий", "Николай");
        addNewTask("Заключить договора с заводом БелМаш", "Встретиться с представителем БелМаш и обсудить условия", "Роман", "Григорий");
        addNewTask("Продумать курс развития компании", "Рассмотреть вопрос масштабирования компании", "Роман", "Роман");

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

    public static ResultSet requestToDb(String sqlQuery)//todo НЕ РАБОТАЕТ Спросить Олега как правильно тут возвращать значение и закрывать соединение
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
        String sqlQuery = "SELECT NAME from USERS\n" +
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

    public static int getIdByName(String tableName, String name)// Пример для запросов к БД
    {
        String sqlQuery = "SELECT ID from " + tableName + "\n" +
                "where NAME = '" + name + "' LIMIT 1;";
        int id = -1;

        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(url, user, password);
            stat = con.createStatement();

            rs = stat.executeQuery(sqlQuery);

            if (rs.next()) {
                id = rs.getInt("ID");
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
        return id;
    }

    public static User getUser(String name)    //todo Протестить
    {
        User userDb = null;
        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(url, user, password);
            stat = con.createStatement();

            rs = stat.executeQuery("SELECT * FROM USERS WHERE NAME = '" + name + "' LIMIT 1;");

            if (rs.next()) {

                userDb = new User(
                        rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getString("ROLE"),
                        rs.getString("LEAD"),
                        rs.getBytes("SIGN"),
                        rs.getBytes("KEYPUBLIC"),
                        rs.getBytes("KEYPRIVATE"),
                        rs.getBytes("SALT"),
                        rs.getBytes("HASHSALTPASSWORD")
                );


            } else {
                return null;
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

        return userDb;


    }

    public static Task getTask(String name)    //todo Протестить
    {
        Task taskDb = null;
        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(url, user, password);
            stat = con.createStatement();

            rs = stat.executeQuery("SELECT * FROM TASK WHERE NAME = '" + name + "' LIMIT 1;");

            if (rs.next()) {

                taskDb = new Task(
                        rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getString("DESCRIPTION"),
                        rs.getString("LEAD"),
                        rs.getString("PERFORMER"),
                        rs.getBytes("SIGNLEAD"),
                        rs.getString("STATUS"),
                        rs.getString("REPORT"),
                        rs.getBytes("SIGNPERFORMER")
                );


            } else {
                return null;
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

        return taskDb;


    }

    public static List<Task> getTasks(String columnName, String data) {
        List<Task> tasksDb = null;
        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(url, user, password);
            stat = con.createStatement();

            rs = stat.executeQuery("SELECT * FROM TASKS WHERE '" + columnName + "' = '" + data + "'");

            while (rs.next()) {
                if (tasksDb == null)
                    tasksDb = new ArrayList<>();

                tasksDb.add(new Task(
                        rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getString("DESCRIPTION"),
                        rs.getString("LEAD"),
                        rs.getString("PERFORMER"),
                        rs.getBytes("SIGNLEAD"),
                        rs.getString("STATUS"),
                        rs.getString("REPORT"),
                        rs.getBytes("SIGNPERFORMER")
                ));


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

        return tasksDb;


    }

    public static List<Task> getTasks(String sqlQuery) {
        List<Task> tasksDb = null;
        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(url, user, password);
            stat = con.createStatement();

            rs = stat.executeQuery(sqlQuery);

            while (rs.next()) {
                if (tasksDb == null)
                    tasksDb = new ArrayList<>();

                tasksDb.add(new Task(
                        rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getString("DESCRIPTION"),
                        rs.getString("LEAD"),
                        rs.getString("PERFORMER"),
                        rs.getBytes("SIGNLEAD"),
                        rs.getString("STATUS"),
                        rs.getString("REPORT"),
                        rs.getBytes("SIGNPERFORMER")
                ));


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

        return tasksDb;


    }

    public static boolean isExistInDb(String tableName, String columnName, String data) {
        boolean ret = false;

        String sqlQuery = "SELECT ID from " + tableName + "\n" +
                "where " + columnName + " = '" + data + "' LIMIT 1;";


        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(url, user, password);
            stat = con.createStatement();

            rs = stat.executeQuery(sqlQuery);

            if (rs.next())
                ret = true;
            else
                ret = false;


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

        return ret;
    }

    public static Task accessToTask(String columnName, String data, String user) {
        List<Task> listTasks = getTasks("SELECT * FROM TASKS\n" +
                "WHERE " + columnName + " = '" + data + "' AND (LEAD = '" + user + "' OR PERFORMER = '" + user + "')LIMIT 1;\n");
        if (listTasks == null)
            return null;


        ListIterator<Task> iter = listTasks.listIterator();
        if (iter.hasNext()) {
            return iter.next();

        }
        return null;


    }

    public static User addNewUser(String name, String role, String lead, String password) {

        String sqlQuery = "INSERT INTO USERS\n" +
                "(NAME, ROLE, LEAD, SIGN, KEYPUBLIC, KEYPRIVATE, SALT, hashSaltPassword)\n" +
                "VALUES ('" + name + "','','','','','','','');";
        updateDb(sqlQuery); //todo Как здесь делать проверки? Раньше ф-ия возвращала t or f и это уже возвращала addNewUser

        User user = getUser(name);

        updateUser(user, name, role, lead, password);


        return user;
    }

    public static Task addNewTask(String name, String description, String lead, String performer)
    {

        String sqlQuery = "INSERT INTO TASKS (NAME, DESCRIPTION, LEAD, PERFORMER, SIGNLEAD, STATUS, REPORT, SIGNPERFORMER) " +
                "VALUES ( '" + name + "', '','','','','','','');";

        updateDb(sqlQuery); //todo Как здесь делать проверки? Раньше ф-ия возвращала t or f и это уже возвращала addNewTask

        Task task = getTask(name);

        String status = "Отправлена на выполнение";
        String report = "";

        updateTask(task, name, description, lead, performer, status, report);

        return task;

    }
    public static void updateUser(User user, String name, String role, String lead, String password)
    {
        user.setName(name);
        user.setRole(role);
        user.setLead(lead);
        Security.setGenerateKeys(user);
        user.setSalt(Security.generateRandomBytes(32));
        user.setHashSaltPassword(Security.generateHashSha256(user.getSalt(), password.getBytes()));
        Security.setGenerateSign(user);

        String sqlQuery ="UPDATE USERS\n" +
                "SET NAME = '"+user.getName()+"', ROLE = '"+user.getRole()+"', LEAD = '"+user.getLead()+"', SIGN = '"+user.getSign()+"', KEYPUBLIC = '"+user.getKeyPublic()+"', KEYPRIVATE = '"+user.getKeyPrivate()+"', SALT = '"+user.getSalt()+"', hashSaltPassword = '"+user.getHashSaltPassword()+"'\n" +
                "WHERE ID = '"+user.getId()+"'";

        updateDb(sqlQuery);

    }

    public static void updateTask(Task task, String name, String description, String lead, String performer, String status, String report)
    {
        task.setName(name);
        task.setDescription(description);
        task.setLead(lead);
        task.setPerformer(performer);
        task.setStatus(status);
        task.setReport(report);

        Security.setGeneratedSignLead(task);
        Security.setGeneratedSignPerformer(task);

        String sqlQuery = "UPDATE TASKS\n" +
                "SET NAME = '" + task.getName() + "', DESCRIPTION = '" + task.getDescription() + "', LEAD = '" + task.getLead() + "', PERFORMER = '" + task.getPerformer() + "', SIGNLEAD = '" + task.getSignLead() + "', STATUS = '" + task.getStatus() + "', REPORT = '" + task.getReport() + "', SIGNPERFORMER = '" + task.getSignPerformer() + "'\n" +
                "WHERE ID = '" + task.getId() + "'\n";

        updateDb(sqlQuery);

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


    /*__________*/


//    public static User getUser (String name){
//        try {
//            Class.forName("org.h2.Driver");
//            con = DriverManager.getConnection(url, user, password);
//            stat = con.createStatement();
//
//            rs = stat.executeQuery("SELECT SALT, SALT_HASH FROM USERS WHERE LOGIN = '" + login + "' LIMIT 1;");
//
//            if (rs.next()) {
//                byte[] salt = rs.getBytes("SALT");
//                byte[] saltHash = rs.getBytes("SALT_HASH");
//
//                try {
//
//                    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
//                    messageDigest.update(salt);
//                    messageDigest.update(pass.getBytes("UTF-8"));
//                    byte[] saltHashCurrent = messageDigest.digest();
//
//                    ret = Arrays.equals(saltHashCurrent, saltHash);
//
//
//                } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
//                    throw new RuntimeException(e);
//                }
//
//            }
//            rs.close();
//        } catch (ClassNotFoundException | SQLException e) {
//            throw new RuntimeException(e);
//        }
//
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
//        return ret; //todo ret
//
//    }

    /*__________*/


//    public static boolean createRolesUser(int idUser, String roles) {
//        // roles - каждая роль - одно слово, роли разделены пробелом, внутри одного слова допускаются символы разделения (,./-_+ и тд)
//
//        boolean ret = true;
//
//        try {
//            Class.forName("org.h2.Driver");
//            con = DriverManager.getConnection(url, user, password);
//            stat = con.createStatement();
//
//            String[] role = roles.split(" ");
//
//            StringBuilder sqlRequestBuilder = new StringBuilder();
//            sqlRequestBuilder.append("CREATE TABLE USER_" + Integer.toString(idUser) + " (ROLE VARCHAR(128));");
//            for (String currentRole : role) {
//                sqlRequestBuilder.append("INSERT INTO USER_" + Integer.toString(idUser) + " (ROLE) VALUES ('" + currentRole + "');");
//            }
//
//            stat.executeUpdate(sqlRequestBuilder.toString());
//
//        } catch (SQLException | ClassNotFoundException e) {
//            System.out.println("Исключение: Роли не добавлены");
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

    /*__________*/


//    public static boolean addReportDb(String name, String textField, String tegs, String sender, String receivers, String roles, String accessOthers) {
//        boolean ret = true;
//
//        try {
//            Class.forName("org.h2.Driver");
//            con = DriverManager.getConnection(url, user, password);
//            stat = con.createStatement();
//
//            rs = stat.executeQuery("SELECT ID FROM REPORTS ORDER BY ID DESC LIMIT 1;");
//            int idNext = 0;
//            if (rs.next()) {
//                idNext = rs.getInt("ID") + 1;
//            }
//            rs.close();
//
//            Date dateNow = new Date();
//            SimpleDateFormat formatDateNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//
//            stat.executeUpdate("INSERT INTO REPORTS (ID, NAME, TEXT_FIELD, TEGS, SENDER, RECEIVERS, DATETIMECREATION)" +
//                    " VALUES ('" + idNext + "', '" + name + "', '" + textField + "', '" + tegs + "', '" + sender + "', '" + receivers + "', '" + formatDateNow.format(dateNow) + "');");
//
//            createReportAccess(idNext, roles, receivers, accessOthers);
//
//
//        } catch (SQLException | ClassNotFoundException e) {
//            System.out.println("Исключение: Отчет не добавлен");
//
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


    /*__________*/

//    public static boolean createReportAccess(int idReport, String roles, String receivers, String accessOthers) {
//        // roles, receivers, accessOthers - каждая роль - одно слово, роли разделены пробелом, внутри одного слова допускаются символы разделения (,./-_+ и тд)
//
//        boolean ret = true;
//
//        try {
//            Class.forName("org.h2.Driver");
//            con = DriverManager.getConnection(url, user, password);
//            stat = con.createStatement();
//
//
//            String[] receiver = receivers.split(" ");
//            String[] role = roles.split(" ");
//            String[] accessOther = accessOthers.split(" ");
//
//            StringBuilder sqlRequestBuilder = new StringBuilder();
//            sqlRequestBuilder.append("CREATE TABLE REPORT_" + Integer.toString(idReport) + " (ROLE VARCHAR(128), RECEIVER VARCHAR(128), ACCESS_OTHER VARCHAR(128));");
//
//
//            for (String currentRole : role) {//todo optimize 1 code
//                sqlRequestBuilder.append("INSERT INTO REPORT_" + Integer.toString(idReport) + " (ROLE) VALUES ('" + currentRole + "');");
//            }
//            for (String currentReceivers : receiver) {
//                sqlRequestBuilder.append("INSERT INTO REPORT_" + Integer.toString(idReport) + " (RECEIVER) VALUES ('" + currentReceivers + "');");
//            }
//            for (String currentAccessOthers : accessOther) {
//                sqlRequestBuilder.append("INSERT INTO REPORT_" + Integer.toString(idReport) + " (ACCESS_OTHER) VALUES ('" + currentAccessOthers + "');");
//            }
//
//            stat.executeUpdate(sqlRequestBuilder.toString());
//
//        } catch (SQLException | ClassNotFoundException e) {
//            System.out.println("Исключение: Роли не добавлены");
//            ret = false;
//            throw new RuntimeException(e);//todo sql1
//        }
//
//        try {
//            stat.close();
//            con.close();
//
//        } catch (SQLException e) {
//            System.out.println("Исключение: соединение с БД не закрыто");
//            throw new RuntimeException(e);
//        }
//
//        return ret;
//    }

    /*__________*/


//    public static boolean addTaskDb(String name, String textField, String lead, String workers, String roles, String accessOthers, String tegs, LocalDateTime datetimeDeadline, String status) {
//        boolean ret = true;
//
//        try {
//            Class.forName("org.h2.Driver");
//            con = DriverManager.getConnection(url, user, password);
//            stat = con.createStatement();
//
//            rs = stat.executeQuery("SELECT ID FROM TASKS ORDER BY ID DESC LIMIT 1;");
//            int idNext = 0;
//            if (rs.next()) {
//                idNext = rs.getInt("ID") + 1;
//            }
//            rs.close();
//
//
//            Date dateNow = new Date();
//            SimpleDateFormat formatDateNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//            stat.executeUpdate("INSERT INTO TASKS (ID , NAME, TEXT_FIELD  ," +
//                    "lead , WORKERS , TEGS ," +
//                    "DATETIMEOFCREATION, DATETIMEDEADLINE, STATUS)" +
//                    " VALUES ('" + idNext + "', '" + name + "', '" + textField + "', '" +
//                    lead + "', '" + workers + "', '" + tegs + "', '" +
//                    formatDateNow.format(dateNow) + "', '" + datetimeDeadline + "', '" + status + "');");
//
//            createTaskAccess(idNext, roles, workers, accessOthers);
//
//        } catch (SQLException | ClassNotFoundException e) {
//            System.out.println("Исключение: Задача не добавлена");
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
//        return ret;//****
//    }

    /*__________*/

//    public static boolean createTaskAccess(int idTask, String roles, String workers, String accessOthers) {
//        // roles, workers, accessOthers - каждая роль - одно слово, роли разделены пробелом, внутри одного слова допускаются символы разделения (,./-_+ и тд)
//
//        boolean ret = true;
//
//        try {
//            Class.forName("org.h2.Driver");
//            con = DriverManager.getConnection(url, user, password);
//            stat = con.createStatement();
//
//
//            String[] worker = workers.split(" ");
//            String[] role = roles.split(" ");
//            String[] accessOther = accessOthers.split(" ");
//
//            StringBuilder sqlRequestBuilder = new StringBuilder();
//            sqlRequestBuilder.append("CREATE TABLE TASK_" + Integer.toString(idTask) + " (ROLE VARCHAR(128), WORKER VARCHAR(128), ACCESS_OTHER VARCHAR(128));");
//
//
//            for (String currentRole : role) {//todo optimize 1 code
//                sqlRequestBuilder.append("INSERT INTO TASK_" + Integer.toString(idTask) + " (ROLE) VALUES ('" + currentRole + "');");
//            }
//            for (String currentReceivers : worker) {
//                sqlRequestBuilder.append("INSERT INTO TASK_" + Integer.toString(idTask) + " (WORKER) VALUES ('" + currentReceivers + "');");
//            }
//            for (String currentAccessOthers : accessOther) {
//                sqlRequestBuilder.append("INSERT INTO TASK_" + Integer.toString(idTask) + " (ACCESS_OTHER) VALUES ('" + currentAccessOthers + "');");
//            }
//
//            stat.executeUpdate(sqlRequestBuilder.toString());
//
//        } catch (SQLException | ClassNotFoundException e) {
//            System.out.println("Исключение: Роли не добавлены");
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

    /*__________*/


//    public static boolean authentication(String login, String pass) {
//        boolean ret = false;
//
//
//        try {
//            Class.forName("org.h2.Driver");
//            con = DriverManager.getConnection(url, user, password);
//            stat = con.createStatement();
//
//            rs = stat.executeQuery("SELECT SALT, SALT_HASH FROM USERS WHERE LOGIN = '" + login + "' LIMIT 1;");
//
//            if (rs.next()) {
//                byte[] salt = rs.getBytes("SALT");
//                byte[] saltHash = rs.getBytes("SALT_HASH");
//
//                try {
//
//                    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
//                    messageDigest.update(salt);
//                    messageDigest.update(pass.getBytes("UTF-8"));
//                    byte[] saltHashCurrent = messageDigest.digest();
//
//                    ret = Arrays.equals(saltHashCurrent, saltHash);
//
//
//                } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
//                    throw new RuntimeException(e);
//                }
//
//            }
//            rs.close();
//        } catch (ClassNotFoundException | SQLException e) {
//            throw new RuntimeException(e);
//        }
//
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
//        return ret; //todo ret
//    }

    /*__________*/

//    public static void test1() {
//        try {
//            Class.forName("org.h2.Driver");
//            con = DriverManager.getConnection(url, user, password);
//            stat = con.createStatement();
//            String str = "masha";
//            System.out.println("BBBBBBBB");
//
//
//            stat.executeUpdate("CREATE TABLE TABLE4 (ID INT, NAME VARCHAR(128));");
//
//            stat.executeUpdate("INSERT INTO TABLE4 (ID, NAME) VALUES (1, 'MASHA');");
//
////            rs = stat.executeQuery("SELECT ID FROM USERS ORDER BY ID DESC LIMIT 1;");
////
////            if (rs.next()){
////                System.out.println(rs.getInt("ID"));
////            }
////            rs.close();
//
////            while (rs.next()){
////                System.out.println(rs.getString("FIRST_NAME"));
////            }
//
//
//        } catch (SQLException | ClassNotFoundException e) {
//            System.out.println("aaaaaaaaaa");
//            e.printStackTrace();
//
//            //throw new RuntimeException(e);
//        }
//
//
//        try {
//            stat.close();
//            con.close();
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//    }


    /*__________*/
//    public static void test2() {
//
//
//        try {
//            Class.forName("org.h2.Driver");
//            con = DriverManager.getConnection(url, user, password);
//            stat = con.createStatement();
//
//            byte[] salt = new byte[32];
//            SecureRandom randByte = new SecureRandom(SecureRandom.getSeed(32));
//            randByte.getInstanceStrong().nextBytes(salt);
//
//            String strHex = ChangeFormat.byteToHexStr(salt);
//
//            //strHex = "X'01 bc 2a'";
//            System.out.println(strHex);
//
//
//            try {
//                byte[] data1 = "0123456789".getBytes("UTF-8");
//                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
//                byte[] digest = messageDigest.digest(data1);
//                String strSaltHexHash = ChangeFormat.byteToHexStr(digest);
//                System.out.println(strSaltHexHash);
//
//            } catch (UnsupportedEncodingException e) {
//                throw new RuntimeException(e);
//            }
//
//
//            stat.executeUpdate("INSERT INTO TABLE2 (SALT) VALUES (" + strHex + ")");
//
//
//        } catch (SQLException | ClassNotFoundException e) {
//            System.out.println("Исключение: не добавлен");
//            throw new RuntimeException(e);//todo sql1
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
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
//
//    }
    /*__________*/

}
