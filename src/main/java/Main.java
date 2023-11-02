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
                case 2: // Перейти в меню с задачами
                    choice = ConsoleInterface.taskMenu();
                    switch (choice) {
                        case 0:
                            break;
                        case 1:// Просмотр текущих задач
                            ConsoleInterface.viewReceivedTasks(DbFunctions.getReceivedTasks(currentUser.getName()));
                            return;
                        case 2:// Просмотр назначенных задач
                            break;
                        case 3:// Создать новую задачу
                            ConsoleInterface.createNewTask();
                            break;

                        default:
                            System.out.println("Ошибка код 2");


                    }

                default:
                    System.out.println("Ошибка код 1");


            }
        }

    }

}