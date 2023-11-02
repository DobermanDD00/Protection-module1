
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.nio.charset.StandardCharsets;

@Getter
@Setter
@AllArgsConstructor

public class User {
    private int id;
    private String name;
    private String role;
    private String lead;
    private byte[] sign;
    private byte[] keyPublic;
    private byte[] keyPrivate;
    private byte[] salt;
    private byte[] hashSaltPassword;

//    public User(int id, String name, String role, String lead, byte[] sign, byte[] keyPublic, byte[] keyPrivate, byte[] salt, byte[] hashSaltPassword) {//todo Сделать генерацию ключей для подписей, сделать обработку ошибок
//        this.id = id;
//        this.name = name;
//        this.role = role;
//        this.lead = lead;
//
//        this.sign = sign;
//        this.keyPublic = keyPublic;
//        this.keyPrivate = keyPrivate;
//
//        this.salt = salt;
//        this.hashSaltPassword = hashSaltPassword;
//
//    }
    public static User createNewUser (String name, String role, String lead, String password){

        byte[] sign = Security.generateRandomBytes(32);//********************
        byte[] keyPublic = Security.generateRandomBytes(512);
        byte[] keyPrivate = Security.generateRandomBytes(512);

        byte[] salt = Security.generateRandomBytes(32);
        byte[] hashSaltPassword = Security.generateHashSha256(salt, password.getBytes(StandardCharsets.UTF_8));

        boolean userAddedInDb = DbFunctions.addNewUser(name, role, lead, sign, keyPublic, keyPrivate, salt, hashSaltPassword);
        if (userAddedInDb)
            System.out.println("Пользователь успешно добавлен");
        else
            System.out.println("Ошибка, пользователь не добавлен");

        int id = DbFunctions.getIdByName("USERS", name);

        return new User(id, name, role, lead, sign, keyPublic, keyPrivate, salt, hashSaltPassword);
    }

    public static void main(String[] args) {
        ConsoleInterface.createNewUser();
    }

}
