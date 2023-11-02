import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        User currentUser = ConsoleInterface.logIn();
        // Главное меню
        int choice;

        while (true) {
            choice = ConsoleInterface.mainMenu();
            switch (choice) {
                case 0: // Выйти из программы
                    return;
                case 1: // Сменить пользователя
                    currentUser = ConsoleInterface.logIn();
                    break;
                case 2: // Перейти в меню задач
                    while (true) {
                        choice = ConsoleInterface.taskMenu();
                        switch (choice) {
                            case 0:
                                break;
                            case 1:// Просмотр текущих задач
                                ConsoleInterface.viewTasks(DbFunctions.getTasks("PERFORMER", currentUser.getName()));
                                Task task = ConsoleInterface.selectTask(currentUser);
                                List<Task> tasks = new ArrayList<>();
                                tasks.add(task);
                                ConsoleInterface.viewTasks(tasks);
                                return;
                            case 2:// Просмотр назначенных задач
                                ConsoleInterface.viewTasks(DbFunctions.getTasks("LEAD", currentUser.getName()));
                                break;
                            case 3:// Создать новую задачу
                                ConsoleInterface.createNewTask();
                                break;

                            default:
                                System.out.println("Ошибка код 2");

                        }
                    }


                default:
                    System.out.println("Ошибка код 1");


            }
        }

    }

}