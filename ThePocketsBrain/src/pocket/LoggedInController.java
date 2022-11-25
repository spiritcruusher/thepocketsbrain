package pocket;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class LoggedInController implements Initializable {
	@FXML
	private Button button_logout;
	@FXML
	private Label label_logout;
	@FXML
	private Label label_welcome;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		button_logout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				DBUtils.changeScene(event, "log-in.fxml", "Log in!", null);
			}
		});
		
	}
	
	public void setUserInformation(String username) {
		label_welcome.setText("Welcome " + username + " !");
	}
	
}
