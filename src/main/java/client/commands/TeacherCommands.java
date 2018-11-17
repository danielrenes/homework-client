package client.commands;

import client.ClientException;
import client.PaginatedQuery;
import model.Course;
import model.Homework;
import model.Solution;
import model.Student;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class TeacherCommands extends Commands {
    public TeacherCommands(OkHttpClient okHttpClient, String serverIp, int serverPort) {
        super(okHttpClient, serverIp, serverPort);
    }

    @SuppressWarnings("unchecked")
    public List<Course> getCourses() throws ClientException {
        PaginatedQuery query = new PaginatedQuery<>(
                new Course.Creator(),
                String.format("http://%s:%d/api/v1/teacher/courses", serverIp, serverPort),
                "courses",
                token
        );

        return query.execute();
    }

    public void createCourse(String name, String description) throws ClientException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("description", description);

        RequestBody formBody = RequestBody.create(JSON, jsonObject.toString());

        Request request = new Request.Builder()
                .url(String.format("http://%s:%d/api/v1/teacher/courses", serverIp, serverPort))
                .header("Authorization", "Bearer " + token)
                .post(formBody)
                .build();

        Response response;

        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            throw new ClientException("Could not execute request");
        }

        if (response.code() != 200 || response.body() == null) {
            throw new ClientException("Request failed");
        }
    }

    public void removeCourse(int id) throws ClientException {
        Request request = new Request.Builder()
                .url(String.format("http://%s:%d/api/v1/teacher/course/%d", serverIp, serverPort, id))
                .header("Authorization", "Bearer " + token)
                .delete()
                .build();

        Response response;

        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            throw new ClientException("Could not execute request");
        }

        if (response.code() != 200 || response.body() == null) {
            throw new ClientException("Request failed");
        }
    }

    @SuppressWarnings("unchecked")
    public List<Homework> getHomeworks(int id) throws ClientException {
        PaginatedQuery query = new PaginatedQuery<>(
                new Homework.Creator(),
                String.format("http://%s:%d/api/v1/teacher/course/%d/homeworks", serverIp, serverPort, id),
                "homeworks",
                token
        );

        return query.execute();
    }

    public void createHomework(String name,
                               String description,
                               String deadline,
                               String headcount,
                               String selfAssignable,
                               int id) throws ClientException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("description", description);
        jsonObject.put("deadline", deadline);
        jsonObject.put("headcount", headcount);
        jsonObject.put("self_assignable", selfAssignable);

        RequestBody formBody = RequestBody.create(JSON, jsonObject.toString());

        Request request = new Request.Builder()
                .url(String.format("http://%s:%d/api/v1/teacher/course/%d/homeworks", serverIp, serverPort, id))
                .header("Authorization", "Bearer " + token)
                .post(formBody)
                .build();

        Response response;

        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            throw new ClientException("Could not execute request");
        }

        if (response.code() != 200 || response.body() == null) {
            throw new ClientException("Request failed");
        }
    }

    public void removeHomework(int id) throws ClientException {
        Request request = new Request.Builder()
                .url(String.format("http://%s:%d/api/v1/teacher/homework/%d", serverIp, serverPort, id))
                .header("Authorization", "Bearer " + token)
                .delete()
                .build();

        Response response;

        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            throw new ClientException("Could not execute request");
        }

        if (response.code() != 200 || response.body() == null) {
            throw new ClientException("Request failed");
        }
    }

    public void modifyHomework(String name,
                               String description,
                               String deadline,
                               String headcount,
                               String selfAssignable,
                               int id) throws ClientException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("description", description);
        jsonObject.put("deadline", deadline);
        jsonObject.put("headcount", headcount);
        jsonObject.put("self_assignable", selfAssignable);

        RequestBody formBody = RequestBody.create(JSON, jsonObject.toString());

        Request request = new Request.Builder()
                .url(String.format("http://%s:%d/api/v1/teacher/homework/%d", serverIp, serverPort, id))
                .header("Authorization", "Bearer " + token)
                .put(formBody)
                .build();

        Response response;

        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            throw new ClientException("Could not execute request");
        }

        if (response.code() != 200 || response.body() == null) {
            throw new ClientException("Request failed");
        }
    }

    public Solution getSolution(int id) throws ClientException {
        Request request = new Request.Builder()
                .url(String.format("http://%s:%d/api/v1/teacher/solution/%d", serverIp, serverPort, id))
                .header("Authorization", "Bearer " + token)
                .get()
                .build();

        Response response;

        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            throw new ClientException("Could not execute request");
        }

        if (response.code() != 200 || response.body() == null) {
            throw new ClientException("Request failed");
        }

        try {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return new Solution.Creator().fromJson(jsonObject.getJSONObject("solution"));
        } catch (IOException e) {
            throw new ClientException("Could not process response body");
        }
    }

    @SuppressWarnings("unchecked")
    public List<Solution> getSolutions(int id) throws ClientException {
        PaginatedQuery query = new PaginatedQuery<>(
                new Solution.Creator(),
                String.format("http://%s:%d/api/v1/teacher/homework/%d/solutions", serverIp, serverPort, id),
                "solutions",
                token
        );

        return query.execute();
    }

    public void modifySolution(int id, String status) throws ClientException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", status);

        RequestBody formBody = RequestBody.create(JSON, jsonObject.toString());

        Request request = new Request.Builder()
                .url(String.format("http://%s:%d/api/v1/teacher/solution/%d", serverIp, serverPort, id))
                .header("Authorization", "Bearer " + token)
                .put(formBody)
                .build();

        Response response;

        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            throw new ClientException("Could not execute request");
        }

        if (response.code() != 200 || response.body() == null) {
            throw new ClientException("Request failed");
        }
    }

    @SuppressWarnings("unchecked")
    public List<Student> getStudents(int id) throws ClientException {
        PaginatedQuery query = new PaginatedQuery<>(
                new Student.Creator(),
                String.format("http://%s:%d/api/v1/teacher/course/%d/students", serverIp, serverPort, id),
                "students",
                token
        );

        return query.execute();
    }
}