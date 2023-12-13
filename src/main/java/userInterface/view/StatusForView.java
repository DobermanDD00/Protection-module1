package userInterface.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatusForView {

    private int id;
    private String name;

    public static void viewStatuses(List<StatusForView> listStatuses) {
        if (listStatuses == null) {
            System.out.println("Задачи отсутствуют");
            return;
        }
        StatusForView tempStatus;
        System.out.println("Статусы задач:  id - Название");
        for (StatusForView listStatus : listStatuses) {
            tempStatus = listStatus;

            System.out.println(tempStatus.getId() + " - " + tempStatus.getName());

        }

    }


    public static void viewStatus(StatusForView taskView){
        ArrayList listStatuses = new ArrayList<>();
        listStatuses.add(taskView);
        viewStatuses(listStatuses);
    }
}


