package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static model.DbFunctions.*;
import static org.junit.Assert.*;


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
        assertEquals(getFieldInt("ROLES", "NAME", "Администратор", "ID"), 6);
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
        assertTrue(getFieldString("ROLES", "Name", "Администратор", "NAME").equals("Администратор"));
    }


    @Test
    void addNewUserTest() {
        initializeDb();

        User user = new User(101, "Вася", 3, 5, Security.generateRandomBytes(512), null);
        User user1 = addNewUser(user);
        User user2 = getUser(user1.getId());

        Assertions.assertTrue(user.equals(user1));
        Assertions.assertTrue(user2.equals(user1));


    }

    @Test
    void updateUserTest() {
        initializeDb();

        User user = new User(2, "Вася", 3, 5, Security.generateRandomBytes(512), null);
        updateUser(user);
        User user1 = getUser(user.getId());

        Assertions.assertTrue(user.equals(user1));

    }

}