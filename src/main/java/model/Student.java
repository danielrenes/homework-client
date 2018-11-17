package model;

import org.json.JSONObject;

public class Student extends User {
    public Student() {
    }

    public static class Creator implements JsonCreator<Student> {
        public Creator() {
        }

        @Override
        public Student fromJson(JSONObject jsonObject) {
            Student student = new Student();
            student.id = jsonObject.getInt("id");
            student.name = jsonObject.getString("name");
            student.username = jsonObject.getString("username");
            return student;
        }
    }
}
