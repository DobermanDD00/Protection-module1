package model;

import lombok.*;
import model.DbFunctions.DbFunctions;
import model.DbFunctions.RoleDb;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Role {
    int id;
    String name;

//    public static Role getRole(int id) {
//        RoleDb roleDb = DbFunctions.getRole(id);
//        if (roleDb == null) return null;
//        return new Role(roleDb.getId(), roleDb.getName());
//    }
//
//    public static String getRoleName(int id)
//    {
//        return getRole(id).getName();
//    }
    public static Role changeRoleDbToRole(RoleDb roleDb){
        return new Role(roleDb.getId(), roleDb.getName());
    }
    public static List<Role> changeRolesDbToRole(List<RoleDb> rolesDb){
        List<Role> roles = new ArrayList<>();
        for(RoleDb roleDb: rolesDb){
            roles.add(new Role(roleDb.getId(), roleDb.getName()));
        }
        return roles;
    }
}