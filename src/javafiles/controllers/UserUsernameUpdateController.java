package javafiles.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafiles.models.DBUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UserUsernameUpdateController implements Initializable {
    @FXML
    private Label label_username;
    @FXML
    private Label label_name;
    @FXML
    private TextField tf_new_username;
    @FXML
    private TextField tf_password;
    @FXML
    private Button button_update;
    @FXML
    private Button button_back;
    @FXML
    Label user_id;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        button_update.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (!tf_new_username.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty()) {
                    DBUtils.updateUserUsername(event, label_username.getText(), tf_new_username.getText(),
                            tf_password.getText(), label_name.getText());
                    DBUtils.changeScene(event, "/resources/fxml/user-profile.fxml", "Profile", user_id.getText());
                } else {
                    System.out.println("Please fill in all information");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all information to update!");
                    alert.show();
                }
            }

        });

        button_back.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "/resources/fxml/user-profile.fxml", "Profile", user_id.getText());
            }

        });
    }

    public void setUserInformation(String id) {
        user_id.setText(id);
        label_username.setText(DBUtils.getUsername(id));
        label_name.setText(DBUtils.getUserName(id));
    }
}
