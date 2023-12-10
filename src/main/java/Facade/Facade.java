package Facade;

import model.DbFunctions;
import model.FileFunctions;
import model.Security;
import model.User;
import userInterface.controller.Controller;
import userInterface.controller.DataLogIn;


public class Facade {
    public static void main(String[] args) {
        initialize();
        while (true){
            if (logIn() != null)
                break;
        }
        // Главное меню
        int choice;
        while (true) {
            choice = Controller.mainMenu();
            switch (choice) {
                case 0: // Выйти из программы
                    return;
                case 1: // Сменить пользователя
//                    currentUser = Controller.logIn();
                    break;
                case 2: // Перейти в меню задач
//                    choice = 100 + Controller.taskMenu();
                    break;
                case 100: // Выйти в главное меню
                    break;
                case 101: // Просмотр полученных задач todo !!!!!!!!!!
                    break;
                case 102: // Просмотр назначенных задач todo !!!!!!!!!!!
                    break;
                case 103: // Создать новую задачу todo !!!!!!!!!!
                    break;
                case 200: // Выйти в главное меню
                    break;
                case 201: // Создать пользователя
                    break;






                default:
                    System.out.println("Ошибка 1, Неверный ввод номера команды");

            }
        }

    }

    public static void initialize()
    {
        DbFunctions.initializeDb();//Создание таблиц, заполнение служебных таблиц
        User user;
        user = new User(1, "Александр", 6, 1, null, null);
        User.createNewUser(user);
        user = new User(2, "Роман", 4, 1, null, null);
        User.createNewUser(user);
        user = new User(3, "Григорий", 3, 2, null, null);
        User.createNewUser(user);
        user = new User(4, "Николай", 2, 3, null, null);
        User.createNewUser(user);
        User.saveUserPrivateKey(user);
        user = new User(5, "Виктория", 1, 4, null, null);
        User.createNewUser(user);
        user = new User(6, "Владимир", 1, 4, null, null);
        User.createNewUser(user);




    }

    public static User logIn()
    {
        DataLogIn dataLogIn = Controller.logIn();
        User userDb = DbFunctions.getUser(dataLogIn.getName());
        if (userDb == null)
            return null;
        byte[] bytesPrivateKey = FileFunctions.readFile(dataLogIn.getFile());
        byte[] bytesPublicKey = userDb.getKeyPublic();
        if(Security.isCorrectPairKeys(bytesPublicKey, bytesPrivateKey))
            return userDb;
        else
            return null;
    }


}