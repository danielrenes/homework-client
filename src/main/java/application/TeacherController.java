package application;

import client.Api;
import client.ClientException;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Course;
import model.Homework;
import model.Solution;
import model.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TeacherController {

	@FXML TextField teacher_coursename;
	@FXML TextField teacher_coursedesc;
	@FXML TextField teacher_courseid;
	@FXML TextField teacher_groupstudents;
	@FXML TextField teacher_homeworkname;
	@FXML TextField teacher_homeworkdescription;
	@FXML TextField teacher_homeworkid;
	@FXML TextField teacher_homeworkdeadline;
	@FXML TextField teacher_homeworkheadcount;
	@FXML TextField teacher_solutionstatus;
	@FXML TextField teacher_solutionnotes;
	@FXML TableView teachertable;
	@FXML TextField teacher_studentid;
	@FXML TextField teacher_solutionid;
	@FXML CheckBox teacher_homeworkselfassignable;

	private final Api api = Api.getInstance();

	@FXML
	private void courseList() throws IOException{
		List<Course> courseList = new ArrayList<Course>();
		try {
			courseList = api.teacher_getCourses();
		} catch (ClientException e) {
			e.printStackTrace();
		}


		teachertable.setEditable(true);

		TableColumn<Course, String> idColumn = new TableColumn<Course, String>("ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		TableColumn<Course, String> nameColumn = new TableColumn<Course, String>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<Course, String> teachernameColumn = new TableColumn<Course, String>("Teacher Name");
		teachernameColumn.setCellValueFactory(new PropertyValueFactory<>("teacherName"));

		TableColumn<Course, String> descColumn = new TableColumn<Course, String>("Description");
		descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

		teachertable.getItems().clear();
		teachertable.getColumns().clear();

		teachertable.getColumns().addAll(idColumn, nameColumn, teachernameColumn, descColumn);
		teachertable.getItems().addAll(courseList);
	}

	@FXML
	private void courseCreate() throws IOException{
		String course = teacher_coursename.getText();
		String description = teacher_coursedesc.getText();

		try {
			api.teacher_createCourse(course, description);
		} catch (ClientException e) {
			e.printStackTrace();
		}

	}

	@FXML
	private void courseDelete() throws IOException{
		Integer courseid = Integer.parseInt(teacher_courseid.getText());

		try {
			api.teacher_removeCourse(courseid);
		} catch (ClientException e) {
			e.printStackTrace();
		}

	}

	@FXML
	private void groupList() throws IOException{

	}

	@FXML
	private void groupCreate() throws IOException{
		String group = teacher_groupstudents.getText();
	}

	@FXML
	private void groupDelete() throws IOException{
		String group = teacher_groupstudents.getText();
	}

	@FXML
	private void groupModify() throws IOException{
		String group = teacher_groupstudents.getText();
	}



	@FXML
	private void homeworkList() throws IOException{
		List<Homework> homeworkList = new ArrayList<Homework>();
		Integer homeworkid = Integer.parseInt(teacher_homeworkid.getText());

		try {
			homeworkList = api.teacher_getHomeworks(homeworkid);
		} catch (ClientException e) {
			e.printStackTrace();
		}


		teachertable.setEditable(true);

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

		teachertable.getItems().clear();
		teachertable.getColumns().clear();

		teachertable.getColumns().addAll(idColumn, nameColumn, deadlineColumn, descriptionColumn, headcountColumn, selfAssignableColumn, courseNameColumn);
        teachertable.getItems().addAll(homeworkList);
	}

	@FXML
	private void homeworkCreate() throws IOException{
		Integer homeworkid = Integer.parseInt(teacher_homeworkid.getText());
		String name = teacher_homeworkname.getText();
		String description = teacher_homeworkdescription.getText();
		String deadline = String.valueOf(teacher_homeworkdeadline.getText());
		String headcount = teacher_homeworkheadcount.getText();
		String selfAssignable = String.valueOf(teacher_homeworkselfassignable.isSelected());

		try {
			api.teacher_createHomework(name, description, deadline, headcount, selfAssignable, homeworkid);
		} catch (ClientException e) {
			e.printStackTrace();
		}

	}

	@FXML
	private void homeworkDelete() throws IOException{
		Integer homeworkid = Integer.parseInt(teacher_homeworkid.getText());

		try {
			api.teacher_removeHomework(homeworkid);
		} catch (ClientException e) {
			e.printStackTrace();
		}

	}

	@FXML
	private void homeworkModify() throws IOException{
		Integer homeworkid = Integer.parseInt(teacher_homeworkid.getText());
		String name = teacher_homeworkname.getText();
		String description = teacher_homeworkdescription.getText();
		String deadline = String.valueOf(teacher_homeworkdeadline.getText());
		String headcount = teacher_homeworkheadcount.getText();
		String selfAssignable = String.valueOf(teacher_homeworkselfassignable.isSelected());

		try {
			api.teacher_modifyHomework(name, description, deadline, headcount, selfAssignable, homeworkid);
		} catch (ClientException e) {
			e.printStackTrace();
		}

	}

	@FXML
	private void studentList() throws IOException{
		Integer studentid = Integer.parseInt(teacher_studentid.getText());
		List<Student> studentList = new ArrayList<Student>();
		try {
			studentList = api.teacher_getStudents(studentid);
		} catch (ClientException e) {
			e.printStackTrace();
		}


		teachertable.setEditable(true);

        TableColumn<Student, String> idColumn = new TableColumn<Student, String>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		TableColumn<Student, String> usernameColumn = new TableColumn<Student, String>("Username");
		usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
		TableColumn<Student, String> nameColumn = new TableColumn<Student, String>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		teachertable.getItems().clear();
		teachertable.getColumns().clear();

		teachertable.getColumns().addAll(idColumn, nameColumn, usernameColumn);
		teachertable.getItems().addAll(studentList);
	}

	@FXML
	private void solutionList() throws IOException{
		Integer solutionid = Integer.parseInt(teacher_solutionid.getText());
		List<Solution> solutionList = new ArrayList<Solution>();
		try {
			solutionList = api.teacher_getSolution(solutionid);
		} catch (ClientException e) {
			e.printStackTrace();
		}


		teachertable.setEditable(true);

        TableColumn<Solution, Date> idColumn = new TableColumn<Solution, Date>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Solution, Date> submittedColumn = new TableColumn<Solution, Date>("Submitted");
		submittedColumn.setCellValueFactory(new PropertyValueFactory<>("submittedAt"));
		TableColumn<Solution, String> statusColumn = new TableColumn<Solution, String>("Status");
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

		teachertable.getItems().clear();
		teachertable.getColumns().clear();

		teachertable.getColumns().addAll(idColumn, submittedColumn, statusColumn);
		teachertable.getItems().addAll(solutionList);
	}

	@FXML
	private void solutionsList() throws IOException{
		Integer solutionid = Integer.parseInt(teacher_solutionid.getText());
		List<Solution> solutionList = new ArrayList<Solution>();
		try {
			solutionList = api.teacher_getSolutions(solutionid);
		} catch (ClientException e) {
			e.printStackTrace();
		}


		teachertable.setEditable(true);

        TableColumn<Solution, Date> idColumn = new TableColumn<Solution, Date>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		TableColumn<Solution, Date> submittedColumn = new TableColumn<Solution, Date>("Submitted");
		submittedColumn.setCellValueFactory(new PropertyValueFactory<>("submittedAt"));
		TableColumn<Solution, String> statusColumn = new TableColumn<Solution, String>("Status");
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

		teachertable.getItems().clear();
		teachertable.getColumns().clear();

		teachertable.getColumns().addAll(idColumn, submittedColumn, statusColumn);
		teachertable.getItems().addAll(solutionList);
	}

	@FXML
	private void solutionModify() throws IOException{
		Integer solutionid = Integer.parseInt(teacher_solutionid.getText());

		try {
			api.teacher_modifySolution(solutionid);
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}
}