package model;

import org.json.JSONObject;

public class Teacher extends User {
    public Teacher() {
    }

    public static class Creator implements JsonCreator<Teacher> {
        public Creator() {
        }

        @Override
        public Teacher fromJson(JSONObject jsonObject) {
            Teacher teacher = new Teacher();
            teacher.id = jsonObject.getInt("id");
            teacher.name = jsonObject.getString("name");
            teacher.username = jsonObject.getString("username");
            return teacher;
        }
    }
}
