package userInterface.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class UserForView {
    private int id;
    private String name;
    private String role;
    private String lead;

    public static void main(String[] args) {
        ArrayList<UserForView> users = new ArrayList<>();
        users.add(new UserForView(3, "Василий0", "Фрезеровщик0", "Николай0"));
        users.add(new UserForView(4, "Василий1", "Фрезеровщик1", "Николай1"));
        users.add(new UserForView(5, "Василий2", "Фрезеровщик2", "Николай2"));
        users.add(new UserForView(6, "Василий3", "Фрезеровщик3", "Николай3"));
        users.add(new UserForView(7, "Василий4", "Фрезеровщик4", "Николай4"));
        viewUsers(users);

        UserForView user = new UserForView(3, "Василий0", "Фрезеровщик0", "Николай0");
        UserForView.viewUser(user);
    }

    public static void viewUsers(List<UserForView> listUsers) {
        if (listUsers == null) {
            System.out.println("Сотрудники отсутствуют");
            return;
        }
        UserForView tempUser;
        for (UserForView listUser : listUsers) {
            tempUser = listUser;
            System.out.println("Имя: " + tempUser.getName());
            System.out.println("Должность: " + tempUser.getRole());
            System.out.println("Руководитель: " + tempUser.getLead());
            System.out.println();
        }

    }
    public static void viewUser (UserForView user)
    {
        ArrayList<UserForView> users = new ArrayList<>();
        users.add(user);
        viewUsers(users);
    }


}
