package model;

import model.Task;
import model.ToDoList;
import org.json.JSONObject;
import persistence.Writable;

// Represents a task having a title, description, and hours left until completion
public class Task implements Writable {
    private String title;
    private String description;
    private int hours;

    // Constructs a task having a title, description, and hours left until completion
    public Task(String title, String description, int hours) {
        this.title = title;
        this.description = description;
        this.hours = hours;

    }



    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getHours() {
        return hours;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }


    // EFFECTS: returns string representation of this task
    @Override
    public String toString() {
        return "Task{"
                + "title='"
                + title + '\'' +  ", description='"
                + description + '\'' + ", hours=" + hours + '}';
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("description", description);
        json.put("hours", hours);
        return json;
    }
}
