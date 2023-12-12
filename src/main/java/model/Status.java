package model;

import lombok.*;
import model.DbFunctions.DbFunctions;
import model.DbFunctions.StatusDb;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Status {
    int id;
    String name;

    public static Status getStatus(int id) {
        StatusDb statusDb = DbFunctions.getStatus(id);
        if (statusDb == null) return null;
        return new Status(statusDb.getId(), statusDb.getName());
    }

    public static String getStatusName(int id)
    {
        return getStatus(id).getName();
    }

}
