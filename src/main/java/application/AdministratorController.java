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
import model.User;


public class AdministratorController {

	@FXML TextField admin_teachername;
	@FXML TextField admin_studentname;
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

        try {
            api.createTeacher(teachername, teachername, teachername);
        } catch (ClientException e) {
            e.printStackTrace();
        }
	}
	
	@FXML
	private void teacherDelete() throws IOException{
		String teachername = admin_teachername.getText();
	}
	
	@FXML
	private void studentList() throws IOException{
		
	}
	
	@FXML
	private void studentCreate() throws IOException{
		String studentname = admin_studentname.getText();
	}
	
	@FXML
	private void studentDelete() throws IOException{
		String studentname = admin_studentname.getText();
	}

}
