package Facade;

import userInterface.view.UserForView;
import model.*;

public class ChangeFormatToView {

    public static UserForView userToView (User user){
        String role = DbFunctions.getFieldString("Roles", "id", Integer.toString(user.getId()), "name" );
        String lead = DbFunctions.getFieldString("Users", "id", Integer.toString(user.getId()), "name" );;
        return new UserForView(user.getId(), user.getName(), role, lead );
    }

    public static void main(String[] args) {
        DbFunctions.initializeDb();
        User user = DbFunctions.getUser(3);
        UserForView user1 = userToView(user);
        UserForView.viewUser(user1);

    }
}
