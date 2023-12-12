package userInterface.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class TaskForView {
    private int id;
    private String name;
    private String description;
    private String lead;
    private String performer;
    private String status;
    private String report;

    public static void main(String[] args) {

    }

    public static void viewTasks(List<TaskForView> listTasks) {
        if (listTasks == null) {
            System.out.println("Задачи отсутствуют");
            return;
        }
        TaskForView tempTask;
        for (TaskForView listTask : listTasks) {
            tempTask = listTask;

            System.out.println("    " + tempTask.getId() + " - " + tempTask.getName());
            System.out.println("Статус: " + tempTask.getStatus());
            System.out.println("Руководитель: " + tempTask.getLead());
            System.out.println("Исполнитель: " + tempTask.getPerformer());
            System.out.println("Описание: " + tempTask.getDescription());
            System.out.println("История отчетов:\n__________\n" + tempTask.getReport() + "\n__________\n");
        }

    }


    public static void viewTask(TaskForView taskView){
        ArrayList listTasks = new ArrayList<>();
        listTasks.add(taskView);
        viewTasks(listTasks);
    }


}
