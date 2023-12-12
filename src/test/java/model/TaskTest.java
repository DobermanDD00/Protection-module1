package model;

import Facade.Facade;
import model.DbFunctions.DbFunctions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import userInterface.view.TaskForView;

import javax.crypto.SecretKey;
import javax.swing.text.View;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;

import static Facade.ChangeFormatToView.*;
import static model.Task.*;
import static userInterface.view.TaskForView.*;

class TaskTest {

    @Test
    void checkSignLeadTest() {
        Task task1 = new Task(1, "f", "5", 55, 34,  333, "fdj", null, null, null, null);
        Task task2 = new Task(1, "f", "5", 55, 34,  333, "fdj", null, null, null, null);

        Assertions.assertTrue(checkSignLead(task1, task2));
    }

    @Test
    void getSignLeadTest() {
        Task task1 = new Task(1, "f", "5", 55, 34,  333, "fdj", null, null, null, null);
        Task task2 = new Task(1, "f", "5", 55, 34,  333, "fdj", null, null, null, null);

        Assertions.assertTrue(checkSignLead(task1, task2));


    }

    @Test
    void getSignPerformerTest() {
        Task task1 = new Task(1, "f", "5", 55, 34,  333, "fdj", null, null, null, null);
        Task task2 = new Task(1, "f", "5", 55, 34,  333, "fdj", null, null, null, null);

        Assertions.assertTrue(checkSignPerformer(task1, task2));
    }

    @Test
    void checkSignPerformerTest() {
        Task task1 = new Task(1, "f", "5", 55, 34,  333, "fdj", null, null, null, null);
        Task task2 = new Task(1, "f", "5", 55, 34,  333, "fdj", null, null, null, null);

        Assertions.assertTrue(checkSignPerformer(task1, task2));
    }

    @Test
    void createNewTaskTest() {
        //***********************************

    }

    @Test
    void getTaskTest() {
//        Facade.initialize();
//        Task task1 = new Task(0, "f", "5", 4, 3, 3, "fdj", null, null, null, null);
//        SecretKey secretKey = createNewTask(task1);
//
//        TaskForView.viewTask(changeTaskToTaskForView(task1));
//
//
//        Task task2 = getTask(5, secretKey);
//        System.out.println("task2 "+task2);
//        TaskForView.viewTask(changeTaskToTaskForView(task2));
//
//
//        Assertions.assertTrue(task1.equals(task2));


    }

    @Test
    void getSecretKeyTest() {
        SecretKey secretKey = Security.generatedAesKey();
        byte[] pass = Security.encodedAnyKey(secretKey);

        KeyPair keyPair = Security.generatedRsaKeys();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();


        byte[] passForLead = Security.cipherRSAEncrypt(pass, publicKey);
        Task task1 = new Task(10, "f", "5", 4, 3, 333, "fdj", null, null, passForLead, null);


        PrivateKey priKey;
    }

    @Test
    void changeTaskDbToTaskTest() {


    }

    @Test
    void changeTaskToTaskDbTest() {

    }

    @Test
    void updateProtectionTest() {


        DbFunctions.initializeDb();//Создание таблиц, заполнение служебных таблиц
        User user = new User(0, "1", 2, 3, null, null);
        User.createNewUser(user);
        Task task;
        task = new Task(0, "Починить токарный станок", "Починить токарный станок 6Б46", 0, 0, 3, "Заказал детели", null, null, null, null);
        Task.createNewTask(task);


        Task task1 = getTask(0, user.getKeyPrivate(), LEAD_MODE);
        System.out.println("task "+task.getDescription());
        System.out.println("task1 "+task1.getDescription());
        System.out.println("taskView "+changeTaskToTaskForView(task1).getDescription());

        TaskForView.viewTask(changeTaskToTaskForView(task1));

        Assertions.assertTrue(task.equals(task1));

    }

    @Test
    void getTasksTest() {
//        DbFunctions.initializeDb();//Создание таблиц, заполнение служебных таблиц
//        User user = new User(0, "1", 2, 3, null, null);
//        User.createNewUser(user);
//        Task task;
//        task = new Task(0, "Починить токарный станок", "Починить токарный станок 6Б46", 0, 0, 3, "Заказал детели", null, null, null, null);
//        Task.createNewTask(task);
//        task = new Task(1, "Починить токарный сdтанок", "Починить токарныfvй станок 6Б46", 0, 1, 3, "Заказал детели", null, null, null, null);
//        Task.createNewTask(task);
//        task = new Task(2, "Починить2 токарный сdтанок", "Починить токарныfvй станок 6Б46", 0, 1, 3, "Заказал детели", null, null, null, null);
//        Task.createNewTask(task);

        Facade.initialize();
        User user = User.getUser("1");
        PrivateKey privateKey = Security.decodedKeyPrivateRsa(FileFunctions.readFile("1.txt"));
        user.setKeyPrivate(privateKey);

        Assertions.assertTrue(Security.isCorrectPairKeys(user.getKeyPublic(), user.getKeyPrivate()));

//        Task task = Task.getTask(2, user.getKeyPrivate(), PERFORMER_MODE);
//        viewTask(changeTaskToTaskForView(task));
        System.out.println("Назначенные задачи:");
        List<TaskForView> tasksForView = changeTasksToTaskForView(Task.getTasks(user, LEAD_MODE));
        viewTasks(tasksForView);
        System.out.println("Полученные задачи:");
        tasksForView = changeTasksToTaskForView(Task.getTasks(user, PERFORMER_MODE));
        viewTasks(tasksForView);



    }
}