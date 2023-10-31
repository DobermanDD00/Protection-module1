import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        while (true) { // Цикл аутентификации
            System.out.println("Введите логин и пароль через пробел");
            Scanner readConsole = new Scanner(System.in);
            String[] strUserAuthoriz = readConsole.nextLine().split(" ");

            if (Authentication.logIn(strUserAuthoriz[0], strUserAuthoriz[1])){
                System.out.println("Авторизация прошла успешно\nПользователь: " + strUserAuthoriz[0] + "\n");
                break;
            }else{
                System.out.println("Неверный логин или пароль");
            }//oh

        }




    }
}