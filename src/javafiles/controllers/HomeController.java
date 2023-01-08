package javafiles.controllers;

import javafiles.models.DBUtils;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class HomeController implements Initializable {

    @FXML
    private Button button_login;
    @FXML
    private Button button_signup;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        button_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "/resources/fxml/log-in.fxml", "Log In!", null);
            }
        });

        button_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "/resources/fxml/sign-up.fxml", "Sign Up!", null);
            }
        });

    }

}
