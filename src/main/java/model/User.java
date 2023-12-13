package model;

import lombok.*;
import model.DbFunctions.DbFunctions;
import model.DbFunctions.UserDb;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

public class User {
    private int id;
    private String name;
    private int idRole;
    private int idLead;
    private PublicKey keyPublic;
    private PrivateKey keyPrivate;

    public static void main(String[] args) {


        User user1 = new User(33, "dff", 33, 3, null, null);
        User user2 = new User(33, "dff", 33, 3, null, null);
        System.out.println(user1.equals(user2));

    }
    public static void createNewUser(User user)
    {
        KeyPair keyPair = Security.generatedRsaKeys();
        user.setKeyPublic(keyPair.getPublic());
        user.setKeyPrivate(keyPair.getPrivate());

        user.setId(DbFunctions.getNewIdUser());

        UserDb userDb = new UserDb(user.getId(), user.getName(), user.getIdRole(), user.getIdLead(), Security.encodedAnyKey(user.getKeyPublic()));
        DbFunctions.addNewUser(userDb);

    }
    public static User getUser(int id)
    {
        UserDb userDb = DbFunctions.getUser(id);
        return changeUserDbToUser(userDb);
    }
    public static User changeUserDbToUser(UserDb userDb){
        return new User(userDb.getId(), userDb.getName(), userDb.getIdRole(), userDb.getIdLead(), Security.decodedKeyPublicRsa(userDb.getKeyPublic()), null);

    }
    public static List<User> changeUsersDbToUsers(List<UserDb> usersDb){
        List<User> users = new ArrayList<>();
        for( UserDb userDb: usersDb){
            users.add(changeUserDbToUser(userDb));
        }
        return users;
    }
    public static String getUserName(int id)
    {
        return DbFunctions.getUser(id).getName();

    }
    public static User getUser(String name)
    {
        UserDb userDb = DbFunctions.getUser(name);
        User user = new User(userDb.getId(), userDb.getName(), userDb.getIdRole(), userDb.getIdLead(), Security.decodedKeyPublicRsa(userDb.getKeyPublic()), null);
        return user;
    }

    public static void saveUserPrivateKey(User user)
    {
        FileFunctions.writeFile(Security.encodedAnyKey(user.getKeyPrivate()), user.getName()+".txt");
    }

}
