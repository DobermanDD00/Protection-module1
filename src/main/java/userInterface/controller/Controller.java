package userInterface.controller;

import Facade.ChangeFormatToView;
import model.DbFunctions.DbFunctions;
import model.DbFunctions.TaskDb;
import model.Status;
import model.Task;
import model.User;
import tools.ChangeFormat;
import userInterface.view.StatusForView;
import userInterface.view.UserForView;


import javax.crypto.SecretKey;
import java.util.Scanner;

public class Controller {
    public static DataLogIn logIn() {
        DataLogIn currentLogIn = new DataLogIn();
        Scanner in = new Scanner(System.in);
        System.out.println("Вход в аккаунт:");

        System.out.println("Введите логин:");
        currentLogIn.setName(in.nextLine());
        System.out.println("Введите название или путь файла с приватным ключем:");
        currentLogIn.setFile(in.nextLine());
        return currentLogIn;

    }

    public static int mainMenu() {
        System.out.println("Главное меню");
        while (true) {
            System.out.println("Выберете действие:");
            System.out.println("1 - Сменить пользователя.");
            System.out.println("2 - Перейти в меню задач.");
//            System.out.println("3 - Перейти в меню пользователей.");
            System.out.println("3 - Выйти из программы.");
            Scanner in = new Scanner(System.in);
            String choice = in.nextLine();
            switch (choice) {
                case "1":
                    return 1;
                case "2":
                    return 2;
                case "3":
                    return 3;
//                case "4":
//                    return 4;

                default:
                    System.out.println("Ошибка, введите другие данные");
            }
        }
    }

    public static int tasksMenu() {
        System.out.println("Меню задач");
        while (true) {
            System.out.println("Выберете действие:");
            System.out.println("1 - Просмотр полученных задач.");
            System.out.println("2 - Просмотр назначенных задач.");
            System.out.println("3 - Выбрать задачу и перейти в меню задачи.");
            System.out.println("4 - Создать новую задачу.");
            System.out.println("5 - Выйти в главное меню.");
            Scanner in = new Scanner(System.in);
            String choice = in.nextLine();
            switch (choice) {
                case "1":
                    return 1;
                case "2":
                    return 2;
                case "3":
                    return 3;
                case "4":
                    return 4;
                case "5":
                    return 5;

                default:
                    System.out.println("Ошибка, введите другие данные");
            }
        }
    }

    public static Task createNewTask() {
        String name, description, lead, performer;
        Scanner in = new Scanner(System.in);
        System.out.println("Создание новой задачи");

        Task task = new Task();

        System.out.println("Введите название:");
        name = in.nextLine();
        task.setName(name);

        System.out.println("Введите описание:");
        description = in.nextLine();
        task.setDescription(description);
        System.out.println("Кандидаты для руководителя и исполнителя задачи:");
        UserForView.viewUsers(ChangeFormatToView.changeUsersToView(User.changeUsersDbToUsers(DbFunctions.getAllUsers())));
        while (true) {
            System.out.println("Введите имя руководителя:");
            lead = in.nextLine();
            if (DbFunctions.isExistInDb("Users", "name", lead)) {
                task.setIdLead(DbFunctions.getUser(lead).getId());
                break;
            } else {
                System.out.println("Отсутствует введенный пользователь");
            }
        }
        while (true) {
            System.out.println("Введите имя исполнителя:");
            performer = in.nextLine();
            if (DbFunctions.isExistInDb("Users", "name", performer)) {
                task.setIdPerformer(DbFunctions.getUser(performer).getId());
                break;
            } else {
                System.out.println("Отсутствует введенный пользователь");
            }

        }
        task.initializeReport();
        return task;
    }

