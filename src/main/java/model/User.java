package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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


    public static void main(String[] args) {

    }

}
