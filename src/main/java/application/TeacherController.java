package application;

import client.Api;
import client.ClientException;
import client.commands.TeacherCommands;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Course;
import model.Homework;
import model.Solution;
import model.Student;

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
	@FXML TableView teachertable;
	@FXML TextField teacher_studentid;
	@FXML TextField teacher_solutionid;
	@FXML CheckBox teacher_homeworkselfassignable;

	private final Api api = Api.getInstance();

	public TeacherController() throws ClientException {
	}

	@FXML
    @SuppressWarnings("unchecked")
	private void courseList() {
		List<Course> courseList = new ArrayList<>();
		try {
		    courseList = api.getCommands(TeacherCommands.class).getCourses();
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
	private void courseCreate() {
		if (teacher_coursename.getText().isEmpty() || teacher_coursedesc.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Please fill in all required fields!");
            alert.setContentText("Name, Description");
			alert.showAndWait();
			return;
		}

		String course = teacher_coursename.getText();
		String description = teacher_coursedesc.getText();

        teacher_coursename.setText("");
        teacher_coursedesc.setText("");

		try {
            api.getCommands(TeacherCommands.class).createCourse(course, description);
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void courseDelete() {
		if(teacher_courseid.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Please fill in all required fields!");
            alert.setContentText("ID");
			alert.showAndWait();
			return;
		}

		int courseid = Integer.parseInt(teacher_courseid.getText());

        teacher_courseid.setText("");

		try {
            api.getCommands(TeacherCommands.class).removeCourse(courseid);
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

	@FXML
    @SuppressWarnings("unchecked")
	private void homeworkList() {
		if (teacher_homeworkid.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Please fill in all required fields!");
            alert.setContentText("ID");
			alert.showAndWait();
			return;
		}

		int homeworkid = Integer.parseInt(teacher_homeworkid.getText());

        teacher_homeworkid.setText("");

        List<Homework> homeworkList = new ArrayList<>();
		try {
		    homeworkList = api.getCommands(TeacherCommands.class).getHomeworks(homeworkid);
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

		teachertable.getColumns().addAll(idColumn,
                                         nameColumn,
                                         deadlineColumn,
                                         descriptionColumn,
                                         headcountColumn,
                                         selfAssignableColumn,
                                         courseNameColumn);

        teachertable.getItems().addAll(homeworkList);
	}

	@FXML
	private void homeworkCreate() {
		if (teacher_homeworkid.getText().isEmpty() ||
				teacher_homeworkname.getText().isEmpty() ||
				teacher_homeworkdescription.getText().isEmpty() ||
				teacher_homeworkdeadline.getText().isEmpty() ||
				teacher_homeworkheadcount.getText().isEmpty() ||
				teacher_homeworkselfassignable.getText().isEmpty()
		) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Please fill in all required fields!");
            alert.setContentText("ID, Name, Description, Deadline, Headcount, SelfAssignable");
			alert.showAndWait();
			return;
		}

		int homeworkid = Integer.parseInt(teacher_homeworkid.getText());
		String name = teacher_homeworkname.getText();
		String description = teacher_homeworkdescription.getText();
		String deadline = String.valueOf(teacher_homeworkdeadline.getText());
		String headcount = teacher_homeworkheadcount.getText();
		String selfAssignable = String.valueOf(teacher_homeworkselfassignable.isSelected());

        teacher_homeworkid.setText("");
        teacher_homeworkname.setText("");
        teacher_homeworkdescription.setText("");
        teacher_homeworkdeadline.setText("");
        teacher_homeworkheadcount.setText("");

		try {
            api.getCommands(TeacherCommands.class).createHomework(
                    name, description, deadline, headcount, selfAssignable, homeworkid);
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void homeworkDelete() {
		if (teacher_homeworkid.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Please fill in all required fields!");
            alert.setContentText("ID");
			alert.showAndWait();
			return;
		}

		int homeworkid = Integer.parseInt(teacher_homeworkid.getText());

		teacher_homeworkid.setText("");

		try {
            api.getCommands(TeacherCommands.class).removeHomework(homeworkid);
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void homeworkModify() {
		if (teacher_homeworkid.getText().isEmpty() ||
				teacher_homeworkname.getText().isEmpty() ||
				teacher_homeworkdescription.getText().isEmpty() ||
				teacher_homeworkdeadline.getText().isEmpty() ||
				teacher_homeworkheadcount.getText().isEmpty() ||
				teacher_homeworkselfassignable.getText().isEmpty()
		) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Please fill in all required fields!");
            alert.setContentText("ID, Name, Description, Deadline, Headcount, SelfAssignable");
			alert.showAndWait();
			return;
		}

		int homeworkid = Integer.parseInt(teacher_homeworkid.getText());
		String name = teacher_homeworkname.getText();
		String description = teacher_homeworkdescription.getText();
		String deadline = String.valueOf(teacher_homeworkdeadline.getText());
		String headcount = teacher_homeworkheadcount.getText();
		String selfAssignable = String.valueOf(teacher_homeworkselfassignable.isSelected());

        teacher_homeworkid.setText("");
        teacher_homeworkname.setText("");
        teacher_homeworkdescription.setText("");
        teacher_homeworkdeadline.setText("");
        teacher_homeworkheadcount.setText("");

		try {
            api.getCommands(TeacherCommands.class).modifyHomework(
                    name, description, deadline, headcount, selfAssignable, homeworkid);
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

	@FXML
    @SuppressWarnings("unchecked")
	private void studentList() {
		if (teacher_studentid.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Please fill in all required fields!");
            alert.setContentText("ID");
			alert.showAndWait();
			return;
		}

		int studentid = Integer.parseInt(teacher_studentid.getText());

        teacher_studentid.setText("");

		List<Student> studentList = new ArrayList<>();
		try {
		    studentList = api.getCommands(TeacherCommands.class).getStudents(studentid);
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
    @SuppressWarnings("unchecked")
	private void solutionGet() {
		if (teacher_solutionid.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Please fill in all required fields!");
            alert.setContentText("ID");
			alert.showAndWait();
			return;
		}

		int solutionid = Integer.parseInt(teacher_solutionid.getText());

        teacher_solutionid.setText("");

		List<Solution> solutionList = new ArrayList<>();
		try {
		    solutionList.add(api.getCommands(TeacherCommands.class).getSolution(solutionid));
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
    @SuppressWarnings("unchecked")
	private void solutionList() {
		if (teacher_solutionid.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Please fill in all required fields!");
            alert.setContentText("ID");
			alert.showAndWait();
			return;
		}

		int solutionid = Integer.parseInt(teacher_solutionid.getText());

        teacher_solutionid.setText("");

		List<Solution> solutionList = new ArrayList<>();
		try {
		    solutionList = api.getCommands(TeacherCommands.class).getSolutions(solutionid);
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
	private void solutionModify() {
		if (teacher_solutionid.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Please fill in all required fields!");
            alert.setContentText("ID");
			alert.showAndWait();
			return;
		}

		int solutionid = Integer.parseInt(teacher_solutionid.getText());
		String status = teacher_solutionstatus.getText();

        teacher_solutionid.setText("");
        teacher_solutionstatus.setText("");

		try {
            api.getCommands(TeacherCommands.class).modifySolution(solutionid, status);
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}
}