package model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.security.KeyPair;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode

public class User {
    private int id;
    private String name;
    private int idRole;
    private int idLead;
    private byte[] keyPublic;
    private byte[] keyPrivate;

    public static void main(String[] args) {

        byte[] ss1 = "fdf".getBytes();
        byte[] ss2 = "fdf".getBytes();


        User user1 = new User(33, "dff", 33, 3, null, null);
        User user2 = new User(33, "dff", 33, 3, null, null);
        System.out.println(user1.equals(user2));

    }
    public static void createNewUser(User user)
    {
        KeyPair keyPair = Security.generatedRsaKeys();
        user.setKeyPublic(Security.encodedAnyKey(keyPair.getPublic()));
        user.setKeyPrivate(Security.encodedAnyKey(keyPair.getPrivate()));

        user.setId(DbFunctions.getNewIdUser());
        DbFunctions.addNewUser(user);

    }
    public static void saveUserPrivateKey(User user)
    {
        FileFunctions.writeFile(user.getKeyPrivate(), user.getName()+".txt");
    }

}
