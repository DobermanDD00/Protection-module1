package Facade;

import model.DbFunctions.DbFunctions;
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
    public static TaskForView changeTaskToTaskForView(Task task){
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
    public static List<TaskForView> changeTasksToTaskForView(List<Task> tasks)

    {
        if (tasks == null) return null;
        if (tasks.size() == 0) return null;
        List<TaskForView> tasksForView = new ArrayList<>();
        for(Task task : tasks){
            tasksForView.add(changeTaskToTaskForView(task));
        }
        return tasksForView;
    }


}
