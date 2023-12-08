package userInterface.controller;

import model.Facade;
import model.User;
import model.Task;

import java.util.Scanner;

public class Controller {
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
            user1 = Facade.logIn(name, password);
            if (user1 == null) {
                System.out.println("Ошибка, неправильный логин или пароль");
            } else {
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

    public static Task selectTask(User user) {
        Task task;
        while (true) {
            System.out.println("Выберете задачу:");
            System.out.println("Для выбора  введите число перед названием задачи, 0 - Выйти в меню задач");
            Scanner in = new Scanner(System.in);
            String choice = in.nextLine();
            if (choice.equals("0")) {
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaa");//******************************8
                return null;
            }
            else{
                System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");//************************


            }
            if ((task = Facade.FcdAccessToTask("ID", Integer.toString(Facade.strToInt(choice)), user.getName())) == null)
                System.out.println("Ошибка, введите другие данные");
            else
                return task;


        }

    }

    public static void editTask(Task task)//todo
    {
        while (true) {
            System.out.println("Выберете действие:");
            System.out.println("0 - Вернуться к выбору задачи.");
            System.out.println("1 - Изменить название.");
            System.out.println("2 - Изменить описание.");
            System.out.println("3 - Сменить руководителя.");
            System.out.println("4 - Сменить исполнителя.");
            System.out.println("5 - Сменить статус.");
            System.out.println("6 - Удалить задачу.");
            Scanner in = new Scanner(System.in);
            String choice = in.nextLine();
            switch (choice) {
                case "0":
                    return;
                case "1":
                    return;
                case "2":
                    return;
                case "3":
                    return;
                case "4":
                    return;
                case "5":
                    return;
                case "6":
                    return;

                default:
                    System.out.println("Ошибка, введите другие данные");
            }
        }

    }

    public static User createNewUser() {//
        String name, role, lead, password;
        Scanner in = new Scanner(System.in);
        System.out.println("Создание нового пользователя");
        while (true) {
            System.out.println("Введите имя:");
            name = in.nextLine();
            if (Facade.isExistInDb("USERS", "NAME", name) || name.equals(""))
                System.out.println("Ошибка, введите другие данные");
            else
                break;
        }

        while (true) {
            System.out.println("Введите должность:");
            role = in.nextLine();
            if (!Facade.isExistInDb("ROLES", "NAME", role))
                System.out.println("Ошибка, введите другие данные");
            else
                break;
        }
        while (true) {
            System.out.println("Введите руководителя:");
            lead = in.nextLine();
            if (!Facade.isExistInDb("USERS", "NAME", lead))
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

        return Facade.addNewUser(name, role, lead, password);
    }

    public static Task createNewTask() {
        String name, description, lead, performer;
        Scanner in = new Scanner(System.in);
        System.out.println("Создание новой задачи");

        while (true) {
            System.out.println("Введите название");
            name = in.nextLine();
            if (Facade.isExistInDb("TASKS", "NAME", name) || name.equals(""))
                System.out.println("Ошибка, введите другие данные");
            else
                break;
        }

        System.out.println("Введите описание");
        description = in.nextLine();

        while (true) {
            System.out.println("Введите руководителя");
            lead = in.nextLine();
            if (!Facade.isExistInDb("USERS", "NAME", lead))
                System.out.println("Ошибка, введите другие данные");
            else
                break;
        }
        while (true) {
            System.out.println("Введите исполнителя");
            performer = in.nextLine();
            if (!Facade.isExistInDb("USERS", "NAME", performer))
                System.out.println("Ошибка, введите другие данные");
            else
                break;
        }

//        String name = "Задача 2";
//        String description = "Описание задачи 2";
//        String lead = "Роман";
//        String performer = "Александр";


        return Facade.addNewTask(name, description, lead, performer);


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


        return ret;

    }

}
