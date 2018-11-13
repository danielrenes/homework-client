package client;

import model.*;
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

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

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


    public void removeTeacher(Integer id) throws ClientException {

        Request request = new Request.Builder()
                .url(String.format("http://%s:%d/api/v1/admin/teachers/" + String.valueOf(id), serverIp, serverPort))
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




    public List<Student> getStudents() throws ClientException {
        if (token == null) {
            throw new ClientException("Authentication token is needed");
        }

        List<Student> students = new ArrayList<>();
        String nextUrl = getStudentsNext(String.format("http://%s:%d/api/v1/admin/students",
                serverIp, serverPort), students);

        while (nextUrl != null) {
            nextUrl = getStudentsNext(nextUrl, students);
        }

        return students;
    }

    private String getStudentsNext(String url, List<Student> students) throws ClientException {
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
            JSONArray jsonArray = jsonObject.getJSONArray("students");
            for (int i = 0; i < jsonArray.length(); i++) {
                students.add(Student.fromJson(jsonArray.getJSONObject(i)));
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




    public void removeStudent(Integer id) throws ClientException {

        Request request = new Request.Builder()
                .url(String.format("http://%s:%d/api/v1/admin/students/" + String.valueOf(id), serverIp, serverPort))
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



    public List<Course> getCourses() throws ClientException {
        if (token == null) {
            throw new ClientException("Authentication token is needed");
        }

        List<Course> courses = new ArrayList<>();
        String nextUrl = getCoursesNext(String.format("http://%s:%d/api/v1/teacher/courses",
                serverIp, serverPort), courses);

        while (nextUrl != null) {
            nextUrl = getCoursesNext(nextUrl, courses);
        }

        return courses;
    }

    private String getCoursesNext(String url, List<Course> courses) throws ClientException {
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
            JSONArray jsonArray = jsonObject.getJSONArray("courses");
            for (int i = 0; i < jsonArray.length(); i++) {
                courses.add(Course.fromJson(jsonArray.getJSONObject(i)));
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


    public void removeCourse(Integer id) throws ClientException {

        Request request = new Request.Builder()
                .url(String.format("http://%s:%d/api/v1/teacher/course/" + String.valueOf(id), serverIp, serverPort))
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



    public List<Homework> getHomework(Integer id) throws ClientException {
        if (token == null) {
            throw new ClientException("Authentication token is needed");
        }

        List<Homework> homeworks = new ArrayList<>();
        String nextUrl = getHomeworksNext(String.format("http://%s:%d/api/v1/teacher/course/" + String.valueOf(id) + "/homeworks",
                serverIp, serverPort), homeworks);

        while (nextUrl != null) {
            nextUrl = getHomeworksNext(nextUrl, homeworks);
        }

        return homeworks;
    }

    private String getHomeworksNext(String url, List<Homework> homeworks) throws ClientException {
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
            JSONArray jsonArray = jsonObject.getJSONArray("homeworks");
            for (int i = 0; i < jsonArray.length(); i++) {
                homeworks.add(Homework.fromJson(jsonArray.getJSONObject(i)));
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




    public void createHomework(Integer id) throws ClientException {
        JSONObject jsonObject = new JSONObject();

        RequestBody formBody = RequestBody.create(JSON, jsonObject.toString());

        Request request = new Request.Builder()
                .url(String.format("http://%s:%d/api/v1/teacher/course/" + String.valueOf(id) + "/homeworks", serverIp, serverPort))
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


    public void removeHomework(Integer id) throws ClientException {

        Request request = new Request.Builder()
                .url(String.format("http://%s:%d/api/v1/teacher/homework/" + String.valueOf(id), serverIp, serverPort))
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


    public void modifyHomework(Integer id) throws ClientException {

        JSONObject jsonObject = new JSONObject();

        RequestBody formBody = RequestBody.create(JSON, jsonObject.toString());

        Request request = new Request.Builder()
                .url(String.format("http://%s:%d/api/v1/teacher/homework/" + String.valueOf(id), serverIp, serverPort))
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




    public List<Solution> getSolution(Integer id) throws ClientException {
        if (token == null) {
            throw new ClientException("Authentication token is needed");
        }

        List<Solution> solutions = new ArrayList<>();
        String nextUrl = getSolutionsNext(String.format("http://%s:%d/api/v1/teacher/solution/" + String.valueOf(id),
                serverIp, serverPort), solutions);

        while (nextUrl != null) {
            nextUrl = getSolutionsNext(nextUrl, solutions);
        }

        return solutions;
    }

    private String getSolutionsNext(String url, List<Solution> solutions) throws ClientException {
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
            JSONArray jsonArray = jsonObject.getJSONArray("solutions");
            for (int i = 0; i < jsonArray.length(); i++) {
                solutions.add(Solution.fromJson(jsonArray.getJSONObject(i)));
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





    public List<Solution> getSolutions(Integer id) throws ClientException {
        if (token == null) {
            throw new ClientException("Authentication token is needed");
        }

        List<Solution> solutions = new ArrayList<>();
        String nextUrl = getSolutionsNexts(String.format("http://%s:%d/api/v1/teacher/homework/" + String.valueOf(id) + "/solutions",
                serverIp, serverPort), solutions);

        while (nextUrl != null) {
            nextUrl = getSolutionsNexts(nextUrl, solutions);
        }

        return solutions;
    }

    private String getSolutionsNexts(String url, List<Solution> solutions) throws ClientException {
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
            JSONArray jsonArray = jsonObject.getJSONArray("solutions");
            for (int i = 0; i < jsonArray.length(); i++) {
                solutions.add(Solution.fromJson(jsonArray.getJSONObject(i)));
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



    public void modifySolution(Integer id) throws ClientException {

        JSONObject jsonObject = new JSONObject();

        RequestBody formBody = RequestBody.create(JSON, jsonObject.toString());

        Request request = new Request.Builder()
                .url(String.format("http://%s:%d/api/v1/teacher/solution/" + String.valueOf(id), serverIp, serverPort))
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



    public List<Student> getHomeworkStudents(Integer id) throws ClientException {
        if (token == null) {
            throw new ClientException("Authentication token is needed");
        }

        List<Student> students = new ArrayList<>();
        String nextUrl = getHomeworkStudentsNext(String.format("http://%s:%d/api/v1/teacher/course/" + String.valueOf(id) + "/students",
                serverIp, serverPort), students);

        while (nextUrl != null) {
            nextUrl = getStudentsNext(nextUrl, students);
        }

        return students;
    }

    private String getHomeworkStudentsNext(String url, List<Student> students) throws ClientException {
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
            JSONArray jsonArray = jsonObject.getJSONArray("students");
            for (int i = 0; i < jsonArray.length(); i++) {
                students.add(Student.fromJson(jsonArray.getJSONObject(i)));
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






    public List<Course> getAppliedCourses() throws ClientException {
        if (token == null) {
            throw new ClientException("Authentication token is needed");
        }

        List<Course> courses = new ArrayList<>();
        String nextUrl = getAppliedCoursesNext(String.format("http://%s:%d/api/v1/student/courses",
                serverIp, serverPort), courses);

        while (nextUrl != null) {
            nextUrl = getAppliedCoursesNext(nextUrl, courses);
        }

        return courses;
    }

    private String getAppliedCoursesNext(String url, List<Course> courses) throws ClientException {
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
            JSONArray jsonArray = jsonObject.getJSONArray("courses");
            for (int i = 0; i < jsonArray.length(); i++) {
                courses.add(Course.fromJson(jsonArray.getJSONObject(i)));
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


    public void abandonCourse(Integer id) throws ClientException {

        Request request = new Request.Builder()
                .url(String.format("http://%s:%d/api/v1/student/course/" + String.valueOf(id), serverIp, serverPort))
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



    public List<Homework> getHomeworkForCourse(Integer id) throws ClientException {
        if (token == null) {
            throw new ClientException("Authentication token is needed");
        }

        List<Homework> homeworks = new ArrayList<>();
        String nextUrl = getHomeworksForCourseNext(String.format("http://%s:%d/api/v1/student/course/" + String.valueOf(id) + "/homeworks",
                serverIp, serverPort), homeworks);

        while (nextUrl != null) {
            nextUrl = getHomeworksForCourseNext(nextUrl, homeworks);
        }

        return homeworks;
    }

    private String getHomeworksForCourseNext(String url, List<Homework> homeworks) throws ClientException {
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
            JSONArray jsonArray = jsonObject.getJSONArray("homeworks");
            for (int i = 0; i < jsonArray.length(); i++) {
                homeworks.add(Homework.fromJson(jsonArray.getJSONObject(i)));
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




    public List<Solution> getStudentSolution(Integer id) throws ClientException {
        if (token == null) {
            throw new ClientException("Authentication token is needed");
        }

        List<Solution> solutions = new ArrayList<>();
        String nextUrl = getStudentSolutionsNext(String.format("http://%s:%d/api/v1/student/solution/" + String.valueOf(id),
                serverIp, serverPort), solutions);

        while (nextUrl != null) {
            nextUrl = getStudentSolutionsNext(nextUrl, solutions);
        }

        return solutions;
    }

    private String getStudentSolutionsNext(String url, List<Solution> solutions) throws ClientException {
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
            JSONArray jsonArray = jsonObject.getJSONArray("solutions");
            for (int i = 0; i < jsonArray.length(); i++) {
                solutions.add(Solution.fromJson(jsonArray.getJSONObject(i)));
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





    public List<Solution> getStudentSolutions(Integer id) throws ClientException {
        if (token == null) {
            throw new ClientException("Authentication token is needed");
        }

        List<Solution> solutions = new ArrayList<>();
        String nextUrl = getStudentSolutionsNexts(String.format("http://%s:%d/api/v1/student/homework/" + String.valueOf(id) + "/solutions",
                serverIp, serverPort), solutions);

        while (nextUrl != null) {
            nextUrl = getStudentSolutionsNexts(nextUrl, solutions);
        }

        return solutions;
    }

    private String getStudentSolutionsNexts(String url, List<Solution> solutions) throws ClientException {
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
            JSONArray jsonArray = jsonObject.getJSONArray("solutions");
            for (int i = 0; i < jsonArray.length(); i++) {
                solutions.add(Solution.fromJson(jsonArray.getJSONObject(i)));
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




    public List<Homework> getStudentHomework() throws ClientException {
        if (token == null) {
            throw new ClientException("Authentication token is needed");
        }

        List<Homework> homeworks = new ArrayList<>();
        String nextUrl = getStudentHomeworksNext(String.format("http://%s:%d/api/v1/student/homeworks",
                serverIp, serverPort), homeworks);

        while (nextUrl != null) {
            nextUrl = getStudentHomeworksNext(nextUrl, homeworks);
        }

        return homeworks;
    }

    private String getStudentHomeworksNext(String url, List<Homework> homeworks) throws ClientException {
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
            JSONArray jsonArray = jsonObject.getJSONArray("homeworks");
            for (int i = 0; i < jsonArray.length(); i++) {
                homeworks.add(Homework.fromJson(jsonArray.getJSONObject(i)));
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




        public void abandonHomework(Integer id) throws ClientException {

            Request request = new Request.Builder()
                    .url(String.format("http://%s:%d/api/v1/student/homework/" + String.valueOf(id), serverIp, serverPort))
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





    public void applyCourse(Integer id) throws ClientException {
        JSONObject jsonObject = new JSONObject();

        RequestBody formBody = RequestBody.create(JSON, jsonObject.toString());

        Request request = new Request.Builder()
                .url(String.format("http://%s:%d/api/v1/student/course/" + String.valueOf(id), serverIp, serverPort))
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




    public void applyHomework(Integer id) throws ClientException {
        JSONObject jsonObject = new JSONObject();

        RequestBody formBody = RequestBody.create(JSON, jsonObject.toString());

        Request request = new Request.Builder()
                .url(String.format("http://%s:%d/api/v1/student/homework/" + String.valueOf(id), serverIp, serverPort))
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
