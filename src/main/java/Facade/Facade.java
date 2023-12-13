package Facade;

import model.DbFunctions.DbFunctions;
import model.FileFunctions;
import model.Security;
import model.Task;
import model.User;
import userInterface.controller.Controller;
import userInterface.controller.DataLogIn;
import userInterface.view.TaskForView;


import javax.annotation.processing.SupportedSourceVersion;
import java.util.List;

import static Facade.ChangeFormatToView.changeTasksToView;
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
        List<Task> currentTasks;
        Task currentTask = null;
        choice = 0;
        while (true) {
            switch (choice) {
                case 0: // ___Главное меню___
                    choice = 0 + Controller.mainMenu();
                    break;
                case 1: // Сменить пользователя
                    userCurrent = logIn();
                    choice = 1;
                    break;
                case 2: // Перейти в меню задач
                    choice = 100;
                    break;
//                case 3: // Перейти в меню пользователей TODO !!!!!!!!!!!!
//
//                    break;
                case 3: // Выйти из программы
                    return;


                case 100: // ___Меню задач___
                    choice = 100 + Controller.tasksMenu();
                    break;
                case 101: // Просмотр полученных задач
                    currentTasks = Task.getTasks(userCurrent, Task.PERFORMER_MODE);

                    viewTasks(changeTasksToView(currentTasks));
                    choice = 100;
                    break;
                case 102: // Просмотр назначенных задач
                    currentTasks = Task.getTasks(userCurrent, Task.LEAD_MODE);
                    viewTasks(changeTasksToView(currentTasks));
                    choice = 100;
                    break;
                case 103: // Выбрать задачу и перейти в меню задач
                    choice = 200;
                    break;
                case 104: // Создать новую задачу
                    currentTask = Controller.createNewTask();
                    Task.createNewTask(currentTask);
                    choice = 100;
                    break;
                case 105: // Выйти в главное меню
                    choice = 0;
                    break;



                case 200: // ___Выбор задачи и меню задачи___
                    currentTask = Controller.selectTask(userCurrent);
                    if (currentTask == null){
                        System.out.println("11111111111111111");
                        choice = 100;
                        break;
                    }
                    TaskForView.viewTask(ChangeFormatToView.changeTaskToView(currentTask));
                    choice = 200 + Controller.taskMenu(currentTask);
                    break;
                case 201: // Редактировать задачу

                    choice = 300;
                    break;
                case 202: // Удалить задачу
                    Controller.deleteTask(currentTask);
                    choice = 200;
                    break;
                case 203: // Выйти в меню задач
                    choice = 100 + Controller.tasksMenu();
                    break;
                case 204: // Выйти в главное меню
                    choice = 0;
                    break;
                case 300: // ___Меню редактирования задачи___
                    choice = 300 + Controller.menuEditTask(currentTask, userCurrent);
                    break;
                case 301: // Изменить название
                    Controller.changeNameTask(currentTask);
                    choice = 300;
                    break;
                case 302: // Изменить описание
                    Controller.changeDescriptionTask(currentTask);
                    choice = 300;
                    break;
                case 303: // Сменить руководителя
                    Controller.changeLeadTask(currentTask);
                    choice = 300;
                    break;
                case 304: // Сменить исполнителя
                    Controller.changePerformerTask(currentTask);
                    choice = 300;
                    break;
                case 305: // Сменить статус
                    Controller.changeStatusTask(currentTask);
                    choice = 300;
                    break;
                case 306: // Добавить отчет
                    Controller.addReportToTask(currentTask);
                    choice = 300;
                    break;
                case 307: // Удалить задачу
                    Controller.deleteTask(currentTask);
                    choice = 202;
                    break;
                case 308: // Вернуться к выбору задачи и меню задачи
                    choice = 200;
                    break;
                case 309: // Вернуться к меню задач
                    choice = 100;
                    break;






                default:
                    System.out.println("Ошибка 1, Неверный ввод номера команды");
                    choice = Controller.mainMenu();

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
        user = new User(2, "Григорий", 3, 1, null, null);
        User.createNewUser(user);
        user = new User(3, "1", 2, 2, null, null);
        User.createNewUser(user);
        User.saveUserPrivateKey(user);
        user = new User(4, "Виктория", 1, 3, null, null);
        User.createNewUser(user);
        user = new User(5, "Владимир", 1, 3, null, null);
        User.createNewUser(user);

        Task task;
        task = new Task(0, "Протереть пыль с компьютеров", "Протереть пыль с компьютера ASUS", 0, 0, 1, "Ищу тряпку", null, null, null, null);
        Task.createNewTask(task);
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