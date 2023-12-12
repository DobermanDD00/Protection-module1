package model;

import model.DbFunctions.UserDb;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
    void updateUserTest() {
        initializeDb();

        UserDb user = new UserDb(101, "Вася", 3, 5, Security.generateRandomBytes(512));
        addNewUser(user);
        UserDb user2 = getUser(user.getId());


        Assertions.assertEquals(user2, user);


    }

}