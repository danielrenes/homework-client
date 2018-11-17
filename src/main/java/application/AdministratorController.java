package application;

import client.Api;
import client.ClientException;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Student;
import model.Teacher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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

    private final Api api = Api.getInstance();

    public AdministratorController() throws ClientException {
    }

    @FXML
    private void teacherList() throws IOException {
        List<Teacher> teacherList = new ArrayList<Teacher>();
        try {
            teacherList = api.admin_getTeachers();
        } catch (ClientException e) {
            e.printStackTrace();
        }

        admintable.setEditable(true);

        TableColumn<Teacher, String> idColumn = new TableColumn<Teacher, String>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Teacher, String> usernameColumn = new TableColumn<Teacher, String>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        TableColumn<Teacher, String> nameColumn = new TableColumn<Teacher, String>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        admintable.getItems().clear();
        admintable.getColumns().clear();

        admintable.getColumns().addAll(idColumn, nameColumn, usernameColumn);

        admintable.getItems().addAll(teacherList);
    }

    @FXML
    private void teacherCreate() throws IOException{
        if(admin_teachername.getText().isEmpty() || admin_teacherusername.getText().isEmpty() || admin_teacherpassword.getText().isEmpty()){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Please fill in all required fields!");
            alert.setContentText("Name, Username, Password");
            alert.showAndWait();
            return;
        }
        String teachername = admin_teachername.getText();
        String teacherusername = admin_teacherusername.getText();
        String teacherpassword = admin_teacherpassword.getText();

        admin_teachername.setText("");
        admin_teacherusername.setText("");
        admin_teacherpassword.setText("");

        try {
            api.admin_createTeacher(teachername, teacherusername, teacherpassword);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void teacherDelete() throws IOException{
        if(admin_teacherid.getText().isEmpty()){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Please fill in all required fields!");
            alert.setContentText("ID");
            alert.showAndWait();
            return;
        }
        Integer teacherid = Integer.parseInt(admin_teacherid.getText());

        admin_teacherid.setText("");

        try {
            api.admin_removeTeacher(teacherid);
        } catch (ClientException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void studentList() throws IOException{
        List<Student> studentList = new ArrayList<Student>();
        try {
            studentList = api.admin_getStudents();
        } catch (ClientException e) {
            e.printStackTrace();
        }


        admintable.setEditable(true);

        TableColumn<Student, String> idColumn = new TableColumn<Student, String>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Student, String> usernameColumn = new TableColumn<Student, String>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        TableColumn<Student, String> nameColumn = new TableColumn<Student, String>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        admintable.getItems().clear();
        admintable.getColumns().clear();

        admintable.getColumns().addAll(idColumn, nameColumn, usernameColumn);

        admintable.getItems().addAll(studentList);
    }

    @FXML
    private void studentCreate() throws IOException{
        if(admin_studentname.getText().isEmpty() || admin_studentusername.getText().isEmpty() || admin_studentpassword.getText().isEmpty()){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Please fill in all required fields!");
            alert.setContentText("Name, Username, Password");
            alert.showAndWait();
            return;
        }
        String studentname = admin_studentname.getText();
        String studentusername = admin_studentusername.getText();
        String studentpassword = admin_studentpassword.getText();

        admin_studentname.setText("");
        admin_studentusername.setText("");
        admin_studentpassword.setText("");

        try {
            api.admin_createStudent(studentname, studentusername, studentpassword);
        } catch (ClientException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void studentDelete() throws IOException{
        if(admin_studentid.getText().isEmpty()){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Please fill in all required fields!");
            alert.setContentText("ID");
            alert.showAndWait();
            return;
        }
        Integer studentid = Integer.parseInt(admin_studentid.getText());

        admin_studentid.setText("");

        try {
            api.admin_removeStudent(studentid);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

}