    public static Task selectTask(User user) {
        Task task;
        while (true) {
            System.out.println("Выберете задачу:");
            System.out.println("Для выбора  введите число перед названием задачи, 0 - Выйти в меню задач для просмотра доступных задач");
            Scanner in = new Scanner(System.in);
            String choice = in.nextLine();
            if (choice.equals("0")) {
                return null;
            }

            if (!DbFunctions.isExistInDb("tasks", "id", choice)) {
                System.out.println("Ошибка, данная задача не существует");
                continue;
            }
            TaskDb taskDb = DbFunctions.getTaskDb(ChangeFormat.strToInt(choice));
            SecretKey secretKey = null;
            if (taskDb.getIdLead() == user.getId()) {
                secretKey = Task.getSecretKey(taskDb.getPassForLead(), user.getKeyPrivate());
            } else if (taskDb.getIdPerformer() == user.getId()) {
                secretKey = Task.getSecretKey(taskDb.getPassForPerformer(), user.getKeyPrivate());
            } else {
                System.out.println("Ошибка, у вас нет доступа к задаче");
                continue;
            }

            task = Task.changeTaskDbToTask(taskDb, secretKey);
            return task;


        }

    }

    public static int taskMenu(Task task) {
        System.out.println("Меню задачи: " + task.getId() + " - " + task.getName());
        while (true) {
            System.out.println("Выберете действие:");
            System.out.println("1 - Редактировать задачу.");
            System.out.println("2 - Удалить задачу.");
            System.out.println("3 - Выйти в меню задач.");
            System.out.println("4 - Выйти в главное меню.");
            Scanner in = new Scanner(System.in);
            String choice = in.nextLine();
            switch (choice) {
                case "1":
                    return 1;
                case "2":
                    return 2;
                case "3":
                    return 3;
                case "4":
                    return 4;

                default:
                    System.out.println("Ошибка, введите другие данные");
            }
        }
    }


    public static int menuEditTask(Task task, User user) {
        while (true) {
            System.out.println("Выберете действие:");
            System.out.println("1 - Изменить название.");
            System.out.println("2 - Изменить описание.");
            System.out.println("3 - Сменить руководителя.");
            System.out.println("4 - Сменить исполнителя.");
            System.out.println("5 - Сменить статус.");
            System.out.println("6 - Добавить отчет.");
            System.out.println("7 - Удалить задачу.");
            System.out.println("8 - Вернуться к выбору задачи и меню задачи.");
            System.out.println("9 - Вернуться к меню задач.");
            Scanner in = new Scanner(System.in);
            String choice = in.nextLine();
            switch (choice) {
                case "1":
                    if (task.getIdLead() == user.getId())
                        return 1;
                    else {
                        System.out.println("Ошибка, отказано в доступе, Вы не руководитель этой задачи");
                        continue;
                    }
                case "2":
                    if (task.getIdLead() == user.getId())
                        return 2;
                    else {
                        System.out.println("Ошибка, отказано в доступе, Вы не руководитель этой задачи");
                        continue;
                    }

                case "3":
                    if (task.getIdLead() == user.getId())
                        return 3;
                    else {
                        System.out.println("Ошибка, отказано в доступе, Вы не руководитель этой задачи");
                        continue;
                    }
                case "4":
                    if (task.getIdLead() == user.getId())
                        return 4;
                    else {
                        System.out.println("Ошибка, отказано в доступе, Вы не руководитель этой задачи");
                        continue;
                    }
                case "5":
                    if (task.getIdPerformer() == user.getId())
                        return 5;
                    else {
                        System.out.println("Ошибка, отказано в доступе, Вы не исполнитель этой задачи");
                        continue;
                    }
                case "6":
                    if (task.getIdPerformer() == user.getId())
                        return 6;
                    else {
                        System.out.println("Ошибка, отказано в доступе, Вы не исполнитель этой задачи");
                        continue;
                    }
                case "7":
                    if (task.getIdLead() == user.getId())
                        return 7;
                    else {
                        System.out.println("Ошибка, отказано в доступе, Вы не руководитель этой задачи");
                        continue;
                    }
                case "8":
                    return 8;
                case "9":
                    return 9;

                default:
                    System.out.println("Ошибка, введите другие данные");
            }
            System.out.println("Ошибка, доступ запрещен");


        }

    }

    public static void changeNameTask(Task task) {
        if (task == null)
            System.out.println("Ошибка, задача отсутствует");
        System.out.println("Введите новое название для задачи");
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();
        task.setName(str);
        task.addToReport("Изменено название задачи на " + task.getName() + "\n");
        task.updateTask();
    }

