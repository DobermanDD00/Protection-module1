package Facade;

import model.DbFunctions.DbFunctions;
import userInterface.view.RoleForView;
import userInterface.view.StatusForView;
import userInterface.view.TaskForView;
import userInterface.view.UserForView;
import model.*;

import java.util.ArrayList;
import java.util.List;

public class ChangeFormatToView {
    public static void main(String[] args) {
        DbFunctions.initializeDb();
        User user = new User(33, "dff", 33, 3, null, null);
        UserForView user1 = changeUserToView(user);
        UserForView.viewUser(user1);
    }


    public static UserForView changeUserToView(User user){
        String role = DbFunctions.getFieldString("Roles", "id", Integer.toString(user.getId()), "name" );
        String lead = DbFunctions.getFieldString("Users", "id", Integer.toString(user.getId()), "name" );
        return new UserForView(user.getId(), user.getName(), role, lead );
    }
    public static List<UserForView> changeUsersToView(List<User> users){
        List<UserForView> usersForView = new ArrayList<>();
        for(User user: users){
            usersForView.add(changeUserToView(user));
        }
        return usersForView;
    }
    public static TaskForView changeTaskToView(Task task){
        TaskForView taskForView = new TaskForView();
        taskForView.setId(task.getId());
        taskForView.setName(task.getName());
        taskForView.setDescription((task.getDescription()));
        taskForView.setLead(User.getUserName(task.getIdLead()));
        taskForView.setPerformer(User.getUserName(task.getIdPerformer()));
        taskForView.setStatus(Status.getStatusName(task.getIdStatus()));
        taskForView.setReport(task.getReport());

        return taskForView;

    }
    public static List<TaskForView> changeTasksToView(List<Task> tasks)

    {
        if (tasks == null) return null;
        if (tasks.size() == 0) return null;
        List<TaskForView> tasksForView = new ArrayList<>();
        for(Task task : tasks){
            tasksForView.add(changeTaskToView(task));
        }
        return tasksForView;
    }
    public static StatusForView changeStatusToView(Status status){
        return new StatusForView(status.getId(), status.getName());
    }
    public static List<StatusForView> changeStatusesToView(List<Status> statuses){
        List<StatusForView> statusesForViews = new ArrayList<>();
        for(Status status: statuses){
            statusesForViews.add(changeStatusToView(status));
        }
        return statusesForViews;
    }
    public static RoleForView changeRoleToView(Role role){
        return new RoleForView(role.getId(), role.getName());
    }
    public static List<RoleForView> changeRolesToView(List<Role> roles){
        List<RoleForView> rolesForViews = new ArrayList<>();
        for(Role role: roles){
            rolesForViews.add(changeRoleToView(role));
        }
        return rolesForViews;
    }


}
