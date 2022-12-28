package javafiles.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafiles.models.DBUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UserNameUpdateController implements Initializable {
    @FXML
    private Label label_username;
    @FXML
    private Label label_name;
    @FXML
    private TextField tf_new_name;
    @FXML
    private TextField tf_password;
    @FXML
    private Button button_update;
    @FXML
    private Button button_back;

    public void initialize(URL arg0, ResourceBundle arg1) {
        button_update.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                DBUtils.updateUserName(event, label_username.getText(), tf_new_name.getText(), tf_password.getText());
                DBUtils.changeScene(event, "/resources/fxml/user-profile.fxml", "Profile", label_username.getText(),
                        label_name.getText());
            }

        });

        button_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "/resources/fxml/user-profile.fxml", "Profile", label_username.getText(),
                        label_name.getText());
            }
        });
    }

    public void setUserInformation(String username, String name) {
        label_username.setText(username);
        label_name.setText(name);
    }
}
