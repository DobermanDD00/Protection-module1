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
public class RoleForView {
    private int id;
    private String name;
    public static void viewRoles(List<RoleForView> listRoles) {
        if (listRoles == null) {
            System.out.println("Задачи отсутствуют");
            return;
        }
        RoleForView tempRole;
        System.out.println("Роли пользователей:  id - роль");
        for (RoleForView listRole : listRoles) {
            tempRole = listRole;

            System.out.println(tempRole.getId() + " - " + tempRole.getName());

        }

    }


    public static void viewRole(RoleForView taskView){
        ArrayList listRoles = new ArrayList<>();
        listRoles.add(taskView);
        viewRoles(listRoles);
    }
}
