import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Task {

    private int id;
    private String name;
    private String description;
    private String lead;
    private String performer;
    private byte[] signLead;
    private String status;
    private String report;
    private byte[] signPerformer;


//    public static Task createNewTask (String name, String description, String lead, String performer) {//todo Сделать  подписи, сделать обработку ошибок
//
//        byte[] signLead = Security.generateRandomBytes(32);//********************
//        String status = "Отправлена на выполнение";
//        String report = "";
//        byte[] signPerformer = Security.generateRandomBytes(32);//********************
//
//
//        boolean taskAddedInDb = DbFunctions.addNewTask(name, description, lead, performer, signLead, status, report, signPerformer);
//        if (taskAddedInDb)
//            System.out.println("Задача успешно добавлена");
//        else
//            System.out.println("Ошибка, задача не добавлена");
//
//        int id = DbFunctions.getIdByName("TASKS", name);
//        return new Task(id, name, description, lead, performer, signLead, status, report, signPerformer);
//
//    }
    public static void main(String[] args) {
        ConsoleInterface.createNewTask();
    }


}
