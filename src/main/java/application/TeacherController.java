package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class TeacherController {

	@FXML TextField teacher_coursename;
	@FXML TextField teacher_coursedesc;
	@FXML TextField teacher_groupstudents;
	@FXML TextField teacher_homeworkname;
	@FXML TextField teacher_homeworkdescription;
	@FXML TextField teacher_homeworkdeadline;
	@FXML TextField teacher_homeworkheadcount;
	@FXML TextField teacher_solutionstatus;
	@FXML TextField teacher_solutionnotes;
	@FXML TableView teachertable;

	
	@FXML
	private void courseList() throws IOException{
		
	}
	
	@FXML
	private void courseCreate() throws IOException{
		String course = teacher_coursename.getText();
		String description = teacher_coursedesc.getText();
	}
	
	@FXML
	private void courseDelete() throws IOException{
		String course = teacher_coursename.getText();
		String description = teacher_coursedesc.getText();
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
		
	}
	
	@FXML
	private void homeworkCreate() throws IOException{
		String name = teacher_homeworkname.getText();
		String description = teacher_homeworkdescription.getText();
		String Deadline = teacher_homeworkdeadline.getText();
		String headcount = teacher_homeworkheadcount.getText();
	}
	
	@FXML
	private void homeworkDelete() throws IOException{
		String name = teacher_homeworkname.getText();
		String description = teacher_homeworkdescription.getText();
		String Deadline = teacher_homeworkdeadline.getText();
		String headcount = teacher_homeworkheadcount.getText();
	}
	
	@FXML
	private void homeworkModify() throws IOException{
		String name = teacher_homeworkname.getText();
		String description = teacher_homeworkdescription.getText();
		String Deadline = teacher_homeworkdeadline.getText();
		String headcount = teacher_homeworkheadcount.getText();
	}
	
	@FXML
	private void studentList() throws IOException{
		
	}
	
	@FXML
	private void solutionList() throws IOException{
		
	}
	
	@FXML
	private void solutionDelete() throws IOException{
		String status = teacher_solutionstatus.getText();
		String notes = teacher_solutionnotes.getText();
	}
	
	@FXML
	private void solutionModify() throws IOException{
		String status = teacher_solutionstatus.getText();
		String notes = teacher_solutionnotes.getText();
	}
	

}
