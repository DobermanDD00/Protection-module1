import java.util.Scanner;

public class ConsoleInterface {
    public static int createNewUser(){
        int ret = 0;

        System.out.println("Создание нового пользователя");
        System.out.println("Введите имя:");
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();
        System.out.println("Введите должность:");
        String role = in.nextLine();
        System.out.println("Введите руководителя:");
        String lead = in.nextLine();
        System.out.println("Введите новый пароль:");
        String password = in.nextLine();



        boolean userAdded = DbFunctions.addNewUser(name,role,lead,password);

        if (userAdded) {
            System.out.println("Пользователь успешно добавлен");
        }
        else{
                System.out.println("Ошибка, пользователь не добавлен");
                ret = 1;
        }
        return ret;
    }
    public static int createNewTask(){
        int ret = 0;
        System.out.println("Создание новой задачи");
        System.out.println("Введите название");
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();
        System.out.println("Введите описание");
        String description = in.nextLine();
        System.out.println("Введите руководителя");
        String lead = in.nextLine();
        System.out.println("Введите исполнителя");
        String performer = in.nextLine();

//        String name = "Задача 1";
//        String description = "Описание задачи 1";
//        String lead = "Роман";
//        String performer = "Александр";


        boolean taskAdded = DbFunctions.addNewTask(name,description,lead,performer);

        if (taskAdded) {
            System.out.println("Задача успешно добавлена");
        }
        else{
            System.out.println("Ошибка, задача не добавлена");
            ret = 1;
        }
        return ret;

    }

    public static int addReportToTask(){
        int ret = 0;
        System.out.println("Создание создание отчета к задаче");
        System.out.println("Введите название задачи");
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();
        System.out.println("Введите текст отчета");
        String report = in.nextLine();

//        String name = "Задача 1";
//        String description = "Описание задачи 1";
//        String lead = "Роман";
//        String performer = "Александр";


        boolean reportAddedToTask = DbFunctions.addReportToTask(name,report);

        if (reportAddedToTask) {
            System.out.println("Отчет успешно добавлен");
        }
        else{
            System.out.println("Ошибка, отчет не добавлен");
            ret = 1;
        }
        return ret;

    }


    public static void main(String[] args) {
        addReportToTask();
    }
}
