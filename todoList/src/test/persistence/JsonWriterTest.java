package persistence;

import org.junit.jupiter.api.Test;

import model.ToDoList;
import model.Task;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {


    @Test
    void testWriterInvalidFile() {
        try {
            ToDoList tr = new ToDoList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            ToDoList ts = new ToDoList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTodoList.json");
            writer.open();
            writer.write(ts);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyTodoList.json");
            ts = reader.read();
            assertEquals(0, ts.numTasks());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralTodoList() {
        try {
            ToDoList ts = new ToDoList();
            ts.addSingleTask(new Task("homework", "chemistry lab report",24));
            ts.addSingleTask(new Task("exercise", "5k run",2));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTodoList.json");
            writer.open();
            writer.write(ts);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralTodoList.json");
            ts = reader.read();
            List<Task> tasks = ts.getTasks();
            assertEquals(2, tasks.size());
            Task t = tasks.get(0);
            checkTask("homework", "chemistry lab report", 24, t);
            t = tasks.get(1);
            checkTask("exercise", "5k run",2, t);


        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
