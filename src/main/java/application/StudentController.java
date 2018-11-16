package application;

import client.Api;
import client.ClientException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Course;
import model.Homework;
import model.Solution;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentController {

	@FXML TextField student_coursecourse;
	@FXML TextField student_courseid;
	@FXML TextField student_homeworkhomework;
	@FXML TextField student_homeworkid;
	@FXML TextField student_solutionid;
	@FXML Button filechoose;
	@FXML TableView studenttable;
    File file;

	private final Api api = Api.getInstance();

	@FXML
	private void courseList() throws IOException{
		List<Course> courseList = new ArrayList<Course>();
		try {
			courseList = api.student_getAppliedCourses();
		} catch (ClientException e) {
			e.printStackTrace();
		}

		studenttable.setEditable(true);

		TableColumn<Course, String> idColumn = new TableColumn<Course, String>("ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		TableColumn<Course, String> nameColumn = new TableColumn<Course, String>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<Course, String> teachernameColumn = new TableColumn<Course, String>("Teacher Name");
		teachernameColumn.setCellValueFactory(new PropertyValueFactory<>("teacherName"));

		TableColumn<Course, String> descColumn = new TableColumn<Course, String>("Description");
		descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

		studenttable.getItems().clear();
		studenttable.getColumns().clear();

		studenttable.getColumns().addAll(idColumn, nameColumn, teachernameColumn, descColumn);
		studenttable.getItems().addAll(courseList);
	}

	@FXML
	private void courseApply() throws IOException{
		if(student_courseid.getText().isEmpty()){
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Please fill in all required fields!");
			alert.setContentText("ID");
			alert.showAndWait();
			return;
		}
		Integer courseid = Integer.parseInt(student_courseid.getText());

        student_courseid.setText("");

		try {
			api.student_applyCourse(courseid);
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void courseDelete() throws IOException{
		if(student_courseid.getText().isEmpty()){
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Please fill in all required fields!");
			alert.setContentText("ID");
			alert.showAndWait();
			return;
		}
		Integer courseid = Integer.parseInt(student_courseid.getText());

        student_courseid.setText("");

		try {
			api.student_abandonCourse(courseid);
		} catch (ClientException e) {
			e.printStackTrace();
		}

	}

	@FXML
	private void homeworkListForCourse() throws IOException{
		List<Homework> homeworkList = new ArrayList<Homework>();
		if(student_homeworkid.getText().isEmpty()){
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Please fill in all required fields!");
			alert.setContentText("ID");
			alert.showAndWait();
			return;
		}
		Integer homeworkid = Integer.parseInt(student_homeworkid.getText());

        student_homeworkid.setText("");

		try {
			homeworkList = api.student_getHomeworkForCourse(homeworkid);
		} catch (ClientException e) {
			e.printStackTrace();
		}


		studenttable.setEditable(true);

        TableColumn<Homework, String> idColumn = new TableColumn<Homework, String>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

		TableColumn<Homework, String> nameColumn = new TableColumn<Homework, String>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<Homework, Date> deadlineColumn = new TableColumn<Homework, Date>("Deadline");
		deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));

		TableColumn<Homework, String> descriptionColumn = new TableColumn<Homework, String>("Description");
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

		TableColumn<Homework, Integer> headcountColumn = new TableColumn<Homework, Integer>("Headcount");
		headcountColumn.setCellValueFactory(new PropertyValueFactory<>("headcount"));

		TableColumn<Homework, Boolean> selfAssignableColumn = new TableColumn<Homework, Boolean>("selfAssignable");
		selfAssignableColumn.setCellValueFactory(new PropertyValueFactory<>("selfAssignable"));

		TableColumn<Homework, String> courseNameColumn = new TableColumn<Homework, String>("Course name");
		courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));

		studenttable.getItems().clear();
		studenttable.getColumns().clear();

		studenttable.getColumns().addAll(idColumn, nameColumn, deadlineColumn, descriptionColumn, headcountColumn, selfAssignableColumn, courseNameColumn);
		studenttable.getItems().addAll(homeworkList);
	}

	@FXML
	private void homeworkListForStudent() throws IOException{
		List<Homework> homeworkList = new ArrayList<Homework>();

		try {
			homeworkList = api.student_getHomeworks();
		} catch (ClientException e) {
			e.printStackTrace();
		}


		studenttable.setEditable(true);

        TableColumn<Homework, String> idColumn = new TableColumn<Homework, String>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

		TableColumn<Homework, String> nameColumn = new TableColumn<Homework, String>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<Homework, Date> deadlineColumn = new TableColumn<Homework, Date>("Deadline");
		deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));

		TableColumn<Homework, String> descriptionColumn = new TableColumn<Homework, String>("Description");
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

		TableColumn<Homework, Integer> headcountColumn = new TableColumn<Homework, Integer>("Headcount");
		headcountColumn.setCellValueFactory(new PropertyValueFactory<>("headcount"));

		TableColumn<Homework, Boolean> selfAssignableColumn = new TableColumn<Homework, Boolean>("selfAssignable");
		selfAssignableColumn.setCellValueFactory(new PropertyValueFactory<>("selfAssignable"));

		TableColumn<Homework, String> courseNameColumn = new TableColumn<Homework, String>("Course name");
		courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));

		studenttable.getItems().clear();
		studenttable.getColumns().clear();

		studenttable.getColumns().addAll(idColumn, nameColumn, deadlineColumn, descriptionColumn, headcountColumn, selfAssignableColumn, courseNameColumn);
		studenttable.getItems().addAll(homeworkList);
	}

	@FXML
	private void homeworkApply() throws IOException{
		if(student_homeworkid.getText().isEmpty()){
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Please fill in all required fields!");
			alert.setContentText("ID");
			alert.showAndWait();
			return;
		}
		Integer homeworkid = Integer.parseInt(student_homeworkid.getText());

        student_homeworkid.setText("");

		try {
			api.student_applyHomework(homeworkid);
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void homeworkDelete() throws IOException{
		if(student_homeworkid.getText().isEmpty()){
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Please fill in all required fields!");
			alert.setContentText("ID");
			alert.showAndWait();
			return;
		}
		Integer homeworkid = Integer.parseInt(student_homeworkid.getText());

        student_homeworkid.setText("");

		try {
			api.student_abandonHomework(homeworkid);
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void homeworkUpload() throws IOException{
        try {
            api.student_uploadHomework(file, file.getName());
        } catch (ClientException e) {
            e.printStackTrace();
        }
	}

	@FXML
	private void homeworkChooseFile() throws IOException{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Upload File");
        Stage stage = (Stage) filechoose.getScene().getWindow();
		fileChooser.showOpenDialog(stage);
		file = fileChooser.showOpenDialog(stage);
		if (file != null) {
			System.out.println(file.getAbsolutePath() + file.getName());
		}

	}

	@FXML
	private void solutionGet() throws IOException{
		if(student_solutionid.getText().isEmpty()){
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Please fill in all required fields!");
			alert.setContentText("ID");
			alert.showAndWait();
			return;
		}
		Integer solutionid = Integer.parseInt(student_solutionid.getText());

        student_solutionid.setText("");

		List<Solution> solutionList = new ArrayList<Solution>();
		try {
			solutionList = api.student_getSolution(solutionid);
		} catch (ClientException e) {
			e.printStackTrace();
		}

		studenttable.setEditable(true);

        TableColumn<Solution, Date> idColumn = new TableColumn<Solution, Date>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		TableColumn<Solution, Date> submittedColumn = new TableColumn<Solution, Date>("Submitted");
		submittedColumn.setCellValueFactory(new PropertyValueFactory<>("submittedAt"));
		TableColumn<Solution, String> statusColumn = new TableColumn<Solution, String>("Status");
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

		studenttable.getItems().clear();
		studenttable.getColumns().clear();

		studenttable.getColumns().addAll(submittedColumn, statusColumn);
		studenttable.getItems().addAll(solutionList);
	}

	@FXML
	private void solutionList() throws IOException{
		if(student_solutionid.getText().isEmpty()){
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Please fill in all required fields!");
			alert.setContentText("ID");
			alert.showAndWait();
			return;
		}
		Integer solutionid = Integer.parseInt(student_solutionid.getText());

        student_solutionid.setText("");

		List<Solution> solutionList = new ArrayList<Solution>();
		try {
			solutionList = api.student_getSolutions(solutionid);
		} catch (ClientException e) {
			e.printStackTrace();
		}

		studenttable.setEditable(true);

        TableColumn<Solution, Date> idColumn = new TableColumn<Solution, Date>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		TableColumn<Solution, Date> submittedColumn = new TableColumn<Solution, Date>("Submitted");
		submittedColumn.setCellValueFactory(new PropertyValueFactory<>("submittedAt"));
		TableColumn<Solution, String> statusColumn = new TableColumn<Solution, String>("Status");
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

		studenttable.getItems().clear();
		studenttable.getColumns().clear();

		studenttable.getColumns().addAll(submittedColumn, statusColumn);
		studenttable.getItems().addAll(solutionList);
	}
}