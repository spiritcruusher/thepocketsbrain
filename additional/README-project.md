## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

## src Folder Structure

- `javafiles`: the folder to maintain java files
- `recources`: the folder to maintain the resources used by java files

## javafiles Folder Structure

- `application`: the package containing the main java file
- `controllers`: the package containing the controllers
- `models`: the package containing the models

## resources Folder Structure

- `fxml`: the folder to maintain fxml files
- `icons`: the folder to maintain the icons used by fxml files

## application Package Structure

It contains only one file that is the main file `App.java`

## controllers Package Structure

- `HomeController.java`: the controller for `application.fxml`
- `HomeMenuController.java`: the controller for `application-menu.fxml`
- `LoggedInController.java`: the controller for `logged-in.fxml`
- `LogInController.java`: the controller for `log-in.fxml`
- `SignUpController.java`: the controller for `sign-up.fxml`

## models Package Structure

It contains only one file that is `DBUtils.java`, its main role is to handle anything related to the database
