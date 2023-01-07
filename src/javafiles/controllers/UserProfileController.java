package javafiles.controllers;

import javafiles.models.DBUtils;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class UserProfileController implements Initializable {
    @FXML
    private Button button_menu;
    @FXML
    private Button button_update_name;
    @FXML
    private Button button_update_username;
    @FXML
    private Button button_update_password;
    @FXML
    private Label label_name;
    @FXML
    private Label label_username;
    @FXML
    private Label user_id;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        button_update_username.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "/resources/fxml/username-update.fxml", "Username Update",
                        label_username.getText(), label_name.getText(), user_id.getText());
            }

        });
        button_update_name.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "/resources/fxml/name-update.fxml", "Name Update", label_username.getText(),
                        label_name.getText(), user_id.getText());
            }

        });
        button_update_password.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "/resources/fxml/password-update.fxml", "Password Update",
                        label_username.getText(), label_name.getText(), user_id.getText());
            }

        });

        button_menu.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "/resources/fxml/user-profile-menu.fxml", "Profile",
                        label_username.getText(),
                        label_name.getText(), user_id.getText());
            }
        }));
    }

    public void setUserInformation(String username, String name, String id) {
        label_username.setText(username);
        label_name.setText(name);
        user_id.setText(id);
    }
}
