package client;

import model.*;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Api {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType MEDIA_TYPE_PDF = MediaType.parse("image/pdf");

    private final String serverIp;
    private final int serverPort;

    private OkHttpClient okHttpClient = new OkHttpClient();

    private String credential;
    private String token;

    private static Api instance;

    private Api(String serverIp, int serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }

    public static Api getInstance() throws ClientException {
        if (instance == null) {
            Configuration configuration = Configuration.getInstance();
            instance = new Api(configuration.getServerIp(), configuration.getServerPort());
        }
        return instance;
    }

    private void checkToken() throws ClientException {
        if (token == null) {
            if (credential == null) {
                throw new ClientException("Token and credential are null");
            }
        } else {
            Request request = new Request.Builder()
                    .url(String.format("http://%s:%d/api/v1/auth/token", serverIp, serverPort))
                    .header("Authorization", "Bearer " + token)
                    .get()
                    .build();

            Response response;

            try {
                response = okHttpClient.newCall(request).execute();
            } catch (IOException e) {
                throw new ClientException("Could not execute request");
            }

            if (response.code() != 200) {
                getToken();
            }
        }
    }

    private void getToken() throws ClientException {
        if (credential == null) {
            throw new ClientException("Credential needed for basic auth");
        }

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

    public void getToken(String username, String password) throws ClientException {
        credential = Credentials.basic(username, password);
        getToken();
    }


    /*--------------------------------------------Begin of Administrator methods--------------------------------------------*/


    //admin-get_teachers()
    public List<Teacher> admin_getTeachers() throws ClientException {
        checkToken();

        List<Teacher> teachers = new ArrayList<>();
        String nextUrl = admin_getTeachersNext(String.format("http://%s:%d/api/v1/admin/teachers",
                serverIp, serverPort), teachers);

        while (nextUrl != null) {
            nextUrl = admin_getTeachersNext(nextUrl, teachers);
        }

        return teachers;
    }

    //admin-get_teachers
    private String admin_getTeachersNext(String url, List<Teacher> teachers) throws ClientException {
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

    //admin-create_teacher()
    public void admin_createTeacher(String name, String username, String password) throws ClientException {
        checkToken();

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

    //admin-remove_teacher(id)
    public void admin_removeTeacher(Integer id) throws ClientException {
        checkToken();

        Request request = new Request.Builder()
                .url(String.format("http://%s:%d/api/v1/admin/teacher/" + String.valueOf(id), serverIp, serverPort))
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

    //admin-get_students()
    public List<Student> admin_getStudents() throws ClientException {
        checkToken();

        if (token == null) {
            throw new ClientException("Authentication token is needed");
        }

        List<Student> students = new ArrayList<>();
        String nextUrl = admin_getStudentsNext(String.format("http://%s:%d/api/v1/admin/students",
                serverIp, serverPort), students);

        while (nextUrl != null) {
            nextUrl = admin_getStudentsNext(nextUrl, students);
        }

        return students;
    }


    //admin-get_students
    private String admin_getStudentsNext(String url, List<Student> students) throws ClientException {
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



    //admin-remove_student(id)
    public void admin_removeStudent(Integer id) throws ClientException {
        checkToken();

        Request request = new Request.Builder()
                .url(String.format("http://%s:%d/api/v1/admin/student/" + String.valueOf(id), serverIp, serverPort))
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


    //admin-create_studetns()
    public void admin_createStudent(String name, String username, String password) throws ClientException {
        checkToken();

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


    /*--------------------------------------------End of Administrator methods--------------------------------------------*/


    /*--------------------------------------------Begin of Teacher methods--------------------------------------------*/


    //teacher-get_courses()
    public List<Course> teacher_getCourses() throws ClientException {
        checkToken();

        if (token == null) {
            throw new ClientException("Authentication token is needed");
        }

        List<Course> courses = new ArrayList<>();
        String nextUrl = teacher_getCoursesNext(String.format("http://%s:%d/api/v1/teacher/courses",
                serverIp, serverPort), courses);

        while (nextUrl != null) {
            nextUrl = teacher_getCoursesNext(nextUrl, courses);
        }

        return courses;
    }

    //teacher-get_courses
    private String teacher_getCoursesNext(String url, List<Course> courses) throws ClientException {
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



    //teacher-create_course()
    public void teacher_createCourse(String name, String description) throws ClientException {
        checkToken();

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

    //teacher-remove_course(id)
    public void teacher_removeCourse(Integer id) throws ClientException {
        checkToken();

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


    //teacher-get_homeworks(id)
    public List<Homework> teacher_getHomeworks(Integer id) throws ClientException {
        checkToken();

        if (token == null) {
            throw new ClientException("Authentication token is needed");
        }

        List<Homework> homeworks = new ArrayList<>();
        String nextUrl = teacher_getHomeworksNext(String.format("http://%s:%d/api/v1/teacher/course/" + String.valueOf(id) + "/homeworks",
                serverIp, serverPort), homeworks);

        while (nextUrl != null) {
            nextUrl = teacher_getHomeworksNext(nextUrl, homeworks);
        }

        return homeworks;
    }

    //teacher-get_homeworks
    private String teacher_getHomeworksNext(String url, List<Homework> homeworks) throws ClientException {
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



    //teacher-create_homework(id)
    public void teacher_createHomework(String name, String description, String deadline, String headcount, String selfAssignable, Integer id) throws ClientException {
        checkToken();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("description", description);
        jsonObject.put("deadline", deadline);
        jsonObject.put("headcount", headcount);
        jsonObject.put("self_assignable", selfAssignable);

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


    //teacher-remove_homework(id)
    public void teacher_removeHomework(Integer id) throws ClientException {
        checkToken();

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


    //teacher-modify_homework(id)
    public void teacher_modifyHomework(String name, String description, String deadline, String headcount, String selfAssignable, Integer id) throws ClientException {
        checkToken();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("description", description);
        jsonObject.put("deadline", deadline);
        jsonObject.put("headcount", headcount);
        jsonObject.put("self_assignable", selfAssignable);

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



    //teacher-get_solution(id)
    public Solution teacher_getSolution(Integer id) throws ClientException {
        checkToken();

        Request request = new Request.Builder()
                .url(String.format("http://%s:%d/api/v1/teacher/solution/" + String.valueOf(id), serverIp, serverPort))
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
            return Solution.fromJson(jsonObject.getJSONObject("solution"));
        } catch (IOException e) {
            throw new ClientException("Could not process response body");
        }
    }

    //teacher-get_solutions(id)
    public List<Solution> teacher_getSolutions(Integer id) throws ClientException {
        checkToken();

        List<Solution> solutions = new ArrayList<>();
        String nextUrl = teacher_getSolutionsNext(String.format("http://%s:%d/api/v1/teacher/homework/" + String.valueOf(id) + "/solutions",
                serverIp, serverPort), solutions);

        while (nextUrl != null) {
            nextUrl = teacher_getSolutionsNext(nextUrl, solutions);
        }

        return solutions;
    }

    //teacher-get_solutions
    private String teacher_getSolutionsNext(String url, List<Solution> solutions) throws ClientException {
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


    //teacher-modify_solution(id)
    public void teacher_modifySolution(Integer id, String status) throws ClientException {
        checkToken();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", status);

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


    //teacher-get_students(id)
    public List<Student> teacher_getStudents(Integer id) throws ClientException {
        checkToken();

        List<Student> students = new ArrayList<>();
        String nextUrl = teacher_getStudentsNext(String.format("http://%s:%d/api/v1/teacher/course/" + String.valueOf(id) + "/students",
                serverIp, serverPort), students);

        while (nextUrl != null) {
            nextUrl = teacher_getStudentsNext(nextUrl, students);
        }

        return students;
    }


    //teacher-get_students
    private String teacher_getStudentsNext(String url, List<Student> students) throws ClientException {
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



    /*--------------------------------------------End of Teacher methods--------------------------------------------*/


    /*--------------------------------------------Begin of Student methods--------------------------------------------*/


    public List<Course> student_getCourses() throws ClientException {
        checkToken();

        List<Course> courses = new ArrayList<>();
        String nextUrl = student_getCoursesNext(String.format("http://%s:%d/api/v1/student/courses/all",
                serverIp, serverPort), courses);

        while (nextUrl != null) {
            nextUrl = student_getCoursesNext(nextUrl, courses);
        }

        return courses;
    }

    private String student_getCoursesNext(String url, List<Course> courses) throws ClientException {
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

    //student-get_applied_courses()
    public List<Course> student_getAppliedCourses() throws ClientException {
        checkToken();

        List<Course> courses = new ArrayList<>();
        String nextUrl = student_getAppliedCoursesNext(String.format("http://%s:%d/api/v1/student/courses",
                serverIp, serverPort), courses);

        while (nextUrl != null) {
            nextUrl = student_getAppliedCoursesNext(nextUrl, courses);
        }

        return courses;
    }

    //student-get_applied_courses
    private String student_getAppliedCoursesNext(String url, List<Course> courses) throws ClientException {
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


    //student-apply_for_course(id)
    public void student_applyCourse(Integer id) throws ClientException {
        checkToken();

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


    //student-abandon_course(id)
    public void student_abandonCourse(Integer id) throws ClientException {
        checkToken();

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


    //student-upload-homework
    public void student_uploadHomework(int id, File f, String fileName) throws ClientException {
        checkToken();

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


    //student-get_homeworks_for_course(id)
    public List<Homework> student_getHomeworkForCourse(Integer id) throws ClientException {
        checkToken();

        List<Homework> homeworks = new ArrayList<>();
        String nextUrl = student_getHomeworksForCourseNext(String.format("http://%s:%d/api/v1/student/course/" + String.valueOf(id) + "/homeworks",
                serverIp, serverPort), homeworks);

        while (nextUrl != null) {
            nextUrl = student_getHomeworksForCourseNext(nextUrl, homeworks);
        }

        return homeworks;
    }


    //student-get_homeworks_for_course
    private String student_getHomeworksForCourseNext(String url, List<Homework> homeworks) throws ClientException {
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




    //student-apply_for_homework(id)
    public void student_applyHomework(Integer id) throws ClientException {
        checkToken();

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


    //student-abandon_homework(id)
    public void student_abandonHomework(Integer id) throws ClientException {
        checkToken();

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


    ////////////////MISSING submit_solution(id)//////////////////////////////////////////////////////////////////////////////////////////////



    //student-get_homeworks()
    public List<Homework> student_getHomeworks() throws ClientException {
        checkToken();

        List<Homework> homeworks = new ArrayList<>();
        String nextUrl = student_getHomeworksNext(String.format("http://%s:%d/api/v1/student/homeworks",
                serverIp, serverPort), homeworks);

        while (nextUrl != null) {
            nextUrl = student_getHomeworksNext(nextUrl, homeworks);
        }

        return homeworks;
    }

    //student-get_homeworks
    private String student_getHomeworksNext(String url, List<Homework> homeworks) throws ClientException {
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



    //student-get_solutions(id)
    public List<Solution> student_getSolutions(Integer id) throws ClientException {
        checkToken();

        List<Solution> solutions = new ArrayList<>();
        String nextUrl = student_getSolutionsNext(String.format("http://%s:%d/api/v1/student/homework/" + String.valueOf(id) + "/solutions",
                serverIp, serverPort), solutions);

        while (nextUrl != null) {
            nextUrl = student_getSolutionsNext(nextUrl, solutions);
        }

        return solutions;
    }

    //student-get_solutions
    private String student_getSolutionsNext(String url, List<Solution> solutions) throws ClientException {
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


    //student-get_solution(id)
    public Solution student_getSolution(Integer id) throws ClientException {
        checkToken();

        Request request = new Request.Builder()
                .url(String.format("http://%s:%d/api/v1/student/solution/" + String.valueOf(id), serverIp, serverPort))
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
            return Solution.fromJson(jsonObject.getJSONObject("solution"));
        } catch (IOException e) {
            throw new ClientException("Could not process response body");
        }
    }



    /*--------------------------------------------End of Student methods--------------------------------------------*/

}