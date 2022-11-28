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

public class UserProfileController implements Initializable {
    @FXML
    private Button button_update;
    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_password;
    @FXML
    private TextField tf_password_repeat;
    @FXML
    private Label label_username;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        button_update.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!tf_username.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty()) {
                    if (tf_password_repeat.getText().equals(tf_password.getText())) {
                        DBUtils.updateUser(event, tf_username.getText(), label_username.getText(),
                                tf_password.getText());
                        label_username.setText(tf_username.getText());
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
    }

    public void setUserInformation(String username) {
        label_username.setText(username);
    }

}
