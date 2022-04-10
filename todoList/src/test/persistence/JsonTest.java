package persistence;

import model.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkTask(String title, String description, int hours, Task t) {
        assertEquals(title, t.getTitle());
        assertEquals(description, t.getDescription());
        assertEquals(hours, t.getHours());
    }
}

