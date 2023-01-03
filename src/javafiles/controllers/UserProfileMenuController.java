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
    private Button button_managment;
    @FXML
    private Button button_statistics;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        button_update_username.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "/resources/fxml/username-update.fxml", "Username Update",
                        label_username.getText(), label_name.getText());
            }

        });
        button_update_name.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "/resources/fxml/name-update.fxml", "Name Update", label_username.getText(),
                        label_name.getText());
            }

        });
        button_update_password.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "/resources/fxml/password-update.fxml", "Password Update",
                        label_username.getText(), label_name.getText());
            }

        });

        button_menu.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "/resources/fxml/user-profile.fxml", "Profile", label_username.getText(),
                        label_name.getText());
            }
        }));

        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "/resources/fxml/application.fxml", "Home", null, null);
            }
        });

    }

    public void setUserInformation(String username, String name) {
        label_username.setText(username);
        label_name.setText(name);
        label_username_menu.setText(username);
    }
}
