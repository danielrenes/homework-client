package client;

import model.Teacher;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Api {
    private OkHttpClient okHttpClient = new OkHttpClient();
    private String serverIp;
    private int serverPort;
    private String token;

    public Api(String serverIp, int serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }

    public void getToken(String username, String password) throws ClientException {
        String credential = Credentials.basic(username, password);

        Request request = new Request.Builder()
                .url(String.format("http://%s:%d/api/v1/auth/token", serverIp, serverPort))
                .header("Authorization", credential)
                .post(RequestBody.create(null, new byte[0]))
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
            token = jsonObject.getString("token");
        } catch (IOException e) {
            throw new ClientException("Could not process response body");
        }
    }

    public List<Teacher> getTeachers() throws ClientException {
        if (token == null) {
            throw new ClientException("Authentication token is needed");
        }

        List<Teacher> teachers = new ArrayList<>();
        String nextUrl = getTeachersNext(String.format("http://%s:%d/api/v1/admin/teachers",
                serverIp, serverPort), teachers);

        while (nextUrl != null) {
            nextUrl = getTeachersNext(nextUrl, teachers);
        }

        return teachers;
    }

    private String getTeachersNext(String url, List<Teacher> teachers) throws ClientException {
        Request request = new Request.Builder()
                .url(url)
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
            JSONArray jsonArray = jsonObject.getJSONArray("teachers");
            for (int i = 0; i < jsonArray.length(); i++) {
                teachers.add(Teacher.fromJson(jsonArray.getJSONObject(i)));
            }
            if (!jsonObject.isNull("next")) {
                return jsonObject.getString("next");
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new ClientException("Could not process response body");
        }
    }


    public void createTeacher(String name, String username, String password) throws ClientException {

        RequestBody formBody = new FormBody.Builder()
                .add("name", "test")
                .add("username", "test")
                .add("password", "test")
                .build();


        Request request = new Request.Builder()
                .url(String.format("http://%s:%d/api/v1/admin/teachers", serverIp, serverPort))
                .header("name", "Bearer " + token)
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
