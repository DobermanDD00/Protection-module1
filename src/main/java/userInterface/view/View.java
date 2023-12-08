package userInterface.view;

import model.Task;

import java.util.List;
import java.util.ListIterator;

public class View {
    public static void viewTasks(List<Task> listTasks) {
        if (listTasks == null) {
            System.out.println("Задачи отсутствуют");
            return;
        }
        Task tempTask;
        ListIterator<Task> iter = listTasks.listIterator();
        while (iter.hasNext()) {
            tempTask = iter.next();

            System.out.println("    " + tempTask.getId() + " - " + tempTask.getName());
            System.out.println("Статус: " + tempTask.getStatus());
            System.out.println("Руководитель: " + tempTask.getLead());
            System.out.println(tempTask.getDescription());
            System.out.println("История отчетов: " + tempTask.getReport() + "\n");
            System.out.println("История отчетов: " + tempTask.getReport() + "\n");
        }

    }





}
