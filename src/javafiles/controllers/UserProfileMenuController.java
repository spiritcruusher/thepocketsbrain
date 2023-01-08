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

public class UserProfileMenuController implements Initializable {
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
    private Label label_username_menu;
    @FXML
    private Button button_logout;
    @FXML
    private Label user_id;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        button_update_username.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "/resources/fxml/username-update.fxml", "Username Update", null);
            }

        });
        button_update_name.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "/resources/fxml/name-update.fxml", "Name Update", user_id.getText());
            }

        });
        button_update_password.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "/resources/fxml/password-update.fxml", "Password Update",
                        user_id.getText());
            }

        });

        button_menu.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "/resources/fxml/user-profile.fxml", "Profile", user_id.getText());
            }
        }));

        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "/resources/fxml/application.fxml", "Home", null);
            }
        });

    }

    public void setUserInformation(String id) {
        user_id.setText(id);
        label_username.setText(DBUtils.getUsername(id));
        label_name.setText(DBUtils.getUserName(id));
    }
}
