package model;

import lombok.*;
import model.DbFunctions.DbFunctions;
import model.DbFunctions.StatusDb;

import java.util.ArrayList;
import java.util.List;

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
    public static Status changeStatusDbToStatus(StatusDb statusDb){
        return new Status(statusDb.getId(), statusDb.getName());
    }
    public static List<Status> changeStatusesDbToStatus(List<StatusDb> statusesDb){
        List<Status> statuses = new ArrayList<>();
        for(StatusDb statusDb: statusesDb){
            statuses.add(new Status(statusDb.getId(), statusDb.getName()));
        }
        return statuses;
    }

}
