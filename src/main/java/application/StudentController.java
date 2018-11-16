package application;

import java.io.IOException;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import client.Api;
import client.ClientException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import model.Course;
import model.Homework;
import model.Solution;

public class StudentController {

	@FXML TextField student_coursecourse;
	@FXML TextField student_courseid;
	@FXML TextField student_homeworkhomework;
	@FXML TextField student_homeworkid;
	@FXML TextField student_solutionid;
	@FXML Button filechoose;
	@FXML TableView studenttable;
    File file;

	String username = "st";
	String password = "st";

	private final Api api = Api.getInstance();
	final FileChooser fileChooser = new FileChooser();

	@FXML
	private void courseList() throws IOException{
		try {
			api.getToken("st", "st");
		} catch (ClientException e) {
			e.printStackTrace();
		}
		List<Course> courseList = new ArrayList<Course>();
		try {
			courseList = api.student_getAppliedCourses();
		} catch (ClientException e) {
			e.printStackTrace();
		}


		studenttable.setEditable(true);

		TableColumn<Course, String> nameColumn = new TableColumn<Course, String>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<Course, String> teachernameColumn = new TableColumn<Course, String>("Teacher Name");
		teachernameColumn.setCellValueFactory(new PropertyValueFactory<>("teacherName"));

		TableColumn<Course, String> descColumn = new TableColumn<Course, String>("Description");
		descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

		studenttable.getItems().clear();
		studenttable.getColumns().clear();

		studenttable.getColumns().addAll(nameColumn, teachernameColumn, descColumn);

		ObservableList<Course> data = FXCollections.observableArrayList();


		Course t = new Course();
		for (int i = 0; i < courseList.size(); i++) {
			t = new Course();
			t.setName(courseList.get(i).getName());
			t.setDescription(courseList.get(i).getDescription());
			t.setTeacherName(courseList.get(i).getTeacherName());
			studenttable.getItems().add(t);
		}

	}

	@FXML
	private void courseApply() throws IOException{
		try {
			api.getToken("st", "st");
		} catch (ClientException e) {
			e.printStackTrace();
		}

		Integer courseid = Integer.parseInt(student_courseid.getText());

		try {
			api.student_applyCourse(courseid);
		} catch (ClientException e) {
			e.printStackTrace();
		}

	}

	@FXML
	private void courseDelete() throws IOException{
		try {
			api.getToken("st", "st");
		} catch (ClientException e) {
			e.printStackTrace();
		}

		Integer courseid = Integer.parseInt(student_courseid.getText());

		try {
			api.student_abandonCourse(courseid);
		} catch (ClientException e) {
			e.printStackTrace();
		}

	}

	@FXML
	private void homeworkListForCourse() throws IOException{
		try {
			api.getToken("st", "st");
		} catch (ClientException e) {
			e.printStackTrace();
		}
		List<Homework> homeworkList = new ArrayList<Homework>();
		Integer homeworkid = Integer.parseInt(student_homeworkid.getText());

		try {
			homeworkList = api.student_getHomeworkForCourse(homeworkid);
		} catch (ClientException e) {
			e.printStackTrace();
		}


		studenttable.setEditable(true);

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

		studenttable.getColumns().addAll(nameColumn, deadlineColumn, descriptionColumn, headcountColumn, selfAssignableColumn, courseNameColumn);

		ObservableList<Homework> data = FXCollections.observableArrayList();


		Homework t = new Homework();
		for (int i = 0; i < homeworkList.size(); i++) {
			t = new Homework();
			t.setName(homeworkList.get(i).getName());
			t.setDescription(homeworkList.get(i).getDescription());
			t.setCourseName(homeworkList.get(i).getCourseName());
			t.setDeadline(homeworkList.get(i).getDeadline());
			t.setHeadcount(homeworkList.get(i).getHeadcount());
			t.setSelfAssignable(true);
			studenttable.getItems().add(t);
		}
	}

