import java.nio.charset.StandardCharsets;

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

    public User(String name, String role, String lead, String password) {//todo Сделать генерацию ключей для подписей, сделать обработку ошибок
        boolean ret = true;
        this.name = name;
        this.role = role;
        this.lead = lead;

        this.sign = ChangeFormat.stringToBytes("123");//********************
        this.keyPublic = ChangeFormat.stringToBytes("123");
        this.keyPrivate = ChangeFormat.stringToBytes("123");

        this.salt = Security.generateSalt(32);
        this.hashSaltPassword = Security.generateHashSha256(this.salt, password.getBytes(StandardCharsets.UTF_8));

        boolean userAddedInDb = DbFunctions.addNewUser(this.name, this.role, this.lead, this.sign, this.keyPublic, this.keyPrivate, this.salt, this.hashSaltPassword);
        if (userAddedInDb)
            System.out.println("Пользователь успешно добавлен");
        else
            System.out.println("Ошибка, пользователь не добавлен");

        this.id = DbFunctions.getIdByName("USERS", name);


    }

    public static void main(String[] args) {
        ConsoleInterface.createNewUser();
    }

}
