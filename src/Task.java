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

    public Task(String name, String description, String lead, String performer) {//todo Сделать  подписи, сделать обработку ошибок
        boolean ret = true;
        this.name = name;
        this.description = description;
        this.lead = lead;
        this.performer = performer;


        this.signLead = ChangeFormat.stringToBytes("123");//********************
        this.status = "Отправлена на выполнение";
        this.report = "";
        this.signPerformer = ChangeFormat.stringToBytes("123");//********************


        boolean taskAddedInDb = DbFunctions.addNewTask(this.name, this.description, this.lead, this.performer, this.signLead, this.status, this.report, this.signPerformer);
        if (taskAddedInDb)
            System.out.println("Задача успешно добавлена");
        else
            System.out.println("Ошибка, задача не добавлена");

        this.id = DbFunctions.getIdByName("TASKS", name);


    }
    public static void main(String[] args) {
        ConsoleInterface.createNewTask();
    }


}
