package model.DbFunctions;

import Facade.Facade;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static model.DbFunctions.DbFunctions.addNewTask;
import static model.DbFunctions.DbFunctions.getTaskDb;

class DbFunctionsTest {

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


}