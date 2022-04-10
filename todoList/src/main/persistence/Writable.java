package persistence;

import org.json.JSONObject;

public interface Writable {
    // EFFECTS: returnsT this as JSON object
    JSONObject toJson();
}
