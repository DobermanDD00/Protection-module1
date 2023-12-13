package model;

import lombok.*;
import model.DbFunctions.DbFunctions;
import model.DbFunctions.TaskDb;
import tools.ChangeFormat;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode


public class Task {
    public static final int LEAD_MODE = 1;
    public static final int PERFORMER_MODE = 2;
    private int id;
    private String name;
    private String description;
    private int idLead;
    private int idPerformer;
    private int idStatus;
    private String report;
    private byte[] signLead;
    private byte[] signPerformer;
    private byte[] passForLead;
    private byte[] passForPerformer;


    public static void main(String[] args) {
//        Task task1 = new Task(1, "f", "5", 55, 34, 333, "fdj", null, null, null, null);
//        System.out.println(new String(generateSignLead(task1)));


    }


    public static SecretKey createNewTask(Task task) {
        if (task == null) return null;

        task.setId(DbFunctions.getNewIdTask());
        task.initializeReport();

        byte[] pubKeyLeadByte = DbFunctions.getUserPublicKey(task.getIdLead());
        if (pubKeyLeadByte == null) return null;
        PublicKey pubKeyLead = Security.decodedKeyPublicRsa(pubKeyLeadByte);

        byte[] pubKeyPerformerByte = DbFunctions.getUserPublicKey(task.getIdPerformer());
        if (pubKeyPerformerByte == null) return null;
        PublicKey pubKeyPerformer = Security.decodedKeyPublicRsa(pubKeyPerformerByte);
        SecretKey secretKey = task.updateProtection(pubKeyLead, pubKeyPerformer);


        DbFunctions.addNewTask(changeTaskToTaskDb(task, secretKey));
        return secretKey;

    }
    public SecretKey updateTask(){
        if (this == null) return null;

        byte[] pubKeyLeadByte = DbFunctions.getUserPublicKey(this.getIdLead());
        if (pubKeyLeadByte == null) return null;
        PublicKey pubKeyLead = Security.decodedKeyPublicRsa(pubKeyLeadByte);

        byte[] pubKeyPerformerByte = DbFunctions.getUserPublicKey(this.getIdPerformer());
        if (pubKeyPerformerByte == null) return null;
        PublicKey pubKeyPerformer = Security.decodedKeyPublicRsa(pubKeyPerformerByte);
        SecretKey secretKey = this.updateProtection(pubKeyLead, pubKeyPerformer);


        DbFunctions.updateTask(changeTaskToTaskDb(this, secretKey));
        return secretKey;
    }

    public static Task getTask(int id, PrivateKey privateKey, int userMode) {


        TaskDb taskDb = DbFunctions.getTaskDb(id);
        SecretKey secretKey;
        if (userMode == LEAD_MODE){
            secretKey = getSecretKey(taskDb.getPassForLead(), privateKey);
        }else {
            secretKey = getSecretKey(taskDb.getPassForPerformer(), privateKey);
        }



        Task task = changeTaskDbToTask(taskDb, secretKey);
        System.out.println("changeTaskDbToTask " + task);
        return task;
    }

    public static List<Task> getTasks(User user, int userMode) {
        if (user == null) return null;

        List<TaskDb> tasksDb;
        List<Task> tasks;
        if (userMode == LEAD_MODE) {
            tasksDb = DbFunctions.getTasksLead(user.getId()); //&&&&&&&&
        }else {
            tasksDb = DbFunctions.getTasksPerformer(user.getId()); //&&&&&&&&
        }
        tasks = Task.changeTasksDbToTask(tasksDb, user.getKeyPrivate(), userMode); //&&&&&&&
        return tasks;
    }


    public String getStatus() {

        Status status = Status.getStatus(this.idStatus);
        return status.getName();
    }





    public static Task changeTaskDbToTask(TaskDb taskDb, SecretKey secretKey) {
        if (taskDb == null || secretKey == null) return null;


        Task task = new Task();
        task.setId(taskDb.getId());
        task.setName(new String(Security.cipherAes(taskDb.getName(), secretKey, Cipher.DECRYPT_MODE)));
        task.setDescription(new String(Security.cipherAes(taskDb.getDescription(), secretKey, Cipher.DECRYPT_MODE)));
        task.setIdLead(taskDb.getIdLead());
        task.setIdPerformer(taskDb.getIdPerformer());
        task.setIdStatus(taskDb.getIdStatus());
        task.setReport(new String(Security.cipherAes(taskDb.getReport(), secretKey, Cipher.DECRYPT_MODE)));

        task.setSignLead(taskDb.getSignLead());
        task.setSignPerformer(taskDb.getSignPerformer());
        task.setPassForLead(taskDb.getPassForLead());
        task.setPassForPerformer(taskDb.getPassForPerformer());
        return task;


    }

