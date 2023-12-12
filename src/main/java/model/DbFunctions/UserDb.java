package model.DbFunctions;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.security.PrivateKey;
import java.security.PublicKey;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode

public class UserDb {
    private int id;
    private String name;
    private int idRole;
    private int idLead;
    private byte[] keyPublic;


}
