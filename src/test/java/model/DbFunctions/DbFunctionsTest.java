package model.DbFunctions;

import Facade.Facade;
import model.Security;
import model.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import userInterface.view.StatusForView;

import java.util.List;

import static Facade.ChangeFormatToView.*;
import static model.DbFunctions.DbFunctions.*;

class DbFunctionsTest {
    @Test
    void getNewIdTest()
    {
        String sqlStr = """
                drop all objects;
                CREATE TABLE roles
                (
                    id int primary key auto_increment,
                    name varchar(256) not null unique
                );
                
                INSERT INTO ROLES
                (name)
                values
                    ('Работник'), ('Руководитель младшего звена'), ('Руководитель среднего звена'), ('Руководитель высшего звена'), ('Владелец'), ('Администратор');

                              
                
                """;
        updateDb(sqlStr);
        Assertions.assertEquals(getNewId("roles"), 7);



    }

    @Test
    void getFieldIntTest() {
        String sqlStr = """
                drop all objects;
                CREATE TABLE roles
                (
                    id int primary key auto_increment,
                    name varchar(256) not null unique
                );
                
                INSERT INTO ROLES
                (name)
                values
                    ('Работник'), ('Руководитель младшего звена'), ('Руководитель среднего звена'), ('Руководитель высшего звена'), ('Владелец'), ('Администратор');

                              
                
                """;
        updateDb(sqlStr);
        Assertions.assertEquals(getFieldInt("ROLES", "NAME", "Администратор", "ID"), 6);
    }

    @Test
    void getFieldStringTest() {
        String sqlStr = """
                drop all objects;
                CREATE TABLE roles
                (
                    id int primary key auto_increment,
                    name varchar(256) not null unique
                );
                
                INSERT INTO ROLES
                (name)
                values
                    ('Работник'), ('Руководитель младшего звена'), ('Руководитель среднего звена'), ('Руководитель высшего звена'), ('Владелец'), ('Администратор');

                              
                
                """;
        updateDb(sqlStr);
        Assertions.assertEquals("Администратор", getFieldString("ROLES", "Name", "Администратор", "NAME"));
    }


    @Test
    void addNewUserTest() {
        initializeDb();

        UserDb user = new UserDb(101, "Вася", 3, 5, Security.generateRandomBytes(512));
        addNewUser(user);
        UserDb user2 = getUser(user.getId());

        Assertions.assertEquals(user2, user);


    }


    @Test
    void addNewTaskTest() {
        Facade.initialize();
        TaskDb taskDb = new TaskDb(10, "ksjd".getBytes(), "skdhf".getBytes(), 5, 5, 2, "sdhfj".getBytes(), "jdfsks".getBytes(), "jdfsks".getBytes(),"jdfsks".getBytes(),"jdfsks".getBytes());
        addNewTask(taskDb);
        TaskDb taskDb1 = getTaskDb(10);

        Assertions.assertEquals(taskDb, taskDb1);
    }

    @Test
    void getTaskDbTest() {

    }


    @Test
    void updateUserTest() {
        Facade.initialize();
        UserDb userDb = new UserDb(10, "123", 1, 1, Security.generateRandomBytes(32));
        DbFunctions.addNewUser(userDb);
        userDb.setName("1234");
        userDb.setIdRole(2);
        userDb.setIdLead(2);
        userDb.setKeyPublic(Security.generateRandomBytes(32));
        DbFunctions.updateUser(userDb);
        UserDb userDb1 = DbFunctions.getUser(userDb.getId());

        Assertions.assertTrue(userDb.equals(userDb1));
    }

    @Test
    void updateTask() {
        Facade.initialize();
        TaskDb taskDb = new TaskDb(10, "ksjd".getBytes(), "skdhf".getBytes(), 5, 5, 2, "sdhfj".getBytes(), "jdfsks".getBytes(), "jdfsks".getBytes(),"jdfsks".getBytes(),"jdfsks".getBytes());
        DbFunctions.addNewTask(taskDb);
        taskDb.setName("1234".getBytes());
        taskDb.setDescription("iudsfkhg".getBytes());
        taskDb.setIdLead(1);
        taskDb.setIdPerformer(2);
        taskDb.setReport("erhskj".getBytes());
        taskDb.setIdStatus(1);
        taskDb.setSignLead("srkudgh".getBytes());
        taskDb.setSignPerformer("eorsil".getBytes());
        taskDb.setPassForLead("oersdl".getBytes());
        taskDb.setPassForPerformer("shld".getBytes());




        DbFunctions.updateTask(taskDb);
        TaskDb taskDb1 = DbFunctions.getTaskDb(taskDb.getId());

        Assertions.assertTrue(taskDb.equals(taskDb1));

    }

    @Test
    void getAllStatusesTest() {
        List<Status> statuses = Status.changeStatusesDbToStatus(DbFunctions.getAllStatuses());
        StatusForView.viewStatuses(changeStatusesToView(statuses));
    }
}