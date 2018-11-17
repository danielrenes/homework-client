package client.commands;

import client.ClientException;
import client.PaginatedQuery;
import model.Course;
import model.Homework;
import model.Solution;
import okhttp3.*;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class StudentCommands extends Commands {
    private static final MediaType MEDIA_TYPE_PDF = MediaType.parse("image/pdf");

    public StudentCommands(OkHttpClient okHttpClient, String serverIp, int serverPort) {
        super(okHttpClient, serverIp, serverPort);
    }

    @SuppressWarnings("unchecked")
    public List<Course> getCourses() throws ClientException {
        PaginatedQuery query = new PaginatedQuery<>(
                new Course.Creator(),
                String.format("http://%s:%d/api/v1/student/courses/all", serverIp, serverPort),
                "courses",
                token
        );

        return query.execute();
    }

    @SuppressWarnings("unchecked")
    public List<Course> getAppliedCourses() throws ClientException {
        PaginatedQuery query = new PaginatedQuery<>(
                new Course.Creator(),
                String.format("http://%s:%d/api/v1/student/courses", serverIp, serverPort),
                "courses",
                token
        );

        return query.execute();
    }

    public void applyCourse(int id) throws ClientException {
        JSONObject jsonObject = new JSONObject();

        RequestBody formBody = RequestBody.create(JSON, jsonObject.toString());

        Request request = new Request.Builder()
                .url(String.format("http://%s:%d/api/v1/student/course/%d", serverIp, serverPort, id))
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

    public void abandonCourse(int id) throws ClientException {
        Request request = new Request.Builder()
                .url(String.format("http://%s:%d/api/v1/student/course/%d", serverIp, serverPort, id))
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

    public void uploadHomework(int id, File f, String fileName) throws ClientException {
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", fileName, RequestBody.create(MEDIA_TYPE_PDF, f))
                .build();

        Request request = new Request.Builder()
                .url(String.format("http://%s:%d/api/v1/student/homework/%d/submit", serverIp, serverPort, id))
                .header("Authorization", "Bearer " + token)
                .post(requestBody)
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
    public List<Homework> getHomeworksForCourse(int id) throws ClientException {
        PaginatedQuery query = new PaginatedQuery<>(
                new Homework.Creator(),
                String.format("http://%s:%d/api/v1/student/course/%d/homeworks", serverIp, serverPort, id),
                "homeworks",
                token
        );

        return query.execute();
    }

    public void applyHomework(int id) throws ClientException {
        JSONObject jsonObject = new JSONObject();

        RequestBody formBody = RequestBody.create(JSON, jsonObject.toString());

        Request request = new Request.Builder()
                .url(String.format("http://%s:%d/api/v1/student/homework/%d", serverIp, serverPort, id))
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

    public void abandonHomework(int id) throws ClientException {
        Request request = new Request.Builder()
                .url(String.format("http://%s:%d/api/v1/student/homework/%d", serverIp, serverPort, id))
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
    public List<Homework> getHomeworks() throws ClientException {
        PaginatedQuery query = new PaginatedQuery<>(
                new Homework.Creator(),
                String.format("http://%s:%d/api/v1/student/homeworks", serverIp, serverPort),
                "homeworks",
                token
        );

        return query.execute();
    }

    @SuppressWarnings("unchecked")
    public List<Solution> getSolutions(int id) throws ClientException {
        PaginatedQuery query = new PaginatedQuery<>(
                new Solution.Creator(),
                String.format("http://%s:%d/api/v1/student/homework/%d/solutions", serverIp, serverPort, id),
                "solutions",
                token
        );

        return query.execute();
    }

    public Solution getSolution(int id) throws ClientException {
        Request request = new Request.Builder()
                .url(String.format("http://%s:%d/api/v1/student/solution/%d", serverIp, serverPort, id))
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
}
