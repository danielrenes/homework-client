package model;

import org.json.JSONObject;

public class Course {
    private int id;
    private String name;
    private String description;
    private String teacherName;

    public Course() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public static class Creator implements JsonCreator<Course> {
        public Creator() {
        }

        @Override
        public Course fromJson(JSONObject jsonObject) {
            Course course = new Course();
            course.id = jsonObject.getInt("id");
            course.name = jsonObject.getString("name");
            course.description = jsonObject.getString("description");
            course.teacherName = jsonObject.getString("teacher");
            return course;
        }
    }
}
