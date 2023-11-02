

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

public class ConsoleInterface {
    public static User logIn() {
        String name, password;
        Scanner in = new Scanner(System.in);
        System.out.println("Вход в аккаунт:");

        User user1 = null;//*************8
        while (true) {
            System.out.println("Введите логин:");
            name = in.nextLine();
            System.out.println("Введите пароль:");
            password = in.nextLine();
            user1 = Authentication.logIn(name, password);
            if (user1 == null) {
                System.out.println("Ошибка, неправильный логин или пароль");
            }else{
                System.out.println("Добро пожаловать " + user1.getName());
                break;
            }
        }
        return user1;

    }

    public static int mainMenu() {
        System.out.println("Главное меню");
        while (true) {
            System.out.println("Выберете действие:");
            System.out.println("0 - Выйти из программы.");
            System.out.println("1 - Сменить пользователя.");
            System.out.println("2 - Перейти в меню задач.");
            Scanner in = new Scanner(System.in);
            String choice = in.nextLine();
            switch (choice) {
                case "0":
                    return 0;
                case "1":
                    return 1;
                case "2":
                    return 2;

                default:
                    System.out.println("Ошибка, введите другие данные");
            }
        }
    }
    public static int taskMenu() {
        System.out.println("Меню задач");
        while (true) {
            System.out.println("Выберете действие:");
            System.out.println("0 - Выйти в главное меню.");
            System.out.println("1 - Просмотр полученных задач.");
            System.out.println("2 - Просмотр назначенных задач.");
            System.out.println("3 - Создать новую задачу.");
            Scanner in = new Scanner(System.in);
            String choice = in.nextLine();
            switch (choice) {
                case "0":
                    return 0;
                case "1":
                    return 1;
                case "2":
                    return 2;
                case "3":
                    return 3;

                default:
                    System.out.println("Ошибка, введите другие данные");
            }
        }
    }


    public static void viewReceivedTasks(List<Task> listTasks){
        System.out.println("Список полученных задач:");
        if (listTasks == null) {
            System.out.println("Задачи отсутствуют");
            return;
        }
        Task tempTask;
        ListIterator<Task> iter = listTasks.listIterator();
        while (iter.hasNext()){
            tempTask = iter.next();

            System.out.println("    "+tempTask.getId()+ " - "+ tempTask.getName());
            System.out.println("Статус: " + tempTask.getStatus());
            System.out.println("Руководитель: " + tempTask.getLead());
            System.out.println(tempTask.getDescription());
            System.out.println("История отчетов: " + tempTask.getReport()+"\n");
        }

    }

    //    public static int RRRcreateNewUserInDb(){
//        int ret = 0;
//
//        System.out.println("Создание нового пользователя");
//        System.out.println("Введите имя:");
//        Scanner in = new Scanner(System.in);
//        String name = in.nextLine();
//        System.out.println("Введите должность:");
//        String role = in.nextLine();
//        System.out.println("Введите руководителя:");
//        String lead = in.nextLine();
//        System.out.println("Введите новый пароль:");
//        String password = in.nextLine();
//
//
//
//        boolean userAdded = DbFunctions.addNewUser(name,role,lead,password);
//
//        if (userAdded) {
//            System.out.println("Пользователь успешно добавлен");
//        }
//        else{
//                System.out.println("Ошибка, пользователь не добавлен");
//                ret = 1;
//        }
//        return ret;
//    }
    public static User createNewUser() {//
        String name, role, lead, password;
        Scanner in = new Scanner(System.in);
        System.out.println("Создание нового пользователя");
        while (true) {
            System.out.println("Введите имя:");
            name = in.nextLine();
            if (DbFunctions.isExistInDb("USERS", "NAME", name) || name.equals(""))
                System.out.println("Ошибка, введите другие данные");
            else
                break;
        }

        while (true) {
            System.out.println("Введите должность:");
            role = in.nextLine();
            if (!DbFunctions.isExistInDb("ROLES", "NAME", role))
                System.out.println("Ошибка, введите другие данные");
            else
                break;
        }
        while (true) {
            System.out.println("Введите руководителя:");
            lead = in.nextLine();
            if (!DbFunctions.isExistInDb("USERS", "NAME", lead))
                System.out.println("Ошибка, введите другие данные");
            else
                break;
        }
        System.out.println("Введите новый пароль:");
        password = in.nextLine();

//        String name = "Валилий";
//        String role = "Работник";
//        String lead = "Роман";
//        String password = "12345";

        return User.createNewUser(name, role, lead, password);
    }

    public static Task createNewTask() {
        String name, description, lead, performer;
        Scanner in = new Scanner(System.in);
        System.out.println("Создание новой задачи");

        while (true) {
            System.out.println("Введите название");
            name = in.nextLine();
            if (DbFunctions.isExistInDb("TASKS", "NAME", name) || name.equals(""))
                System.out.println("Ошибка, введите другие данные");
            else
                break;
        }

        System.out.println("Введите описание");
        description = in.nextLine();

        while (true) {
            System.out.println("Введите руководителя");
            lead = in.nextLine();
            if (!DbFunctions.isExistInDb("USERS", "NAME", lead))
                System.out.println("Ошибка, введите другие данные");
            else
                break;
        }
        while (true) {
            System.out.println("Введите исполнителя");
            performer = in.nextLine();
            if (!DbFunctions.isExistInDb("USERS", "NAME", performer))
                System.out.println("Ошибка, введите другие данные");
            else
                break;
        }

//        String name = "Задача 2";
//        String description = "Описание задачи 2";
//        String lead = "Роман";
//        String performer = "Александр";


        return Task.createNewTask(name, description, lead, performer);


    }


    public static int addReportToTask() {//todo исправить с учетом авторизации
        int ret = 0;
        System.out.println("Создание создание отчета к задаче");
        System.out.println("Введите название задачи");
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();
        System.out.println("Введите текст отчета");
        String report = in.nextLine();

//        String name = "Задача 1";
//        String description = "Описание задачи 1";
//        String lead = "Роман";
//        String performer = "Александр";


        boolean reportAddedToTask = DbFunctions.addReportToTask(name, report);

        if (reportAddedToTask) {
            System.out.println("Отчет успешно добавлен");
        } else {
            System.out.println("Ошибка, отчет не добавлен");
            ret = 1;
        }
        return ret;

    }

    public static void main(String[] args) {
        mainMenu();
    }
}
