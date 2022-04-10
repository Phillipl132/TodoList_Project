package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ToDoListTest {
    private ToDoList testToDoList;


    @BeforeEach
    void runBefore() {
        testToDoList = new ToDoList();
    }

    @Test
    void testConstructor() {
        assertEquals("", testToDoList.toString());
    }

    @Test
    void testAddTask() {
        Task t = new Task("homework", "Chemistry lab report",48);
        testToDoList.addSingleTask(t);
        String expected = t + "\n";
        assertEquals(expected, testToDoList.toString());

    }

    @Test
    void testDeleteTask() {
        Task t = new Task("homework", "Chemistry lab report",48);
        testToDoList.addSingleTask(t);

        testToDoList.deleteTask("Exercise");
        String expected = t + "\n";
        assertEquals(expected, testToDoList.toString());


        testToDoList.deleteTask("homework");
        expected = "";
        assertEquals(expected, testToDoList.toString());
    }

    @Test

    void testActionByIndex() {
        Task t = new Task("homework", "Chemistry lab report",48);
        Task t2 = new Task("homework2", "Bio lab report",48);
        testToDoList.addSingleTask(t);
        testToDoList.addSingleTask(t2);

        assertEquals(testToDoList.getTaskByIndex(0),t);
        testToDoList.removeTaskByIndex(0);
        assertEquals(testToDoList.getTaskByIndex(0),t2);
        testToDoList.addTaskByIndex(t,0);
        assertEquals(testToDoList.getTaskByIndex(0),t);

    }

}
