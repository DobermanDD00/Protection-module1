

import java.io.File;
import java.io.FileNotFoundException;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Authentication {

    public static void main(String[] args) {
        System.out.println(logIn("masha", "12345"));

    }

    public static User logIn(String name, String password){
            User userDb = DbFunctions.gerUser(name);

            if (userDb == null)
                return null;

//        System.out.println("Соль из дб");
//        System.out.println(ChangeFormat.byteToHexStr(userDb.getSalt()));
//        System.out.println("Пароль из ввода");
//        System.out.println(ChangeFormat.byteToHexStr(password.getBytes(StandardCharsets.UTF_8)));
//        System.out.println("Хеш (соли из дб) и пароля");
//        System.out.println(ChangeFormat.byteToHexStr(Security.generateHashSha256(userDb.getSalt(), password.getBytes(StandardCharsets.UTF_8))));
//        System.out.println("Хеш (соли и пароля из дб)");
//        System.out.println(ChangeFormat.byteToHexStr(userDb.getHashSaltPassword()));

        if (ChangeFormat.byteToHexStr(Security.generateHashSha256(userDb.getSalt(), password.getBytes(StandardCharsets.UTF_8))).equals(ChangeFormat.byteToHexStr(userDb.getHashSaltPassword())))
            return userDb;
        else
            return null;





    }


}
