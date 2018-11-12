package application;

import java.io.IOException;

import client.Api;
import client.ClientException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SignInController {
	@FXML
	private TextField usernametextfield;
	@FXML
	private PasswordField passwordfield;
	@FXML
	private Label errortextlabel;
	@FXML
	private Button signinbtn;
	@FXML
	private AnchorPane pane;
	@FXML
	private ToggleGroup signin = new ToggleGroup();
	@FXML
	private RadioButton adminradiobutton = new RadioButton();	
	@FXML
	private RadioButton teacherradiobutton = new RadioButton();	
	@FXML
	private RadioButton studentradiobutton = new RadioButton();
	
	private String toogleGroupValue="";

	public SignInController(){
	}

	@FXML
	private void initialize(){
	}

	String serverIp = "localhost";
	int serverPort = 5000;

	String username = "asd";
	String password = "asd";

	public Api api = new Api(serverIp, serverPort);
	
	
	

	@FXML
	private int signIn() throws IOException{ 
		adminradiobutton.setToggleGroup(signin);
		teacherradiobutton.setToggleGroup(signin);
		studentradiobutton.setToggleGroup(signin);
		
		if(usernametextfield.getText().equals("")) {
			errortextlabel.setText(errortextlabel.getText() + "\nUsername field is empty!");
			return -1;
		}
		
		if(passwordfield.getText().equals("")) {
			errortextlabel.setText(errortextlabel.getText() + "\nPassword field is empty!");
			return -1;
		}
		
		RadioButton selectedRadioButton = (RadioButton) signin.getSelectedToggle();
		if(selectedRadioButton != null) {
			toogleGroupValue = selectedRadioButton.getText();

			try {
				api.getToken(usernametextfield.getText(), passwordfield.getText());
			} catch (ClientException e) {
				e.printStackTrace();
				errortextlabel.setText(errortextlabel.getText() + "\nIncorrect username or password!");
				return -1;
			}

			if(toogleGroupValue.equals("Administrator")){
				loadAdministratorPane();
			}
			else if (toogleGroupValue.equals("Teacher")){
				loadTeacherPane();
			}
			else if(toogleGroupValue.equals("Student")){
				loadStudentPane();
			}
		} else {
			errortextlabel.setText(errortextlabel.getText() + "\nPlease choose the role!");
			return -1;
		}
		

		
		return 0;
	}
	
	@FXML
	private void loadAdministratorPane() throws IOException{
		pane = FXMLLoader.load(getClass().getClassLoader().getResource("Administrator.fxml"));
		Stage stage = (Stage) signinbtn.getScene().getWindow();
		Scene scene = new Scene(pane,600,600);
		stage.setScene(scene);
	}
	@FXML
	private void loadTeacherPane() throws IOException{
		pane = FXMLLoader.load(getClass().getClassLoader().getResource("Teacher.fxml"));
		Stage stage = (Stage) signinbtn.getScene().getWindow();
		Scene scene = new Scene(pane,600,600);
		stage.setScene(scene);
	}
	@FXML
	private void loadStudentPane() throws IOException{
		pane = FXMLLoader.load(getClass().getClassLoader().getResource("Student.fxml"));
		Stage stage = (Stage) signinbtn.getScene().getWindow();
		Scene scene = new Scene(pane,600,600);
		stage.setScene(scene);
	}
}
