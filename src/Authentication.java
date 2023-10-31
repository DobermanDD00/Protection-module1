import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;

public class Authentication {

    public static void main(String[] args) {
        System.out.println(logIn("masha", "12345"));

    }

    public static boolean logIn(String login, String pass){

        File fileAuthorization = new File("fileAuthorization.txt");
        Scanner scanner;
        String lineUserAuthoriz;
        try {
            scanner = new Scanner(fileAuthorization);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (scanner.hasNextLine()){
            lineUserAuthoriz = scanner.nextLine();
            String[] strUserAuthoriz = lineUserAuthoriz.split(" "); // id login pass
            if (login.equals(strUserAuthoriz[1]))
                if (pass.equals(strUserAuthoriz[2])){
                    return true;
                }
        }


        return false;
    }


}
