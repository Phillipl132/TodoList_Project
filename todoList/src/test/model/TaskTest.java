package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskTest {
    private Task testTask;

    @BeforeEach
    void runBefore() {
        testTask = new Task("homework", "Chemistry lab report",48);
    }

    @Test
    void testConstructor() {
        assertEquals("homework", testTask.getTitle());
        assertEquals("Chemistry lab report", testTask.getDescription());
        assertEquals(48, testTask.getHours());
        assertTrue(testTask.getHours() > 0);
    }

    @Test
    void testSet() {
        testTask.setDescription("Run 5k");
        testTask.setHours(72);
        testTask.setTitle("Exercise");

        assertEquals("Exercise", testTask.getTitle());
        assertEquals("Run 5k", testTask.getDescription());
        assertEquals(72, testTask.getHours());

    }

    @Test
    void testToString(){
        testTask.setDescription("Run 5k");
        testTask.setHours(72);
        testTask.setTitle("Exercise");

        String s = "Task{"
                + "title='"
                + "Exercise" + '\'' + ", description='"
                + "Run 5k" + '\'' + ", hours=" + 72 + '}';

        assertEquals(s, testTask.toString());
    }







}
