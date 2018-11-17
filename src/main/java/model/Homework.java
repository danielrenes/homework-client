package model;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Homework {
    private int id;
    private String name;
    private String description;
    private Date deadline;
    private int headcount;
    private boolean selfAssignable;
    private String courseName;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Homework() {
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

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public int getHeadcount() {
        return headcount;
    }

    public void setHeadcount(int headcount) {
        this.headcount = headcount;
    }

    public boolean isSelfAssignable() {
        return selfAssignable;
    }

    public void setSelfAssignable(boolean selfAssignable) {
        this.selfAssignable = selfAssignable;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public static Homework fromJson(JSONObject jsonObject) {
        Homework homework = new Homework();
        homework.id = jsonObject.getInt("id");
        homework.name = jsonObject.getString("name");
        homework.courseName = jsonObject.getString("course");
        homework.description = jsonObject.getString("description");
        homework.headcount = jsonObject.getInt("headcount");
        homework.selfAssignable = jsonObject.getBoolean("self_assignable");
        try {
            homework.deadline = DATE_FORMAT.parse(jsonObject.getString("deadline"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return homework;
    }
}
