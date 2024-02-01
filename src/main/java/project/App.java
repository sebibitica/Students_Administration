package project;

import project.LogicClasses.StudentDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The main class for the JavaFX application.
 */
public class App extends Application {

    /**
     * The entry point for the JavaFX application.
     *
     * @param stage The primary stage for the application.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    @Override
    public void start(Stage stage) throws IOException {
        try {
            // Establish database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentsapp", "root", "Bitica890*!");
            StudentDAO studentDAO = new StudentDAO(connection);

            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("hello-view.fxml"));

            // Set up the scene
            Scene scene = new Scene(fxmlLoader.load(), 1200, 800);

            // Set the controller
            AppController appController = fxmlLoader.getController();
            appController.initialize(studentDAO);

            // Set the stage properties
            stage.setTitle("Students_Administration");
            stage.setScene(scene);
            stage.show();
        } catch (SQLException e) {
            System.err.println("Cannot connect to DB: " + e);
        }
    }

    /**
     * The main method to launch the JavaFX application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        launch();
    }
}