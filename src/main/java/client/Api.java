package client;

import model.*;
import okhttp3.*;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
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
    @SuppressWarnings("unchecked")
    public List<Teacher> admin_getTeachers() throws ClientException {
        checkToken();

        PaginatedQuery query = new PaginatedQuery<>(
                new Teacher.Creator(),
                String.format("http://%s:%d/api/v1/admin/teachers", serverIp, serverPort),
                "teachers",
                token
        );

        return query.execute();
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
    @SuppressWarnings("unchecked")
    public List<Student> admin_getStudents() throws ClientException {
        checkToken();

        PaginatedQuery query = new PaginatedQuery<>(
                new Student.Creator(),
                String.format("http://%s:%d/api/v1/admin/students", serverIp, serverPort),
                "students",
                token
        );

        return query.execute();
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
    @SuppressWarnings("unchecked")
    public List<Course> teacher_getCourses() throws ClientException {
        checkToken();

        PaginatedQuery query = new PaginatedQuery<>(
                new Course.Creator(),
                String.format("http://%s:%d/api/v1/teacher/courses", serverIp, serverPort),
                "courses",
                token
        );

        return query.execute();
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
    @SuppressWarnings("unchecked")
    public List<Homework> teacher_getHomeworks(Integer id) throws ClientException {
        checkToken();

        PaginatedQuery query = new PaginatedQuery<>(
                new Homework.Creator(),
                String.format("http://%s:%d/api/v1/teacher/course/%d/homeworks", serverIp, serverPort, id),
                "homeworks",
                token
        );

        return query.execute();
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
            return new Solution.Creator().fromJson(jsonObject.getJSONObject("solution"));
        } catch (IOException e) {
            throw new ClientException("Could not process response body");
        }
    }

    //teacher-get_solutions(id)
    @SuppressWarnings("unchecked")
    public List<Solution> teacher_getSolutions(Integer id) throws ClientException {
        checkToken();

        PaginatedQuery query = new PaginatedQuery<>(
                new Solution.Creator(),
                String.format("http://%s:%d/api/v1/teacher/homework/%d/solutions", serverIp, serverPort, id),
                "solutions",
                token
        );

        return query.execute();
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
    @SuppressWarnings("unchecked")
    public List<Student> teacher_getStudents(Integer id) throws ClientException {
        checkToken();

        PaginatedQuery query = new PaginatedQuery<>(
                new Student.Creator(),
                String.format("http://%s:%d/api/v1/teacher/course/%d/students", serverIp, serverPort, id),
                "students",
                token
        );

        return query.execute();
    }


    /*--------------------------------------------End of Teacher methods--------------------------------------------*/


    /*--------------------------------------------Begin of Student methods--------------------------------------------*/


    @SuppressWarnings("unchecked")
    public List<Course> student_getCourses() throws ClientException {
        checkToken();

        PaginatedQuery query = new PaginatedQuery<>(
                new Course.Creator(),
                String.format("http://%s:%d/api/v1/student/courses/all", serverIp, serverPort),
                "courses",
                token
        );

        return query.execute();
    }

    //student-get_applied_courses()
    @SuppressWarnings("unchecked")
    public List<Course> student_getAppliedCourses() throws ClientException {
        checkToken();

        PaginatedQuery query = new PaginatedQuery<>(
                new Course.Creator(),
                String.format("http://%s:%d/api/v1/student/courses", serverIp, serverPort),
                "courses",
                token
        );

        return query.execute();
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
    @SuppressWarnings("unchecked")
    public List<Homework> student_getHomeworksForCourse(Integer id) throws ClientException {
        checkToken();

        PaginatedQuery query = new PaginatedQuery<>(
                new Homework.Creator(),
                String.format("http://%s:%d/api/v1/student/course/%d/homeworks", serverIp, serverPort, id),
                "homeworks",
                token
        );

        return query.execute();
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

    //student-get_homeworks()
    @SuppressWarnings("unchecked")
    public List<Homework> student_getHomeworks() throws ClientException {
        checkToken();

        PaginatedQuery query = new PaginatedQuery<>(
                new Homework.Creator(),
                String.format("http://%s:%d/api/v1/student/homeworks", serverIp, serverPort),
                "homeworks",
                token
        );

        return query.execute();
    }

    //student-get_solutions(id)
    @SuppressWarnings("unchecked")
    public List<Solution> student_getSolutions(Integer id) throws ClientException {
        checkToken();

        PaginatedQuery query = new PaginatedQuery<>(
                new Solution.Creator(),
                String.format("http://%s:%d/api/v1/student/homework/%d/solutions", serverIp, serverPort, id),
                "solutions",
                token
        );

        return query.execute();
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
            return new Solution.Creator().fromJson(jsonObject.getJSONObject("solution"));
        } catch (IOException e) {
            throw new ClientException("Could not process response body");
        }
    }



    /*--------------------------------------------End of Student methods--------------------------------------------*/

}