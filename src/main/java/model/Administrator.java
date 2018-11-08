package model;

import org.json.JSONObject;

public class Administrator extends User {
    public Administrator() {
    }

    public static Administrator fromJson(JSONObject jsonObject) {
        Administrator administrator = new Administrator();
        administrator.name = jsonObject.getString("name");
        administrator.username = jsonObject.getString("username");
        return administrator;
    }
}
