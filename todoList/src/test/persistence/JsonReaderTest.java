package persistence;

import model.Task;
import model.ToDoList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ToDoList ts = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTodoList.json");
        try {
            ToDoList ts = reader.read();
            assertEquals(0, ts.numTasks());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralTodoList.json");
        try {
            ToDoList ts = reader.read();
            ts = reader.read();
            ArrayList<Task> tasks = ts.getTasks();
            assertEquals(2, tasks.size());
            Task t = tasks.get(0);
            checkTask("homework", "chemistry lab report", 24, t);
            t = tasks.get(1);
            checkTask("exercise", "5k run",2, t);

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
