package javafiles.controllers;

import javafiles.models.DBUtils;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UserPasswordUpdateController implements Initializable {
    @FXML
    private Button button_update;
    @FXML
    private Label label_name;
    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_old_password;
    @FXML
    private TextField tf_new_password;
    @FXML
    private TextField tf_new_password_repeat;
    @FXML
    private Label label_username;
    @FXML
    private Button button_back;
    @FXML
    private Label user_id;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        button_update.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!tf_old_password.getText().trim().isEmpty() && !tf_new_password.getText().trim().isEmpty()
                        && !tf_new_password_repeat.getText().trim().isEmpty()) {
                    if (tf_new_password_repeat.getText().equals(tf_new_password.getText())) {
                        DBUtils.updateUserPassword(event, user_id.getText(), tf_old_password.getText(),
                                tf_new_password.getText());
                        DBUtils.changeScene(event, "/resources/fxml/user-profile.fxml", "Profile", user_id.getText());
                    } else {
                        System.out.println("Please enter identical passwords");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Please enter identical passwords!");
                        alert.show();
                    }
                } else {
                    System.out.println("Please fill in all information");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all information to sign up!");
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
