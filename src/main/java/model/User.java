package model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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


        User user1 = new User(33, "dff", 33, 3, ss1, null);
        User user2 = new User(33, "dff", 33, 3, ss2, null);
        System.out.println(user1.equals(user2));

    }

}
