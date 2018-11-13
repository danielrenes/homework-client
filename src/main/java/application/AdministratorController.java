package application;

import java.io.IOException;
import java.util.*;

import client.Api;
import client.ClientException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Administrator;
import model.Teacher;
import model.Student;
import model.User;


public class AdministratorController {

	@FXML TextField admin_teachername;
	@FXML TextField admin_studentname;
    @FXML TextField admin_teacherid;
    @FXML TextField admin_studentid;
    @FXML TextField admin_teacherusername;
    @FXML TextField admin_studentusername;
    @FXML TextField admin_teacherpassword;
    @FXML TextField admin_studentpassword;
	@FXML TableView admintable;



    String serverIp = "localhost";
    int serverPort = 5000;

    String username = "asd";
    String password = "asd";

    public Api api = new Api(serverIp, serverPort);


	@FXML
	private void teacherList() throws IOException{
        try {
            api.getToken("asd", "asd");
        } catch (ClientException e) {
            e.printStackTrace();
        }
        List<Teacher> teacherList = new ArrayList<Teacher>();
        try {
            teacherList = api.getTeachers();
        } catch (ClientException e) {
            e.printStackTrace();
        }


        admintable.setEditable(true);

        TableColumn<Teacher, String> usernameColumn = new TableColumn<Teacher, String>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        TableColumn<Teacher, String> nameColumn = new TableColumn<Teacher, String>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        admintable.getItems().clear();
        admintable.getColumns().clear();

        admintable.getColumns().addAll(nameColumn, usernameColumn);

        Teacher t = new Teacher();
        for (int i = 0; i < teacherList.size(); i++) {
            t = new Teacher();
            t.setUsername(teacherList.get(i).getUsername());
            t.setName(teacherList.get(i).getName());
            admintable.getItems().add(t);
        }

	}
	
	@FXML
	private void teacherCreate() throws IOException{
        try {
            api.getToken("asd", "asd");
        } catch (ClientException e) {
            e.printStackTrace();
        }

		String teachername = admin_teachername.getText();
        String teacherusername = admin_teacherusername.getText();
        String teacherpassword = admin_teacherpassword.getText();

        try {
            api.createTeacher(teachername, teacherusername, teacherpassword);
        } catch (ClientException e) {
            e.printStackTrace();
        }
	}
	
	@FXML
	private void teacherDelete() throws IOException{
        try {
            api.getToken("asd", "asd");
        } catch (ClientException e) {
            e.printStackTrace();
        }

		Integer teacherid = Integer.parseInt(admin_teacherid.getText());

        try {
            api.removeTeacher(teacherid);
        } catch (ClientException e) {
            e.printStackTrace();
        }

	}
	
	@FXML
	private void studentList() throws IOException{
        try {
            api.getToken("asd", "asd");
        } catch (ClientException e) {
            e.printStackTrace();
        }
        List<Student> studentList = new ArrayList<Student>();
        try {
            studentList = api.getStudents();
        } catch (ClientException e) {
            e.printStackTrace();
        }


        admintable.setEditable(true);

        TableColumn<Student, String> usernameColumn = new TableColumn<Student, String>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        TableColumn<Student, String> nameColumn = new TableColumn<Student, String>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        admintable.getItems().clear();
        admintable.getColumns().clear();

        admintable.getColumns().addAll(nameColumn, usernameColumn);

        ObservableList<Student> data = FXCollections.observableArrayList();


        Student t = new Student();
        for (int i = 0; i < studentList.size(); i++) {
            t = new Student();
            t.setUsername(studentList.get(i).getUsername());
            t.setName(studentList.get(i).getName());
            admintable.getItems().add(t);
        }

	}
	
	@FXML
	private void studentCreate() throws IOException{
        try {
            api.getToken("asd", "asd");
        } catch (ClientException e) {
            e.printStackTrace();
        }

        String studentname = admin_studentname.getText();
        String studentusername = admin_studentusername.getText();
        String studentpassword = admin_studentpassword.getText();

        try {
            api.createStudent(studentname, studentusername, studentpassword);
        } catch (ClientException e) {
            e.printStackTrace();
        }

	}
	
	@FXML
	private void studentDelete() throws IOException{
        try {
            api.getToken("asd", "asd");
        } catch (ClientException e) {
            e.printStackTrace();
        }

        Integer studentid = Integer.parseInt(admin_studentid.getText());

        try {
            api.removeStudent(studentid);
        } catch (ClientException e) {
            e.printStackTrace();
        }
	}

}
