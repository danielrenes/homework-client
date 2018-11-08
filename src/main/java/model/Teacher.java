package model;

import org.json.JSONObject;

public class Teacher extends User {
    public Teacher() {
    }

    public static Teacher fromJson(JSONObject jsonObject) {
        Teacher teacher = new Teacher();
        teacher.name = jsonObject.getString("name");
        teacher.username = jsonObject.getString("username");
        return teacher;
    }
}