    public static List<Task> changeTasksDbToTask(List<TaskDb> tasksDb, PrivateKey privateKey, int userMode) {
        if (tasksDb.size() == 0 || tasksDb == null || privateKey == null) {
            System.out.println("Error null imput");
            return null;
        }
        List<Task> tasks = new ArrayList<>();
        Task task;
        SecretKey secretKey;
//        System.out.println("tasksDb.size() "+tasksDb.size());
        for (TaskDb taskDb : tasksDb){
            if (userMode == Task.LEAD_MODE)
                secretKey = getSecretKey(taskDb.getPassForLead(), privateKey);
            else
                secretKey = getSecretKey(taskDb.getPassForPerformer(), privateKey);

            task = changeTaskDbToTask(taskDb, secretKey);
            tasks.add(task);
        }
        return tasks;

    }

    public static TaskDb changeTaskToTaskDb(Task task, SecretKey secretKey) {
        if (task == null || secretKey == null) return null;

        byte[] name = Security.cipherAes(task.getName().getBytes(), secretKey, Cipher.ENCRYPT_MODE);
        byte[] description = Security.cipherAes(task.getDescription().getBytes(), secretKey, Cipher.ENCRYPT_MODE);
        byte[] report = Security.cipherAes(task.getReport().getBytes(), secretKey, Cipher.ENCRYPT_MODE);

        TaskDb taskDb = new TaskDb();

        taskDb.setId(task.getId());
        taskDb.setName(name);
        taskDb.setDescription(description);
        taskDb.setIdLead(task.getIdLead());
        taskDb.setIdPerformer(task.getIdPerformer());
        taskDb.setIdStatus(task.getIdStatus());
        taskDb.setReport(report);
        taskDb.setSignLead(task.getSignLead());
        taskDb.setSignPerformer(task.getSignPerformer());
        taskDb.setPassForLead(task.getPassForLead());
        taskDb.setPassForPerformer(task.getPassForPerformer());

        return taskDb;
    }


    public SecretKey updateProtection(PublicKey pubKeyLead, PublicKey pubKeyPerformer) {
        this.generateSignLead();
        this.generateSignPerformer();

        SecretKey secretKey = Security.generatedAesKey();

        this.setPassForLead(Security.cipherRSAEncrypt(Security.encodedAnyKey(secretKey), pubKeyLead));
        this.setPassForPerformer(Security.cipherRSAEncrypt(Security.encodedAnyKey(secretKey), pubKeyPerformer));

        return secretKey;
    }

    public static SecretKey getSecretKey(byte[] cipherPass, PrivateKey privateKey) {
        if (privateKey == null || cipherPass == null) {
            System.out.println("privateKey == null || cipherPass == null");
            return null;
        }
        byte[] pass = Security.cipherRSADecrypt(cipherPass, privateKey);

        return Security.decodedKeyAes(pass);

    }

    public void generateSignLead() {
        byte[] signLead = Security.generateHashSha256(ChangeFormat.intToBytes(this.getId()), this.getName().getBytes());
        signLead = Security.generateHashSha256(signLead, this.getDescription().getBytes());
        signLead = Security.generateHashSha256(signLead, ChangeFormat.intToBytes(this.getIdLead()));
        signLead = Security.generateHashSha256(signLead, ChangeFormat.intToBytes(this.getIdPerformer()));
        this.setSignLead(signLead);
    }

    public static boolean checkSignLead(Task task1, Task task2) {
        return Arrays.equals(task1.getSignLead(), task2.getSignLead());
    }

    public void generateSignPerformer() {
        byte[] signPerformer = Security.generateHashSha256(ChangeFormat.intToBytes(this.getId()), ChangeFormat.intToBytes(this.getIdStatus()));
        signPerformer = Security.generateHashSha256(signPerformer, this.getReport().getBytes());
        this.setSignPerformer(signPerformer);
    }

    public static boolean checkSignPerformer(Task task1, Task task2) {
        return Arrays.equals(task1.getSignPerformer(), task2.getSignPerformer());
    }

    public void addToReport(String str){
        this.setReport(this.getReport()+ str);
    }
    public void initializeReport (){
        this.setIdStatus(1);
        this.setReport("Задача создана.\n");
        this.addToReport("Название: "+ name+"\n");
        this.addToReport("Дата: " + LocalDateTime.now().toString() +"\n");
        this.addToReport("Руководитель: "+ User.getUserName(this.idLead)+"\n");
        this.addToReport("Исполнитель: "+ User.getUserName(this.idPerformer)+"\n");
        this.addToReport("Статус :" + Status.getStatusName(this.getIdStatus()));
    }
}


