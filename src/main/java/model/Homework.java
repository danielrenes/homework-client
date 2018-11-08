package model;

import java.util.Date;

public class Homework {
    private String name;
    private String description;
    private Date deadline;
    private int headcount;
    private boolean selfAssignable;
    private String courseName;

    public Homework() {
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
}
