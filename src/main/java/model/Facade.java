package model;

import userInterface.controller.Controller;
import userInterface.view.View;


import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Facade {
    public static void main(String[] args) {
        User currentUser = Controller.logIn();
        // Главное меню
        int choice;












        while (true) {
            choice = Controller.mainMenu();
            switch (choice) {
                case 0: // Выйти из программы
                    return;
                case 1: // Сменить пользователя
                    currentUser = Controller.logIn();
                    break;
                case 2: // Перейти в меню задач
                    while (true) {
                        choice = Controller.taskMenu();
                        switch (choice) {
                            case 0:
//                                System.out.println("ddddddddddddddddd");//************** Проблнма брейк завершает свич, но не цикл
                                break;
                            case 1:// Просмотр текущих задач
                                View.viewTasks(DbFunctions.getTasks("PERFORMER", currentUser.getName()));
                                Task task = Controller.selectTask(currentUser);
                                if (task == null)
                                    break;

                                List<Task> tasks = new ArrayList<>();
                                tasks.add(task);
                                View.viewTasks(tasks);
                                return;
                            case 2:// Просмотр назначенных задач
                                View.viewTasks(DbFunctions.getTasks("LEAD", currentUser.getName()));
                                break;
                            case 3:// Создать новую задачу
                                Controller.createNewTask();
                                break;

                            default:
                                System.out.println("Ошибка код 2");

                        }
                    }


                default:
                    System.out.println("Ошибка код 1");


            }
        }

    }
    public static User logIn(String name, String password){
        User userDb = DbFunctions.getUser(name);

        if (userDb == null)
            return null;
        if (ChangeFormat.byteToHexStr(Security.generateHashSha256(userDb.getSalt(), password.getBytes(StandardCharsets.UTF_8))).equals(ChangeFormat.byteToHexStr(userDb.getHashSaltPassword())))
            return userDb;
        else
            return null;

    }
    public static Task FcdAccessToTask(String columnName, String data, String user){
        return DbFunctions.accessToTask(columnName, data, user);
    }
    public static int strToInt(String str) {
        return ChangeFormat.strToInt(str);
    }
    public static boolean isExistInDb(String tableName, String columnName, String data){
        return DbFunctions.isExistInDb(tableName, columnName, data);
    }
    public static User addNewUser(String name, String role, String lead, String password){
        return DbFunctions.addNewUser(name, role, lead, password);
    }
    public static Task addNewTask(String name, String description, String lead, String performer){
        return DbFunctions.addNewTask(name, description, lead,performer);
    }

}