package persistence;

import model.Task;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.ToDoList;
import org.json.*;

public class JsonReader {

    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads todolist from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ToDoList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseToDoList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses todolist from JSON object and returns it
    private ToDoList parseToDoList(JSONObject jsonObject) {
        ToDoList ts = new ToDoList();
        addTasks(ts, jsonObject);
        return ts;
    }

    // MODIFIES: ts
    // EFFECTS: parses tasks from JSON object and adds them to todolist
    private void addTasks(ToDoList ts, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("tasks");
        for (Object json : jsonArray) {
            JSONObject nextTask = (JSONObject) json;
            addTask(ts, nextTask);
        }
    }


    // MODIFIES: ts
    // EFFECTS: parses task from JSON object and adds it to todolist
    private void addTask(ToDoList ts, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        String description = jsonObject.getString("description");
        int hours = jsonObject.getInt("hours");
        Task t = new Task(title, description, hours);
        ts.addSingleTask(t);
    }
}
