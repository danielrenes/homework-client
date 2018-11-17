package client.commands;

import client.ClientException;
import client.PaginatedQuery;
import model.Student;
import model.Teacher;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class AdministratorCommands extends Commands {
    public AdministratorCommands(OkHttpClient okHttpClient,
                                 String serverIp,
                                 int serverPort) {
        super(okHttpClient, serverIp, serverPort);
    }

    @SuppressWarnings("unchecked")
    public List<Teacher> getTeachers() throws ClientException {
        PaginatedQuery query = new PaginatedQuery<>(
                new Teacher.Creator(),
                String.format("http://%s:%d/api/v1/admin/teachers", serverIp, serverPort),
                "teachers",
                token
        );

        return query.execute();
    }

    public void createTeacher(String name, String username, String password) throws ClientException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("username", username);
        jsonObject.put("password", password);

        RequestBody formBody = RequestBody.create(JSON, jsonObject.toString());

        Request request = new Request.Builder()
                .url(String.format("http://%s:%d/api/v1/admin/teachers", serverIp, serverPort))
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

    public void removeTeacher(int id) throws ClientException {
        Request request = new Request.Builder()
                .url(String.format("http://%s:%d/api/v1/admin/teacher/%d", serverIp, serverPort, id))
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
    public List<Student> getStudents() throws ClientException {
        PaginatedQuery query = new PaginatedQuery<>(
                new Student.Creator(),
                String.format("http://%s:%d/api/v1/admin/students", serverIp, serverPort),
                "students",
                token
        );

        return query.execute();
    }

    public void removeStudent(int id) throws ClientException {
        Request request = new Request.Builder()
                .url(String.format("http://%s:%d/api/v1/admin/student/%d", serverIp, serverPort, id))
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

    public void createStudent(String name, String username, String password) throws ClientException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("username", username);
        jsonObject.put("password", password);

        RequestBody formBody = RequestBody.create(JSON, jsonObject.toString());

        Request request = new Request.Builder()
                .url(String.format("http://%s:%d/api/v1/admin/students", serverIp, serverPort))
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
}