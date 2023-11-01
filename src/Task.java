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

    public Task(int id, String name, String description, String lead, String performer, byte[] signLead, String status, String report, byte[] signPerformer) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.lead = lead;
        this.performer = performer;

        this.signLead = signLead;
        this.status = status;
        this.report = report;
        this.signPerformer = signPerformer;

    }
    public static Task createNewTask (String name, String description, String lead, String performer) {//todo Сделать  подписи, сделать обработку ошибок

        byte[] signLead = ChangeFormat.stringToBytes("123");//********************
        String status = "Отправлена на выполнение";
        String report = "";
        byte[] signPerformer = ChangeFormat.stringToBytes("123");//********************


        boolean taskAddedInDb = DbFunctions.addNewTask(name, description, lead, performer, signLead, status, report, signPerformer);
        if (taskAddedInDb)
            System.out.println("Задача успешно добавлена");
        else
            System.out.println("Ошибка, задача не добавлена");

        int id = DbFunctions.getIdByName("TASKS", name);
        return new Task(id, name, description, lead, performer, signLead, status, report, signPerformer);

    }
    public static void main(String[] args) {
        ConsoleInterface.createNewTask();
    }


}
