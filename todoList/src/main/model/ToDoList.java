package model;

import java.util.ArrayList;

import org.json.*;
import persistence.Writable;
// Represents a todo list having a collection of tasks

public class ToDoList implements Writable {
    private final ArrayList<Task> listOfTask;

    // EFFECTS: constructs a todo list
    public ToDoList() {
        listOfTask = new ArrayList<Task>();
    }

    // MODIFIES: this
    // EFFECTS: adds tasks to this todo list
    public void addSingleTask(Task t) {
        listOfTask.add(t);
        //EventLog.getInstance().logEvent(new Event("_____"));
    }

    // EFFECTS: returns an unmodifiable list of tasks in this todo list
    public ArrayList<Task> getTasks() {
        return listOfTask;
    }

    // EFFECTS: returns number of tasks in this todo list
    public int numTasks() {
        return listOfTask.size();
    }


    //Effects: return strings of all tasks
    @Override
    public String toString() {
        String all = "";
        for (Task t: listOfTask) {
            all += t.toString() + "\n";
        }
        return all;
    }

    // Modifies: listOfTask
    // Effects: delete task with given name
    public void deleteTask(String title) {
        for (Task t: listOfTask) {
            if (t.getTitle().equals(title)) {
                listOfTask.remove(t);
                return;
            }
        }
        EventLog.getInstance().logEvent(new Event("Task deleted from todo list"));
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("tasks", tasksToJson());
        return json;
    }

    // EFFECTS: returns things in this todo list as a JSON array
    private JSONArray tasksToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Task t : listOfTask) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }


    // Modifies: listOfTask
    // Effects: add task with given name and index
    public void addTaskByIndex(Task t, int index) {
        listOfTask.add(index,t);
        EventLog.getInstance().logEvent(new Event("Task added to todo list"));
    }

    // Modifies: listOfTask
    // Effects: delete task with given name and index
    public void removeTaskByIndex(int index) {
        listOfTask.remove(index);
        EventLog.getInstance().logEvent(new Event("Task deleted from todo list"));

    }


    // Effects: return task with given name and index
    public Task getTaskByIndex(int index) {
        return listOfTask.get(index);

    }


    //Effects: print EventLog
    public void printLogToConsole() {

        EventLog el = EventLog.getInstance();
        for (Event s : el) {
            System.out.println(s);
        }

    }

    //Effects: prints to console
    public void logSave() {
        EventLog.getInstance().logEvent(new Event("Task from todo list saved to Json File"));



    }

    //Effects: prints to console
    public void logLoad() {
        EventLog.getInstance().logEvent(new Event("Task from todo list loaded from Json File"));



    }




}
