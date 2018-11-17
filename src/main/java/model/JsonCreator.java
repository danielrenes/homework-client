package model;

import org.json.JSONObject;

public interface JsonCreator<T> {
    T fromJson(JSONObject jsonObject);
}
