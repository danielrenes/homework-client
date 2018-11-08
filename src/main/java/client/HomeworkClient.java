package client;

import model.Teacher;

public class HomeworkClient {
    public static void main(String[] args) {
        String serverIp = "localhost";
        int serverPort = 5000;

        String username = "asd";
        String password = "asd";

        Api api = new Api(serverIp, serverPort);

        try {
            api.getToken(username, password);
        } catch (ClientException e) {
            e.printStackTrace();
        }

        try {
            for (Teacher teacher : api.getTeachers()) {
                System.out.println(teacher.getName());
            }
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
