//import model.*;
//import tools.ChangeFormat;
//import userInterface.controller.Controller;
//
//
//import java.nio.charset.StandardCharsets;
//
//public class Facade {
//    public static void main(String[] args) {
//        User currentUser = Controller.logIn();
//        // Главное меню
//        int choice;
//        while (true) {
//            choice = Controller.mainMenu();
//            switch (choice) {
//                case 0: // Выйти из программы
//                    return;
//                case 1: // Сменить пользователя
//                    currentUser = Controller.logIn();
//                    break;
//                case 2: // Перейти в меню задач
//                    choice = 100 + Controller.taskMenu();
//                    break;
//                case 100: // Выйти в главное меню
//                    break;
//                case 101: // Просмотр полученных задач todo !!!!!!!!!!
//                    break;
//                case 102: // Просмотр назначенных задач todo !!!!!!!!!!!
//                    break;
//                case 103: // Создать новую задачу todo !!!!!!!!!!
//                    break;
//
//
//
//
//
//
//                default:
//                    System.out.println("Ошибка 1, Неверный ввод номера команды");
//
//            }
//        }
//
//    }
//    public static User logIn(String name, String password){
//        User userDb = DbFunctions.getUser(name);
//
//        if (userDb == null)
//            return null;
//        if (ChangeFormat.byteToHexStr(Security.generateHashSha256(userDb.getSalt(), password.getBytes(StandardCharsets.UTF_8))).equals(ChangeFormat.byteToHexStr(userDb.getHashSaltPassword())))
//            return userDb;
//        else
//            return null;
//
//    }
//    public static Task FcdAccessToTask(String columnName, String data, String user){
//        return DbFunctions.accessToTask(columnName, data, user);
//    }
//    public static int strToInt(String str) {
//        return ChangeFormat.strToInt(str);
//    }
//    public static boolean isExistInDb(String tableName, String columnName, String data){
//        return DbFunctions.isExistInDb(tableName, columnName, data);
//    }
//    public static User addNewUser(String name, String role, String lead, String password){
//        return DbFunctions.addNewUser(name, role, lead, password);
//    }
//    public static Task addNewTask(String name, String description, String lead, String performer){
//        return DbFunctions.addNewTask(name, description, lead,performer);
//    }
//
//}