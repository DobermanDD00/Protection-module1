package model.DbFunctions;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TaskDb {

        private int id;
        private byte[] name;
        private byte[] description;
        private int idLead;
        private int idPerformer;

        private int idStatus;
        private byte[] report;
        private byte[] signLead;
        private byte[] signPerformer;
        private byte[] passForLead;
        private byte[] passForPerformer;

}
