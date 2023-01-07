package javafiles.models;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafiles.controllers.UserNameUpdateController;
import javafiles.controllers.UserPasswordUpdateController;
import javafiles.controllers.UserProfileController;
import javafiles.controllers.UserProfileMenuController;
import javafiles.controllers.UserUsernameUpdateController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.Node;

public class DBUtils {

    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username, String name,
            String id) {
        Parent root = null;

        if (username != null) {
            try {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                if (fxmlFile == "/resources/fxml/user-profile.fxml") {
                    UserProfileController userProfileController = loader.getController();
                    userProfileController.setUserInformation(username, name, id);
                } else {
                    if (fxmlFile == "/resources/fxml/username-update.fxml") {
                        UserUsernameUpdateController userUsernameUpdateController = loader.getController();
                        userUsernameUpdateController.setUserInformation(username, name, id);
                    } else {
                        if (fxmlFile == "/resources/fxml/password-update.fxml") {
                            UserPasswordUpdateController userPasswordUpdateController = loader.getController();
                            userPasswordUpdateController.setUserInformation(username, name, id);
                        } else {
                            if (fxmlFile == "/resources/fxml/name-update.fxml") {
                                UserNameUpdateController userNameUpdateController = loader.getController();
                                userNameUpdateController.setUserInformation(username, name, id);
                            } else {
                                if (fxmlFile == "/resources/fxml/user-profile-menu.fxml") {
                                    UserProfileMenuController userProfileMenuController = loader.getController();
                                    userProfileMenuController.setUserInformation(username, name, id);
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();

    }

    public static void signUpUser(ActionEvent event, String username, String password, String name) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql_ihm", "root", "mysql123");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("User already exists !");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot user this username!");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO users (username, password, name) VALUES (?, ?, ?)");
                psInsert.setString(1, username);
                psInsert.setString(2, password);
                psInsert.setString(3, name);
                psInsert.executeUpdate();

                changeScene(event, "/resources/fxml/user-profile.fxml", "Profile", username, name, null);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        if (psCheckUserExists != null) {
            try {
                psCheckUserExists.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (psInsert != null) {
            try {
                psInsert.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void logInUser(ActionEvent event, String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement userName = null;
        PreparedStatement userId = null;
        ResultSet resultSet = null;
        ResultSet userNameResultSet = null;
        ResultSet idResultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql_ihm", "root", "mysql123");
            preparedStatement = connection.prepareStatement("SELECT password FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            userName = connection.prepareStatement("SELECT name FROM users WHERE username = ?");
            userName.setString(1, username);
            userNameResultSet = userName.executeQuery();
            userId = connection.prepareStatement("SELECT user_id FROM users WHERE username = ?");
            userId.setString(1, username);
            idResultSet = userId.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found in the database!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect!");
                alert.show();
            } else {
                while (resultSet.next() && userNameResultSet.next() && idResultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");
                    String retrievedUserName = userNameResultSet.getString("name");
                    String retrievedId = idResultSet.getString("user_id");
                    if (retrievedPassword.equals(password)) {
                        changeScene(event, "/resources/fxml/user-profile.fxml", "Profile", username, retrievedUserName,
                                retrievedId);
                    } else {
                        System.out.println("Password did not match!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Provided credentials are incorrect!");
                        alert.show();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static void updateUserPassword(ActionEvent event, String username, String oldPassword, String newPassword) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql_ihm", "root", "mysql123");
            psInsert = connection.prepareStatement("UPDATE users SET password = ? WHERE username = ?");
            psInsert.setString(1, newPassword);
            psInsert.setString(2, username);
            psInsert.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        if (psInsert != null) {
            try {
                psInsert.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateUserUsername(ActionEvent event, String username, String newUsername, String password,
            String name) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;
        PreparedStatement userId = null;
        ResultSet idResultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql_ihm", "root", "mysql123");
            psCheckUserExists = connection.prepareStatement("SELECT username FROM users WHERE username = ?");
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("User already exists !");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this username!");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("UPDATE users SET username = ? WHERE username = ?");
                psInsert.setString(1, newUsername);
                psInsert.setString(2, username);
                psInsert.executeUpdate();
                userId = connection.prepareStatement("SELECT user_id FROM users WHERE username = ?");
                userId.setString(1, username);
                idResultSet = userId.executeQuery();
                String retrievedId = idResultSet.getString("user_id");
                changeScene(event, "/resources/fxml/username-update.fxml", "Profile", username, null,
                        retrievedId);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        if (psCheckUserExists != null) {
            try {
                psCheckUserExists.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (psInsert != null) {
            try {
                psInsert.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateUserName(ActionEvent event, String username, String name, String password) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        ResultSet resultSet = null;
        PreparedStatement psGet = null;
        ResultSet resultSet2 = null;
        PreparedStatement userId = null;
        ResultSet idResultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql_ihm", "root", "mysql123");
            psInsert = connection.prepareStatement("UPDATE users SET name = ? WHERE username = ?");
            psInsert.setString(1, name);
            psInsert.setString(2, username);
            psInsert.executeUpdate();
            psGet = connection.prepareStatement("SELECT name FROM users WHERE username = ?");
            psGet.setString(1, username);
            resultSet2 = psGet.executeQuery();
            userId = connection.prepareStatement("SELECT user_id FROM users WHERE username = ?");
            userId.setString(1, username);
            idResultSet = userId.executeQuery();
            String retrievedId = idResultSet.getString("user_id");
            changeScene(event, "/resources/fxml/name-update.fxml", "Profile", username, null,
                    retrievedId);
            if (!resultSet2.isBeforeFirst()) {
                System.out.println("User not found in the database!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect!");
                alert.show();
            } else {
                while (resultSet2.next()) {
                    String retrievedName = resultSet2.getString("name");
                    UserNameUpdateController userNameUpdateController = new UserNameUpdateController();
                    userNameUpdateController.setUserName(retrievedName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        if (psInsert != null) {
            try {
                psInsert.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