	@FXML
	private void homeworkListForStudent() throws IOException{
		try {
			api.getToken("st", "st");
		} catch (ClientException e) {
			e.printStackTrace();
		}
		List<Homework> homeworkList = new ArrayList<Homework>();

		try {
			homeworkList = api.student_getHomeworks();
		} catch (ClientException e) {
			e.printStackTrace();
		}


		studenttable.setEditable(true);

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

		studenttable.getColumns().addAll(nameColumn, deadlineColumn, descriptionColumn, headcountColumn, selfAssignableColumn, courseNameColumn);

		ObservableList<Homework> data = FXCollections.observableArrayList();


		Homework t = new Homework();
		for (int i = 0; i < homeworkList.size(); i++) {
			t = new Homework();
			t.setName(homeworkList.get(i).getName());
			t.setDescription(homeworkList.get(i).getDescription());
			t.setCourseName(homeworkList.get(i).getCourseName());
			t.setDeadline(homeworkList.get(i).getDeadline());
			t.setHeadcount(homeworkList.get(i).getHeadcount());
			t.setSelfAssignable(true);
			studenttable.getItems().add(t);
		}
	}


	@FXML
	private void homeworkApply() throws IOException{
		try {
			api.getToken("st", "st");
		} catch (ClientException e) {
			e.printStackTrace();
		}

		Integer homeworkid = Integer.parseInt(student_homeworkid.getText());

		try {
			api.student_applyHomework(homeworkid);
		} catch (ClientException e) {
			e.printStackTrace();
		}

	}

	@FXML
	private void homeworkDelete() throws IOException{
		try {
			api.getToken("st", "st");
		} catch (ClientException e) {
			e.printStackTrace();
		}

		Integer homeworkid = Integer.parseInt(student_homeworkid.getText());

		try {
			api.student_abandonHomework(homeworkid);
		} catch (ClientException e) {
			e.printStackTrace();
		}

	}

	@FXML
	private void homeworkUpload() throws IOException{
        try {
            api.getToken("st", "st");
        } catch (ClientException e) {
            e.printStackTrace();
        }

        try {
            api.student_uploadHomewirk(file, file.getName());
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
		try {
			api.getToken("st", "st");
		} catch (ClientException e) {
			e.printStackTrace();
		}
		Integer solutionid = Integer.parseInt(student_solutionid.getText());
		List<Solution> solutionList = new ArrayList<Solution>();
		try {
			solutionList = api.student_getSolution(solutionid);
		} catch (ClientException e) {
			e.printStackTrace();
		}


		studenttable.setEditable(true);


		TableColumn<Solution, Date> submittedColumn = new TableColumn<Solution, Date>("Submitted");
		submittedColumn.setCellValueFactory(new PropertyValueFactory<>("submittedAt"));
		TableColumn<Solution, String> statusColumn = new TableColumn<Solution, String>("Status");
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

		studenttable.getItems().clear();
		studenttable.getColumns().clear();

		studenttable.getColumns().addAll(submittedColumn, statusColumn);

		ObservableList<Solution> data = FXCollections.observableArrayList();


		Solution t = new Solution();
		for (int i = 0; i < solutionList.size(); i++) {
			t = new Solution();
			t.setStatus(solutionList.get(i).getStatus());
			t.setSubmittedAt(solutionList.get(i).getSubmittedAt());
			studenttable.getItems().add(t);
		}
	}

	@FXML
	private void solutionList() throws IOException{
		try {
			api.getToken("st", "st");
		} catch (ClientException e) {
			e.printStackTrace();
		}
		Integer solutionid = Integer.parseInt(student_solutionid.getText());
		List<Solution> solutionList = new ArrayList<Solution>();
		try {
			solutionList = api.student_getSolutions(solutionid);
		} catch (ClientException e) {
			e.printStackTrace();
		}


		studenttable.setEditable(true);


		TableColumn<Solution, Date> submittedColumn = new TableColumn<Solution, Date>("Submitted");
		submittedColumn.setCellValueFactory(new PropertyValueFactory<>("submittedAt"));
		TableColumn<Solution, String> statusColumn = new TableColumn<Solution, String>("Status");
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

		studenttable.getItems().clear();
		studenttable.getColumns().clear();

		studenttable.getColumns().addAll(submittedColumn, statusColumn);

		ObservableList<Solution> data = FXCollections.observableArrayList();


		Solution t = new Solution();
		for (int i = 0; i < solutionList.size(); i++) {
			t = new Solution();
			t.setStatus(solutionList.get(i).getStatus());
			t.setSubmittedAt(solutionList.get(i).getSubmittedAt());
			studenttable.getItems().add(t);
		}
	}



}