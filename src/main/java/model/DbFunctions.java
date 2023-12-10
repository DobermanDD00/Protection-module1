package model;

import tools.ChangeFormat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import static tools.ChangeFormat.byteToHexStr;


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
                    idRole int,
                    idLead int,
                    keyPublic varbinary(600)
                );

                CREATE TABLE statuses
                (
                    id int primary key auto_increment,
                    name varchar(256) not null unique
                );


                CREATE TABLE tasks
                (
                    id int primary key auto_increment,
                    name varchar(256) not null unique,
                    description varbinary(max),
                    idLead int,
                    idPerformer int,
                    signlead varbinary(32),
                    status int,
                    report varbinary(max),
                    signperformer varbinary(32)
                );""";
        updateDb(str2);
        String str3 = """
                INSERT INTO ROLES
                (name)
                values
                    ('Работник'), ('Руководитель младшего звена'), ('Руководитель среднего звена'), ('Руководитель высшего звена'), ('Владелец'), ('Администратор');

                INSERT INTO STATUSES
                (name)
                values
                    ( 'Отправлена на выполнение'), ('Возвращена для корректировки'), ('В процессе выполнения'), ('Отправлено на проверку'), ('Завершена'), ('Возвращена на доработку'), ('Прочее');""";
        updateDb(str3);

        // Добавление пользователей

//        addNewUser(new User(1, "Александр", 6, 1, Security.generateRandomBytes(512), null));
//        addNewUser(new User(2, "Роман", 4, 1, Security.generateRandomBytes(512), null));
//        addNewUser(new User(3, "Григорий", 3, 2, Security.generateRandomBytes(512), null));
//        addNewUser(new User(4, "Николай", 2, 3, Security.generateRandomBytes(512), null));
//        addNewUser(new User(5, "Виктория", 1, 4, Security.generateRandomBytes(512), null));
//        addNewUser(new User(6, "Владимир", 1, 4, Security.generateRandomBytes(512), null));

//         Добавление задач
//        addNewTask("Починить токарный станок", "Починить токарный станок 6Б46", "Николай", "Виктория");
//        addNewTask("Починить фрезерный станок", "Починить фрезерный станок 3ГСЕ51", "Николай", "Владимир");
//        addNewTask("Наладить работы сборочной линии", "Починить токарные и фрезерные станки", "Григорий", "Николай");
//        addNewTask("Заключить договора с заводом БелМаш", "Встретиться с представителем БелМаш и обсудить условия", "Роман", "Григорий");
//        addNewTask("Продумать курс развития компании", "Рассмотреть вопрос масштабирования компании", "Роман", "Роман");

    }

    public static User addNewUser(User user)
    {


        String sqlQuery = "insert into USERS\n" +
                "(id, name, idRole, idLead, keyPublic)\n" +
                "VALUES ("+user.getId()+",'"+user.getName()+"', "+user.getIdRole()+", "+user.getIdLead()+","+ ChangeFormat.byteToHexStr(user.getKeyPublic()) +"  );";

        updateDb(sqlQuery);
        return user;
    }

    public static void updateUser(User user)
    {
        String sqlQuery = "update USERS\n" +
                "set NAME = '"+user.getName()+"',\n" +
                "    IDROLE = "+user.getIdRole()+",\n" +
                "    IDLEAD = "+user.getIdLead()+",\n" +
                "    KEYPUBLIC = "+ChangeFormat.byteToHexStr(user.getKeyPublic())+"\n" +
                "where ID = "+user.getId()+";";

        updateDb(sqlQuery);
    }

    public static User getUser(int id)
    {
        User user = null;
        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(url, DbFunctions.user, password);
            stat = con.createStatement();

            rs = stat.executeQuery("SELECT * FROM USERS WHERE ID = '" + id + "' LIMIT 1;");

            if (rs.next()) {

                user = new User(
                        rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getInt("idROLE"),
                        rs.getInt("idLEAD"),
                        rs.getBytes("KEYPUBLIC"),
                        null
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

        return user;


    }
    public static User getUser(String name)
    {
        User user = null;
        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(url, DbFunctions.user, password);
            stat = con.createStatement();

            rs = stat.executeQuery("SELECT * FROM USERS WHERE NAME = '" + name + "' LIMIT 1;");

            if (rs.next()) {

                user = new User(
                        rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getInt("idROLE"),
                        rs.getInt("idLEAD"),
                        rs.getBytes("KEYPUBLIC"),
                        null
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

        return user;


    }

    public static int getFieldInt(String tableName, String columnForFind, String dataForFind, String columnForResult)
    {
        String sqlQuery = "SELECT "+columnForResult+" from " + tableName + "\n" +
                "where "+columnForFind+" = '"+dataForFind+"' LIMIT 1;";
        int field = -1;

        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(url, user, password);
            stat = con.createStatement();

            rs = stat.executeQuery(sqlQuery);

            if (rs.next()) {
                field = rs.getInt(columnForResult);
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
        return field;
    }
    public static String getFieldString(String tableName, String columnForFind, String dataForFind, String columnForResult)
    {
        String sqlQuery = "SELECT "+columnForResult+" from " + tableName + "\n" +
                "where "+columnForFind+" = '"+dataForFind+"' LIMIT 1;";
        String field = "";

        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(url, user, password);
            stat = con.createStatement();

            rs = stat.executeQuery(sqlQuery);

            if (rs.next()) {
                field = rs.getString(columnForResult);
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
        return field;
    }

    public static int getNewIdUser()
    {
        return getNewId("Users");
    }
    public static int getNewId(String tableName)// Пример для запросов к БД
    {
        String sqlQuery = "SELECT ID from " + tableName + "\n" +
                "ORDER BY ID DESC LIMIT 1;";
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
        return id + 1;
    }

    public static void updateDb(String sqlQuery)//todo Исключения, вывод
    {
        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection(url, user, password);
            stat = con.createStatement();

            stat.executeUpdate(sqlQuery);


        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Исключение, БД не была обновлена");
            throw new RuntimeException(e);//todo sql1
        }
        try {
            stat.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("Исключение, соединение с БД не закрыто");
            throw new RuntimeException(e);
        }


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


//    public static void getUserNameById(int id)// Пример для запросов к БД
//    {
//        String sqlQuery = "SELECT NAME from USERS\n" +
//                "where ID = " + id + " LIMIT 1;";
//
//        try {
//            Class.forName("org.h2.Driver");
//            con = DriverManager.getConnection(url, user, password);
//            stat = con.createStatement();
//
//            rs = stat.executeQuery(sqlQuery);
//
//            if (rs.next()) {
//                String name = rs.getString("NAME");
//                System.out.println(name);
//            }
//
//
//        } catch (ClassNotFoundException | SQLException e) {
//            System.out.println("Исключение, данные из БД не получены");
//            throw new RuntimeException(e);
//        }
//
//        try {
//            rs.close();
//            stat.close();
//            con.close();
//
//        } catch (SQLException e) {
//            System.out.println("Исключение, соединение с БД не закрыто");
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static int getIdByName(String tableName, String name)// Пример для запросов к БД
//    {
//        String sqlQuery = "SELECT ID from " + tableName + "\n" +
//                "where NAME = '" + name + "' LIMIT 1;";
//        int id = -1;
//
//        try {
//            Class.forName("org.h2.Driver");
//            con = DriverManager.getConnection(url, user, password);
//            stat = con.createStatement();
//
//            rs = stat.executeQuery(sqlQuery);
//
//            if (rs.next()) {
//                id = rs.getInt("ID");
//            }
//
//
//        } catch (ClassNotFoundException | SQLException e) {
//            System.out.println("Исключение, данные из БД не получены");
//            throw new RuntimeException(e);
//        }
//
//        try {
//            rs.close();
//            stat.close();
//            con.close();
//
//        } catch (SQLException e) {
//            System.out.println("Исключение, соединение с БД не закрыто");
//            throw new RuntimeException(e);
//        }
//        return id;
//    }
//
//
//
//    public static Task getTask(String name)    //todo Протестить
//    {
//        Task taskDb = null;
//        try {
//            Class.forName("org.h2.Driver");
//            con = DriverManager.getConnection(url, user, password);
//            stat = con.createStatement();
//
//            rs = stat.executeQuery("SELECT * FROM TASKS WHERE NAME = '" + name + "' LIMIT 1;");
//
//            if (rs.next()) {
//
//                taskDb = new Task(
//                        rs.getInt("ID"),
//                        rs.getString("NAME"),
//                        rs.getString("DESCRIPTION"),
//                        rs.getString("LEAD"),
//                        rs.getString("PERFORMER"),
//                        rs.getBytes("SIGNLEAD"),
//                        rs.getString("STATUS"),
//                        rs.getString("REPORT"),
//                        rs.getBytes("SIGNPERFORMER")
//                );
//
//
//            } else {
//                return null;
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
//        return taskDb;
//
//
//    }
//
//    public static List<Task> getTasksLead(int idLead)
//    {
//        return getTasks("idLead", Integer.toString(idLead));
//    }
//    public static List<Task> getTasksPerformer(int idLead)
//    {
//        return getTasks("idPerformer", Integer.toString(idLead));
//    }
//
//    private static List<Task> getTasks(String columnName, String data) {
//        return getTasks("SELECT * FROM TASKS WHERE '" + columnName + "' = '" + data + "'");
//
//    }
//
//    private static List<Task> getTasks(String sqlQuery) {
//        List<Task> tasksDb = null;
//        try {
//            Class.forName("org.h2.Driver");
//            con = DriverManager.getConnection(url, user, password);
//            stat = con.createStatement();
//
//            rs = stat.executeQuery(sqlQuery);
//
//            while (rs.next()) {
//                if (tasksDb == null)
//                    tasksDb = new ArrayList<>();
//
//                tasksDb.add(new Task(
//                        rs.getInt("ID"),
//                        rs.getString("NAME"),
//                        rs.getString("DESCRIPTION"),
//                        rs.getString("LEAD"),
//                        rs.getString("PERFORMER"),
//                        rs.getBytes("SIGNLEAD"),
//                        rs.getString("STATUS"),
//                        rs.getString("REPORT"),
//                        rs.getBytes("SIGNPERFORMER")
//                ));
//
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
//        return tasksDb;
//
//
//    }
//
//
//
//    public static Task accessToTask(String columnName, String data, String user) {// TODO добавить иерархию
//        List<Task> listTasks = getTasks("SELECT * FROM TASKS\n" +
//                "WHERE " + columnName + " = '" + data + "' AND (LEAD = '" + user + "' OR PERFORMER = '" + user + "')LIMIT 1;\n");
//        if (listTasks == null)
//            return null;
//
//
//        ListIterator<Task> iter = listTasks.listIterator();
//        if (iter.hasNext()) {
//            return iter.next();
//
//        }
//        return null;
//
//
//    }
//
//    public static User addNewUser(String name, String role, String lead, String password) {
//
//        String sqlQuery = "INSERT INTO USERS\n" +
//                "(NAME, ROLE, LEAD, SIGN, KEYPUBLIC, KEYPRIVATE, SALT, hashSaltPassword)\n" +
//                "VALUES ('" + name + "','','','','','','','');";
//        updateDb(sqlQuery); //todo Как здесь делать проверки? Раньше ф-ия возвращала t or f и это уже возвращала addNewUser
//
//        User user = getUser(name);
//
//        updateUser(user, name, role, lead, password);
//
//
//        return user;
//    }
//
//    public static Task addNewTask(String name, String description, String lead, String performer)
//    {
//
//        String sqlQuery = "INSERT INTO TASKS (NAME, DESCRIPTION, LEAD, PERFORMER, SIGNLEAD, STATUS, REPORT, SIGNPERFORMER) " +
//                "VALUES ( '" + name + "', '','','','','','','');";
//
//        updateDb(sqlQuery); //todo Как здесь делать проверки? Раньше ф-ия возвращала t or f и это уже возвращала addNewTask
//
//        Task task = getTask(name);
//
//        String status = "Отправлена на выполнение";
//        String report = "";
//
//        updateTask(task, name, description, lead, performer, status, report);
//
//        return task;
//
//    }

//
//    public static void updateTask(Task task, String name, String description, String lead, String performer, String status, String report)
//    {
//        task.setName(name);
//        task.setDescription(description);
//        task.setLead(lead);
//        task.setPerformer(performer);
//        task.setStatus(status);
//        task.setReport(report);
//
//        Security.setGeneratedSignLead(task);
//        Security.setGeneratedSignPerformer(task);
//
//        String sqlQuery = "UPDATE TASKS\n" +
//                "SET NAME = '" + task.getName() + "', DESCRIPTION = '" + task.getDescription() + "', LEAD = '" + task.getLead() + "', PERFORMER = '" + task.getPerformer() + "', SIGNLEAD = '" + task.getSignLead() + "', STATUS = '" + task.getStatus() + "', REPORT = '" + task.getReport() + "', SIGNPERFORMER = '" + task.getSignPerformer() + "'\n" +
//                "WHERE ID = '" + task.getId() + "'\n";
//
//        updateDb(sqlQuery);
//
//    }

}