    public static void changeDescriptionTask(Task task) {
        if (task == null)
            System.out.println("Ошибка, задача отсутствует");
        System.out.println("Введите новое описание для задачи");
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();
        task.setDescription(str);
        task.addToReport("Изменено описание задачи на " + task.getDescription() + "\n");

        task.updateTask();
    }

    public static void changeLeadTask(Task task)// Todo Вывод польщователей на экран
    {
        if (task == null)
            System.out.println("Ошибка, задача отсутствует");
        System.out.println("Кандидаты для руководителя задачи:");
        UserForView.viewUsers(ChangeFormatToView.changeUsersToView(User.changeUsersDbToUsers(DbFunctions.getAllUsers())));
        System.out.println("Введите нового руководителя для задачи");
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();
        int idLead = DbFunctions.getUser(str).getId();
        task.setIdLead(idLead);
        task.addToReport("Изменен руководитель задачи на " + User.getUserName(idLead) + "\n");
        task.updateTask();
    }

    public static void changePerformerTask(Task task)// Todo Вывод польщователей на экран
    {
        if (task == null)
            System.out.println("Ошибка, задача отсутствует");
        System.out.println("Кандидаты для исполнителя задачи:");
        UserForView.viewUsers(ChangeFormatToView.changeUsersToView(User.changeUsersDbToUsers(DbFunctions.getAllUsers())));
        System.out.println("Введите нового исполнителя для задачи");
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();
        int idPerformer = DbFunctions.getUser(str).getId();
        task.setIdPerformer(idPerformer);
        task.addToReport("Изменен исполнитель задачи на " + User.getUserName(idPerformer) + "\n");
        task.updateTask();
    }

    public static void changeStatusTask(Task task) {
        if (task == null)
            System.out.println("Ошибка, задача отсутствует");
        StatusForView.viewStatuses(ChangeFormatToView.changeStatusesToView(Status.changeStatusesDbToStatus(DbFunctions.getAllStatuses())));
        System.out.println("Введите новый статус для задачи");
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();
        task.setIdStatus(ChangeFormat.strToInt(str));
        task.updateTask();
    }
    public static void addReportToTask(Task task) {
        if (task == null)
            System.out.println("Ошибка, задача отсутствует");
        System.out.println("Введите новый отчет для задачи");
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();
        task.addToReport(str);
        task.updateTask();
    }


    public static void deleteTask(Task task) {
        if (task == null)
            System.out.println("Ошибка, задача отсутствует");
        System.out.println("Вы точно хотите удалить задачу?\n1 - Да, 2 - Нет");
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();
        if (ChangeFormat.strToInt(str) == 1) {
            DbFunctions.deleteTask(task.getId());
        }

    }


//    public static User createNewUser() {//
//        String name, role, lead, password;
//        Scanner in = new Scanner(System.in);
//        System.out.println("Создание нового пользователя");
//        while (true) {
//            System.out.println("Введите имя:");
//            name = in.nextLine();
//            if (Facade.isExistInDb("USERS", "NAME", name) || name.equals(""))
//                System.out.println("Ошибка, введите другие данные");
//            else
//                break;
//        }
//
//        while (true) {
//            System.out.println("Введите должность:");
//            role = in.nextLine();
//            if (!Facade.isExistInDb("ROLES", "NAME", role))
//                System.out.println("Ошибка, введите другие данные");
//            else
//                break;
//        }
//        while (true) {
//            System.out.println("Введите руководителя:");
//            lead = in.nextLine();
//            if (!Facade.isExistInDb("USERS", "NAME", lead))
//                System.out.println("Ошибка, введите другие данные");
//            else
//                break;
//        }
//        System.out.println("Введите новый пароль:");
//        password = in.nextLine();
//
////        String name = "Валилий";
////        String role = "Работник";
////        String lead = "Роман";
////        String password = "12345";
//
//        return Facade.addNewUser(name, role, lead, password);
//    }
//


    public static int addReportToTask() {//todo исправить
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
