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

    // CHANGE SCENE

    public static void changeScene(ActionEvent event, String fxmlFile, String title, String id) {
        Parent root = null;

        try {
            FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
            root = loader.load();
            if (fxmlFile == "/resources/fxml/user-profile.fxml") {
                UserProfileController userProfileController = loader.getController();
                userProfileController.setUserInformation(id);
            } else {
                if (fxmlFile == "/resources/fxml/username-update.fxml") {
                    UserUsernameUpdateController userUsernameUpdateController = loader.getController();
                    userUsernameUpdateController.setUserInformation(id);
                } else {
                    if (fxmlFile == "/resources/fxml/password-update.fxml") {
                        UserPasswordUpdateController userPasswordUpdateController = loader.getController();
                        userPasswordUpdateController.setUserInformation(id);
                    } else {
                        if (fxmlFile == "/resources/fxml/name-update.fxml") {
                            UserNameUpdateController userNameUpdateController = loader.getController();
                            userNameUpdateController.setUserInformation(id);
                        } else {
                            if (fxmlFile == "/resources/fxml/user-profile-menu.fxml") {
                                UserProfileMenuController userProfileMenuController = loader.getController();
                                userProfileMenuController.setUserInformation(id);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();

    }

    // SIGN UP

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
                changeScene(event, "/resources/fxml/log-in.fxml", "Profile", null);

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

    // LOG IN

    public static void logInUser(ActionEvent event, String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement userId = null;
        ResultSet resultSet = null;
        ResultSet idResultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql_ihm", "root", "mysql123");
            preparedStatement = connection.prepareStatement("SELECT password FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            userId = connection.prepareStatement("SELECT user_id FROM users WHERE username = ?");
            userId.setString(1, username);
            idResultSet = userId.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found in the database!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect!");
                alert.show();
            } else {
                while (resultSet.next() && idResultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");
                    String retrievedId = idResultSet.getString("user_id");
                    if (retrievedPassword.equals(password)) {
                        changeScene(event, "/resources/fxml/user-profile.fxml", "Profile", retrievedId);
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

    // UPDATE PASSWORD

    public static void updateUserPassword(ActionEvent event, String user_id, String oldPassword, String newPassword) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        ResultSet resultSet = null;
        PreparedStatement psCheckPassword = null;
        ResultSet checkResultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql_ihm", "root", "mysql123");
            psCheckPassword = connection.prepareStatement("SELECT password FROM users WHERE user_id = ?");
            psCheckPassword.setString(1, user_id);
            checkResultSet = psCheckPassword.executeQuery();
            if (!checkResultSet.isBeforeFirst()) {
                System.out.println("User not found in the database!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect!");
                alert.show();
            } else {
                while (checkResultSet.next()) {
                    String retrievedPassword = checkResultSet.getString("password");
                    if (retrievedPassword.equals(oldPassword)) {
                        psInsert = connection.prepareStatement("UPDATE users SET password = ? WHERE user_id = ?");
                        psInsert.setString(1, newPassword);
                        psInsert.setString(2, user_id);
                        psInsert.executeUpdate();

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

    // UPDATE USERNAME

    public static void updateUserUsername(ActionEvent event, String user_id, String newUsername, String password) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql_ihm", "root", "mysql123");
            preparedStatement = connection.prepareStatement("SELECT password FROM users WHERE user_id = ?");
            preparedStatement.setString(1, user_id);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found in the database!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect!");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");
                    if (retrievedPassword.equals(password)) {
                        psInsert = connection.prepareStatement("UPDATE users SET username = ? WHERE user_id = ?");
                        psInsert.setString(1, newUsername);
                        psInsert.setString(2, user_id);
                        psInsert.executeUpdate();
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

    public static void updateUserName(ActionEvent event, String user_id, String newName, String password) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql_ihm", "root", "mysql123");
            preparedStatement = connection.prepareStatement("SELECT password FROM users WHERE user_id = ?");
            preparedStatement.setString(1, user_id);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found in the database!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect!");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");
                    if (retrievedPassword.equals(password)) {
                        psInsert = connection.prepareStatement("UPDATE users SET name = ? WHERE user_id = ?");
                        psInsert.setString(1, newName);
                        psInsert.setString(2, user_id);
                        psInsert.executeUpdate();
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

    public static String getUsername(String id) {
        Connection connection = null;
        PreparedStatement psGet = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql_ihm", "root", "mysql123");
            psGet = connection.prepareStatement("SELECT username FROM users WHERE user_id = ?");
            psGet.setString(1, id);
            resultSet = psGet.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found in the database!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect!");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String username = resultSet.getString("username");
                    id = username;
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
        if (psGet != null) {
            try {
                psGet.close();
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
        return id;
    }

    public static String getUserName(String id) {
        Connection connection = null;
        PreparedStatement psGet = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql_ihm", "root", "mysql123");
            psGet = connection.prepareStatement("SELECT name FROM users WHERE user_id = ?");
            psGet.setString(1, id);
            resultSet = psGet.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found in the database!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect!");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    id = name;
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
        if (psGet != null) {
            try {
                psGet.close();
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
        return id;
    }
}