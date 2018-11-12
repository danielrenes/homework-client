package application;

import java.io.IOException;

import java.io.File;
import javafx.stage.FileChooser;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class StudentController {

	@FXML TextField student_coursecourse;
	@FXML TextField student_homeworkhomework;
	@FXML Button filechoose;
	@FXML TableView studenttable;
	
	final FileChooser fileChooser = new FileChooser();
	
	@FXML
	private void courseList() throws IOException{
		
	}
	
	@FXML
	private void courseApply() throws IOException{
		String course = student_coursecourse.getText();
	}
	
	@FXML
	private void courseDelete() throws IOException{
		String course = student_coursecourse.getText();
	}
	
	@FXML
	private void homeworkList() throws IOException{
		
	}
	
	@FXML
	private void homeworkApply() throws IOException{
		String homework = student_homeworkhomework.getText();
	}
	
	@FXML
	private void homeworkDelete() throws IOException{
		String homework = student_homeworkhomework.getText();
	}
	
	@FXML
	private void homeworkUpload() throws IOException{
		
	}
	
	@FXML
	private void homeworkChooseFile() throws IOException{
		Stage stage = (Stage) filechoose.getScene().getWindow();
	
		/*FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		Stage stage = (Stage) filechoose.getScene().getWindow();
		fileChooser.showOpenDialog(stage);*/
		File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            System.out.println(file.getAbsolutePath() + file.getName());
        }
            
	}
	
	@FXML
	private void solutionList() throws IOException{
		
	}
	
	@FXML
	private void solutionGet() throws IOException{
		
	}
	

}
