package model;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Solution {
    private int id;
    private Date submittedAt;
    private String status;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Solution() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(Date submittedAt) {
        this.submittedAt = submittedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class Creator implements JsonCreator<Solution> {
        public Creator() {
        }

        @Override
        public Solution fromJson(JSONObject jsonObject) {
            Solution solution = new Solution();
            solution.id = jsonObject.getInt("id");
            solution.status = jsonObject.getString("status");
            try {
                solution.submittedAt = DATE_FORMAT.parse(jsonObject.getString("submitted_at"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return solution;
        }
    }
}
