package model;

import org.json.JSONObject;

public class Student extends User {
    public Student() {
    }

    public static Student fromJson(JSONObject jsonObject) {
        Student student = new Student();
        student.name = jsonObject.getString("name");
        student.username = jsonObject.getString("username");
        return student;
    }
}
