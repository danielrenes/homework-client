package application;

import client.Api;
import client.ClientException;
import client.commands.StudentCommands;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Course;
import model.Homework;
import model.Solution;

import java.io.File;
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

    private File file;

	private final Api api = Api.getInstance();

	public StudentController() throws ClientException {
	}

	@FXML
    @SuppressWarnings("unchecked")
	private void courseListAll() {
		List<Course> courseList = new ArrayList<>();
		try {
		    courseList = api.getCommands(StudentCommands.class).getCourses();
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
    @SuppressWarnings("unchecked")
	private void courseList() {
		List<Course> courseList = new ArrayList<>();
		try {
		    courseList = api.getCommands(StudentCommands.class).getAppliedCourses();
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
	private void courseApply() {
		if (student_courseid.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Please fill in all required fields!");
			alert.setContentText("ID");
			alert.showAndWait();
			return;
		}

		int courseid = Integer.parseInt(student_courseid.getText());

        student_courseid.setText("");

		try {
            api.getCommands(StudentCommands.class).applyCourse(courseid);
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void courseDelete() {
		if (student_courseid.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Please fill in all required fields!");
			alert.setContentText("ID");
			alert.showAndWait();
			return;
		}

		int courseid = Integer.parseInt(student_courseid.getText());

        student_courseid.setText("");

		try {
            api.getCommands(StudentCommands.class).abandonCourse(courseid);
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

	@FXML
    @SuppressWarnings("unchecked")
	private void homeworkListForCourse() {
		if (student_homeworkid.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Please fill in all required fields!");
			alert.setContentText("ID");
			alert.showAndWait();
			return;
		}

		int homeworkid = Integer.parseInt(student_homeworkid.getText());

        student_homeworkid.setText("");

        List<Homework> homeworkList = new ArrayList<>();
		try {
		    homeworkList = api.getCommands(StudentCommands.class).getHomeworksForCourse(homeworkid);
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

		studenttable.getColumns().addAll(idColumn,
                                         nameColumn,
                                         deadlineColumn,
                                         descriptionColumn,
                                         headcountColumn,
                                         selfAssignableColumn,
                                         courseNameColumn);

		studenttable.getItems().addAll(homeworkList);
	}

	@FXML
    @SuppressWarnings("unchecked")
	private void homeworkListForStudent() {
		List<Homework> homeworkList = new ArrayList<>();

		try {
		    homeworkList = api.getCommands(StudentCommands.class).getHomeworks();
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

		studenttable.getColumns().addAll(idColumn,
                                         nameColumn,
                                         deadlineColumn,
                                         descriptionColumn,
                                         headcountColumn,
                                         selfAssignableColumn,
                                         courseNameColumn);

		studenttable.getItems().addAll(homeworkList);
	}

	@FXML
	private void homeworkApply() {
		if (student_homeworkid.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Please fill in all required fields!");
			alert.setContentText("ID");
			alert.showAndWait();
			return;
		}

		int homeworkid = Integer.parseInt(student_homeworkid.getText());

        student_homeworkid.setText("");

		try {
            api.getCommands(StudentCommands.class).applyHomework(homeworkid);
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void homeworkDelete() {
		if (student_homeworkid.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Please fill in all required fields!");
			alert.setContentText("ID");
			alert.showAndWait();
			return;
		}

		int homeworkid = Integer.parseInt(student_homeworkid.getText());

        student_homeworkid.setText("");

		try {
            api.getCommands(StudentCommands.class).abandonHomework(homeworkid);
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void homeworkUpload() {
        if (student_homeworkid.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Please fill in all required fields!");
            alert.setContentText("ID");
            alert.showAndWait();
            return;
        }

        int homeworkid = Integer.parseInt(student_homeworkid.getText());

        try {
            api.getCommands(StudentCommands.class).uploadHomework(
                    homeworkid, file, file.getName());
        } catch (ClientException e) {
            e.printStackTrace();
        }
	}

	@FXML
	private void homeworkChooseFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Upload File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PDF", "*.pdf")
        );
        Stage stage = (Stage) filechoose.getScene().getWindow();
        file = fileChooser.showOpenDialog(stage);
		if (file != null) {
			System.out.println(file.getAbsolutePath() + file.getName());
		}
	}

	@FXML
    @SuppressWarnings("unchecked")
	private void solutionGet() {
		if (student_solutionid.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Please fill in all required fields!");
			alert.setContentText("ID");
			alert.showAndWait();
			return;
		}

		int solutionid = Integer.parseInt(student_solutionid.getText());

        student_solutionid.setText("");

		List<Solution> solutionList = new ArrayList<Solution>();
		try {
		    solutionList.add(api.getCommands(StudentCommands.class).getSolution(solutionid));
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
    @SuppressWarnings("unchecked")
	private void solutionList() {
		if (student_solutionid.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Please fill in all required fields!");
			alert.setContentText("ID");
			alert.showAndWait();
			return;
		}

		int solutionid = Integer.parseInt(student_solutionid.getText());

        student_solutionid.setText("");

		List<Solution> solutionList = new ArrayList<>();
		try {
		    solutionList = api.getCommands(StudentCommands.class).getSolutions(solutionid);
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