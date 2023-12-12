package Facade;

import model.DbFunctions.DbFunctions;
import model.DbFunctions.UserDb;
import model.FileFunctions;
import model.Security;
import model.Task;
import model.User;
import userInterface.controller.Controller;
import userInterface.controller.DataLogIn;
import userInterface.view.TaskForView;
import userInterface.view.View;

import java.util.List;

import static Facade.ChangeFormatToView.changeTaskToTaskForView;
import static Facade.ChangeFormatToView.changeTasksToTaskForView;
import static userInterface.view.TaskForView.viewTasks;


public class Facade {
    public static void main(String[] args) {
        initialize();
        User userCurrent;
        while (true){
            if ((userCurrent = logIn()) != null)
                break;
        }
        System.out.println(userCurrent.getId());//*************
        // Главное меню
        int choice;
        List<Task> tasks;
        choice = Controller.mainMenu();
        while (true) {
            switch (choice) {
                case 0: // Выйти из программы
                    return;
                case 1: // Сменить пользователя
                    userCurrent = logIn();
                    choice = 100;
                    break;
                case 2: // Перейти в меню задач
                    choice = 100 + Controller.taskMenu();
                    break;
                case 100: // Выйти в главное меню
                    choice = Controller.mainMenu();
                    break;
                case 101: // Просмотр полученных задач todo !!!!!!!!!!
                    tasks = Task.getTasks(userCurrent, Task.PERFORMER_MODE);
                    viewTasks(changeTasksToTaskForView(tasks));
                    choice = 2;
                    break;
                case 102: // Просмотр назначенных задач todo !!!!!!!!!!!
                    tasks = Task.getTasks(userCurrent, Task.LEAD_MODE);
                    viewTasks(changeTasksToTaskForView(tasks));
                    choice = 2;
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
        user = new User(0, "Александр", 6, 1, null, null);
        User.createNewUser(user);
        user = new User(1, "Роман", 4, 1, null, null);
        User.createNewUser(user);
        user = new User(2, "Григорий", 3, 2, null, null);
        User.createNewUser(user);
        user = new User(3, "1", 2, 3, null, null);
        User.createNewUser(user);
        User.saveUserPrivateKey(user);
        user = new User(4, "Виктория", 1, 4, null, null);
        User.createNewUser(user);
        user = new User(5, "Владимир", 1, 4, null, null);
        User.createNewUser(user);

        Task task;
        task = new Task(0, "Починить токарный станок", "Починить токарный станок 6Б46", 3, 4, 3, "Заказал детели", null, null, null, null);
        Task.createNewTask(task);
        task = new Task(1, "Починить фрезерный станок", "Починить фрезерный станок 3ГСЕ51", 3, 5, 3, "Приступлю в среду", null, null, null, null);
        Task.createNewTask(task);
        task = new Task(2, "Наладить работы сборочной линии", "Починить токарные и фрезерные станки", 2, 3, 3, "Заказываем детали и приступаем к выполнению", null, null, null, null);
        Task.createNewTask(task);
        task = new Task(3, "Заключить договора с заводом БелМаш", "Встретиться с представителем БелМаш и обсудить условия", 1, 2, 4, "Встреча проведена, продажи планируем начать в следующем месяце", null, null, null, null);
        Task.createNewTask(task);
        task = new Task(4, "Продумать курс развития компании", "Рассмотреть вопрос масштабирования компании", 1, 1, 1, "", null, null, null, null);
        Task.createNewTask(task);







    }

    public static User logIn()
    {
        DataLogIn dataLogIn = Controller.logIn();
        User user = User.getUser(dataLogIn.getName());
        if (user == null)
            return null;
        byte[] bytesPrivateKey = FileFunctions.readFile(dataLogIn.getFile());
        byte[] bytesPublicKey = Security.encodedAnyKey(user.getKeyPublic());
        user.setKeyPrivate(Security.decodedKeyPrivateRsa(bytesPrivateKey));
        if(Security.isCorrectPairKeys(bytesPublicKey, bytesPrivateKey))
            return user;
        else
            return null;
    }


}